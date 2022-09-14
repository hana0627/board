package jpaproject.board.controller;

import jpaproject.board.domain.Answer;
import jpaproject.board.domain.Member;
import jpaproject.board.domain.Qstatus;
import jpaproject.board.domain.Question;
import jpaproject.board.service.AnswerService;
import jpaproject.board.service.MemberService;
import jpaproject.board.service.QuestionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class AnswerController {

    private final MemberService memberService;
    private final QuestionService questionService;
    private final AnswerService answerService;


    /**
     * 답변등록 Form
     */
    @GetMapping("/answers/create")
    public String createForm(Model model,
                             @RequestParam Long questionId) {
        log.info("== Controller - createForm ==");
        log.info("questionId = {}", questionId);
        model.addAttribute("answerForm", new AnswerForm());
        model.addAttribute("questionId", questionId);
        return "/answers/createAnswerForm";
    }


    /**
     * 답변등록
     */

    /*
    Have to Study
      Member - Question 간 1:N 매핑이 되어있고
      Question - Answer 간 1:N 매핑이 되어있는 상황에서

      Member - Question 사이에 1:N 매핑을 추가하는 테이블 설계는 본 적이 없고 좋은방법도 아니라고 생각합니다.
      다만 이렇게 매핑없이 진행하였을때, 질문글 작성자의 id와, 답변글 작성자의 id를 구분할 방법이 없어집니다.

      -> 해결방안
        테이블간 매핑을 하지 않은채, Answer 테이블에 memeber_id컬럼을 추가하여
        해당값은 session으로 받은값을 넣도록 하였습니다.
        view페이지로 넘겨줄때 해당 member_id값을 이용하여 작성자를 구하는 방법을 사용하였습니다.

        역시 좋은방법은 아니라고 생각합니다. 좀 더 공부하면서 더 나은방법을 찾겠습니다.
     */
    @PostMapping("/answers/create")
    public String create(Model model, AnswerForm form,
                         @RequestParam Long memberId,
                         @RequestParam Long questionId) {

        log.info("== Controller - create ==");
        log.info("memberId ={}", memberId);
        log.info("questionId = {}", questionId);

        Question question = questionService.findById(questionId);
        question.setQstatus(Qstatus.FINISH);

        Answer answer = new Answer();
        answer.setTitle(form.getTitle());
        answer.setContent(form.getTitle());
        answer.setQuestion(question);
        answer.setMemberId(memberId);

        answerService.create(answer);

        return "redirect:/";
    }


    /**
     * 답변보기
     */
    @GetMapping("/answers/{id}/view")
    public String answerView(@PathVariable("id") Long id, Model model) {
        log.info("== Controller - answerView ==");
        Question question = questionService.findById(id);
        List<Answer> answers = answerService.answers(question);

        model.addAttribute("answers", answers);
        return "/answers/answerList";
    }

    /**
     * 내 답변보기
     */
    @GetMapping("/answers/{id}/list")
    public String myAnswers(@PathVariable("id") Long id, Model model) {
        log.info("== Controller myAnswers ==");
        List<Answer> answers = answerService.findByMemberId(id);
        model.addAttribute("answers", answers);

        return "/answers/answerList";
    }
}