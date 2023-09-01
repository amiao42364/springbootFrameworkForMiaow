package cn.miaow.framework.controller.monitor;

import cn.miaow.framework.aspectj.annotation.Log;
import cn.miaow.framework.config.security.impl.SysPasswordService;
import cn.miaow.framework.constant.enums.BusinessType;
import cn.miaow.framework.entity.system.SysLoginInfo;
import cn.miaow.framework.model.AjaxResult;
import cn.miaow.framework.model.BaseController;
import cn.miaow.framework.model.TableDataInfo;
import cn.miaow.framework.service.system.ISysLoginInfoService;
import cn.miaow.framework.util.ExcelUtil;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 系统访问记录
 *
 * @author miaow
 */
@RestController
@RequestMapping("/monitor/logininfor" )
public class SysLogininforController extends BaseController {
    private final ISysLoginInfoService logininforService;

    private final SysPasswordService passwordService;

    public SysLogininforController(ISysLoginInfoService logininforService, SysPasswordService passwordService) {
        this.logininforService = logininforService;
        this.passwordService = passwordService;
    }


    @PreAuthorize("@ss.hasPermission('monitor:logininfor:list')" )
    @GetMapping("/list" )
    public TableDataInfo list(SysLoginInfo logininfor) {
        startPage();
        List<SysLoginInfo> list = logininforService.selectLogininforList(logininfor);
        return getDataTable(list);
    }

    @Log(title = "登录日志" , businessType = BusinessType.EXPORT)
    @PreAuthorize("@ss.hasPermission('monitor:logininfor:export')" )
    @PostMapping("/export" )
    public void export(HttpServletResponse response, SysLoginInfo logininfor) {
        List<SysLoginInfo> list = logininforService.selectLogininforList(logininfor);
        ExcelUtil<SysLoginInfo> util = new ExcelUtil<>(SysLoginInfo.class);
        util.exportExcel(response, list, "登录日志" );
    }

    @PreAuthorize("@ss.hasPermission('monitor:logininfor:remove')" )
    @Log(title = "登录日志" , businessType = BusinessType.DELETE)
    @DeleteMapping("/{infoIds}" )
    public AjaxResult remove(@PathVariable Long[] infoIds) {
        return toAjax(logininforService.deleteLogininforByIds(infoIds));
    }

    @PreAuthorize("@ss.hasPermission('monitor:logininfor:remove')" )
    @Log(title = "登录日志" , businessType = BusinessType.CLEAN)
    @DeleteMapping("/clean" )
    public AjaxResult clean() {
        logininforService.cleanLogininfor();
        return success();
    }

    @PreAuthorize("@ss.hasPermission('monitor:logininfor:unlock')" )
    @Log(title = "账户解锁" , businessType = BusinessType.OTHER)
    @GetMapping("/unlock/{userName}" )
    public AjaxResult unlock(@PathVariable("userName" ) String userName) {
        passwordService.clearLoginRecordCache(userName);
        return success();
    }
}
