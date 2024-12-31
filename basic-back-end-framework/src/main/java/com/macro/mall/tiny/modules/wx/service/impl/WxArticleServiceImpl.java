package com.macro.mall.tiny.modules.wx.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.macro.mall.tiny.common.api.CommonResult;
import com.macro.mall.tiny.common.utils.AuthUtils;
import com.macro.mall.tiny.modules.wx.mapper.WxArticleRepository;
import com.macro.mall.tiny.modules.wx.model.WxArticle;
import com.macro.mall.tiny.modules.wx.service.WxArticleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

/**
 * (WxArticle)服务接口实现类
 *
 * @author makejava
 * @since 2024-02-10 09:49:41
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class WxArticleServiceImpl extends ServiceImpl<WxArticleRepository, WxArticle> implements WxArticleService {


    /**
     * 上传文章
     *
     * @param wxArticle 入参
     * @return 结果
     */
    @Override
    public CommonResult<Void> commitWxArticle(WxArticle wxArticle) {
        if (StringUtils.isEmpty(wxArticle.getContent())) {
            return CommonResult.failed("内容不能为空!");
        }
        wxArticle.setUserId(AuthUtils.getWxUser().getWxUser().getId());
        wxArticle.setReadNum(BigDecimal.ZERO);
        this.save(wxArticle);
        return CommonResult.success(null);
    }

    /**
     * 获取心理师的文章列表
     *
     * @param wxArticle 入参
     * @return 结果
     */
    @Override
    public CommonResult<List<WxArticle>> getCounselorArticles(WxArticle wxArticle) {
        return CommonResult.success(this.list(Wrappers.<WxArticle>lambdaQuery()
            .eq(WxArticle::getUserId, AuthUtils.getWxUser().getWxUser().getId())
            .orderByDesc(WxArticle::getCreatedDatetime)
        ));
    }

    /**
     * 获取热门文章列表(根据读取数排序)
     *
     * @param wxArticle 入参
     * @return 结果
     */
    @Override
    public CommonResult<List<WxArticle>> getHotWxArticleList(WxArticle wxArticle) {
        return CommonResult.success(this.list(Wrappers.<WxArticle>lambdaQuery()
            .orderByDesc(WxArticle::getReadNum)));
    }

    /**
     * 获取文章详情
     *
     * @param wxArticle 入参
     * @return 结果
     */
    @Override
    public CommonResult<WxArticle> getArticleDetail(WxArticle wxArticle) {
        WxArticle article = this.getById(wxArticle.getId());
        if (Objects.isNull(article)) {
            return CommonResult.failed("文章不存在");
        }
        // 更新阅读次数
        this.update(Wrappers.<WxArticle>lambdaUpdate()
            .set(WxArticle::getReadNum, article.getReadNum().add(BigDecimal.ONE))
            .eq(WxArticle::getId, wxArticle.getId())
        );
        return CommonResult.success(article);
    }
}
