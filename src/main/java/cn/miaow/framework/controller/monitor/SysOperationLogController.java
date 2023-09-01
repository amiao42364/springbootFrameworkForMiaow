package cn.miaow.framework.controller.monitor;

import cn.miaow.framework.aspectj.annotation.Log;
import cn.miaow.framework.constant.enums.BusinessType;
import cn.miaow.framework.entity.system.SysOperationLog;
import cn.miaow.framework.model.AjaxResult;
import cn.miaow.framework.model.BaseController;
import cn.miaow.framework.model.TableDataInfo;
import cn.miaow.framework.service.system.ISysOperationLogService;
import cn.miaow.framework.util.ExcelUtil;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 操作日志记录
 *
 * @author miaow
 */
@RestController
@RequestMapping("/monitor/operationLog" )
public class SysOperationLogController extends BaseController {
    private final ISysOperationLogService operationLogService;

    public SysOperationLogController(ISysOperationLogService operationLogService) {
        this.operationLogService = operationLogService;
    }

    @PreAuthorize("@ss.hasPermission('monitor:operlog:list')" )
    @GetMapping("/list" )
    public TableDataInfo list(SysOperationLog operationLog) {
        startPage();
        List<SysOperationLog> list = operationLogService.selectOperLogList(operationLog);
        return getDataTable(list);
    }

    @Log(title = "操作日志" , businessType = BusinessType.EXPORT)
    @PreAuthorize("@ss.hasPermission('monitor:operlog:export')" )
    @PostMapping("/export" )
    public void export(HttpServletResponse response, SysOperationLog operationLog) {
        List<SysOperationLog> list = operationLogService.selectOperLogList(operationLog);
        ExcelUtil<SysOperationLog> util = new ExcelUtil<>(SysOperationLog.class);
        util.exportExcel(response, list, "操作日志" );
    }

    @Log(title = "操作日志" , businessType = BusinessType.DELETE)
    @PreAuthorize("@ss.hasPermission('monitor:operlog:remove')" )
    @DeleteMapping("/{operationIds}" )
    public AjaxResult remove(@PathVariable Long[] operationIds) {
        return toAjax(operationLogService.deleteOperLogByIds(operationIds));
    }

    @Log(title = "操作日志" , businessType = BusinessType.CLEAN)
    @PreAuthorize("@ss.hasPermission('monitor:operlog:remove')" )
    @DeleteMapping("/clean" )
    public AjaxResult clean() {
        operationLogService.cleanOperLog();
        return success();
    }
}
