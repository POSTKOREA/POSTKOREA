package com.ssafy.dmobile.oauth.service.response;

import com.ssafy.dmobile.oauth.service.OAuthInfo;

public interface OAuthInfoResponse {
    String getEmail();
    String getNickname();
    String getProfileImg();
    OAuthInfo getOAuthInfo();
}
