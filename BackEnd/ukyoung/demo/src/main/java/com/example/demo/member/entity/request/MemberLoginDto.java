package com.example.demo.member.entity.request;

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
