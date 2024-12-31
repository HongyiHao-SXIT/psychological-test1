package com.macro.mall.tiny.modules.wx.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.macro.mall.tiny.common.model.BaseModel;
import com.macro.mall.tiny.modules.wx.enumeration.AppointmentStatusEnum;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * (WxAppointment)实体类
 *
 * @author makejava
 * @since 2024-02-10 09:49:39
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@TableName("wx_appointment")
@EqualsAndHashCode(callSuper = true)
public class WxAppointment extends BaseModel implements Serializable {


    /**
     * 咨询师id
     */
    private String counselorId;

    @TableField(exist = false)
    private WxCounselor wxCounselor;
    /**
     * 咨询师用户id
     */
    private String counselorUserId;
    /**
     * 预约日期
     */
    private LocalDate applicationDate;
    /**
     * 预约状态
     */
    private AppointmentStatusEnum status;
    /**
     * 预约人姓名
     */
    private String name;
    /**
     * 预约人手机
     */
    private String phone;
    /**
     * 预约人性别
     */
    private String gender;

    private String userId;

}

