package com.example.demo.oauth.service.client;

import com.example.demo.oauth.service.OAuthInfo;
import com.example.demo.oauth.service.params.OAuthLoginParams;
import com.example.demo.oauth.service.response.OAuthInfoResponse;

public interface OAuthApiClient {
    OAuthInfo oAuthInfo();
    String requestAccessToken(OAuthLoginParams params);
    OAuthInfoResponse requestOAuthInfo(String accessToken);
}
