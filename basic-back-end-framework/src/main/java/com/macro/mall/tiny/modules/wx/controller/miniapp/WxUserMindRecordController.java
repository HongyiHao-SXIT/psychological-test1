package com.macro.mall.tiny.modules.wx.controller.miniapp;

import com.macro.mall.tiny.common.api.CommonResult;
import com.macro.mall.tiny.common.controller.AbstractFrameworkController;
import com.macro.mall.tiny.modules.wx.model.WxUserMindRecord;
import com.macro.mall.tiny.modules.wx.service.WxUserMindRecordService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * (WxUserMindRecord)控制器类
 *
 * @author makejava
 * @since 2024-02-10 09:49:42
 */
@Slf4j
@RestController
@RequestMapping("/wxUserMindRecord")
@RequiredArgsConstructor
public class WxUserMindRecordController extends AbstractFrameworkController<WxUserMindRecord, WxUserMindRecordService> {


    /**
     * 保存测试结果
     *
     * @param wxUserMindRecord 入参
     * @return 结果
     */
    @PostMapping("/saveWxUserMindRecord")
    public CommonResult<WxUserMindRecord> saveWxUserMindRecord(@RequestBody WxUserMindRecord wxUserMindRecord) {
        return this.service.saveWxUserMindRecord(wxUserMindRecord);
    }

    /**
     * 根据用户id获取测评记录
     *
     * @param wxUserMindRecord 入参
     * @return 结果
     */
    @GetMapping("/mindRecordByUserId")
    public CommonResult<List<WxUserMindRecord>> getMindRecordByUserId(WxUserMindRecord wxUserMindRecord) {
        return this.service.getMindRecordByUserId(wxUserMindRecord);
    }
}
