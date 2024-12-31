package com.macro.mall.tiny.common.dto;

import com.macro.mall.tiny.common.model.BaseModel;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

/**
 * 请求实体
 *
 * @author ztx
 * @version 1
 */
@Data
@SuperBuilder
@RequiredArgsConstructor
public class RequestDto extends BaseModel implements Serializable {
    private String keyword;
    private String status;
    private Integer pageSize;
    private Integer pageNum;
    private String mindId;
}
