package cn.miaow.framework.entity.system;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;

/**
 * 用户和岗位关联 sys_user_post
 */
@Data
@TableName(value = "sys_user_post" )
public class SysUserPost implements Serializable {
    private static final long serialVersionUID = -6772644864837093913L;
    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 岗位ID
     */
    private Long postId;

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("userId" , getUserId())
                .append("postId" , getPostId())
                .toString();
    }
}
