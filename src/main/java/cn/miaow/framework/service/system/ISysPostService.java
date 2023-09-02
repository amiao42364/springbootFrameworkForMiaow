package cn.miaow.framework.service.system;

import cn.miaow.framework.entity.system.SysPost;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 岗位信息 服务层
 *
 * @author miaow
 */
public interface ISysPostService extends IService<SysPost> {
    /**
     * 查询岗位信息集合
     *
     * @param post 岗位信息
     * @return 岗位列表
     */
    List<SysPost> selectPostList(SysPost post);

    /**
     * 查询所有岗位
     *
     * @return 岗位列表
     */
    List<SysPost> selectPostAll();

    /**
     * 通过岗位ID查询岗位信息
     *
     * @param postId 岗位ID
     * @return 角色对象信息
     */
    SysPost selectPostById(Long postId);

    /**
     * 根据用户ID获取岗位选择框列表
     *
     * @param userId 用户ID
     * @return 选中岗位ID列表
     */
    List<Long> selectPostListByUserId(Long userId);

    /**
     * 校验岗位名称
     *
     * @param post 岗位信息
     * @return 结果
     */
    boolean checkPostNameNotUnique(SysPost post);

    /**
     * 校验岗位编码
     *
     * @param post 岗位信息
     * @return 结果
     */
    boolean checkPostCodeNotUnique(SysPost post);

    /**
     * 通过岗位ID查询岗位使用数量
     *
     * @param postId 岗位ID
     * @return 结果
     */
    int countUserPostById(Long postId);

    /**
     * 删除岗位信息
     *
     * @param postId 岗位ID
     * @return 结果
     */
    @SuppressWarnings("unused" )
    int deletePostById(Long postId);

    /**
     * 批量删除岗位信息
     *
     * @param postIds 需要删除的岗位ID
     * @return 结果
     */
    int deletePostByIds(Long[] postIds);

    /**
     * 新增保存岗位信息
     *
     * @param post 岗位信息
     * @return 结果
     */
    int insertPost(SysPost post);

    /**
     * 修改保存岗位信息
     *
     * @param post 岗位信息
     * @return 结果
     */
    int updatePost(SysPost post);
}
