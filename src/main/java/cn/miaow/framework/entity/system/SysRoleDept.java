package cn.miaow.framework.entity.system;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;

/**
 * 角色和部门关联 sys_role_dept
 */
@Data
@TableName(value = "sys_role_dept" )
public class SysRoleDept implements Serializable {
    private static final long serialVersionUID = 367293928378869426L;
    /**
     * 角色ID
     */
    private Long roleId;

    /**
     * 部门ID
     */
    private Long deptId;

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("roleId" , getRoleId())
                .append("deptId" , getDeptId())
                .toString();
    }
}
