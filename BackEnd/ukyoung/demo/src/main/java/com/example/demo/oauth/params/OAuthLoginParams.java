package com.example.demo.oauth.params;

import com.example.demo.oauth.OAuthInfo;
import org.springframework.util.MultiValueMap;

public interface OAuthLoginParams {
    OAuthInfo oAuthInfo();
    MultiValueMap<String, String> makeBody();
}
