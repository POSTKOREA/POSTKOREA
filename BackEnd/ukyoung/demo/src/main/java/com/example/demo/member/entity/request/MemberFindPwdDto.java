package com.example.demo.member.entity.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class MemberFindPwdDto {

    @JsonProperty("user_email")
    private String userEmail;
    @JsonProperty("user_name")
    private String userName;
}