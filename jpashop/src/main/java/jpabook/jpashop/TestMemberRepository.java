package jpabook.jpashop;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class TestMemberRepository {
    @PersistenceContext
    private EntityManager em;

    public Long save(TestMember member){
        em.persist(member);
        return member.getId();
    }

    public TestMember find(Long id) {
        return em.find(TestMember.class, id);
    }
}
