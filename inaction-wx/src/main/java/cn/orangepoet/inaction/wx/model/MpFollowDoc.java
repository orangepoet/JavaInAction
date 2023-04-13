package cn.orangepoet.inaction.wx.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;
import java.util.List;

import static org.springframework.data.elasticsearch.annotations.FieldType.Keyword;

/**
 * @author orange.cheng
 * @date 2023/2/16
 */
@Getter
@Setter
@Document(indexName = "flotage_mp")
public class MpFollowDoc {
    /**
     * Id
     */
    @Id
    private Long id;
    /**
     * 公众号ID
     */
    @Field(type = Keyword)
    private String appId;
    /**
     * [NIO]手机号
     */
    @Field(type = FieldType.Long)
    private String mobile;
    /**
     * [NIO]用户ID (关联状态 -> !=null)
     */
    @Field(type = FieldType.Long)
    private Long userId;
    /**
     * 标签ID组
     */
    @Field(type = FieldType.Integer)
    private List<Integer> tagIds;
    /**
     * 关注来源
     */
    @Field(type = Keyword)
    private String subscribeScene;
    /**
     * 关注状态
     */
    @Field(type = FieldType.Integer)
    private Integer subscribe;
    /**
     * 关注时间
     */
    @Field(type = FieldType.Date, format = DateFormat.date_hour_minute_second)
    private Date subscribeTime;
    /**
     * 取注时间
     */
    @Field(type = FieldType.Date, format = DateFormat.date_hour_minute_second)
    private Date ubSubscribeTime;
    /**
     * 最后一次互动时间
     */
    @Field(type = FieldType.Date, format = DateFormat.date_hour_minute_second)
    private Date interactLastTime;
    /**
     * 互动次数
     */
    @Field(type = FieldType.Integer)
    private Integer interactTimes;
}
