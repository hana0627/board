package jpaproject.board.controller;

import jpaproject.board.domain.Member;
import jpaproject.board.domain.Question;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnswerForm {

    private Long id;

    private String title;

    private String content;

    private Question question;

    private Long memberId;

}
