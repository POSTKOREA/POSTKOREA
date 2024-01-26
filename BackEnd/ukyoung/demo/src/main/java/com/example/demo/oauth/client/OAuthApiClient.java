package com.example.demo.oauth.client;

import com.example.demo.oauth.OAuthProvider;
import com.example.demo.oauth.params.OAuthLoginParams;
import com.example.demo.oauth.response.OAuthInfoResponse;

public interface OAuthApiClient {
    OAuthProvider oAuthProvider();
    String requestAccessToken(OAuthLoginParams params);
    OAuthInfoResponse requestOAuthInfo(String accessToken);
}
