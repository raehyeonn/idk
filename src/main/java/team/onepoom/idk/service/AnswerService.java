package team.onepoom.idk.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.onepoom.idk.domain.Provider;
import team.onepoom.idk.domain.answer.Answer;
import team.onepoom.idk.domain.answer.dto.CreateAnswerRequest;
import team.onepoom.idk.repository.AnswerRepository;

@Service
@RequiredArgsConstructor
public class AnswerService {

    private final AnswerRepository answerRepository;

    @Transactional
    public void create(Answer answer) {
        // 질문 존재하는지 확인하는 로직 필요
        long questionId = answer.getQuestion().getId();
        //TODO:questionService.findQuestion();
        answerRepository.save(answer);
    }

    @Transactional
    public void modify(Provider provider, long id, CreateAnswerRequest request) {
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
            .orElseThrow(() -> new RuntimeException("Answer not found"));
    }

    private Answer getValidatedAnswer(long id, Provider provider) {
        Answer answer = findAnswer(id);
        answer.checkAnswerOwner(provider);
        if (answer.isSelected()) {
            throw new RuntimeException("채택된 답변은 수정 또는 삭제할 수 없습니다.");
        }
        return answer;
    }

    public void select(Provider provider, long id) {
        Answer answer = findAnswer(id);
        //provider, questionid 비교로직
        answer.select();
        //question. select 설정
    }

    public Page<Answer> getMyAnswers(Provider provider, Pageable pageable) {
        return answerRepository.findByWriter_Id(provider.id(), pageable);
    }
}