package team.onepoom.idk.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.onepoom.idk.common.exception.AnswerNotFoundException;
import team.onepoom.idk.common.exception.SelectedAnswerForbiddenException;
import team.onepoom.idk.domain.Provider;
import team.onepoom.idk.domain.answer.Answer;
import team.onepoom.idk.domain.answer.dto.ModifyAnswerRequest;
import team.onepoom.idk.domain.answer.dto.MyAnswerResponse;
import team.onepoom.idk.repository.AnswerRepository;

@Service
@RequiredArgsConstructor
public class AnswerService {

    private final AnswerRepository answerRepository;
    private final QuestionService questionService;

    @Transactional
    public void create(Answer answer) {
        long questionId = answer.getQuestion().getId();
        questionService.findQuestion(questionId);
        answerRepository.save(answer);
    }

    @Transactional
    public void modify(Provider provider, long id, ModifyAnswerRequest request) {
        Answer answer = getValidatedAnswer(id, provider);
        answer.updateAnswer(request.content());
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
        answer.getQuestion().checkQuestionOwner(provider);
        answer.getQuestion().answerSelected();
        answer.select();
    }

    @Transactional(readOnly = true)
    public Page<MyAnswerResponse> getMyAnswers(Provider provider, Pageable pageable) {
        return answerRepository.findByWriter_Id(provider.id(), pageable).map(MyAnswerResponse::from);
    }
}