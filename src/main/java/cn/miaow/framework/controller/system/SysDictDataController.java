package cn.miaow.framework.controller.system;

import cn.miaow.framework.aspectj.annotation.Log;
import cn.miaow.framework.constant.enums.BusinessType;
import cn.miaow.framework.entity.system.SysDictData;
import cn.miaow.framework.model.AjaxResult;
import cn.miaow.framework.model.BaseController;
import cn.miaow.framework.model.TableDataInfo;
import cn.miaow.framework.service.system.ISysDictDataService;
import cn.miaow.framework.service.system.ISysDictTypeService;
import cn.miaow.framework.util.ExcelUtil;
import cn.miaow.framework.util.StringUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * 数据字典信息
 *
 * @author miaow
 */
@RestController
@RequestMapping("/system/dict/data" )
public class SysDictDataController extends BaseController {
    private final ISysDictDataService dictDataService;

    private final ISysDictTypeService dictTypeService;

    public SysDictDataController(ISysDictDataService dictDataService, ISysDictTypeService dictTypeService) {
        this.dictDataService = dictDataService;
        this.dictTypeService = dictTypeService;
    }

    @PreAuthorize("@ss.hasPermission('system:dict:list')" )
    @GetMapping("/list" )
    public TableDataInfo list(SysDictData dictData) {
        startPage();
        List<SysDictData> list = dictDataService.selectDictDataList(dictData);
        return getDataTable(list);
    }

    @Log(title = "字典数据" , businessType = BusinessType.EXPORT)
    @PreAuthorize("@ss.hasPermission('system:dict:export')" )
    @PostMapping("/export" )
    public void export(HttpServletResponse response, SysDictData dictData) {
        List<SysDictData> list = dictDataService.selectDictDataList(dictData);
        ExcelUtil<SysDictData> util = new ExcelUtil<>(SysDictData.class);
        util.exportExcel(response, list, "字典数据" );
    }

    /**
     * 查询字典数据详细
     */
    @PreAuthorize("@ss.hasPermission('system:dict:query')" )
    @GetMapping(value = "/{dictCode}" )
    public AjaxResult getInfo(@PathVariable Long dictCode) {
        return success(dictDataService.selectDictDataById(dictCode));
    }

    /**
     * 根据字典类型查询字典数据信息
     */
    @GetMapping(value = "/type/{dictType}" )
    public AjaxResult dictType(@PathVariable String dictType) {
        List<SysDictData> data = dictTypeService.selectDictDataByType(dictType);
        if (StringUtils.isNull(data)) {
            data = new ArrayList<>();
        }
        return success(data);
    }

    /**
     * 新增字典类型
     */
    @PreAuthorize("@ss.hasPermission('system:dict:add')" )
    @Log(title = "字典数据" , businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody SysDictData dict) {
        dict.setCreateBy(getUsername());
        return toAjax(dictDataService.insertDictData(dict));
    }

    /**
     * 修改保存字典类型
     */
    @PreAuthorize("@ss.hasPermission('system:dict:edit')" )
    @Log(title = "字典数据" , businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody SysDictData dict) {
        dict.setUpdateBy(getUsername());
        return toAjax(dictDataService.updateDictData(dict));
    }

    /**
     * 删除字典类型
     */
    @PreAuthorize("@ss.hasPermission('system:dict:remove')" )
    @Log(title = "字典类型" , businessType = BusinessType.DELETE)
    @DeleteMapping("/{dictCodes}" )
    public AjaxResult remove(@PathVariable Long[] dictCodes) {
        dictDataService.deleteDictDataByIds(dictCodes);
        return success();
    }
}
