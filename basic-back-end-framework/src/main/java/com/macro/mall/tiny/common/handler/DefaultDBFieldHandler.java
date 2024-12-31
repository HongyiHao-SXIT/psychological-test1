package com.macro.mall.tiny.common.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.macro.mall.tiny.common.model.BaseModel;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * 通用参数填充实现类
 *
 * @author ztx
 * @version 1
 */
@Component
public class DefaultDBFieldHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        if (Objects.nonNull(metaObject) && metaObject.getOriginalObject() instanceof BaseModel) {
            BaseModel baseDO = (BaseModel) metaObject.getOriginalObject();
            // 创建时间为空，则以当前时间为插入时间
            if (Objects.isNull(baseDO.getCreatedDatetime())) {
                baseDO.setCreatedDatetime(LocalDateTime.now());
            }

        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        // 暂不处理
    }
}
