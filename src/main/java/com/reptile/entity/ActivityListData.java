package com.reptile.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class ActivityListData {
    @JsonProperty("Flag")
    private Integer flag;
    @JsonProperty("FlagString")
    private String flagString;
    @JsonProperty("data")
    private List<DataDTO> data;

    @NoArgsConstructor
    @Data
    public static class DataDTO {
        @JsonProperty("id")
        private Integer id;
        @JsonProperty("user_id")
        private Integer userId;
        @JsonProperty("dating_status")
        private Integer datingStatus;
        @JsonProperty("is_recommend")
        private Integer isRecommend;
        @JsonProperty("enroll_number")
        private Integer enrollNumber;
        @JsonProperty("status")
        private Integer status;
        @JsonProperty("place")
        private String place;
        @JsonProperty("activity_type")
        private String activityType;
        @JsonProperty("target")
        private String target;
        @JsonProperty("content")
        private String content;
        @JsonProperty("create_time")
        private String createTime;
        @JsonProperty("avatar")
        private String avatar;
        @JsonProperty("recent_enrolls_number")
        private Integer recentEnrollsNumber;
        @JsonProperty("nick_name")
        private String nickName;
        @JsonProperty("is_vip")
        private Integer isVip;
        @JsonProperty("location")
        private String location;
        @JsonProperty("sex")
        private Integer sex;
        @JsonProperty("is_video_authentication")
        private Integer isVideoAuthentication;
        @JsonProperty("photo")
        private List<PhotoDTO> photo;
        @JsonProperty("icon")
        private String icon;
        @JsonProperty("enrolls_status")
        private Integer enrollsStatus;
        @JsonProperty("is_enrolls")
        private Integer isEnrolls;
        @JsonProperty("is_pzp")
        private Integer isPzp;

        @NoArgsConstructor
        @Data
        public static class PhotoDTO {
            @JsonProperty("url")
            private String url;
            @JsonProperty("width")
            private Integer width;
            @JsonProperty("height")
            private Integer height;
        }
    }
}
