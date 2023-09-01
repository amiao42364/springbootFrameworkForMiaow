package cn.miaow.framework.entity.system;

import cn.miaow.framework.aspectj.annotation.Xss;
import cn.miaow.framework.model.BaseEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * 通知公告表 sys_notice
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName(value = "sys_notice" )
public class SysNotice extends BaseEntity {

    private static final long serialVersionUID = -4359730999324870829L;
    /**
     * 公告ID
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long noticeId;

    /**
     * 公告标题
     */
    @Xss(message = "公告标题不能包含脚本字符" )
    @NotBlank(message = "公告标题不能为空" )
    @Size(max = 50, message = "公告标题不能超过50个字符" )
    private String noticeTitle;

    /**
     * 公告类型（1通知 2公告）
     */
    private String noticeType;

    /**
     * 公告内容
     */
    private String noticeContent;

    /**
     * 公告状态（0正常 1关闭）
     */
    private String status;

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("noticeId" , getNoticeId())
                .append("noticeTitle" , getNoticeTitle())
                .append("noticeType" , getNoticeType())
                .append("noticeContent" , getNoticeContent())
                .append("status" , getStatus())
                .append("createBy" , getCreateBy())
                .append("createTime" , getCreateTime())
                .append("updateBy" , getUpdateBy())
                .append("updateTime" , getUpdateTime())
                .append("remark" , getRemark())
                .toString();
    }
}
