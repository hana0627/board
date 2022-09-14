package jpaproject.board.controller;

import jpaproject.board.domain.Qstatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuestionForm {

    private Long id;

    private String title;

    private String content;

    private Qstatus qstatus;

}
