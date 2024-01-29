package com.example.demo.controller.dto;

import com.example.demo.oauth.OAuthInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ModifyMemberDto {

    @JsonProperty("current_pwd")
    private String currentPwd;
    @JsonProperty("new_pwd")
    private String newPwd;
    @JsonProperty("user_nickname")
    private String userNickname;
    @JsonProperty("user_age")
    private Integer userAge;
    @JsonProperty("user_gender")
    private String userGender;
}
