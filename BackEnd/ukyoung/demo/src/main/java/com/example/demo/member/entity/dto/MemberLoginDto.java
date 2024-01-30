package com.example.demo.member.entity.dto;

import com.example.demo.oauth.service.OAuthInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberLoginDto {

    @JsonProperty("user_email")
    private String userEmail;
    @JsonProperty("user_pwd")
    private String userPwd;
}
