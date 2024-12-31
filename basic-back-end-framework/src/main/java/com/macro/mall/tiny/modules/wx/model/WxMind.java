package com.macro.mall.tiny.modules.wx.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.macro.mall.tiny.common.model.BaseModel;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * (WxMind)实体类
 *
 * @author makejava
 * @since 2024-02-10 09:49:42
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@TableName("wx_mind")
@EqualsAndHashCode(callSuper = true)
public class WxMind extends BaseModel implements Serializable {


    /**
     * 心理测评标题
     */
    private String title;
    /**
     * 心理测评说明
     */
    private String intro;
    /**
     * 心理测评内容
     */
    private String content;
    /**
     * 出分乘以比例
     */
    private BigDecimal scoreScale;
    /**
     * 得分范围结果
     */
    private String scoreRadiusResult;

    private String imageUrl;

    @TableField(exist = false)
    private long itemNum;
}

