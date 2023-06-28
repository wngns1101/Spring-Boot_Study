package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
// final 필드에 한해서 자동으로 autowired가 적용된 생성자 생성해줌
@RequiredArgsConstructor
public class MemberService {

//    필드 인젝션 추천하지는 않음 변경이 안 되기 떄문
//    @Autowired
//    private MemberRepository memberRepository;

    private final MemberRepository memberRepository;

//    @Autowired // 생성자 하나만 있는 경우 자동으로 해줘서 생략해도 됨 얘는 간단히 어노테이션으로 할 수 있음
//    public MemberService(MemberRepository memberRepository) {
//        this.memberRepository = memberRepository;
//    }

    //회원 가입
    @Transactional
    public Long join(Member member) {
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Member findOne(Long memberId) {
        return memberRepository.findOne(memberId);
    }
}
