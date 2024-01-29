package com.example.demo.service;

import com.example.demo.controller.dto.MemberDto;
import com.example.demo.oauth.OAuthInfo;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MemberServiceTest {

    @Test
    void registerMember() {

        MemberDto memberDto = MemberDto.builder()
                .userEmail("test@gmail.com")
                .userPwd("test1234")
                .userName("테스트이름")
                .userNickname("테스트닉네임")
                .userAge(20)
                .userGender("남")
                .userAuth(OAuthInfo.NONE)
                .build();


    }

    @Test
    void loginMember() {
    }

    @Test
    void getMemberById() {
    }

    @Test
    void updateMember() {
    }

    @Test
    void deleteMember() {
    }
}