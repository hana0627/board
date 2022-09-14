package jpaproject.board.service;

import jpaproject.board.domain.Address;
import jpaproject.board.domain.Grade;
import jpaproject.board.domain.Member;
import jpaproject.board.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class MemberService {

    private final MemberRepository memberRepository;

    /**
     * 회원가입
     */
    @Transactional
    public Long join(Member member){
        log.info("== MemberService - join == ");
        validateDuplicateMember(member); //중복 회원 검증
        memberRepository.save(member);
        return member.getId();
    }

    /**
     * 중복회원검증
     */
    private void validateDuplicateMember(Member member){
        List<Member> result = memberRepository.findByIdid(member.getIdid());
        if(!result.isEmpty()){
            throw new IllegalStateException("이미 존재하는 id입니다");
        }
    }


    /**
     * 전체 회원조회
     */
    public List<Member> findMembers(){
        log.info("== MemberService - findMembers == ");
        return memberRepository.findAll();
    }

    /**
     * 회원 한명조회 - id(PK)로 조회
     */
    public Member findById(Long id){
        log.info("== MemberService - findById ==");
        return memberRepository.findById(id);
    }


    /**
     * 회원 update
     */
    @Transactional
    public void updateMember(Long id, String city, String street, String zipcode, Grade grade){
        log.info("== MemberService - updateMember ==");
        Member member = memberRepository.findById(id);
        Address address = new Address(city, street, zipcode);
        member.setAddress(address);
        member.setGrade(grade);
    }

    /**
     * 회원삭제
     */
    @Transactional
    public void deleteMember(Long id){
        log.info("== MemberService - deleteMember ==");
        memberRepository.deleteMember(id);
    }

    /**
     * 로그인
     * .stream 등의 문법 공부 필요성
     */
    public Long login(String idid, String password){
        log.info("== MemberService - login ==");
        List<Member> members = memberRepository.findAll();
        for (Member member : members) {
            if(member.getIdid().equals(idid)){
                log.info("아이디있음");
                if (member.getPassword().equals(password)) {
                    log.info("비밀번호 같음");
                    return member.getId();
                }
            }
        }
        log.info("아아디 혹은 비밀번호 불일치");
        return 0L;
    }



}


/*
-- 폐기코드 --

// 로그인 검증
* 사유 : findByidid(idid)로 아이디값 존재여부 판단로직
        => entity값 없을때 예외발생
        => 예외처리에 대한 로직을 추가로 작성하는것 보단
           findAll로 list를 뽑은 후, 원하는 값이 들어있는지 여부를 확인하는것이
           효율적이라고 판단

public Long login(String idid, String password){
    log.info("== MemberService - login ==");
    Member member = memberRepository.findByidid(idid);
    if(member.getIdid() != null){
        //아이디 존재
        log.info("--아이디 존재--");
        if(member.getPassword().equals(password)){
            //비밀번호 일치
            log.info("--비밀번호 일치--");
            return member.getId();
        }
        log.info("--비밀번호 불일치--");
    }


    return null;
}


 */