package com.lingzhong.video.bean.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;


@TableName(value ="user_attention")
@Data
public class UserAttention implements Serializable {
    /**
     * 用户关注主键
     */
    @TableId(type = IdType.AUTO)
    private Integer attentionId;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 被关注的用户id
     */
    private Integer beUserId;

    /**
     * 关注的时间
     */
    private Date attentionDate;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        UserAttention other = (UserAttention) that;
        return (this.getAttentionId() == null ? other.getAttentionId() == null : this.getAttentionId().equals(other.getAttentionId()))
            && (this.getUserId() == null ? other.getUserId() == null : this.getUserId().equals(other.getUserId()))
            && (this.getBeUserId() == null ? other.getBeUserId() == null : this.getBeUserId().equals(other.getBeUserId()))
            && (this.getAttentionDate() == null ? other.getAttentionDate() == null : this.getAttentionDate().equals(other.getAttentionDate()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getAttentionId() == null) ? 0 : getAttentionId().hashCode());
        result = prime * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
        result = prime * result + ((getBeUserId() == null) ? 0 : getBeUserId().hashCode());
        result = prime * result + ((getAttentionDate() == null) ? 0 : getAttentionDate().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", attentionId=").append(attentionId);
        sb.append(", userId=").append(userId);
        sb.append(", beUserId=").append(beUserId);
        sb.append(", attentionDate=").append(attentionDate);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}