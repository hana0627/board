package jpaproject.board.controller;

import jpaproject.board.domain.Member;
import jpaproject.board.domain.Qstatus;
import jpaproject.board.domain.Question;
import jpaproject.board.service.MemberService;
import jpaproject.board.service.QuestionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class QuestionController {


    private final MemberService memberService;
    private final QuestionService questionService;


    /**
     * 질문등록페이지로 이동
     */

    @GetMapping("/questions/new")
    public String createForm(Model model){
        model.addAttribute("questionForm", new QuestionForm());
        log.info("== Controller - createForm ==");
        return "/questions/createQuestionForm";
    }

    /**
     * 질문등록
     */
    @PostMapping("/questions/new")
    public String create(Model model, QuestionForm form, String memberId){

        log.info("== Controller - create ==");
        //log.info("getMemberId={}" , form.getMemberId());
        //Long memberId = form.getMemberId();
        log.info("memberId={}" , memberId);
        Member member = memberService.findById(Long.parseLong(memberId));
        log.info("member.getId={}",member.getId());

        Question question = new Question();

        question.setTitle(form.getTitle());
        question.setContent(form.getContent());
        question.setQstatus(Qstatus.READY);
        question.setHit(0);


        question.setMember(member);

        questionService.create(question);

        return "redirect:/";
    }


    /**
     * 질문목록보기
     */
    @GetMapping("/questions/list")
    public String questionList(Model model, @PageableDefault(sort = "id") Pageable pageable) {
        log.info("== Controller - questionList ==");

        model.addAttribute("questions", questionService.findQuestions(pageable));
        model.addAttribute("previous", pageable.previousOrFirst().getPageNumber());
        model.addAttribute("next", pageable.next().getPageNumber());

        return "/questions/questionList";
    }

    /**
     * 질문내용보기
     */
    @GetMapping("/questions/{id}/view")
    public String questionView(@PathVariable("id") Long id, Model model){
        log.info("== Controller - questionView ==");
        Question question = questionService.findById(id);
        question.setHit(question.getHit()+1);
        log.info("question.id = {}",question.getId());
        model.addAttribute("questionForm", new QuestionForm());
        model.addAttribute("question", question);
        return "/questions/questionView";
    }

    /**
     * 내가 쓴 질문글 조회
     */
    @GetMapping("/questions/{id}/list")
    public String myQuestions(@PathVariable("id") Long id, Model model){
        log.info("== Controller myQuestions ==");
        List<Question> questions = questionService.findByMemberId(id);
        model.addAttribute("questions" , questions);

        return "/questions/myQuestions";
    }


}


/*
-- 폐기코드 --

//질문 전체목록 조회
  * 사유 : 페이징 처리를 위한 파라미터값 변경
  
    @GetMapping("/questions/list")
    public String questionList(Model model){
        log.info("== Controller - questionList ==");
        List<Question> questions = questionService.findQuestions();
        model.addAttribute("questions", questions);

        return "/questions/questionList";
    }


//내가 쓴 글 조회
  * 사유 : 페이징 처리 및
          기존 html페이지 재활용을 위해 시도해보았으나,
           스프링 dataJpa 활용능력의 부족

    @GetMapping("/questions/{id}/list")
    public String myQuestions(@PathVariable("id") Long id, @PageableDefault(sort = "id") Pageable pageable,  Model model){
        log.info("== Controller myQuestions ==");

        Page<Question> questions = questionService.findByMemberId(pageable, id);
        model.addAttribute("questions", questionService.findQuestions(pageable));
        model.addAttribute("previous", pageable.previousOrFirst().getPageNumber());
        model.addAttribute("next", pageable.next().getPageNumber());
        model.addAttribute("questions" , questions);

        return "/questions/questionList";
    }

 */
