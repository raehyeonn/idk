package team.onepoom.idk.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import team.onepoom.idk.domain.answer.Answer;

public interface AnswerRepository extends JpaRepository<Answer, Long> {

    Page<Answer> findByWriter_Id(long id, Pageable pageable);
}
