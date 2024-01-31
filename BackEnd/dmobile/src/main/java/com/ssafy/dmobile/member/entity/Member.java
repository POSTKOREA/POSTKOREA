package com.ssafy.dmobile.member.entity;

import com.ssafy.dmobile.oauth.service.OAuthInfo;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Getter
@Entity
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String password;
    private String name;
    private String nickname;
    private Integer age;
    private String gender;
    private String profileImg;
    private Integer point = 0;
    private OAuthInfo oAuthInfo;
}
