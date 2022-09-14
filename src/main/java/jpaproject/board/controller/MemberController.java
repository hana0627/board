package jpaproject.board.controller;

import jpaproject.board.domain.*;
import jpaproject.board.service.AnswerService;
import jpaproject.board.service.MemberService;
import jpaproject.board.service.QuestionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final MemberService memberService;
    private final QuestionService questionService;
    private final AnswerService answerService;

    /**
     * 회원가입페이지로 이동
     */
    @GetMapping("/members/new")
    public String crateForm(Model model){
        model.addAttribute("memberForm", new MemberForm());
        log.info("== Controller - createForm ==");
        return "/members/createMemberForm";
    }

    /**
     * 회원가입 -> 메인화면으로
     */
    @PostMapping("/members/new")
    public String create(MemberForm form){
        log.info("== Controller - create ==");

        log.info("getPassword={}" , form.getPassword());
        log.info("getPassword2={}" , form.getPassword2());

        if(!form.getPassword().equals(form.getPassword2())){
            log.info("비밀번호가 같지않음");
            return "/members/createMemberForm";
        }

        Address address = new Address(form.getCity(), form.getStreet(), form.getZipcode());
        Member member = new Member();

        member.setIdid(form.getIdid());
        member.setName(form.getName());
        member.setPassword(form.getPassword());
        member.setAddress(address);
        member.setGrade(Grade.USER);

        memberService.join(member);
        
        return "redirect:/";
    }

    /**
     * 회원 전체목록 조회
     */
    @GetMapping("/members")
    public String memberList(Model model){
        log.info("== Controller - memberList ==");
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);
        return "/members/memberList";
    }

    /**
     * 회원 수정 Form
     * 내정보보기
     */
    @GetMapping("/members/{id}/update")
    public String updateForm(@PathVariable("id") Long id, Model model){
        log.info("== Controller - updateForm ==");

        Member member = memberService.findById(id);
        model.addAttribute("memberForm", new MemberForm());
        model.addAttribute("member", member);

        return "/members/updateForm";
    }

    /**
     * 회원 수정 -> 메인화면
     */
    @PostMapping("/members/{id}/update")
    public String update(@PathVariable("id") Long id,MemberForm form){
        log.info("== Controller - update ==");
        memberService.updateMember(id,form.getCity(),form.getStreet(),form.getZipcode(),form.getGrade());

        return "redirect:/";
    }

    /**
     * 회원 삭제
     */
    @GetMapping("/members/{id}/delete")
    public String delete(@PathVariable("id") Long id){
        log.info("== Controller - delete ==");

        memberService.deleteMember(id);

        return "redirect:/";
    }


    /**
     * 로그인 -Form
     */
    @GetMapping("/members/loginForm")
    public String loginForm(){
        return "home";
    }

    /**
     * 로그인
     */
    @PostMapping("/members/login")
    public String login(String idid, String password, Model model, HttpSession session){
        log.info("== Controller - login ==");
        Long id = memberService.login(idid, password);
        if (id == 0L){
            return "redirect:/";
        }
        model.addAttribute("id", id);
        session.setAttribute("id",id);
        return "/mainpage";
    }


    /**
     * 마이페이지로 이동
     */
    @GetMapping("/members/{id}/info")
    public String infoForm(@PathVariable("id") Long id, Model model){
        log.info("== Controller - infoForm ==");
        log.info("id={}",id);
        Member member = memberService.findById(id);
        //List<Question> questions = questionService.findByMemberId(id);
        List<Answer> answers = answerService.findByMemberId(id);

        model.addAttribute("member" , member);
        //model.addAttribute("questions" , questions);
        model.addAttribute("answers", answers);

        return "/members/info";
    }

}

/*
-- 폐기코드 --




// 중복회원검증
 * 사유
      회원가입 button을 눌렀을때 검증로직으로 구현
      원래는 따로 중복확인 button을 만들어서 logic을 작성하려 하였으나,
      비동기 처리가 요구됨.
      현재 프로젝트의 목적은 그동안 학습한 springboot와 jpa의 활용이며,
      front단에 대한 구현은 이전 hanashop project에서 충분히 숙달하였다고 판단.

@RequestMapping("/members/{idid}new")
public String validate(@PathVariable String idid){
    log.info("== Controller - validate");
    return "";
}
 */
