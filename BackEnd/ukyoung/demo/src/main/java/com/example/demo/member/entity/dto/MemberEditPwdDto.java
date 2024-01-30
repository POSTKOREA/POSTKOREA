package com.example.demo.member.entity.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class MemberEditPwdDto {

    @JsonProperty("current_pwd")
    private String currentPwd;
    @JsonProperty("new_pwd")
    private String newPwd;
}
