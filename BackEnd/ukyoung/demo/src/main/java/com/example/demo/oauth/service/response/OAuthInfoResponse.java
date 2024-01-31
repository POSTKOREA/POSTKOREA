package com.example.demo.oauth.service.response;

import com.example.demo.oauth.service.OAuthInfo;

public interface OAuthInfoResponse {
    String getEmail();
    String getNickname();
    String getProfileImg();
    OAuthInfo getOAuthInfo();
}
