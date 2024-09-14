package team.onepoom.idk.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.Optional;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import team.onepoom.idk.common.exception.AnswerForbiddenException;
import team.onepoom.idk.common.exception.AnswerNotFoundException;
import team.onepoom.idk.common.exception.ForbiddenException;
import team.onepoom.idk.common.exception.QuestionNotFoundException;
import team.onepoom.idk.common.exception.SelectionConflictException;
import team.onepoom.idk.common.exception.UserNotFoundException;
import team.onepoom.idk.domain.Provider;
import team.onepoom.idk.domain.answer.Answer;
import team.onepoom.idk.domain.answer.dto.AnswerDTO;
import team.onepoom.idk.domain.answer.dto.CreateAnswerRequest;
import team.onepoom.idk.domain.answer.dto.ModifyAnswerRequest;
import team.onepoom.idk.domain.answer.dto.MyAnswerResponse;
import team.onepoom.idk.domain.question.Question;
import team.onepoom.idk.domain.user.User;
import team.onepoom.idk.repository.AnswerRepository;
import team.onepoom.idk.repository.QuestionRepository;
import team.onepoom.idk.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
class AnswerServiceTest {

    @Mock private AnswerRepository answerRepository;
    @Mock private UserRepository userRepository;
    @Mock private QuestionRepository questionRepository;
    @Mock private User user;
    @Mock private Question question;
    @Mock private Answer answer;

    @InjectMocks private AnswerService answerService;

    private Provider provider;

    @BeforeEach
    void setUp() {
        provider = new Provider(1L, "test@example.com", "Test User", Collections.emptySet());
    }

    @Test
    void create_는_답변을_생성할_수_있다() {
        // given
        CreateAnswerRequest request = new CreateAnswerRequest(1L, "Test Answer");
        when(user.getId()).thenReturn(1L);
        when(user.getNickname()).thenReturn("Test User");
        when(question.getWriter()).thenReturn(mock(User.class));
        when(question.getWriter().getId()).thenReturn(2L);
        when(answer.getId()).thenReturn(1L);
        when(answer.getWriter()).thenReturn(user);
        when(answer.getContent()).thenReturn("Test Answer");
        when(userRepository.findById(provider.id())).thenReturn(Optional.of(user));
        when(questionRepository.findById(1L)).thenReturn(Optional.of(question));
        when(answerRepository.save(any(Answer.class))).thenReturn(answer);
        // when
        AnswerDTO result = answerService.create(provider, request);
        // then
        assertThat(result).isNotNull();
        assertThat(result.getContent()).isEqualTo("Test Answer");
        verify(answerRepository).save(any(Answer.class));
    }

    @Test
    void create_는_질문작성자가_답변작성시_ForbiddenException을_던진다() {
        // given
        CreateAnswerRequest request = new CreateAnswerRequest(1L, "Test Answer");
        when(userRepository.findById(provider.id())).thenReturn(Optional.of(user));
        when(questionRepository.findById(1L)).thenReturn(Optional.of(question));
        when(question.getWriter()).thenReturn(user);
        when(user.getId()).thenReturn(provider.id());
        // when & then
        assertThatThrownBy(() -> answerService.create(provider, request))
            .isInstanceOf(ForbiddenException.class);
    }

    @Test
    void create_는_사용자를_찾을_수_없을_때_UserNotFoundException을_던진다() {
        // given
        CreateAnswerRequest request = new CreateAnswerRequest(1L, "Test Answer");
        when(userRepository.findById(provider.id())).thenReturn(Optional.empty());
        // when & then
        assertThatThrownBy(() -> answerService.create(provider, request))
            .isInstanceOf(UserNotFoundException.class);
    }

    @Test
    void create_는_질문을_찾을_수_없을_때_QuestionNotFoundException을_던진다() {
        // given
        CreateAnswerRequest request = new CreateAnswerRequest(1L, "Test Answer");
        when(userRepository.findById(provider.id())).thenReturn(Optional.of(user));
        when(questionRepository.findById(1L)).thenReturn(Optional.empty());
        // when & then
        assertThatThrownBy(() -> answerService.create(provider, request))
            .isInstanceOf(QuestionNotFoundException.class);
    }

    @Test
    void modify_는_답변을_수정할_수_있다() {
        // given
        ModifyAnswerRequest request = new ModifyAnswerRequest("Modified Answer");
        when(answerRepository.findById(1L)).thenReturn(Optional.of(answer));
        when(answer.getWriter()).thenReturn(user);
        when(user.getId()).thenReturn(provider.id());
        when(answer.getContent()).thenReturn("Modified Answer");
        // when
        AnswerDTO result = answerService.modify(provider, 1L, request);
        // then
        assertThat(result).isNotNull();
        assertThat(result.getContent()).isEqualTo("Modified Answer");
        verify(answer).updateAnswer("Modified Answer");
    }

    @Test
    void modify_는_답변을_찾을_수_없을_때_AnswerNotFoundException을_던진다() {
        // given
        ModifyAnswerRequest request = new ModifyAnswerRequest("Modified Answer");
        when(answerRepository.findById(1L)).thenReturn(Optional.empty());
        // when & then
        assertThatThrownBy(() -> answerService.modify(provider, 1L, request))
            .isInstanceOf(AnswerNotFoundException.class);
    }

    @Test
    void modify_는_답변_작성자가_아닐_때_AnswerForbiddenException을_던진다() {
        // given
        ModifyAnswerRequest request = new ModifyAnswerRequest("Modified Answer");
        when(answerRepository.findById(1L)).thenReturn(Optional.of(answer));
        doThrow(new AnswerForbiddenException(provider.id())).when(answer).checkAnswerOwner(provider);
        // when & then
        assertThatThrownBy(() -> answerService.modify(provider, 1L, request))
            .isInstanceOf(AnswerForbiddenException.class)
            .hasMessageContaining(String.valueOf(provider.id()));
    }

    @Test
    void delete_는_답변을_삭제할_수_있다() {
        // given
        when(answerRepository.findById(1L)).thenReturn(Optional.of(answer));
        // when
        answerService.delete(provider, 1L);
        // then
        verify(answerRepository).delete(answer);
    }

    @Test
    void delete_는_답변을_찾을_수_없을_때_AnswerNotFoundException을_던진다() {
        // given
        when(answerRepository.findById(1L)).thenReturn(Optional.empty());
        // when & then
        assertThatThrownBy(() -> answerService.delete(provider, 1L))
            .isInstanceOf(AnswerNotFoundException.class);
    }

    @Test
    void select_는_답변을_채택할_수_있다() {
        // given
        when(answerRepository.findById(1L)).thenReturn(Optional.of(answer));
        when(answer.getQuestion()).thenReturn(question);
        when(question.isSelected()).thenReturn(false);
        // when
        answerService.select(provider, 1L);
        // then
        verify(answer).select();
        verify(question).answerSelected();
    }

    @Test
    void select_는_이미_채택된_질문일_때_SelectionConflictException을_던진다() {
        // given
        when(answerRepository.findById(1L)).thenReturn(Optional.of(answer));
        when(answer.getQuestion()).thenReturn(question);
        when(question.isSelected()).thenReturn(true);
        // when & then
        assertThatThrownBy(() -> answerService.select(provider, 1L))
            .isInstanceOf(SelectionConflictException.class);
    }

    @Test
    void getMyAnswers_는_내_답변_목록을_조회할_수_있다() {
        // given
        when(user.getId()).thenReturn(1L);
        when(user.getNickname()).thenReturn("Test User");
        when(question.getId()).thenReturn(1L);
        when(question.getWriter()).thenReturn(mock(User.class));
        when(question.getWriter().getId()).thenReturn(2L);
        when(question.getTitle()).thenReturn("Test Question");
        when(question.getContent()).thenReturn("Test Content");
        when(answer.getId()).thenReturn(1L);
        when(answer.getWriter()).thenReturn(user);
        when(answer.getQuestion()).thenReturn(question);
        when(answer.getContent()).thenReturn("Test Answer");
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<Answer> answerPage = new PageImpl<>(Collections.singletonList(answer));
        when(answerRepository.findByWriter_Id(provider.id(), pageRequest)).thenReturn(answerPage);
        //when
        Page<MyAnswerResponse> result = answerService.getMyAnswers(provider, pageRequest);
        //then
        AssertionsForClassTypes.assertThat(result).isNotNull();
        AssertionsForClassTypes.assertThat(result.getContent().size()).isEqualTo(1);
        MyAnswerResponse response = result.getContent().get(0);
        AssertionsForClassTypes.assertThat(response.questionId()).isEqualTo(1L);
        AssertionsForClassTypes.assertThat(response.title()).isEqualTo("Test Question");
        AssertionsForClassTypes.assertThat(response.content()).isEqualTo("Test Content");
        AssertionsForClassTypes.assertThat(response.myAnswer().content()).isEqualTo("Test Answer");
    }

    @Test
    void getMyAnswers_는_답변이_없을_때_빈_페이지를_반환한다() {
        // given
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<Answer> emptyPage = new PageImpl<>(Collections.emptyList());
        when(answerRepository.findByWriter_Id(provider.id(), pageRequest)).thenReturn(emptyPage);
        // when
        Page<MyAnswerResponse> result = answerService.getMyAnswers(provider, pageRequest);
        // then
        assertThat(result).isNotNull();
        assertThat(result.getContent()).isEmpty();
    }
}