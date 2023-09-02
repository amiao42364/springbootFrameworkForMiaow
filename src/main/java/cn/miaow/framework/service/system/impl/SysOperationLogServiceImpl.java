package cn.miaow.framework.service.system.impl;

import cn.miaow.framework.entity.system.SysOperationLog;
import cn.miaow.framework.mapper.system.SysOperationLogMapper;
import cn.miaow.framework.service.system.ISysOperationLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 操作日志 服务层处理
 */
@Service
public class SysOperationLogServiceImpl extends ServiceImpl<SysOperationLogMapper, SysOperationLog> implements ISysOperationLogService {

    private final SysOperationLogMapper operationLogMapper;

    public SysOperationLogServiceImpl(SysOperationLogMapper operationLogMapper) {
        this.operationLogMapper = operationLogMapper;
    }

    /**
     * 新增操作日志
     *
     * @param operationLog 操作日志对象
     */
    @Override
    public void insertOperationLog(SysOperationLog operationLog) {
        operationLogMapper.insertOperationLog(operationLog);
    }

    /**
     * 查询系统操作日志集合
     *
     * @param operationLog 操作日志对象
     * @return 操作日志集合
     */
    @Override
    public List<SysOperationLog> selectOperationLogList(SysOperationLog operationLog) {
        return operationLogMapper.selectOperationLogList(operationLog);
    }

    /**
     * 批量删除系统操作日志
     *
     * @param operationIds 需要删除的操作日志ID
     * @return 结果
     */
    @Override
    public int deleteOperationLogByIds(Long[] operationIds) {
        return operationLogMapper.deleteOperationLogByIds(operationIds);
    }

    /**
     * 查询操作日志详细
     *
     * @param operationId 操作ID
     * @return 操作日志对象
     */
    @Override
    public SysOperationLog selectOperationLogById(Long operationId) {
        return operationLogMapper.selectOperationLogById(operationId);
    }

    /**
     * 清空操作日志
     */
    @Override
    public void cleanOperationLog() {
        operationLogMapper.cleanOperationLog();
    }
}
