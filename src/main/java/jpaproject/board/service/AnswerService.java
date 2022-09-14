package jpaproject.board.service;

import jpaproject.board.domain.Answer;
import jpaproject.board.domain.Question;
import jpaproject.board.repository.AnswerRepository;
import jpaproject.board.repository.MemberRepository;
import jpaproject.board.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class AnswerService {

    private final AnswerRepository answerRepository;
    private final QuestionRepository questionRepository;
    private final MemberRepository memberRepository;


    /**
     * 답변등록
     */
    @Transactional
    public Long create(Answer answer){
        log.info("== AnswerService - create == ");
        answerRepository.save(answer);
        return answer.getId();
    }


    /**
     * 답변 검색 - question을 이용한 검색
     */
    /*
      question entity를 조회해서 parameter로 넘기는 방법 외에도
      table join등을 활용하는 방법도 있을 것 같습니다.
     */
    public List<Answer> answers(Question question){
        log.info("== AnswerService - answers ==");
        return answerRepository.findByQid(question);

    }


    /**
     * 답변 검색 - memberId를 이용한 검색
     */
    public List<Answer> findByMemberId(Long memberId){
        log.info("== AnswerService - findByMemberId");
        return answerRepository.findByMember(memberId);
    }

}
