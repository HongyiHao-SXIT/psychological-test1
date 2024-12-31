package com.macro.mall.tiny.modules.wx.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.macro.mall.tiny.common.api.CommonResult;
import com.macro.mall.tiny.modules.wx.model.WxArticle;

import java.util.List;

/**
 * (WxArticle)服务接口
 *
 * @author makejava
 * @since 2024-02-10 09:49:41
 */
public interface WxArticleService extends IService<WxArticle> {
    /**
     * 上传文章
     *
     * @param wxArticle 入参
     * @return 结果
     */
    CommonResult<Void> commitWxArticle(WxArticle wxArticle);

    /**
     * 获取心理师的文章列表
     *
     * @param wxArticle 入参
     * @return 结果
     */
    CommonResult<List<WxArticle>> getCounselorArticles(WxArticle wxArticle);

    /**
     * 获取热门文章列表(根据读取数排序)
     *
     * @param wxArticle 入参
     * @return 结果
     */
    CommonResult<List<WxArticle>> getHotWxArticleList(WxArticle wxArticle);

    /**
     * 获取文章详情
     *
     * @param wxArticle 入参
     * @return 结果
     */
    CommonResult<WxArticle> getArticleDetail(WxArticle wxArticle);
}
