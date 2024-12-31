package com.macro.mall.tiny.modules.wx.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.macro.mall.tiny.common.model.BaseModel;
import com.macro.mall.tiny.modules.wx.enumeration.CounselorStatusEnum;
import com.macro.mall.tiny.modules.wx.enumeration.GenderEnum;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * (WxCounselor)实体类
 *
 * @author makejava
 * @since 2024-02-10 09:49:42
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@TableName("wx_counselor")
@EqualsAndHashCode(callSuper = true)
public class WxCounselor extends BaseModel implements Serializable {


    /**
     * 申请人
     */
    private String userId;
    /**
     * 头像
     */
    private String imageUrl;
    /**
     * 详情轮播图
     */
    private String bannerUrl;
    /**
     * 姓名
     */
    private String name;
    /**
     * 性别
     */
    private GenderEnum gender;
    /**
     * 擅长
     */
    private String goodAt;
    /**
     * 从业时间
     */
    private BigDecimal workTime;
    /**
     * 咨询时间
     */
    private BigDecimal consultTime;
    /**
     * 自我介绍
     */
    private String intro;
    /**
     * 状态
     */
    private CounselorStatusEnum status;
    /**
     * 咨询等级
     */
    private String levelText;
}

