package com.strr.mybatis.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.strr.mybatis.core.model.BaseEntity;
import org.apache.ibatis.reflection.MetaObject;

import java.util.Date;

public class MyMetaObjectHandler implements MetaObjectHandler {
    private static final Long DEFAULT_USER_ID = 1L;

    @Override
    public void insertFill(MetaObject metaObject) {
        if (metaObject != null && metaObject.getOriginalObject() instanceof BaseEntity baseEntity) {
            Date current = baseEntity.getCreateTime() != null ? baseEntity.getCreateTime() : new Date();
            baseEntity.setCreateTime(current);
            baseEntity.setUpdateTime(current);
            Long userId = baseEntity.getCreateBy() != null ? baseEntity.getCreateBy() : DEFAULT_USER_ID;
            // 当前已登录 且 创建人为空 则填充
            baseEntity.setCreateBy(userId);
            // 当前已登录 且 更新人为空 则填充
            baseEntity.setUpdateBy(userId);
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        if (metaObject != null && metaObject.getOriginalObject() instanceof BaseEntity baseEntity) {
            Date current = new Date();
            // 更新时间填充(不管为不为空)
            baseEntity.setUpdateTime(current);
            // 当前已登录 更新人填充(不管为不为空)
            baseEntity.setUpdateBy(DEFAULT_USER_ID);
        }
    }
}
