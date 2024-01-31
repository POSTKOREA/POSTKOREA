package com.example.demo.member.service;

import com.example.demo.exception.CustomException;
import com.example.demo.exception.ExceptionType;
import com.example.demo.member.entity.request.*;
import com.example.demo.member.entity.Member;
import com.example.demo.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    // 회원가입
    @Transactional
    public Member registerMember(MemberDto memberDto) {
        
        // 이메일 중복 체크
        if(memberRepository.existsByEmail(memberDto.getUserEmail())) {
            throw new CustomException(ExceptionType.DUPLICATE_EMAIL_EXCEPTION);
        }

        // 닉네임 중복 체크
        if (memberRepository.existsByNickname(memberDto.getUserNickname())) {
            throw new CustomException(ExceptionType.DUPLICATE_NICKNAME_EXCEPTION);
        }

        // 비밀번호가 생성 규칙 체크 (예. 4자리 미만)
        if (memberDto.getUserPwd().length() < 4) {
            throw new CustomException(ExceptionType.INVALID_PASSWORD_FORMAT_EXCEPTION);
        }
        
        // Spring Security 를 통한 비밀번호 암호화
        String password = passwordEncoder.encode(memberDto.getUserPwd());

        // DTO 기반 Member 엔티티 생성
        Member member = Member.builder()
                .email(memberDto.getUserEmail())
                .password(password)
                .name(memberDto.getUserName())
                .nickname(memberDto.getUserNickname())
                .age(memberDto.getUserAge())
                .gender(memberDto.getUserGender())
                // 프로필 이미지의 경우 추가적인 작업 필요
                .oAuthInfo(memberDto.getUserAuth())
                .point(0)
                .build();
        
        return memberRepository.save(member);
    }

    // 로그인
    public Member loginMember(MemberLoginDto memberDto) {

        String email = memberDto.getUserEmail();
        String password = memberDto.getUserPwd();

        Optional<Member> optionalMember = memberRepository.findByEmail(email);
        // 이메일 정보 없는 경우
        if (optionalMember.isEmpty()) {
            throw new CustomException(ExceptionType.INVALID_LOGIN_EXCEPTION);
        }

        Member member = optionalMember.get();
        // 비밀번호 일치하지 않는 경우
        if (!passwordEncoder.matches(password, member.getPassword())) {
            throw new CustomException(ExceptionType.INVALID_LOGIN_EXCEPTION);
        }

        return member;
    }

    public Map<String, Object> getMemberDetails(Long id) {

        Optional<Member> optionalMember = memberRepository.findById(id);
        // 유저 정보가 없는 경우
        if (optionalMember.isEmpty()) {
            throw new CustomException(ExceptionType.MEMBER_NOT_FOUND_EXCEPTION);
        }

        Member member = optionalMember.get();
        Map<String, Object> response = new HashMap<>();

        // 아이디, OAuth 정보 생략
        response.put("user_email", member.getEmail());
        response.put("user_name", member.getName());
        response.put("user_nickname", member.getNickname());
        response.put("user_age", member.getAge());
        response.put("user_gender", member.getGender());
        response.put("user_point", member.getPoint());

        return response;
    }

    @Transactional
    public Member editMemberInfo(Long id, MemberEditDto memberDto) {

        Optional<Member> optionalMember = memberRepository.findById(id);
        // 유저 정보가 없는 경우
        if (optionalMember.isEmpty()) {
            throw new CustomException(ExceptionType.MEMBER_NOT_FOUND_EXCEPTION);
        }

        Member modifiedMember = optionalMember.get();
        Member.MemberBuilder builder = modifiedMember.toBuilder();
        // 현재 변경 가능한 필드 : 닉네임, 나이, 성별 (변경가능)

        // 닉네임 중복 체크
        if (memberRepository.existsByNickname(memberDto.getUserNickname())) {
            throw new CustomException(ExceptionType.DUPLICATE_NICKNAME_EXCEPTION);
        }

        if (memberDto.getUserNickname() != null) {
            builder.nickname(memberDto.getUserNickname());
        }
        if (memberDto.getUserAge() != null) {
            builder.age(memberDto.getUserAge());
        }
        if (memberDto.getUserGender() != null) {
            builder.gender(memberDto.getUserGender());
        }

        return memberRepository.save(builder.build());
    }

    @Transactional
    public Member editMemberPassword(Long id, MemberEditPwdDto pwdDto) {

        Optional<Member> optionalMember = memberRepository.findById(id);
        // 유저 정보가 없는 경우
        if (optionalMember.isEmpty()) {
            throw new CustomException(ExceptionType.MEMBER_NOT_FOUND_EXCEPTION);
        }

        Member modifiedMember = optionalMember.get();
        // 비밀번호 일치하지 않는 경우
        if (!passwordEncoder.matches(pwdDto.getCurrentPwd(), modifiedMember.getPassword())) {
            throw new CustomException(ExceptionType.INVALID_CURRENT_PASSWORD_EXCEPTION);
        }

        Member.MemberBuilder builder = modifiedMember.toBuilder();
        // 비밀번호가 생성 규칙 체크 (예. 4자리 미만)
        if (pwdDto.getNewPwd().length() < 4) {
            throw new CustomException(ExceptionType.INVALID_PASSWORD_FORMAT_EXCEPTION);
        }

        // Spring Security 를 통한 비밀번호 암호화
        String password = passwordEncoder.encode(pwdDto.getNewPwd());
        builder.password(password);

        return memberRepository.save(builder.build());
    }

    @Transactional
    public void removeMember(Long id) {
        memberRepository.deleteById(id);
    }

    public String generateTempoPassword(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

        Random random = new Random();
        StringBuilder sb = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            sb.append(characters.charAt(index));
        }

        return sb.toString();
    }

    @Transactional
    public Member editMemberTempoPassword(MemberFindPwdDto pwdDto) {

        Optional<Member> optionalMember = memberRepository.findByEmail(pwdDto.getUserEmail());
        // 유저 정보가 없는 경우
        if (optionalMember.isEmpty()) {
            throw new CustomException(ExceptionType.MEMBER_NOT_FOUND_EXCEPTION);
        }

        Member member = optionalMember.get();

        // 임시 비밀번호 생성
        String tempoPassword = generateTempoPassword(8);

        Member.MemberBuilder builder = member.toBuilder();

        // Spring Security 를 통한 비밀번호 암호화
        String password = passwordEncoder.encode(tempoPassword);
        builder.password(password);

        // 비밀번호 업데이트
        Member savedMember = memberRepository.save(builder.build());

        // 이메일 보내기
        emailService.sendTemporaryPassword(savedMember.getEmail(), savedMember.getName(), tempoPassword);

        return savedMember;
    }

    // Direct JPA Resources -----
    public Long getUserIdByEmail(String email) {
        return memberRepository.findByEmail(email)
                .map(Member::getId)
                .orElseThrow(() -> new CustomException(ExceptionType.MEMBER_NOT_FOUND_EXCEPTION));
    }

    public String getUserEmailById(Long id) {
        return memberRepository.findById(id)
                .map(Member::getEmail)
                .orElseThrow(() -> new CustomException(ExceptionType.MEMBER_NOT_FOUND_EXCEPTION));
    }
//    public boolean existsById(Long id) {
//        return memberRepository.existsById(id);
//    }
}
