package hello.hellospring;

import hello.hellospring.repository.JdbcMemberRepository;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import hello.hellospring.repository.jdbcTemplateMemberRepository;
import hello.hellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class SpringConfig {

    private DataSource dataSource;

    @Autowired
    public SpringConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Bean
    public MemberService memberService(){
        return new MemberService(memberRepository());
    }
    // 빈을 통해 의존성을 주입해야 하는 환경은 repository가 수정될 여지가 있을 때
    // 예를 들면, 당장 데이터베이스가 정해지지 않았고 그 전까지는 메모리로 운영할 때 사용
    @Bean
    public MemberRepository memberRepository(){
        return new jdbcTemplateMemberRepository(dataSource);
//        return new JdbcMemberRepository(dataSource);
    }
}
