package com.example.demo.oauth.service;

import com.example.demo.member.entity.Member;
import com.example.demo.oauth.service.params.OAuthLoginParams;
import com.example.demo.oauth.service.response.OAuthInfoResponse;
import com.example.demo.member.repository.MemberRepository;
import com.example.demo.utils.AuthTokens;
import com.example.demo.utils.AuthTokensGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OAuthLoginService {

    private final MemberRepository memberRepository;
    private final AuthTokensGenerator authTokensGenerator;
    private final RequestOAuthInfoService requestOAuthInfoService;

    // params 기준으로 login 실행

    public AuthTokens login(OAuthLoginParams params) {
        OAuthInfoResponse oAuthInfoResponse = requestOAuthInfoService.request(params);
        Long memberId = findOrCreateMember(oAuthInfoResponse);
        return authTokensGenerator.generate(memberId);
    }

    private Long findOrCreateMember(OAuthInfoResponse oAuthInfoResponse) {
        return memberRepository.findByEmail(oAuthInfoResponse.getEmail())
                .map(Member::getId)
                .orElseGet(() -> newMember(oAuthInfoResponse));
    }

    //
    private Long newMember(OAuthInfoResponse oAuthInfoResponse) {
        Member member = Member.builder()
                .email(oAuthInfoResponse.getEmail())
                .nickname(oAuthInfoResponse.getNickname())
                .profileImg(oAuthInfoResponse.getProfileImg())
                .oAuthInfo(oAuthInfoResponse.getOAuthInfo())
                .build();
        return memberRepository.save(member).getId();
    }
}
