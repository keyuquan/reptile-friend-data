package com.reptile.entity;

import java.util.List;

@lombok.NoArgsConstructor
@lombok.Data
public class UserListData {
    @com.fasterxml.jackson.annotation.JsonProperty("data")
    private List<DataDTO> data;
    @com.fasterxml.jackson.annotation.JsonProperty("FlagString")
    private String flagString;
    @com.fasterxml.jackson.annotation.JsonProperty("Flag")
    private Integer flag;

    @lombok.NoArgsConstructor
    @lombok.Data
    public static class DataDTO {
        @com.fasterxml.jackson.annotation.JsonProperty("photo_num")
        private Integer photoNum;
        @com.fasterxml.jackson.annotation.JsonProperty("is_follow")
        private Integer isFollow;
        @com.fasterxml.jackson.annotation.JsonProperty("distance")
        private Integer distance;
        @com.fasterxml.jackson.annotation.JsonProperty("is_vip")
        private Integer isVip;
        @com.fasterxml.jackson.annotation.JsonProperty("sex")
        private Integer sex;
        @com.fasterxml.jackson.annotation.JsonProperty("avatar")
        private String avatar;
        @com.fasterxml.jackson.annotation.JsonProperty("is_video_authentication")
        private Integer isVideoAuthentication;
        @com.fasterxml.jackson.annotation.JsonProperty("is_identity_authentication")
        private Integer isIdentityAuthentication;
        @com.fasterxml.jackson.annotation.JsonProperty("unit")
        private String unit;
        @com.fasterxml.jackson.annotation.JsonProperty("online_state")
        private Integer onlineState;
        @com.fasterxml.jackson.annotation.JsonProperty("user_id")
        private String userId;
        @com.fasterxml.jackson.annotation.JsonProperty("login_time")
        private String loginTime;
        @com.fasterxml.jackson.annotation.JsonProperty("nick_name")
        private String nickName;
        @com.fasterxml.jackson.annotation.JsonProperty("location")
        private String location;
        @com.fasterxml.jackson.annotation.JsonProperty("age")
        private Integer age;
        @com.fasterxml.jackson.annotation.JsonProperty("height")
        private Integer height;
    }
}
