package com.example.demo.member.controller;

import com.example.demo.member.entity.dto.MemberDto;
import com.example.demo.member.entity.dto.MemberEditDto;
import com.example.demo.member.entity.Member;
import com.example.demo.member.entity.dto.MemberEditPwdDto;
import com.example.demo.member.entity.dto.MemberLoginDto;
import com.example.demo.member.service.MemberService;
import com.example.demo.utils.AuthTokens;
import com.example.demo.utils.AuthTokensGenerator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
@Tag(name = "Member", description = "회원관리 API Document")
public class MemberController {

    private final MemberService memberService;
    private final AuthTokensGenerator authTokensGenerator;

    // 회원가입
    @PostMapping("/signup")
    @Operation(summary = "회원가입", description = "회원 가입 절차를 진행합니다.")
    public ResponseEntity<?> registerMember(
            @RequestBody MemberDto memberDto) {

        Member registeredMember = memberService.registerMember(memberDto);

        Map<String, Object> response = new HashMap<>();
        response.put("code", 0);
        response.put("msg", "succeed");
        response.put("user_email", registeredMember.getEmail());

        return ResponseEntity.status(HttpStatus.CREATED).body(response); // 201
    }

    // 로그인
    @PostMapping("/login")
    @Operation(summary = "로그인", description = "회원 로그인 절차를 진행하며, 반환값으로 토큰이 주어집니다.")
    public ResponseEntity<?> loginMember(
            @RequestBody MemberLoginDto memberDto) {
        Member loginMember = memberService.loginMember(memberDto);

        Long userId = loginMember.getId();
        AuthTokens tokens = authTokensGenerator.generate(userId);

        Map<String, Object> response = new HashMap<>();
        response.put("code", 0);
        response.put("msg", "succeed");
        response.put("access_token", tokens.getAccessToken());
        response.put("refresh_token", tokens.getRefreshToken());
        response.put("grant_type", tokens.getGrantType());
        response.put("expires_in", tokens.getExpiresIn());

        return ResponseEntity.ok(response);
    }

    // 유저 정보 가져오기
    @GetMapping
    @Operation(summary = "회원정보 조회", description = "회원 정보 조회를 진행합니다. 인가 과정에서 Token이 사용됩니다.")
    public ResponseEntity<?> getMemberDetails(
            @RequestHeader("Authorization") String token) {

        // 토큰을 통해 userId 추출 후 Member 객체 생성
        Long userId = authTokensGenerator.extractMemberId(token);
        Map<String, Object> response = memberService.getMemberDetails(userId);
        response.put("code", 0);
        response.put("msg", "succeed");

        return ResponseEntity.ok(response); // 200
    }

    // 회원정보 수정
    @PutMapping("/edit")
    @Operation(summary = "회원정보 수정", description = "회원 정보 수정을 진행합니다. 인가 과정에서 Token이 사용됩니다.")
    public ResponseEntity<?> editMemberInfo(
            @RequestHeader("Authorization") String token,
            @RequestBody MemberEditDto memberDto) {
        // 토큰을 통해 userId 추출 후 Member 객체 생성
        Long userId = authTokensGenerator.extractMemberId(token);
        Member modifiedMember = memberService.editMemberInfo(userId, memberDto);

        Map<String, Object> response = new HashMap<>();
        response.put("user_email", modifiedMember.getEmail());
        response.put("code", 0);
        response.put("msg", "succeed");

        return ResponseEntity.status(HttpStatus.CREATED).body(response); // 201
    }
    
    // 비밀번호 수정
    @PutMapping("/edit-password")
    @Operation(summary = "비밀번호 수정", description = "비밀번호 수정을 진행합니다. 인가 과정에서 Token이 사용되며, 기존 비밀번호 재인증이 진행됩니다.")
    public ResponseEntity<?> editMemberPassword(
            @RequestHeader("Authorization") String token,
            @RequestBody MemberEditPwdDto pwdDto) {
        // 토큰을 통해 userId 추출 후 Member 객체 생성
        Long userId = authTokensGenerator.extractMemberId(token);
        Member modifiedMember = memberService.editMemberPassword(userId, pwdDto);

        Map<String, Object> response = new HashMap<>();
        response.put("user_email", modifiedMember.getEmail());
        response.put("code", 0);
        response.put("msg", "succeed");

        return ResponseEntity.status(HttpStatus.CREATED).body(response); // 201
    }

    @DeleteMapping
    @Operation(summary = "회원탈퇴", description = "회원 정보 삭제를 진행합니다. 인가 과정에서 Token이 사용됩니다.")
    public ResponseEntity<?> removeMember(
            @RequestHeader("Authorization") String token) {
        // 토큰을 통해 userId 추출 후 Member 객체 생성
        Long userId = authTokensGenerator.extractMemberId(token);
        memberService.removeMember(userId);

        Map<String, Object> response = new HashMap<>();
        response.put("code", 0);
        response.put("msg", "succeed");

        return ResponseEntity.ok(response); // 200
    }
}
