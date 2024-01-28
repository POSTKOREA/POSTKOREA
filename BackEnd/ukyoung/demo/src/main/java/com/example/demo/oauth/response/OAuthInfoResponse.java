package com.example.demo.oauth.response;

import com.example.demo.oauth.OAuthInfo;

public interface OAuthInfoResponse {
    String getEmail();
    String getNickname();
    String getProfileImg();
    OAuthInfo getOAuthInfo();
}
