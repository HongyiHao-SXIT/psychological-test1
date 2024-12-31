package com.macro.mall.tiny.modules.wx.service.impl;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.macro.mall.tiny.common.api.CommonResult;
import com.macro.mall.tiny.common.utils.AuthUtils;
import com.macro.mall.tiny.modules.wx.mapper.WxUserMindRecordRepository;
import com.macro.mall.tiny.modules.wx.model.WxMind;
import com.macro.mall.tiny.modules.wx.model.WxUserMindRecord;
import com.macro.mall.tiny.modules.wx.service.WxMindService;
import com.macro.mall.tiny.modules.wx.service.WxUserMindRecordService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * (WxUserMindRecord)服务接口实现类
 *
 * @author makejava
 * @since 2024-02-10 09:49:42
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class WxUserMindRecordServiceImpl extends ServiceImpl<WxUserMindRecordRepository, WxUserMindRecord> implements WxUserMindRecordService {

    private final WxMindService wxMindService;


    /**
     * 保存测试结果
     *
     * @param wxUserMindRecord 入参
     * @return 结果
     */
    @Override
    public CommonResult<WxUserMindRecord> saveWxUserMindRecord(WxUserMindRecord wxUserMindRecord) {
        // 首先获取题目的概况信息
        WxMind wxMind = this.wxMindService.getById(wxUserMindRecord.getMindId());
        if (Objects.isNull(wxMind)) {
            return CommonResult.failed("题库不存在");
        }
        // 获取题目的分数范围数据
        JSONArray jsonArray = JSONUtil.parseArray(wxMind.getScoreRadiusResult());
        List<Object> objectList = jsonArray.stream().filter(item -> {
            JSONObject jsonObject = JSONUtil.parseObj(item.toString());
            BigDecimal minValue = jsonObject.getBigDecimal("min");
            BigDecimal maxValue = jsonObject.getBigDecimal("max");
            return wxUserMindRecord.getScore()
                .multiply(wxMind.getScoreScale())
                .compareTo(minValue) >= 0 && wxUserMindRecord.getScore()
                .multiply(wxMind.getScoreScale()).compareTo(maxValue) <= 0;
        }).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(objectList)) {
            return CommonResult.failed("得分不在题库的得分范围，请检查题库的分支范围是否完整");
        }
        if (objectList.size() > 1) {
            return CommonResult.failed("得分在题库的得分满足两个范围条件，请检查题库的分支范围是否完整");
        }
        JSONObject resultObject = JSONUtil.parseObj(objectList.get(0).toString());
        // 此时只有一个满足条件的结果出来了
        WxUserMindRecord result = WxUserMindRecord.builder()
            .userId(AuthUtils.getWxUser().getWxUser().getId())
            .mindId(wxMind.getId())
            .result(resultObject.getStr("result"))
            .wxMindIntro(wxMind.getIntro())
            .score(wxUserMindRecord.getScore())
            .title(wxMind.getTitle())
            .build();
        this.save(result);
        return CommonResult.success(result);
    }

    /**
     * 根据用户id获取测评记录
     *
     * @param wxUserMindRecord 入参
     * @return 结果
     */
    @Override
    public CommonResult<List<WxUserMindRecord>> getMindRecordByUserId(WxUserMindRecord wxUserMindRecord) {
        return CommonResult.success(this.list(Wrappers.<WxUserMindRecord>lambdaQuery()
            .eq(WxUserMindRecord::getUserId, wxUserMindRecord.getUserId())
            .orderByDesc(WxUserMindRecord::getCreatedDatetime)
        ));
    }
}
