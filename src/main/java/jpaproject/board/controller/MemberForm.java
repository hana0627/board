package jpaproject.board.controller;

import jpaproject.board.domain.Grade;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberForm {


    private String idid;
    private String name;
    private String password;
    private String password2;


    private String city;
    private String street;
    private String zipcode;

    private Grade grade;
}
