package cn.miaow.framework.mapper.system;

import cn.miaow.framework.entity.system.SysLoginInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * 系统访问日志情况信息 数据层
 */
public interface SysLoginInfoMapper extends BaseMapper<SysLoginInfo> {
    /**
     * 新增系统登录日志
     *
     * @param logininfor 访问日志对象
     */
    void insertLogininfor(SysLoginInfo logininfor);

    /**
     * 查询系统登录日志集合
     *
     * @param logininfor 访问日志对象
     * @return 登录记录集合
     */
    List<SysLoginInfo> selectLogininforList(SysLoginInfo logininfor);

    /**
     * 批量删除系统登录日志
     *
     * @param infoIds 需要删除的登录日志ID
     * @return 结果
     */
    int deleteLogininforByIds(Long[] infoIds);

    /**
     * 清空系统登录日志
     *
     * @return 结果
     */
    int cleanLogininfor();
}
