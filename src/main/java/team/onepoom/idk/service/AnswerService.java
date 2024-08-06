package team.onepoom.idk.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.onepoom.idk.common.exception.AnswerNotFoundException;
import team.onepoom.idk.common.exception.ForbiddenException;
import team.onepoom.idk.common.exception.QuestionNotFoundException;
import team.onepoom.idk.common.exception.SelectedAnswerForbiddenException;
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

@Service
@RequiredArgsConstructor
public class AnswerService {

    private final AnswerRepository answerRepository;
    private final UserRepository userRepository;
    private final QuestionRepository questionRepository;

    @Transactional
    public AnswerDTO create(Provider provider, CreateAnswerRequest request) {
        User user = userRepository.findById(provider.id())
            .orElseThrow(() -> new UserNotFoundException(provider.id()));
        Question question = questionRepository.findById(request.questionId())
            .orElseThrow(() -> new QuestionNotFoundException(request.questionId()));
        if (provider.id() == question.getWriter().getId()) {
            throw new ForbiddenException("질문 작성자는 답변을 작성할 수 없습니다.");
        }
        Answer answer = Answer.builder()
            .writer(user)
            .questionId(request.questionId())
            .content(request.content())
            .build();
        answerRepository.save(answer);

        return new AnswerDTO(answer);
    }

    @Transactional
    public AnswerDTO modify(Provider provider, long id, ModifyAnswerRequest request) {
        Answer answer = getValidatedAnswer(id, provider);
        answer.updateAnswer(request.content());
        return new AnswerDTO(answer);
    }

    @Transactional
    public void delete(Provider provider, long id) {
        Answer answer = getValidatedAnswer(id, provider);
        answerRepository.delete(answer);
    }

    @Transactional(readOnly = true)
    public Answer findAnswer(long id) {
        return answerRepository.findById(id)
            .orElseThrow(() -> new AnswerNotFoundException(id));
    }

    private Answer getValidatedAnswer(long id, Provider provider) {
        Answer answer = findAnswer(id);
        answer.checkAnswerOwner(provider);
        if (answer.isSelected()) {
            throw new SelectedAnswerForbiddenException();
        }
        return answer;
    }

    @Transactional
    public void select(Provider provider, long id) {
        Answer answer = findAnswer(id);
        //중복 채택 제한
        if(answer.getQuestion().isSelected()) throw new SelectionConflictException();
        //채택 권한 확인
        answer.getQuestion().checkQuestionOwner(provider);
        answer.getQuestion().answerSelected();
        answer.select();
    }

    @Transactional(readOnly = true)
    public Page<MyAnswerResponse> getMyAnswers(Provider provider, Pageable pageable) {
        return answerRepository.findByWriter_Id(provider.id(), pageable).map(MyAnswerResponse::from);
    }
}
