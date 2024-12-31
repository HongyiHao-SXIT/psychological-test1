package com.macro.mall.tiny.modules.wx.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.macro.mall.tiny.common.model.BaseModel;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * (WxMindItem)实体类
 *
 * @author makejava
 * @since 2024-02-10 09:49:42
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@TableName("wx_mind_item")
@EqualsAndHashCode(callSuper = true)
public class WxMindItem extends BaseModel implements Serializable {


    /**
     * 心理题标题
     */
    private String mindId;
    /**
     * 心理项目标题
     */
    private String title;
    /**
     * A选项标题
     */
    private String chooseA;
    /**
     * A选项分数
     */
    private BigDecimal chooseANum;
    /**
     * B选项标题
     */
    private String chooseB;
    /**
     * B选项分数
     */
    private BigDecimal chooseBNum;
    /**
     * C选项标题
     */
    private String chooseC;
    /**
     * C选项分数
     */
    private BigDecimal chooseCNum;
    /**
     * D选项标题
     */
    private String chooseD;
    /**
     * D选项分数
     */
    private BigDecimal chooseDNum;

    private Integer seq;
}

