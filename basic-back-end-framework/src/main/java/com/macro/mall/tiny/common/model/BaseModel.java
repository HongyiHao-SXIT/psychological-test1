package com.macro.mall.tiny.common.model;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

/**
 * 基础类
 *
 * @author ztx
 * @version 1
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "基础类", description = "基础类")
@SuperBuilder
@RequiredArgsConstructor
public class BaseModel {

    @TableId(type = IdType.ASSIGN_ID)
    private String id;
    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建时间")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)        // 反序列化
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime createdDatetime;

    @TableField(exist = false)
    private Integer pageSize;
    @TableField(exist = false)
    private Integer pageNum;
}
