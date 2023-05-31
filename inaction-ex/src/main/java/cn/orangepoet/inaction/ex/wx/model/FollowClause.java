package cn.orangepoet.inaction.ex.wx.model;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author orange.cheng
 * @date 2023/3/15
 */
@Getter
@Setter
@Builder
public class FollowClause {
    private String appId;
    /**
     * 手机号
     */
    private String mobiles;
    /**
     * 蔚来userId
     */
    private String userIds;
    /**
     * and关系的tags
     */
    private String andTags;
    /**
     * or关系的tags
     */
    private String orTags;
    /**
     * 注册来源
     */
    private String sources;
    /**
     * 关联状态
     */
    private Boolean associateNio;
    /**
     * 关注状态
     */
    private Integer subscribe;
    /**
     * 关注来源
     */
    private String subscribeScenes;
    /**
     * 最后一次互动时间
     */
    private DateRange interactLastTime;
    /**
     * 关注时间
     */
    private DateRange subscribeTime;
    /**
     * 取关时间
     */
    private DateRange unSubscribeTime;
    /**
     * 互动次数
     */
    private Integer interactTimes;

    @Getter
    @Setter
    public static class DateRange {
        private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        private String start;
        private String end;

        public static DateRange of(LocalDateTime start, LocalDateTime end) {
            DateRange dateRange = new DateRange();
            dateRange.setStart(start.format(FORMATTER));
            dateRange.setEnd(end.format(FORMATTER));
            return dateRange;
        }
    }
}
