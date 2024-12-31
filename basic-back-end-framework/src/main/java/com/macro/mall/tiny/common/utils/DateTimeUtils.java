package com.macro.mall.tiny.common.utils;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.google.common.collect.Lists;
import com.macro.mall.tiny.common.constant.FrameworkConstants;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.ConcurrentReferenceHashMap;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.Temporal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 日期时间工具类
 *
 * @author venus
 * @version 1
 * @date 2020/3/26 2:03 下午 星期四
 */
public final class DateTimeUtils {

    /**
     * 格式化器对象缓存
     */
    private static final Map<String, DateTimeFormatter> DATE_TIME_FORMATTER_MAP = new ConcurrentReferenceHashMap<>(32);

    private static final List<String> COMMON_PATTERNS = Lists.newArrayList(
        "yyyy-MM-dd HH:mm:ss",
        "yyyy-MM-dd HH:mm",
        "yyyy-MM-dd",
        "yyyy/MM/dd HH:mm:ss",
        "yyyy/MM/dd HH:mm",
        "yyyy/MM/dd",
        "yyyyMMddHHmmss",
        "yyyyMMddHHmm",
        "yyyyMMdd"
    );

    private static final String[] DURATION_FORMAT_PARTS = new String[]{"天", "小时", "分钟", "秒"};

    /**
     * 获取格式化器
     *
     * @param pattern 格式
     * @return 格式化器对象
     */
    private static DateTimeFormatter getFormatter(String pattern) {
        return DATE_TIME_FORMATTER_MAP.computeIfAbsent(
            pattern,
            DateTimeFormatter::ofPattern
        );
    }

    /**
     * 格式化日期时间
     *
     * @param temporal 时间对象
     * @return 字符串形式
     */
    public static String toStringFormat(Temporal temporal) {
        if (temporal instanceof LocalDateTime) {
            return FrameworkConstants.DATE_TIME_FORMATTER.format(temporal);
        } else if (temporal instanceof LocalDate) {
            return FrameworkConstants.DATE_FORMATTER.format(temporal);
        } else if (temporal instanceof LocalTime) {
            return FrameworkConstants.TIME_FORMATTER.format(temporal);
        } else if (temporal instanceof YearMonth) {
            return FrameworkConstants.YEAR_MONTH_FORMATTER.format(temporal);
        } else if (temporal instanceof Year) {
            return FrameworkConstants.YEAR_FORMATTER.format(temporal);
        } else {
            return StringPool.EMPTY;
        }
    }

    /**
     * 格式化日期时间
     *
     * @param temporal 时间对象
     * @param pattern  格式化模式
     * @return 字符串形式
     */
    public static String toStringFormat(Temporal temporal, String pattern) {
        return getFormatter(pattern).format(temporal);
    }

    /**
     * 时间戳转LocalDateTime
     *
     * @param timestamp 时间戳（单位秒）
     * @return 日期时间对象
     */
    public static LocalDateTime fromSecondTimestamp(long timestamp) {
        return LocalDateTime.ofInstant(Instant.ofEpochSecond(timestamp), ZoneId.systemDefault());
    }

    /**
     * 时间戳转LocalDateTime
     *
     * @param timestamp 时间戳（单位毫秒）
     * @return 日期时间对象
     */
    public static LocalDateTime fromMilliTimestamp(long timestamp) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneId.systemDefault());
    }

    /**
     * 解析可能的日期时间对象
     *
     * @param datetime 日期时间字符串
     * @return 日期时间对象
     */
    public static LocalDateTime parseGuessedLocalDateTime(String datetime) {
        datetime = StringUtils.replace(datetime, "T", StringPool.SPACE);
        for (String commonPattern : COMMON_PATTERNS) {
            if (commonPattern.length() == datetime.length()) {
                try {
                    if (datetime.length() <= 10) {
                        // 优先使用LocalDate解析
                        return LocalDate.parse(
                            datetime,
                            getFormatter(commonPattern)
                        ).atStartOfDay();
                    }
                    return LocalDateTime.parse(
                        datetime,
                        getFormatter(commonPattern)
                    );
                } catch (DateTimeParseException ignored) {
                }
            }
        }

        // 未能解析
        return null;
    }

    /**
     * 解析日期时间对象
     *
     * @param dateTime 日期时间字符串
     * @param pattern  格式
     * @return 日期时间对象
     */
    public static LocalDateTime parseLocalDateTime(String dateTime, String pattern) {
        return LocalDateTime.parse(dateTime, getFormatter(pattern));
    }

    /**
     * 解析日期时间对象
     *
     * @param dateTime 日期时间字符串
     * @return 日期时间对象
     */
    public static LocalDateTime parseLocalDateTime(String dateTime) {
        return LocalDateTime.parse(dateTime.replace("T", StringPool.SPACE), FrameworkConstants.DATE_TIME_FORMATTER);
    }

    /**
     * 解析日期对象
     *
     * @param date    日期字符串
     * @param pattern 格式
     * @return 日期对象
     */
    public static LocalDate parseLocalDate(String date, String pattern) {
        return LocalDate.parse(date, getFormatter(pattern));
    }

    /**
     * 解析日期对象
     *
     * @param date 日期字符串
     * @return 日期对象
     */
    public static LocalDate parseLocalDate(String date) {
        return LocalDate.parse(date, FrameworkConstants.DATE_FORMATTER);
    }

    /**
     * 解析年月对象
     *
     * @param yearMonth 年月字符串
     * @return 年月对象
     */
    public static YearMonth parseYearMonth(String yearMonth) {
        return YearMonth.parse(yearMonth, FrameworkConstants.YEAR_MONTH_FORMATTER);
    }

    /**
     * 解析年月对象
     *
     * @param yearMonth 年月字符串
     * @param pattern   年月字符串格式
     * @return 年月对象
     */
    public static YearMonth parseYearMonth(String yearMonth, String pattern) {
        return YearMonth.parse(yearMonth, getFormatter(pattern));
    }

    /**
     * 解析年月对象
     *
     * @param year 年月字符串
     * @return 年月对象
     */
    public static Year parseYear(String year) {
        return Year.parse(year, FrameworkConstants.YEAR_FORMATTER);
    }

    /**
     * 格式化LocalDate
     *
     * @param localDate 日期对象
     * @return 日期字符串
     */
    public static String toDateStringFormat(LocalDate localDate) {
        return FrameworkConstants.DATE_FORMATTER.format(localDate);
    }

    /**
     * 格式化LocalDate
     *
     * @param localDate 日期对象
     * @param pattern   日期模式
     * @return 日期字符串
     */
    public static String toDateStringFormat(LocalDate localDate, String pattern) {
        return getFormatter(pattern).format(localDate);
    }

    /**
     * 格式化LocalDateTime
     *
     * @param localDateTime 日期时间对象
     * @return 日期字符串
     */
    public static String toDateStringFormat(LocalDateTime localDateTime) {
        return FrameworkConstants.DATE_FORMATTER.format(localDateTime);
    }

    /**
     * 格式化LocalDate
     *
     * @return 日期字符串
     */
    public static String toDateStringFormat() {
        return FrameworkConstants.DATE_FORMATTER.format(LocalDate.now());
    }

    /**
     * 格式化LocalTime
     *
     * @return 时间符串
     */
    public static String toTimeStringFormat() {
        return FrameworkConstants.TIME_FORMATTER.format(LocalTime.now());
    }

    /**
     * 格式化LocalTime
     *
     * @param time 时间对象
     * @return 时间符串
     */
    public static String toTimeStringFormat(LocalTime time) {
        return FrameworkConstants.TIME_FORMATTER.format(time);
    }

    /**
     * 格式化LocalDateTime
     *
     * @param localDateTime 日期时间对象
     * @return 日期时间字符串
     */
    public static String toDateTimeStringFormat(LocalDateTime localDateTime) {
        return FrameworkConstants.DATE_TIME_FORMATTER.format(localDateTime);
    }

    /**
     * 格式化LocalDateTime
     *
     * @param localDateTime 日期时间对象
     * @param pattern       格式
     * @return 日期时间字符串
     */
    public static String toDateTimeStringFormat(LocalDateTime localDateTime, String pattern) {
        return getFormatter(pattern).format(localDateTime);
    }

    /**
     * 从一个日期时间字符串到另一个格式的日期时间字符串
     *
     * @param datetime 日期时间字符串
     * @param pattern  期望的日期时间格式
     * @return 期望格式的日期时间字符串
     */
    public static String toDateTimeStringFormat(String datetime, String pattern) {
        if (StringUtils.isBlank(datetime)) {
            return datetime;
        }

        LocalDateTime localDateTime = parseGuessedLocalDateTime(datetime);
        if (localDateTime == null) {
            return datetime;
        }

        return toDateTimeStringFormat(localDateTime, pattern);
    }

    /**
     * 格式化LocalDateTime
     *
     * @return 日期时间字符串
     */
    public static String toDateTimeStringFormat() {
        return FrameworkConstants.DATE_TIME_FORMATTER.format(LocalDateTime.now());
    }

    /**
     * 格式化LocalDateTime
     *
     * @param pattern 日期时间格式
     * @return 日期时间字符串
     */
    public static String toDateTimeStringFormat(String pattern) {
        return getFormatter(pattern).format(LocalDateTime.now());
    }

    /**
     * 格式化年月
     *
     * @param yearMonth 年月对象
     * @param pattern   格式
     * @return 年月字符串
     */
    public static String toYearMonthStringFormat(YearMonth yearMonth, String pattern) {
        return getFormatter(pattern).format(yearMonth);
    }

    /**
     * 格式化年月
     *
     * @param yearMonth 年月对象
     * @return 年月字符串
     */
    public static String toYearMonthStringFormat(YearMonth yearMonth) {
        return FrameworkConstants.YEAR_MONTH_FORMATTER.format(yearMonth);
    }

    /**
     * 格式化年月
     *
     * @return 年月字符串
     */
    public static String toYearMonthStringFormat() {
        return FrameworkConstants.YEAR_MONTH_FORMATTER.format(YearMonth.now());
    }

    /**
     * 格式化年月
     *
     * @param pattern 年月格式
     * @return 年月字符串
     */
    public static String toYearMonthStringFormat(String pattern) {
        return getFormatter(pattern).format(YearMonth.now());
    }

    /**
     * 格式化年
     *
     * @return 年字符串
     */
    public static String toYearStringFormat() {
        return FrameworkConstants.YEAR_FORMATTER.format(Year.now());
    }

    /**
     * 格式化年
     *
     * @param year 年对象
     * @return 年字符串
     */
    public static String toYearStringFormat(Year year) {
        return FrameworkConstants.YEAR_FORMATTER.format(year);
    }

    /**
     * LocalDateTime转为Date对象
     *
     * @param localDateTime 日期时间对象
     * @return Date对象
     */
    public static Date toDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }


    /**
     * LocalDate转为Date对象
     *
     * @param localDate 日期对象
     * @return Date对象
     */
    public static Date toDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    /**
     * 日期对象转LocalDateTime对象
     *
     * @param date 日期对象
     * @return 日期时间对象
     */
    public static LocalDateTime toLocalDateTime(Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }

    /**
     * 解析时间对象
     *
     * @param time 时间字符串
     * @return 时间对象
     */
    public static LocalTime parseLocalTime(String time) {
        return LocalTime.parse(time, FrameworkConstants.TIME_FORMATTER);
    }

    /**
     * 解析时间对象
     * 转为解析 消息队列 CSharp 空时间问题
     *
     * @param time 时间字符串
     * @return 时间对象
     */
    public static LocalTime parseCSharpLocalTime(String time) {
        LocalTime localTime;
        try {
            localTime = LocalTime.parse(time, FrameworkConstants.TIME_FORMATTER);
        } catch (DateTimeParseException e) {
            localTime = LocalTime.now();
        }
        return localTime;
    }

    /**
     * 是否是当天
     *
     * @param date 日期
     * @return bool
     */
    public static boolean isToday(Date date) {
        return isToday(toLocalDateTime(date));
    }

    /**
     * 是否是当天
     *
     * @param localDateTime 日期
     * @return bool
     */
    public static boolean isToday(LocalDateTime localDateTime) {
        return StringUtils.equals(toDateStringFormat(localDateTime), toDateStringFormat(LocalDate.now()));
    }

    /**
     * 日期时间字符串转日期字符串
     *
     * @param dateTimeString 日期时间字符串
     * @return 日期字符串
     */
    public static String dateTimeStringToDateString(String dateTimeString) {
        return StrUtil.sub(dateTimeString, 0, 10);
    }

    /**
     * 判断是否为指定的日期类型
     *
     * @param date    日期字符串
     * @param pattern 格式
     * @return bool
     */
    public static boolean isValidDate(String date, String pattern) {
        if (StringUtils.isAnyBlank(date, pattern)) {
            return false;
        }

        DateTimeFormatter formatter = getFormatter(pattern);
        try {
            formatter.parse(date);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    /**
     * 获取两个时间分钟差
     *
     * @param beginDateTime 开始时间
     * @param endDateTime   结束时间
     * @return 相差分钟
     */
    public static Duration getDuration(LocalDateTime beginDateTime, LocalDateTime endDateTime) {
        if (Objects.isNull(beginDateTime) || Objects.isNull(endDateTime)) {
            return Duration.ZERO;
        }

        return Duration.between(beginDateTime, endDateTime);
    }

    /**
     * 获取从0点开始的分钟数
     *
     * @param dateTime 日期时间对象
     * @return 一天中的duration偏移
     */
    public static Duration getDayOffsetDuration(LocalDateTime dateTime) {
        if (Objects.isNull(dateTime)) {
            return Duration.ZERO;
        }

        return Duration.between(dateTime.toLocalDate().atStartOfDay(), dateTime);
    }


}
