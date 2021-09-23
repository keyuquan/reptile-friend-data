package com.reptile.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class UserActivityData implements Serializable {
    @JsonProperty("FlagString")
    private String FlagString;
    @JsonProperty("Flag")
    private Integer Flag;
    @JsonProperty("data")
    private List<DataDTO> data;

    @JsonIgnoreProperties(ignoreUnknown = true)
    @Data
    public static class DataDTO implements Serializable {
        @JsonProperty("date")
        private String date;
        @JsonProperty("create_time")
        private String createTime;
        @JsonProperty("is_vip")
        private Integer isVip;
        @JsonProperty("sex")
        private Integer sex;
        @JsonProperty("icon")
        private String icon;
        @JsonProperty("photo")
        private PhotoDTO photo;
        @JsonProperty("is_enrolls")
        private Integer isEnrolls;
        @JsonProperty("avatar")
        private String avatar;
        @JsonProperty("is_video_authentication")
        private Integer isVideoAuthentication;
        @JsonProperty("content")
        private String content;
        @JsonProperty("is_identity_authentication")
        private Integer isIdentityAuthentication;
        @JsonProperty("target")
        private String target;
        @JsonProperty("user_id")
        private Integer userId;
        @JsonProperty("enrolls_status")
        private Integer enrollsStatus;
        @JsonProperty("activity_type")
        private String activityType;
        @JsonProperty("nick_name")
        private String nickName;
        @JsonProperty("location")
        private String location;
        @JsonProperty("id")
        private Integer id;
        @JsonProperty("dating_status")
        private Integer datingStatus;
        @JsonProperty("place")
        private String place;
        @JsonProperty("start_date")
        private String startDate;
        @JsonProperty("is_pzp")
        private Integer isPzp;

        @JsonIgnoreProperties(ignoreUnknown = true)
        @Data
        public static class PhotoDTO implements Serializable {
            @JsonProperty("width")
            private Integer width;
            @JsonProperty("url")
            private String url;
            @JsonProperty("height")
            private Integer height;
        }
    }
}
