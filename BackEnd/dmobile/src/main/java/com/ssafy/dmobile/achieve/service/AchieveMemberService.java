package com.ssafy.dmobile.achieve.service;

import com.ssafy.dmobile.achieve.entity.Achieve;
import com.ssafy.dmobile.achieve.entity.AchieveMember;
import com.ssafy.dmobile.achieve.entity.AchieveMemberKey;
import com.ssafy.dmobile.achieve.repository.AchieveMemberRepository;
import com.ssafy.dmobile.achieve.repository.AchieveRepository;
import com.ssafy.dmobile.exception.CustomException;
import com.ssafy.dmobile.exception.ExceptionType;
import com.ssafy.dmobile.member.repository.MemberRepository;
import com.ssafy.dmobile.visit.entity.MemberRelic;
import lombok.RequiredArgsConstructor;
import org.checkerframework.checker.units.qual.A;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AchieveMemberService {

    public final MemberRepository memberRepository;
    public final AchieveRepository achieveRepository;
    public final AchieveMemberRepository achieveMemberRepository;

    // 플래아어에게 업적 추가
    public void addAchieveInMember(Long memberId, Long achieveId) {

        AchieveMemberKey key = new AchieveMemberKey();
        key.setMemberId(memberId);
        key.setAchieveId(achieveId);

        AchieveMember achieveMember = new AchieveMember();
        achieveMember.setKey(key);
        achieveMember.setAchieve(achieveRepository.getReferenceById(achieveId));
        achieveMember.setMember(memberRepository.getReferenceById(memberId));
        achieveMember.setAchieveDate(new Date().getTime());

        achieveMemberRepository.save(achieveMember);
    }

    // 개별 업적의 정보 조회
    public Achieve getAchieveInfo(Long achieveId) {
        return achieveRepository.findById(achieveId).orElseThrow(
                () -> new CustomException(ExceptionType.ACHIEVE_NOT_FOUND_EXCEPTION)
        );
    }

    // 플레이어가 보유한 업적 조회
    public List<Achieve> getAchievesInMember(Long memberId) {
        return achieveMemberRepository.findByAchievesOwnedByMember(memberId);
    }

    // 플레이어가 보유하지 않은 업적 조회
    public List<Achieve> getAchievesNotInMember(Long memberId) {
        return achieveMemberRepository.findByAchievesNotOwnedByMember(memberId);
    }
    
}