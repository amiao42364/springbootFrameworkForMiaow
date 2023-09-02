package cn.miaow.framework.controller.system;

import cn.miaow.framework.aspectj.annotation.Log;
import cn.miaow.framework.constant.UserConstants;
import cn.miaow.framework.constant.enums.BusinessType;
import cn.miaow.framework.entity.system.SysDept;
import cn.miaow.framework.model.AjaxResult;
import cn.miaow.framework.model.BaseController;
import cn.miaow.framework.service.system.ISysDeptService;
import cn.miaow.framework.util.StringUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 部门信息
 *
 * @author miaow
 */
@RestController
@RequestMapping("/system/dept" )
public class SysDeptController extends BaseController {
    private final ISysDeptService deptService;

    public SysDeptController(ISysDeptService deptService) {
        this.deptService = deptService;
    }

    /**
     * 获取部门列表
     */
    @PreAuthorize("@ss.hasPermission('system:dept:list')" )
    @GetMapping("/list" )
    public AjaxResult list(SysDept dept) {
        List<SysDept> deptList = deptService.selectDeptList(dept);
        return success(deptList);
    }

    /**
     * 查询部门列表（排除节点）
     */
    @PreAuthorize("@ss.hasPermission('system:dept:list')" )
    @GetMapping("/list/exclude/{deptId}" )
    public AjaxResult excludeChild(@PathVariable(value = "deptId" , required = false) Long deptId) {
        List<SysDept> deptList = deptService.selectDeptList(new SysDept());
        deptList.removeIf(d -> d.getDeptId().intValue() == deptId || ArrayUtils.contains(StringUtils.split(d.getAncestors(), "," ), deptId + "" ));
        return success(deptList);
    }

    /**
     * 根据部门编号获取详细信息
     */
    @PreAuthorize("@ss.hasPermission('system:dept:query')" )
    @GetMapping(value = "/{deptId}" )
    public AjaxResult getInfo(@PathVariable Long deptId) {
        deptService.checkDeptDataScope(deptId);
        return success(deptService.selectDeptById(deptId));
    }

    /**
     * 新增部门
     */
    @PreAuthorize("@ss.hasPermission('system:dept:add')" )
    @Log(title = "部门管理" , businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody SysDept dept) {
        if (deptService.checkDeptNameNotUnique(dept)) {
            return error("新增部门'" + dept.getDeptName() + "'失败，部门名称已存在" );
        }
        dept.setCreateBy(getUsername());
        return toAjax(deptService.insertDept(dept));
    }

    /**
     * 修改部门
     */
    @PreAuthorize("@ss.hasPermission('system:dept:edit')" )
    @Log(title = "部门管理" , businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody SysDept dept) {
        Long deptId = dept.getDeptId();
        deptService.checkDeptDataScope(deptId);
        if (deptService.checkDeptNameNotUnique(dept)) {
            return error("修改部门'" + dept.getDeptName() + "'失败，部门名称已存在" );
        } else if (dept.getParentId().equals(deptId)) {
            return error("修改部门'" + dept.getDeptName() + "'失败，上级部门不能是自己" );
        } else if (StringUtils.equals(UserConstants.DEPT_DISABLE, dept.getStatus()) && deptService.selectNormalChildrenDeptById(deptId) > 0) {
            return error("该部门包含未停用的子部门！" );
        }
        dept.setUpdateBy(getUsername());
        return toAjax(deptService.updateDept(dept));
    }

    /**
     * 删除部门
     */
    @PreAuthorize("@ss.hasPermission('system:dept:remove')" )
    @Log(title = "部门管理" , businessType = BusinessType.DELETE)
    @DeleteMapping("/{deptId}" )
    public AjaxResult remove(@PathVariable Long deptId) {
        if (deptService.hasChildByDeptId(deptId)) {
            return warn("存在下级部门,不允许删除" );
        }
        if (deptService.checkDeptExistUser(deptId)) {
            return warn("部门存在用户,不允许删除" );
        }
        deptService.checkDeptDataScope(deptId);
        return toAjax(deptService.deleteDeptById(deptId));
    }
}
