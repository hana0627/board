package jpaproject.board.repository;

import jpaproject.board.domain.Answer;
import jpaproject.board.domain.Question;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class AnswerRepository {

    private final EntityManager em;


    /**
     * 답변등록
     */
    public void save(Answer answer){
        em.persist(answer);
    }

    /**
     * 답변조회 - 아이디(PK)로 조회
     */
    public Answer findOne(Long id){
        return em.find(Answer.class, id);
    }

    /**
     * 답변 전체조회
     */
    public List<Answer> findAll(){
        String query = "select a from Answer a";
        return em.createQuery(query, Answer.class)
                .getResultList();
    }

    /**
     * questionId를 통한 답변목록 조회
     */
    public List<Answer> findByQid(Question question) {
        String query = "select distinct a from Answer a "
                +"where a.question = :question";

        return em.createQuery(query, Answer.class)
                .setParameter("question" , question)
                .getResultList();

    }

    /**
     * memberId를 이용한 조회
     */
    public List<Answer> findByMember(Long memberId) {
        String query = "select a from Answer a "+
                "where a.memberId = :memberId";

        return em.createQuery(query,Answer.class)
                .setParameter("memberId", memberId)
                .getResultList();
    }
}
