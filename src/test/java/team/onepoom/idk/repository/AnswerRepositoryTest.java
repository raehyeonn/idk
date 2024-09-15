package team.onepoom.idk.repository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import team.onepoom.idk.domain.answer.Answer;

@DataJpaTest
@ActiveProfiles("test")
@Sql("/sql/answer-repository-test-data.sql")
class AnswerRepositoryTest {

    @Autowired
    private AnswerRepository answerRepository;

    @Test
    void findByWriter_Id_는_작성자의_답변을_페이지로_반환한다() {
        // given
        PageRequest pageRequest = PageRequest.of(0, 10);
        // when
        Page<Answer> result = answerRepository.findByWriter_Id(1L, pageRequest);
        // then
        assertThat(result.getContent().size()).isEqualTo(2);
        assertThat(result.getTotalElements()).isEqualTo(2);
        assertThat(result.getContent().getFirst().getWriter().getId()).isEqualTo(1L);
        assertThat(result.getContent().getFirst().getQuestion()).isNotNull();
    }

    @Test
    void findByWriter_Id_는_존재하지_않는_작성자ID로_조회하면_빈_페이지를_반환한다() {
        // given
        PageRequest pageRequest = PageRequest.of(0, 10);
        // when
        Page<Answer> result = answerRepository.findByWriter_Id(999L, pageRequest);
        // then
        assertThat(result.getContent().isEmpty()).isTrue();
        assertThat(result.getTotalElements()).isZero();
    }

    @Test
    void findByWriter_Id_는_페이지네이션을_올바르게_처리한다() {
        // given
        PageRequest pageRequest = PageRequest.of(0, 1);

        // when
        Page<Answer> result = answerRepository.findByWriter_Id(1L, pageRequest);

        // then
        assertThat(result.getContent().size()).isEqualTo(1);
        assertThat(result.getTotalElements()).isEqualTo(2);
        assertThat(result.getTotalPages()).isEqualTo(2);
    }

    @Test
    void findByWriter_Id_는_연관_엔티티를_함께_로드한다() {
        // given
        PageRequest pageRequest = PageRequest.of(0, 10);

        // when
        Page<Answer> result = answerRepository.findByWriter_Id(1L, pageRequest);

        // then
        Answer answer = result.getContent().getFirst();
        assertThat(answer.getWriter()).isNotNull();
        assertThat(answer.getQuestion()).isNotNull();
        assertThat(answer.getQuestion().getWriter()).isNotNull();
    }

}