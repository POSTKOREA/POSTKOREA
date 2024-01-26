package com.example.demo.oauth.response;

import com.example.demo.oauth.OAuthProvider;

public interface OAuthInfoResponse {
    String getEmail();
    String getNickname();
    String getProfileImg();
    OAuthProvider getOAuthProvider();
}
