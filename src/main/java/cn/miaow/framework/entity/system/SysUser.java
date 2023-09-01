package cn.miaow.framework.entity.system;

import cn.miaow.framework.aspectj.annotation.Excel;
import cn.miaow.framework.aspectj.annotation.Xss;
import cn.miaow.framework.model.BaseEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

/**
 * 用户对象 sys_user
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName(value = "sys_user" )
public class SysUser extends BaseEntity {

    private static final long serialVersionUID = -6102948907906490645L;
    /**
     * 用户ID
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long userId;

    /**
     * 部门ID
     */
    private Long deptId;

    /**
     * 用户账号
     */
    @Excel(name = "登录名称" )
    @Xss(message = "用户账号不能包含脚本字符" )
    @NotBlank(message = "用户账号不能为空" )
    @Size(max = 30, message = "用户账号长度不能超过30个字符" )
    private String userName;

    /**
     * 用户昵称
     */
    @Excel(name = "用户名称" )
    @Xss(message = "用户昵称不能包含脚本字符" )
    @Size(max = 30, message = "用户昵称长度不能超过30个字符" )
    private String nickName;

    /**
     * 用户邮箱
     */
    @Excel(name = "用户邮箱" )
    @Email(message = "邮箱格式不正确" )
    @Size(max = 50, message = "邮箱长度不能超过50个字符" )
    private String email;

    /**
     * 手机号码
     */
    @Excel(name = "手机号码" )
    @Size(max = 11, message = "手机号码长度不能超过11个字符" )
    private String phoneNumber;

    /**
     * 用户性别
     */
    @Excel(name = "用户性别" , readConverterExp = "0=男,1=女,2=未知" )
    private String sex;

    /**
     * 用户头像
     */
    private String avatar;

    /**
     * 密码
     */
    private String password;

    /**
     * 帐号状态（0正常 1停用）
     */
    @Excel(name = "帐号状态" , readConverterExp = "0=正常,1=停用" )
    private String status;

    /**
     * 删除标志（0代表存在 2代表删除）
     */
    private String delFlag;

    /**
     * 最后登录IP
     */
    private String loginIp;

    /**
     * 最后登录时间
     */
    private Date loginDate;

    /**
     * 部门对象
     */
    private SysDept dept;

    /**
     * 角色对象
     */
    private List<SysRole> roles;

    /**
     * 角色组
     */
    private Long[] roleIds;

    /**
     * 岗位组
     */
    private Long[] postIds;

    /**
     * 角色ID
     */
    private Long roleId;

    public SysUser() {

    }

    public SysUser(Long userId) {
        this.userId = userId;
    }

    public static boolean isAdmin(Long userId) {
        return userId != null && 1L == userId;
    }

    public boolean isAdmin() {
        return isAdmin(this.userId);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("userId" , getUserId())
                .append("deptId" , getDeptId())
                .append("userName" , getUserName())
                .append("nickName" , getNickName())
                .append("email" , getEmail())
                .append("phoneNumber" , getPhoneNumber())
                .append("sex" , getSex())
                .append("avatar" , getAvatar())
                .append("password" , getPassword())
                .append("status" , getStatus())
                .append("delFlag" , getDelFlag())
                .append("loginIp" , getLoginIp())
                .append("loginDate" , getLoginDate())
                .append("createBy" , getCreateBy())
                .append("createTime" , getCreateTime())
                .append("updateBy" , getUpdateBy())
                .append("updateTime" , getUpdateTime())
                .append("remark" , getRemark())
                .append("dept" , getDept())
                .toString();
    }
}
