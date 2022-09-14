package jpaproject.board.service;

import jpaproject.board.domain.Member;
import jpaproject.board.domain.Question;
import jpaproject.board.repository.MemberRepository;
import jpaproject.board.repository.QuestionRepository;
import jpaproject.board.repository.QuestionRepositoryDatajpa;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final MemberRepository memberRepository;
    private final QuestionRepositoryDatajpa questionRepositoryDatajpa;

    /**
     * 질문등록
     */
    @Transactional
    public Long create(Question question){
            log.info("== QuestionService - create == ");
            questionRepository.save(question);
            return question.getId();
    }


    /**
     * 질문 전체조회 - 페이징
     */
    public Page<Question> findQuestions(Pageable pageable) {
        log.info("== QuestionService - findQuestions ==");
        return questionRepositoryDatajpa.findAll(pageable);
    }


    /**
     * 질문 조회 - id(PK)로 조회
     */
    public Question findById(Long id){
        log.info("== QuestionService - findById ==");
        return questionRepository.findOne(id);
    }

    /**
     * 질문조회 - member를 이용한 조회
     */
    public List<Question> findByMemberId(Long id){
        log.info("== QuestionService - findByMemberId ==");
        Member member = memberRepository.findById(id);
        List<Question> questions = questionRepository.findByMember(member);
        return questions;
    }

}



/*
-- 폐기코드 --

// 질문전체조회
* 사유 : 페이징처리를 위한 파라미터값 변경

public List<Question> findQuestions(){
    log.info("== QuestionService - findQuestions ==");
    return questionRepository.findAll();
}

//내가 쓴 질문 조회
* 사유 : 페이징 처리 및 html파일 재사용을 위해 다음과 같은 방법을 사용하려 했으나
        스프링 dataJpa 활용능력 부족으로 구현하지 못하였음.


        public Page<Question> myQuestions(Pageable pageable, Long id) {
        log.info("== QuestionService - myQuestions ==");
        Member member = memberRepository.findById(id);
        return questionRepositoryDatajpa.QUESTIONS(pageable, member);
    }
 */
