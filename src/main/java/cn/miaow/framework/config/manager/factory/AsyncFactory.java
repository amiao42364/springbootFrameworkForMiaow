package cn.miaow.framework.config.manager.factory;

import cn.hutool.http.useragent.UserAgent;
import cn.hutool.http.useragent.UserAgentUtil;
import cn.miaow.framework.constant.Constants;
import cn.miaow.framework.entity.system.SysLoginInfo;
import cn.miaow.framework.entity.system.SysOperationLog;
import cn.miaow.framework.service.system.ISysLoginInfoService;
import cn.miaow.framework.service.system.ISysOperationLogService;
import cn.miaow.framework.util.LogUtils;
import cn.miaow.framework.util.ServletUtils;
import cn.miaow.framework.util.StringUtils;
import cn.miaow.framework.util.ip.AddressUtils;
import cn.miaow.framework.util.ip.IpUtils;
import cn.miaow.framework.util.spring.SpringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.TimerTask;

/**
 * 异步工厂（产生任务用）
 *
 * @author miaow
 */
public class AsyncFactory {
    private static final Logger sys_user_logger = LoggerFactory.getLogger("sys-user");

    /**
     * 记录登录信息
     *
     * @param username 用户名
     * @param status   状态
     * @param message  消息
     * @param args     列表
     * @return 任务task
     */
    public static TimerTask recordLogininfor(final String username, final String status, final String message,
                                             final Object... args) {
        final UserAgent userAgent = UserAgentUtil.parse(ServletUtils.getRequest().getHeader("User-Agent"));
        final String ip = IpUtils.getIpAddr();
        return new TimerTask() {
            @Override
            public void run() {
                String address = AddressUtils.getRealAddressByIP(ip);
                String s = LogUtils.getBlock(ip) +
                        address +
                        LogUtils.getBlock(username) +
                        LogUtils.getBlock(status) +
                        LogUtils.getBlock(message);
                // 打印信息到日志
                sys_user_logger.info(s, args);
                // 获取客户端操作系统
                String os = userAgent.getOs().getName();
                // 获取客户端浏览器
                String browser = userAgent.getBrowser().getName();
                // 封装对象
                SysLoginInfo logininfor = new SysLoginInfo();
                logininfor.setUserName(username);
                logininfor.setIpaddr(ip);
                logininfor.setLoginLocation(address);
                logininfor.setBrowser(browser);
                logininfor.setOs(os);
                logininfor.setMsg(message);
                // 日志状态
                if (StringUtils.equalsAny(status, Constants.LOGIN_SUCCESS, Constants.LOGOUT, Constants.REGISTER)) {
                    logininfor.setStatus(Constants.SUCCESS);
                } else if (Constants.LOGIN_FAIL.equals(status)) {
                    logininfor.setStatus(Constants.FAIL);
                }
                // 插入数据
                SpringUtils.getBean(ISysLoginInfoService.class).insertLogininfor(logininfor);
            }
        };
    }

    /**
     * 操作日志记录
     *
     * @param operationLog 操作日志信息
     * @return 任务task
     */
    public static TimerTask recordOperation(final SysOperationLog operationLog) {
        return new TimerTask() {
            @Override
            public void run() {
                // 远程查询操作地点
                operationLog.setOperationLocation(AddressUtils.getRealAddressByIP(operationLog.getOperationIp()));
                SpringUtils.getBean(ISysOperationLogService.class).insertOperlog(operationLog);
            }
        };
    }
}
