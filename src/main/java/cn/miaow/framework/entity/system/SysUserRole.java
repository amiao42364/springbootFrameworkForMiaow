package cn.miaow.framework.entity.system;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;

/**
 * 用户和角色关联 sys_user_role
 */
@Data
@TableName(value = "sys_user_role" )
public class SysUserRole implements Serializable {
    private static final long serialVersionUID = -4450637642278695511L;
    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 角色ID
     */
    private Long roleId;

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("userId" , getUserId())
                .append("roleId" , getRoleId())
                .toString();
    }
}
