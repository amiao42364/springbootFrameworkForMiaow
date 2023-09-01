package cn.miaow.framework.service.system;

import cn.miaow.framework.entity.system.SysLoginInfo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 系统访问日志情况信息 服务层
 *
 * @author miaow
 */
public interface ISysLoginInfoService extends IService<SysLoginInfo> {
    /**
     * 新增系统登录日志
     *
     * @param logininfor 访问日志对象
     */
    public void insertLogininfor(SysLoginInfo logininfor);

    /**
     * 查询系统登录日志集合
     *
     * @param logininfor 访问日志对象
     * @return 登录记录集合
     */
    public List<SysLoginInfo> selectLogininforList(SysLoginInfo logininfor);

    /**
     * 批量删除系统登录日志
     *
     * @param infoIds 需要删除的登录日志ID
     * @return 结果
     */
    public int deleteLogininforByIds(Long[] infoIds);

    /**
     * 清空系统登录日志
     */
    public void cleanLogininfor();
}
