package com.example.demo.controller;

import com.example.demo.controller.dto.MemberDto;
import com.example.demo.controller.dto.ModifyMemberDto;
import com.example.demo.entity.Member;
import com.example.demo.service.MemberService;
import com.example.demo.utils.AuthTokens;
import com.example.demo.utils.AuthTokensGenerator;
import com.example.demo.utils.JwtTokenProvider;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class MemberController {

    private final MemberService memberService;
    private final AuthTokensGenerator authTokensGenerator;

    // 회원가입
    @PostMapping("/signup")
    public ResponseEntity<?> registerMember(@RequestBody MemberDto memberDto) {
        try {
            Member registeredMember = memberService.registerMember(memberDto);

            Map<String, Object> response = new HashMap<>();
            response.put("user_email", registeredMember.getEmail());
            response.put("code", 0);
            response.put("msg", "succeed");

            return ResponseEntity.status(HttpStatus.CREATED).body(response); // 201
        } catch (IllegalArgumentException e) {
            Map<String, Object> response = new HashMap<>();
            response.put("code", 1);
            response.put("msg", e.getMessage());    // 이미 사용중인 이메일 or 닉네임

            return ResponseEntity.status(HttpStatus.CONFLICT).body(response); // 409
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("code", 2);
            response.put("msg", e.getMessage());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response); // 500
        }
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<?> loginMember(@RequestBody MemberDto memberDto) {
        try {
            Member loginMember = memberService.loginMember(memberDto);
            Long userId = loginMember.getId();
            AuthTokens tokens = authTokensGenerator.generate(userId);

            Map<String, Object> response = new HashMap<>();
            response.put("code", 0);
            response.put("msg", "succeed");

            response.put("accessToken", tokens.getAccessToken());
            response.put("refreshToken", tokens.getRefreshToken());
            response.put("grantType", tokens.getGrantType());
            response.put("expiresIn", tokens.getExpiresIn());

            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            Map<String, Object> response = new HashMap<>();
            response.put("code", 1);
            response.put("msg", e.getMessage());    // 잘못된 비밀번호 입니다.

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response); // 401

        } catch (EntityNotFoundException e) {
            Map<String, Object> response = new HashMap<>();
            response.put("code", 2);
            response.put("msg", e.getMessage());    // 존재하지 않는 이메일입니다.

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response); // 404
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("code", 3);
            response.put("msg", e.getMessage());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response); // 500
        }
    }

    // 유저 정보 가져오기 : accessToken에 대해 디코딩 후 DB조회
    @GetMapping
    public ResponseEntity<?> getMemberDetails(@RequestHeader("Authorization") String token) {
        try {
            // 토큰을 통해 userId 추출 후 Member 객체 생성
            Long userId = authTokensGenerator.extractMemberId(token);
            Map<String, Object> response = memberService.getMemberDetails(userId);
            response.put("code", 0);
            response.put("msg", "succeed");

            return ResponseEntity.ok(response); // 200
        } catch (EntityNotFoundException e) {
            Map<String, Object> response = new HashMap<>();
            response.put("code", 1);
            response.put("msg", e.getMessage());    // 사용자를 찾을 수 없습니다.

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response); // 401
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("code", 2);
            response.put("msg", e.getMessage());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response); // 500
        }
    }

    // 회원정보 수정, 기존 비밀번호가 일치하는지 아닌지 대조 필수
    @PutMapping
    public ResponseEntity<?> modifyMember(
            @RequestHeader("Authorization") String token,
            @RequestBody ModifyMemberDto memberDto) {
        try {
            // 토큰을 통해 userId 추출 후 Member 객체 생성
            Long userId = authTokensGenerator.extractMemberId(token);
            Member modifiedMember = memberService.modifyMember(userId, memberDto);

            Map<String, Object> response = new HashMap<>();
            response.put("user_email", modifiedMember.getEmail());
            response.put("code", 0);
            response.put("msg", "succeed");

            return ResponseEntity.status(HttpStatus.CREATED).body(response); // 201
        } catch (IllegalArgumentException e) {
            Map<String, Object> response = new HashMap<>();
            response.put("code", 1);
            response.put("msg", e.getMessage());    // 비밀번호가 일치하지 않습니다.

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response); // 401

        } catch (EntityNotFoundException e) {
            Map<String, Object> response = new HashMap<>();
            response.put("code", 2);
            response.put("msg", e.getMessage());    // 회원정보를 찾을 수 없습니다.

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response); // 404
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("code", 3);
            response.put("msg", e.getMessage());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response); // 500
        }
    }

    @DeleteMapping
    public ResponseEntity<?> removeMember(@RequestHeader("Authorization") String token) {
        try {
            // 토큰을 통해 userId 추출 후 Member 객체 생성
            Long userId = authTokensGenerator.extractMemberId(token);
            memberService.removeMember(userId);

            Map<String, Object> response = new HashMap<>();
            response.put("code", 0);
            response.put("msg", "succeed");

            return ResponseEntity.ok(response); // 200
        } catch (EntityNotFoundException e) {
            Map<String, Object> response = new HashMap<>();
            response.put("code", 1);
            response.put("msg", e.getMessage());    // 사용자를 찾을 수 없습니다.

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response); // 404
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("code", 2);
            response.put("msg", e.getMessage());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response); // 500
        }
    }

}
