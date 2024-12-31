package com.macro.mall.tiny.modules.wx.controller.miniapp;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ztx
 * @date 2022-02-21 13:35
 */
@RestController
@AllArgsConstructor
@RequestMapping("/wxpay")
@CrossOrigin
public class WxPayController {
//
//    private final WxPayService wxPayService;
//
//    private final WxPayProperties wxPayProperties;
//
//    /**
//     * 创建微信订单
//     *
//     * @param vipFillRecord 订单数据
//     * @return 订单参数
//     * @throws WxPayException 支付异常
//     */
//    @PostMapping("/createOrder")
//    public Result<?> createOrder(@RequestBody VipFillRecord vipFillRecord) throws WxPayException {
//        User user = this.userService.getById(vipFillRecord.getUserId());
//        if (Objects.isNull(user)) {
//            return Result.createFail("未找到用户信息");
//        }
//        Vip vip = this.vipService.getById(vipFillRecord.getVipId());
//        if (Objects.isNull(vip)) {
//            return Result.createFail("未找到会员充值信息");
//        }
//        VipFillRecord fillRecord = VipFillRecord.builder()
//            .userId(vipFillRecord.getUserId())
//            .vipId(vipFillRecord.getVipId())
//            .createdDatetime(LocalDateTime.now())
//            .status(VipFillStatusEnum.WAITING.getValue())
//            .build();
//        this.vipFillRecordService.save(fillRecord);
//        return Result.createSuccess(this.wxPayService.createOrder(WxPayUnifiedOrderRequest.newBuilder()
//            .body("会员充值")
//            .outTradeNo(fillRecord.getId())
//            .totalFee(vip.getPrice().multiply(BigDecimal.valueOf(100)).intValue())
//            .spbillCreateIp("10.01.01.01")
//            .openid(user.getOpenId())
//            .tradeType(WxPayConstants.TradeType.JSAPI)
//            .notifyUrl(this.wxPayProperties.getNotifyUrl() + "/wxpay/notify/order")
//            .build()));
//    }
//
//
//    /**
//     * 微信成功支付回调
//     *
//     * @param xmlData xml数据
//     * @return 成功结果
//     * @throws WxPayException
//     */
//    @PostMapping("/notify/order")
//    @Transactional
//    public String parseOrderNotifyResult(@RequestBody String xmlData) throws WxPayException {
//        final WxPayOrderNotifyResult notifyResult = this.wxPayService.parseOrderNotifyResult(xmlData);
//        // TODO 根据自己业务场景需要构造返回对象
//        VipFillRecord vipFillRecord = this.vipFillRecordService.getById(notifyResult.getOutTradeNo());
//        UserVip userVip = this.userVipService.getOne(Wrappers.<UserVip>lambdaQuery()
//            .eq(UserVip::getUserId, vipFillRecord.getUserId())
//            .eq(UserVip::getStatus, VipStatusEnum.USEING.getValue())
//            .gt(UserVip::getEndDatetime, LocalDateTime.now()));
//        Vip vip = this.vipService.getById(vipFillRecord.getVipId());
//        if (Objects.isNull(userVip)) {
//            // 根据vip类型插入会员表
//            this.userVipService.saveOrUpdate(UserVip.builder()
//                .userId(vipFillRecord.getUserId())
//                .status(VipStatusEnum.USEING.getValue())
//                .createdDatetime(LocalDateTime.now())
//                .endDatetime(LocalDateTime.now().plusMonths(vip.getMonth()))
//                .build());
//        } else {
//            // 已经存在有会员有效数据，那么就属于还没过期就准备续费,直接将会员结束日期更新
//            this.userVipService.saveOrUpdate(UserVip.builder()
//                .id(userVip.getId())
//                .endDatetime(userVip.getEndDatetime().plusMonths(vip.getMonth()))
//                .build());
//        }
//        // 更新订单表
//        this.vipFillRecordService.saveOrUpdate(VipFillRecord.builder()
//            .id(vipFillRecord.getId())
//            .status(VipFillStatusEnum.PAYED.getValue())
//            .build());
//        return WxPayNotifyResponse.success("成功");
//    }
}
