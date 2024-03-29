package com.ssafy.dmobile.member.controller;

import com.ssafy.dmobile.member.entity.Member;
import com.ssafy.dmobile.member.entity.response.MemberResponseDto;
import com.ssafy.dmobile.member.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member/manage")
@Tag(name = "Member Management", description = "회원관리 API Document")
public class MemberManageController {

    private final MemberService memberService;

    @GetMapping("/{memberId}")
    @Operation(summary = "개별 회원정보 조회", description = "회원 정보 조회를 진행합니다. memberId를 기준으로 조회하며 관리자만 가능합니다.")
    public ResponseEntity<?> getSpecificMemberDetail(
            @PathVariable Long memberId) {

        Member member = memberService.getMemberDetails(memberId);

        MemberResponseDto response = MemberResponseDto.builder()
                .memberEmail(member.getEmail())
                .memberName(member.getName())
                .memberNickname(member.getNickname())
                .memberProfileUrl(member.getProfileUrl())
                .memberAge(member.getAge())
                .memberPoint(member.getPoint())
                .memberGender(member.getGender())
                .memberAuth(member.getOAuthType())
                .memberRole(member.getRole())
                .memberAchieve(member.getAchieve())
                .build();

        return ResponseEntity.ok(response); // 200
    }

    @PutMapping("/{memberId}/{point}")
    @Operation(summary = "개별 회원 포인트 업데이트", description = "회원이 가진 포인트를 업데이트합니다. 관리자만 가능합니다.")
    public ResponseEntity<?> updateMemberPoint(
            @PathVariable Long memberId,
            @PathVariable Integer point) {

        Member member = memberService.getMemberById(memberId);

        member.setPoint(member.getPoint() + point);
        memberService.updateMemberDirectly(member);

        Map<String, Object> response = new HashMap<>();
        response.put("code", 0);
        response.put("msg", "succeed");

        return ResponseEntity.ok(response);
    }

    @PutMapping("/check/{email}")
    @Operation(summary = "사용자 확인용 API", description = "해당 이메일이 가입되어있는지 확인힙나디. 관리자만 가능합니다.")
    public ResponseEntity<?> checkEmailIsExist(
            @PathVariable String email) {

        memberService.findByMemberEmail(email);

        Map<String, Object> response = new HashMap<>();
        response.put("code", 0);
        response.put("msg", "succeed");

        return ResponseEntity.ok(response);
    }
}
