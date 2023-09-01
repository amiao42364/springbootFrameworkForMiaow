package cn.miaow.framework.mapper.system;

import cn.miaow.framework.entity.system.SysOperationLog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * 操作日志 数据层
 */
public interface SysOperationLogMapper  extends BaseMapper<SysOperationLog> {
    /**
     * 新增操作日志
     *
     * @param operationLog 操作日志对象
     */
    public void insertOperationLog(SysOperationLog operationLog);

    /**
     * 查询系统操作日志集合
     *
     * @param operationLog 操作日志对象
     * @return 操作日志集合
     */
    public List<SysOperationLog> selectOperationLogList(SysOperationLog operationLog);

    /**
     * 批量删除系统操作日志
     *
     * @param operationIds 需要删除的操作日志ID
     * @return 结果
     */
    public int deleteOperationLogByIds(Long[] operationIds);

    /**
     * 查询操作日志详细
     *
     * @param operationId 操作ID
     * @return 操作日志对象
     */
    public SysOperationLog selectOperationLogById(Long operationId);

    /**
     * 清空操作日志
     */
    public void cleanOperationLog();
}
