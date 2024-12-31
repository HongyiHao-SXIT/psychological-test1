package com.macro.mall.tiny.modules.wx.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.macro.mall.tiny.common.model.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

/**
 * 微信用户实体
 *
 * @author ztx
 * @version 1
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("wx_user")
@ApiModel(value = "微信用户实体", description = "微信用户实体")
@SuperBuilder
@RequiredArgsConstructor
public class WxUser extends BaseModel implements Serializable {

    @ApiModelProperty(value = "openid")
    private String openId;
    @ApiModelProperty(value = "昵称")
    private String nickName;
    @ApiModelProperty(value = "性别")
    private String gender;
    @ApiModelProperty(value = "省份")
    private String province;
    @ApiModelProperty(value = "头像")
    private String avatarUrl;

    @ApiModelProperty(value = "账号")
    private String username;
    @ApiModelProperty(value = "密码")
    private String password;

}
