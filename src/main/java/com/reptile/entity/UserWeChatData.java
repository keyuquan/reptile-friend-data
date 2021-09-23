package com.reptile.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class UserWeChatData {
    @JsonProperty("data")
    private UserWeChatData.DataDTO data;
    @JsonProperty("FlagString")
    private String flagString;
    @JsonProperty("Flag")
    private Integer flag;

    @NoArgsConstructor
    @Data
    public static class DataDTO {
        @JsonProperty("wechat")
        private String wechat;
    }
}
