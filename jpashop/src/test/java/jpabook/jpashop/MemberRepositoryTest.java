package jpabook.jpashop;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MemberRepositoryTest{
    @Autowired
    private MemberRepository memberRepository;

    @Test
    // Transactional이 Test에 있으면 정상적으로 수행한 후 롤백한다.
    @Transactional
    // 데이터 변경 떄문에 Transactional을 무조건 써야하지만 Transactional 때문에 롤백이 일어나는 것을 막고 싶다면 Rollback 어노테이션을 추가하면 된다.
    @Rollback(false)
    public void testMember() throws Exception{
        //given
        Member member = new Member();
        member.setUsername("memberA");
        //when
        Long saveId = memberRepository.save(member);
        Member findMember = memberRepository.find(saveId);
        //then
        //Transactional 없이 돌리면 에러가 남 이유는 EntityManager를 통한 모든 데이터 변경은 Transaction 안에서 이루어져야 하기 때문
        // 두 값을 비교할 때 사용
        Assertions.assertThat(findMember.getId()).isEqualTo(member.getId());
        Assertions.assertThat(findMember.getId()).isEqualTo(member.getId());
        Assertions.assertThat(findMember).isEqualTo(member);
    }
}