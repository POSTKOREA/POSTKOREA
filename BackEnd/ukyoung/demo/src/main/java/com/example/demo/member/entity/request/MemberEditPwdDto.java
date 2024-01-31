package com.example.demo.member.entity.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class MemberEditPwdDto {

    @JsonProperty("user_email")
    private String userEmail;
    @JsonProperty("current_pwd")
    private String currentPwd;
    @JsonProperty("new_pwd")
    private String newPwd;
}
