package com.macro.mall.tiny.modules.wx.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.macro.mall.tiny.common.model.BaseModel;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * (WxUserMindRecord)实体类
 *
 * @author makejava
 * @since 2024-02-10 09:49:42
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@TableName("wx_user_mind_record")
@EqualsAndHashCode(callSuper = true)
public class WxUserMindRecord extends BaseModel implements Serializable {


    /**
     * 用户id
     */
    private String userId;
    /**
     * 心理题id
     */
    private String mindId;
    /**
     * 测试结果
     */
    private String result;
    /**
     * 心理题说明
     */
    private String wxMindIntro;

    /**
     * 分数
     */
    private BigDecimal score;

    /**
     * 心理题标题
     */
    private String title;


}

