package com.bestrookie.model.param;


/**
 * @author : bestrookie
 * @date : 15:45 2020/10/24
 */
public enum BannedEnum {
    /**
     * 封禁一天
     */
    ONE_DAY(86400000L),
    /**
     * 封禁一周
     */
    ONE_WEEK(604800000L),
    /**
     * 封禁半个月
     */
    HALF_MONTH(1296000000L),
    /**
     * 封禁一个月
     */
    ONE_MONTH(2592000000L);
    private long day;
    BannedEnum(long day){
        this.day = day;
    }

    @Override
    public String toString() {
        return String.valueOf(this.day);
    }
}
