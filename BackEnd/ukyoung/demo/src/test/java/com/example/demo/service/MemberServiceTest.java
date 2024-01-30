package com.example.demo.service;

import com.example.demo.member.entity.dto.MemberDto;
import com.example.demo.oauth.service.OAuthInfo;
import org.junit.jupiter.api.Test;

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