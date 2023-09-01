package cn.miaow.framework.service.system.impl;

import cn.miaow.framework.entity.system.SysOperationLog;
import cn.miaow.framework.mapper.system.SysOperationLogMapper;
import cn.miaow.framework.service.system.ISysOperationLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 操作日志 服务层处理
 */
@Service
public class SysOperationLogServiceImpl extends ServiceImpl<SysOperationLogMapper, SysOperationLog> implements ISysOperationLogService {

    @Autowired
    private SysOperationLogMapper operLogMapper;

    /**
     * 新增操作日志
     *
     * @param operLog 操作日志对象
     */
    @Override
    public void insertOperlog(SysOperationLog operLog) {
        operLogMapper.insertOperationLog(operLog);
    }

    /**
     * 查询系统操作日志集合
     *
     * @param operationLog 操作日志对象
     * @return 操作日志集合
     */
    @Override
    public List<SysOperationLog> selectOperLogList(SysOperationLog operationLog) {
        return operLogMapper.selectOperationLogList(operationLog);
    }

    /**
     * 批量删除系统操作日志
     *
     * @param operIds 需要删除的操作日志ID
     * @return 结果
     */
    @Override
    public int deleteOperLogByIds(Long[] operIds) {
        return operLogMapper.deleteOperationLogByIds(operIds);
    }

    /**
     * 查询操作日志详细
     *
     * @param operId 操作ID
     * @return 操作日志对象
     */
    @Override
    public SysOperationLog selectOperLogById(Long operId) {
        return operLogMapper.selectOperationLogById(operId);
    }

    /**
     * 清空操作日志
     */
    @Override
    public void cleanOperLog() {
        operLogMapper.cleanOperationLog();
    }
}
