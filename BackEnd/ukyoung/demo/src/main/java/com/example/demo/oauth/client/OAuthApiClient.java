package com.example.demo.oauth.client;

import com.example.demo.oauth.OAuthInfo;
import com.example.demo.oauth.params.OAuthLoginParams;
import com.example.demo.oauth.response.OAuthInfoResponse;

public interface OAuthApiClient {
    OAuthInfo oAuthInfo();
    String requestAccessToken(OAuthLoginParams params);
    OAuthInfoResponse requestOAuthInfo(String accessToken);
}
