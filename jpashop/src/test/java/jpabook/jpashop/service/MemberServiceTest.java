package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

@RunWith(SpringRunner.class) // junit을 스프링과 함께 실행할거야
@SpringBootTest // 스프링 부트를 띄운 상태로 테스트하기 위해 필요. 스프링 컨테이너 안에서 테스트 돌리는거
@Transactional // 이게 있어야 테스트를 할 때 롤백이 됨
public class MemberServiceTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;
    @Autowired EntityManager em;

    @Test
//    @Rollback(false) // 이걸 해주면 insert 쿼리가 보임 + DB에 저장됨
    public void 회원가입() throws Exception {
        //given
        Member member = new Member();
        member.setName("kim");

        //when
        Long savedId = memberService.join(member);
        Member findMember = memberRepository.findById(savedId).get();

        //then
        em.flush(); // flush는 영속성 컨텍스트에 있는 것을 DB에 반      영하는 것임
        assertEquals(member, memberRepository.findById(savedId));


    }

    @Test(expected = IllegalStateException.class)
    public void 중복_회원_예외() throws Exception {
        //given
        Member member1 = new Member();
        member1.setName("kim");

        Member member2 = new Member();
        member2.setName("kim");

        //when
        memberService.join(member1);
        memberService.join(member2); //예외가 발생해야 한다.

        //then
        fail("예외가 발생해야 한다.");
    }

}