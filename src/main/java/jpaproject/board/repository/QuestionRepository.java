package jpaproject.board.repository;

import jpaproject.board.domain.Member;
import jpaproject.board.domain.Question;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class QuestionRepository {

    private final EntityManager em;

    /**
     * 질문등록
     */
    public void save(Question question){
        em.persist(question);
    }

    /**
     * 질문조회 - 아이디(PK)로 조회
     */
    public Question findOne(Long id){
        return em.find(Question.class, id);
    }

    /**
     * 질문 전체조회
     */
    public List<Question> findAll(){
        String query = "select q from Question q";
        return em.createQuery(query, Question.class)
                .getResultList();
    }

    /**
     * 질문 전체조회 - 페이징
     */
    public List<Question> findAll(int offset, int limit){
        String query = "select q from Question q";
        return em.createQuery(query, Question.class)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }

    /**
     * 해당 멤버가 쓴 질문글 조회
     */
    public List<Question> findByMember(Member member) {
        String query = "select q from Question q "
                +"where q.member = :member";

        return em.createQuery(query, Question.class)
                .setParameter("member", member)
                .getResultList();
    }
}
