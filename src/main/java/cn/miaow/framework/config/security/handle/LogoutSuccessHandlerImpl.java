package cn.miaow.framework.config.security.handle;

import cn.miaow.framework.config.manager.AsyncManager;
import cn.miaow.framework.config.manager.factory.AsyncFactory;
import cn.miaow.framework.config.security.impl.TokenService;
import cn.miaow.framework.constant.Constants;
import cn.miaow.framework.model.AjaxResult;
import cn.miaow.framework.model.LoginUser;
import cn.miaow.framework.util.ServletUtils;
import cn.miaow.framework.util.StringUtils;
import com.alibaba.fastjson2.JSON;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 自定义退出处理类 返回成功
 *
 * @author miaow
 */
@Configuration
public class LogoutSuccessHandlerImpl implements LogoutSuccessHandler {
    private final TokenService tokenService;

    public LogoutSuccessHandlerImpl(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    /**
     * 退出处理
     */
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        LoginUser loginUser = tokenService.getLoginUser(request);
        if (StringUtils.isNotNull(loginUser)) {
            String userName = loginUser.getUsername();
            // 删除用户缓存记录
            tokenService.delLoginUser(loginUser.getToken());
            // 记录用户退出日志
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(userName, Constants.LOGOUT, "退出成功"));
        }
        ServletUtils.renderString(response, JSON.toJSONString(AjaxResult.success("退出成功")));
    }
}
