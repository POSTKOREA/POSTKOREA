package com.ssafy.dmobile.oauth.service.client;

import com.ssafy.dmobile.oauth.service.OAuthInfo;
import com.ssafy.dmobile.oauth.service.params.OAuthLoginParams;
import com.ssafy.dmobile.oauth.service.response.OAuthInfoResponse;

public interface OAuthApiClient {
    OAuthInfo oAuthInfo();
    String requestAccessToken(OAuthLoginParams params);
    OAuthInfoResponse requestOAuthInfo(String accessToken);
}
