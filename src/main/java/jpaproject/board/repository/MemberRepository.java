package jpaproject.board.repository;

import jpaproject.board.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

    private final EntityManager em;

    /**
     * 회원저장
     */
    public void save(Member member){
        em.persist(member);
    }

    /**
     * 회원조회 - 아이디(PK)로 조회
     */
    public Member findById(Long id){
        return em.find(Member.class, id);
    }

    /**
     * 회원 전체조회
     */
    public List<Member> findAll(){
        String query = "select m from Member m";
        return em.createQuery(query, Member.class)
                .getResultList();
    }

    /**
     * 회원삭제
     * 좋은 로직은 아닌것 같음.
     * 존재 유무에 따른 결과값 반환하는 방식으로 refactor
     */
    public void deleteMember(Long id){
        Member member = findById(id);
        em.remove(member);
    }

    /**
     * 회원검색 - 아이디 조회(아이디 중복여부확인)
     */
    public List<Member> findByIdid(String idid){
        String query = "select m from Member m "+
                "where m.idid = :idid";

        return em.createQuery(query,Member.class)
                .setParameter("idid",idid)
                .getResultList();
    }
}
