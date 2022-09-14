package jpaproject.board.repository;

import jpaproject.board.domain.Member;
import jpaproject.board.domain.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepositoryDatajpa extends JpaRepository<Question,Long> {
}
