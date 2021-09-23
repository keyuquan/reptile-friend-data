package com.reptile.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class UserData {
    @JsonProperty("data")
    private DataDTO data;
    @JsonProperty("FlagString")
    private String flagString;
    @JsonProperty("Flag")
    private Integer flag;

    @NoArgsConstructor
    @Data
    public static class DataDTO {
        @JsonProperty("activity")
        private   List<UserActivityData.DataDTO> activity ;
        @JsonProperty("username")
        private String username;
        @JsonProperty("show_wechat")
        private Integer showWechat;
        @JsonProperty("distance")
        private Integer distance;
        @JsonProperty("signature")
        private String signature;
        @JsonProperty("show_homepage_tip")
        private String showHomepageTip;
        @JsonProperty("is_video_authentication")
        private Integer isVideoAuthentication;
        @JsonProperty("is_black")
        private Integer isBlack;
        @JsonProperty("province")
        private String province;
        @JsonProperty("risk_warning_tip")
        private String riskWarningTip;
        @JsonProperty("can_impression")
        private Integer canImpression;
        @JsonProperty("dating_list")
        private List<String> datingList;
        @JsonProperty("dynamics_list")
        private List<DynamicsListDTO> dynamicsList;
        @JsonProperty("annual_income")
        private String annualIncome;
        @JsonProperty("height")
        private Integer height;
        @JsonProperty("resident_city")
        private List<String> residentCity;
        @JsonProperty("profession")
        private String profession;
        @JsonProperty("is_follow")
        private Integer isFollow;
        @JsonProperty("sex")
        private Integer sex;
        @JsonProperty("wechat")
        private String wechat;
        @JsonProperty("avatar")
        private String avatar;
        @JsonProperty("photo_list")
        private List<String> photoList;
        @JsonProperty("dating_count")
        private Integer datingCount;
        @JsonProperty("unit")
        private String unit;
        @JsonProperty("online_state")
        private Integer onlineState;
        @JsonProperty("user_id")
        private Integer userId;
        @JsonProperty("login_time")
        private String loginTime;
        @JsonProperty("nick_name")
        private String nickName;
        @JsonProperty("interest_label")
        private List<InterestLabelDTO> interestLabel;
        @JsonProperty("is_risk_warning")
        private Integer isRiskWarning;
        @JsonProperty("impression_list")
        private List<ImpressionListDTO> impressionList;
        @JsonProperty("location")
        private String location;
        @JsonProperty("show_homepage")
        private Integer showHomepage;
        @JsonProperty("age")
        private Integer age;
        @JsonProperty("is_pzp")
        private Integer isPzp;

        @NoArgsConstructor
        @Data
        public static class DynamicsListDTO {
            @JsonProperty("hot_score")
            private Double hotScore;
            @JsonProperty("normal_score")
            private Integer normalScore;
            @JsonProperty("is_deleted")
            private Integer isDeleted;
            @JsonProperty("politics_score")
            private Double politicsScore;
            @JsonProperty("width")
            private Integer width;
            @JsonProperty("terror_score")
            private Integer terrorScore;
            @JsonProperty("url")
            private String url;
            @JsonProperty("porn_score")
            private Double pornScore;
            @JsonProperty("height")
            private Integer height;
        }

        @NoArgsConstructor
        @Data
        public static class InterestLabelDTO {
            @JsonProperty("icon_url")
            private String iconUrl;
            @JsonProperty("color")
            private String color;
            @JsonProperty("name")
            private String name;
            @JsonProperty("id")
            private Integer id;
            @JsonProperty("child")
            private List<String> child;
        }

        @NoArgsConstructor
        @Data
        public static class ImpressionListDTO {
            @JsonProperty("times")
            private Integer times;
            @JsonProperty("color")
            private String color;
            @JsonProperty("id")
            private Integer id;
            @JsonProperty("title")
            private String title;
        }
    }
}
