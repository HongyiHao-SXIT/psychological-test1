package com.macro.mall.tiny.modules.wx.controller.miniapp;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.macro.mall.tiny.common.api.CommonResult;
import com.macro.mall.tiny.common.controller.AbstractFrameworkController;
import com.macro.mall.tiny.common.dto.RequestDto;
import com.macro.mall.tiny.modules.wx.model.WxArticle;
import com.macro.mall.tiny.modules.wx.service.WxArticleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * (WxArticle)控制器类
 *
 * @author makejava
 * @since 2024-02-10 09:49:41
 */
@Slf4j
@RestController
@RequestMapping("/wxArticle")
@RequiredArgsConstructor
public class WxArticleController extends AbstractFrameworkController<WxArticle, WxArticleService> {


    /**
     * 上传文章
     *
     * @param wxArticle 入参
     * @return 结果
     */
    @PostMapping("/commitWxArticle")
    public CommonResult<Void> commitWxArticle(@RequestBody WxArticle wxArticle) {
        return this.service.commitWxArticle(wxArticle);
    }


    /**
     * 获取心理师的文章列表
     *
     * @param wxArticle 入参
     * @return 结果
     */
    @GetMapping("/counselorArticles")
    public CommonResult<List<WxArticle>> getCounselorArticles(WxArticle wxArticle) {
        return this.service.getCounselorArticles(wxArticle);
    }


    /**
     * 获取热门文章列表(根据读取数排序)
     *
     * @param wxArticle 入参
     * @return 结果
     */
    @GetMapping("/hotWxArticleList")
    public CommonResult<List<WxArticle>> getHotWxArticleList(WxArticle wxArticle) {
        return this.service.getHotWxArticleList(wxArticle);
    }

    /**
     * 获取文章详情
     *
     * @param wxArticle 入参
     * @return 结果
     */
    @GetMapping("/articleDetail")
    public CommonResult<WxArticle> getArticleDetail(WxArticle wxArticle) {
        return this.service.getArticleDetail(wxArticle);
    }

    @Override
    protected Wrapper<WxArticle> wrappersBuilder(RequestDto requestDto) {
        return Wrappers.<WxArticle>lambdaQuery()
            .like(StringUtils.isNotBlank(requestDto.getKeyword()), WxArticle::getTitle, requestDto.getKeyword())
            .or()
            .like(StringUtils.isNotBlank(requestDto.getKeyword()), WxArticle::getContent, requestDto.getKeyword());
    }
}
