package com.macro.mall.tiny.modules.wx.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.macro.mall.tiny.common.model.BaseModel;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * (WxArticle)实体类
 *
 * @author makejava
 * @since 2024-02-10 09:49:41
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@TableName("wx_article")
@EqualsAndHashCode(callSuper = true)
public class WxArticle extends BaseModel implements Serializable {


    /**
     * 文章封面
     */
    private String imageUrl;
    /**
     * 文章标题
     */
    private String title;
    /**
     * 文章内容
     */
    private String content;
    /**
     * 创建人
     */
    private String userId;
    /**
     * 读取次数
     */
    private BigDecimal readNum;
}

