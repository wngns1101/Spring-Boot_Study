package jpabook.jpashop.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jpabook.jpashop.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

// 스프링이 컴포넌트 스캔을 통해 스프링 bean에 자동으로 올려준다.
@Repository
@RequiredArgsConstructor
public class MemberRepository {
// 이것도 service 처럼 표현 가능
//    @PersistenceContext
//    private EntityManager em;
    private final EntityManager em;
    public void save(Member member) {
        em.persist(member);
    }

    public Member findOne(Long id) {
        return em.find(Member.class, id);
    }

    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class).getResultList();
    }

    public List<Member> findByName(String name) {
        return em.createQuery("select m from Member m where m.name = :name", Member.class).setParameter("name", name).getResultList();
    }
}
