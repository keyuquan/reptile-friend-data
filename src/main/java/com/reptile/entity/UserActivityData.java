package com.reptile.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class UserActivityData implements Serializable {

    @JsonProperty("Flag")
    private Integer Flag;
    @JsonProperty("FlagString")
    private String FlagString;
    @JsonProperty("data")
    private List<DataDTO> data;

    @JsonIgnoreProperties(ignoreUnknown = true)
    @Data
    public static class DataDTO implements Serializable {
        @JsonProperty("id")
        private Integer id;
        @JsonProperty("dating_status")
        private Integer datingStatus;
        @JsonProperty("create_time")
        private String createTime;
        @JsonProperty("target")
        private String target;
        @JsonProperty("date")
        private String date;
        @JsonProperty("place")
        private String place;
        @JsonProperty("activity_type")
        private String activityType;
        @JsonProperty("start_date")
        private String startDate;
        @JsonProperty("content")
        private Object content;
        @JsonProperty("user_id")
        private Integer userId;
        @JsonProperty("avatar")
        private String avatar;
        @JsonProperty("nick_name")
        private String nickName;
        @JsonProperty("location")
        private String location;
        @JsonProperty("sex")
        private Integer sex;
        @JsonProperty("is_video_authentication")
        private Integer isVideoAuthentication;
        @JsonProperty("is_identity_authentication")
        private Integer isIdentityAuthentication;
        @JsonProperty("icon")
        private String icon;
        @JsonProperty("enrolls_status")
        private Integer enrollsStatus;
        @JsonProperty("is_enrolls")
        private Integer isEnrolls;
        @JsonProperty("is_pzp")
        private Integer isPzp;
        @JsonProperty("is_vip")
        private Integer isVip;
        @JsonProperty("photo")
        private List<PhotoDTO> photo;

        @JsonIgnoreProperties(ignoreUnknown = true)
        @Data
        public static class PhotoDTO implements Serializable {
            @JsonProperty("url")
            private String url;
            @JsonProperty("width")
            private Integer width;
            @JsonProperty("height")
            private Integer height;
        }
    }
}
