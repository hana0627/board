package jpaproject.board.controller;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
@Slf4j
public class HomeController {


    @RequestMapping("/")
    public String home(HttpSession session){
        log.info("== home Controller ==");
        log.info("session={}",session);
        if(session != null){
            return "mainpage";
        }
        return "home";
    }
}
