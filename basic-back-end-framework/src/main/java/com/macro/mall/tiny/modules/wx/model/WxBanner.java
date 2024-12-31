package com.macro.mall.tiny.modules.wx.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.macro.mall.tiny.common.model.BaseModel;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

/**
 * (WxBanner)实体类
 *
 * @author makejava
 * @since 2024-02-10 09:49:42
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@TableName("wx_banner")
@EqualsAndHashCode(callSuper = true)
public class WxBanner extends BaseModel implements Serializable {


    /**
     * 轮播图连接
     */
    private String imageUrl;
}

