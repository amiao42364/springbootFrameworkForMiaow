package cn.miaow.framework.controller.system;

import cn.miaow.framework.aspectj.annotation.Log;
import cn.miaow.framework.constant.enums.BusinessType;
import cn.miaow.framework.entity.system.SysPost;
import cn.miaow.framework.model.AjaxResult;
import cn.miaow.framework.model.BaseController;
import cn.miaow.framework.model.TableDataInfo;
import cn.miaow.framework.service.system.ISysPostService;
import cn.miaow.framework.util.ExcelUtil;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 岗位信息操作处理
 *
 * @author miaow
 */
@RestController
@RequestMapping("/system/post" )
public class SysPostController extends BaseController {
    private final ISysPostService postService;

    public SysPostController(ISysPostService postService) {
        this.postService = postService;
    }

    /**
     * 获取岗位列表
     */
    @PreAuthorize("@ss.hasPermission('system:post:list')" )
    @GetMapping("/list" )
    public TableDataInfo list(SysPost post) {
        startPage();
        List<SysPost> list = postService.selectPostList(post);
        return getDataTable(list);
    }

    @Log(title = "岗位管理" , businessType = BusinessType.EXPORT)
    @PreAuthorize("@ss.hasPermission('system:post:export')" )
    @PostMapping("/export" )
    public void export(HttpServletResponse response, SysPost post) {
        List<SysPost> list = postService.selectPostList(post);
        ExcelUtil<SysPost> util = new ExcelUtil<>(SysPost.class);
        util.exportExcel(response, list, "岗位数据");
    }

    /**
     * 根据岗位编号获取详细信息
     */
    @PreAuthorize("@ss.hasPermission('system:post:query')" )
    @GetMapping(value = "/{postId}" )
    public AjaxResult getInfo(@PathVariable Long postId) {
        return success(postService.selectPostById(postId));
    }

    /**
     * 新增岗位
     */
    @PreAuthorize("@ss.hasPermission('system:post:add')" )
    @Log(title = "岗位管理" , businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody SysPost post) {
        if (!postService.checkPostNameUnique(post)) {
            return error("新增岗位'" + post.getPostName() + "'失败，岗位名称已存在" );
        } else if (!postService.checkPostCodeUnique(post)) {
            return error("新增岗位'" + post.getPostName() + "'失败，岗位编码已存在" );
        }
        post.setCreateBy(getUsername());
        return toAjax(postService.insertPost(post));
    }

    /**
     * 修改岗位
     */
    @PreAuthorize("@ss.hasPermission('system:post:edit')" )
    @Log(title = "岗位管理" , businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody SysPost post) {
        if (!postService.checkPostNameUnique(post)) {
            return error("修改岗位'" + post.getPostName() + "'失败，岗位名称已存在" );
        } else if (!postService.checkPostCodeUnique(post)) {
            return error("修改岗位'" + post.getPostName() + "'失败，岗位编码已存在" );
        }
        post.setUpdateBy(getUsername());
        return toAjax(postService.updatePost(post));
    }

    /**
     * 删除岗位
     */
    @PreAuthorize("@ss.hasPermission('system:post:remove')" )
    @Log(title = "岗位管理" , businessType = BusinessType.DELETE)
    @DeleteMapping("/{postIds}" )
    public AjaxResult remove(@PathVariable Long[] postIds) {
        return toAjax(postService.deletePostByIds(postIds));
    }

    /**
     * 获取岗位选择框列表
     */
    @GetMapping("/optionSelect" )
    public AjaxResult optionSelect() {
        List<SysPost> posts = postService.selectPostAll();
        return success(posts);
    }
}
