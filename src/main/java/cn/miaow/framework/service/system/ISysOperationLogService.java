package cn.miaow.framework.service.system;

import cn.miaow.framework.entity.system.SysOperationLog;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 操作日志 服务层
 *
 * @author miaow
 */
public interface ISysOperationLogService  extends IService<SysOperationLog> {
    /**
     * 新增操作日志
     *
     * @param operLog 操作日志对象
     */
    public void insertOperlog(SysOperationLog operLog);

    /**
     * 查询系统操作日志集合
     *
     * @param operLog 操作日志对象
     * @return 操作日志集合
     */
    public List<SysOperationLog> selectOperLogList(SysOperationLog operLog);

    /**
     * 批量删除系统操作日志
     *
     * @param operIds 需要删除的操作日志ID
     * @return 结果
     */
    public int deleteOperLogByIds(Long[] operIds);

    /**
     * 查询操作日志详细
     *
     * @param operId 操作ID
     * @return 操作日志对象
     */
    public SysOperationLog selectOperLogById(Long operId);

    /**
     * 清空操作日志
     */
    public void cleanOperLog();
}
