package com.reptile.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class UserWeChatData implements Serializable {

    @JsonProperty("Flag")
    private Integer Flag;
    @JsonProperty("FlagString")
    private String FlagString;
    @JsonProperty("data")
    private DataDTO data;

    @JsonIgnoreProperties(ignoreUnknown = true)
    @Data
    public static class DataDTO implements Serializable {
        @JsonProperty("wechat")
        private String wechat;
    }
}
