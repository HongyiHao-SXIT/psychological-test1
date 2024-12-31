package com.macro.mall.tiny.common.utils;

/**
 * 计算距离工具类
 *
 * @author ztx
 * @version 1
 */

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.geo.Point;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.core.GeoOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 计算经纬度工具类
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class SphereUtil {

    private final RedisTemplate redisTemplate;

    /**
     * @param blongitude B1经度
     * @param blatitude  B1维度
     * @param ulongitude B2经度
     * @param ulatitude  B2维度
     * @return
     */
    public double getDistance(double blongitude, double blatitude, double ulongitude, double ulatitude) {
        GeoOperations geoOperations = redisTemplate.opsForGeo();
        //首先存入客户端上传的经纬度和指定地点的经纬度
        Map<String, Object> map = new HashMap<>();
        map.put("ZB", new Point(blongitude, blatitude));
        map.put("GZ", new Point(ulongitude, ulatitude));
        // 将这些地址数据保存到redis中
        geoOperations.geoAdd("GET_DISTANCE", map);
        // 调用方法,计算之间的距离;
        double value = geoOperations.geoDist("GET_DISTANCE", "ZB", "GZ", RedisGeoCommands.DistanceUnit.METERS).getValue();
        return value;
    }

}
