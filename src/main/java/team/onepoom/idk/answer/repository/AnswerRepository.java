package team.onepoom.idk.answer.repository;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import team.onepoom.idk.answer.domain.Answer;
import team.onepoom.idk.member.domain.Member;

public interface AnswerRepository extends JpaRepository<Answer, Long> {

    @EntityGraph(attributePaths = {"question", "question.writer", "writer"})
    Page<Answer> findByWriter_Id(long id, Pageable pageable);

    List<Answer> findAllByWriter(Member member);
}
