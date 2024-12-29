package com.strr.mybatis.core.model;

import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;
import java.util.Date;

public class BaseEntity implements Serializable {
    /**
     * 创建者
     */
    @TableField(exist = false)
    private Long createBy;

    /**
     * 创建时间
     */
    @TableField(exist = false)
    private Date createTime;

    /**
     * 更新者
     */
    @TableField(exist = false)
    private Long updateBy;

    /**
     * 更新时间
     */
    @TableField(exist = false)
    private Date updateTime;

    public Long getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Long createBy) {
        this.createBy = createBy;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(Long updateBy) {
        this.updateBy = updateBy;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
