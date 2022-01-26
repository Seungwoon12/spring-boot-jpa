package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true) // 스프링이 제공하는 @Transactional을 쓰는게 좋음. join을 제외하고 나머지 메서드에 적용됨
@RequiredArgsConstructor // final이 붙은 필드만 갖고 생성자를 만들어준다. 이 방법이 권장됨.
public class MemberService {

    private final MemberRepository memberRepository;


    /**
     * 회원 가입
     * readOnly는 default가 false 임. 조회 시 readOnly = true로 해주는 게 성능상 좋음.
     * join은 데이터를 읽고 써야하는 작업이므로 readOnly = true를 하면 안 된다.
     */
//    @Transactional(readOnly = false)
    @Transactional
    public Long join(Member member) {
        validateDuplicateMember(member); // 중복 회원 검증
        memberRepository.save(member);
        return member.getId();
        
    }

    private void validateDuplicateMember(Member member) {
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    //회원 전체 조회
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    //단건 조회
    public Member findOne(Long memberId) {
        return memberRepository.findById(memberId).get();
    }

    @Transactional
    public void update(Long id, String name) {
        Member member = memberRepository.findById(id).get();
        member.setName(name);
    }
}
