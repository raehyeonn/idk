package team.onepoom.idk.question.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import team.onepoom.idk.member.domain.Member;
import team.onepoom.idk.question.domain.Question;

public interface QuestionRepository extends JpaRepository<Question, Long> {

    List<Question> findAllByWriter(Member member);

}
