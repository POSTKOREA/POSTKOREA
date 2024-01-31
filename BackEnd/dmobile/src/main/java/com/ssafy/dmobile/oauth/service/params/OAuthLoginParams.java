package com.ssafy.dmobile.oauth.service.params;

import com.ssafy.dmobile.oauth.service.OAuthInfo;
import org.springframework.util.MultiValueMap;

public interface OAuthLoginParams {
    OAuthInfo oAuthInfo();
    MultiValueMap<String, String> makeBody();
}
