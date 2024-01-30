package com.example.demo.oauth.service.params;

import com.example.demo.oauth.service.OAuthInfo;
import org.springframework.util.MultiValueMap;

public interface OAuthLoginParams {
    OAuthInfo oAuthInfo();
    MultiValueMap<String, String> makeBody();
}
