package com.example.demo.service;

import com.example.demo.controller.dto.MemberDto;
import com.example.demo.controller.dto.ModifyMemberDto;
import com.example.demo.entity.Member;
import com.example.demo.repository.MemberRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    // 회원가입
    @Transactional
    public Member registerMember(MemberDto memberDto) {
        
        // 이메일 중복 체크
        if(memberRepository.existsByEmail(memberDto.getUserEmail())) {
            throw new IllegalArgumentException("이미 가입된 이메일 입니다.");
        }

        // 닉네임 중복 체크
        if (memberRepository.existsByNickname(memberDto.getUserNickname())) {
            throw new IllegalArgumentException("이미 사용중인 닉네임 입니다.");
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
                // 프로필 이미지의 경우 회원가입에서 입력 X, 기본값으로 입력 필요
                .oAuthInfo(memberDto.getUserAuth())
                .point(0)
                .build();
        
        // 회원정보 JPA를 통해 DB에 저장
        return memberRepository.save(member);
    }

    // 로그인
    public Member loginMember(MemberDto memberDto) {

        String email = memberDto.getUserEmail();
        String password = memberDto.getUserPwd();

        Optional<Member> optionalMember = memberRepository.findByEmail(email);
        // 이메일 정보 없는 경우
        if (optionalMember.isEmpty()) {
            throw new EntityNotFoundException("존재하지 않는 이메일입니다.");
        }

        Member member = optionalMember.get();
        // 비밀번호 일치하지 않는 경우
        if (!passwordEncoder.matches(password, member.getPassword())) {
            throw new IllegalArgumentException("잘못된 비밀번호 입니다.");
        }

        return member;
    }

    public Map<String, Object> getMemberDetails(Long id) {

        Optional<Member> optionalMember = memberRepository.findById(id);
        // 잘못된 토큰을 입력받은 경우
        if (optionalMember.isEmpty()) {
            throw new EntityNotFoundException("사용자를 찾을 수 없습니다.");
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
    public Member modifyMember(Long id, ModifyMemberDto memberDto) {
        // Setter 대신 Builder 이용하여 회원정보 수정
        return memberRepository.findById(id)
                .map(currentMember -> {
                    Member.MemberBuilder builder = currentMember.toBuilder();
                    // 이름, 이메일 변경 불가
                    if (memberDto.getUserNickname() != null) {
                        builder.nickname(memberDto.getUserNickname());
                    }
                    if (memberDto.getUserAge() != null) {
                        builder.age(memberDto.getUserAge());
                    }
                    if (memberDto.getUserGender() != null) {
                        builder.gender(memberDto.getUserGender());
                    }
                    
                    // 회원정보 변경 시 반드시 기존 비밀번호가 일치하는지 확인함
                    String currentPwd = currentMember.getPassword();
                    String requestPwd = memberDto.getCurrentPwd();
                    String newPwd = memberDto.getNewPwd();

                    if (!passwordEncoder.matches(requestPwd, currentPwd)) {
                        throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
                    }

                    // 빈 값이면 비밀번호 변경 안하고 넘어감
                    if(!newPwd.isEmpty()) {
                        builder.password(passwordEncoder.encode(newPwd));
                    }
                    return builder.build();
                })
                .map(memberRepository::save)
                .orElseThrow(() ->
                        new EntityNotFoundException("회원정보를 찾을 수 없습니다.")
                );
    }

    @Transactional
    public void removeMember(Long id) {
        memberRepository.deleteById(id);
    }

    public Long getUserIdByEmail(String email) {
        return memberRepository.findByEmail(email)
                .map(Member::getId)
                .orElseThrow(() -> new EntityNotFoundException("회원정보를 찾을 수 없습니다."));
    }

    public boolean existsById(Long id) {
        return memberRepository.existsById(id);
    }
}
