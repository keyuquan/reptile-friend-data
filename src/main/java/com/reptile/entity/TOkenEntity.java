package com.reptile.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class TOkenEntity implements Serializable {
    @JsonProperty("data")
    private DataDTO data;
    @JsonProperty("errCode")
    private Integer errCode;
    @JsonProperty("errMsg")
    private String errMsg;

    @JsonIgnoreProperties(ignoreUnknown = true)
    @Data
    public static class DataDTO implements Serializable {
        @JsonProperty("uid")
        private String uid;
        @JsonProperty("expiredTime")
        private Integer expiredTime;
        @JsonProperty("token")
        private String token;
    }
}
