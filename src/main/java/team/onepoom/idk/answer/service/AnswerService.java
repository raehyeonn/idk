package team.onepoom.idk.answer.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.onepoom.idk.answer.Exception.ReportedAnswerException;
import team.onepoom.idk.answer.converter.DtoToEntityConverter;
import team.onepoom.idk.answer.converter.EntityToDtoConverter;
import team.onepoom.idk.answer.dto.CreateAnswerRequest;
import team.onepoom.idk.answer.dto.GetMyAnswersResponse;
import team.onepoom.idk.answer.dto.UpdateAnswerRequest;
import team.onepoom.idk.answer.Exception.AnswerNotFoundException;
import team.onepoom.idk.common.exception.ForbiddenException;
import team.onepoom.idk.common.exception.QuestionNotFoundException;
import team.onepoom.idk.answer.domain.Answer;
import team.onepoom.idk.answer.Exception.SelectedAnswerForbiddenException;
import team.onepoom.idk.common.exception.SelectionConflictException;
import team.onepoom.idk.member.domain.Member;
import team.onepoom.idk.member.repository.MemberRepository;
import team.onepoom.idk.question.domain.Question;
import team.onepoom.idk.answer.repository.AnswerRepository;
import team.onepoom.idk.question.repository.QuestionRepository;
import team.onepoom.idk.security.jwt.CustomUserDetails;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AnswerService {

    private final AnswerRepository answerRepository;
    private final QuestionRepository questionRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public void createAnswer(CustomUserDetails customUserDetails, CreateAnswerRequest createAnswerRequest) {
        Question question = questionRepository.findById(createAnswerRequest.getQuestionId()).orElseThrow(() -> new QuestionNotFoundException(createAnswerRequest.getQuestionId()));

        if (customUserDetails.getId() == question.getWriter().getId()) {
            throw new ForbiddenException("질문 작성자는 답변을 작성할 수 없습니다.");
        }

        Answer answer = DtoToEntityConverter.toEntity(question, customUserDetails, createAnswerRequest);
        answerRepository.save(answer);
    }

    public Answer getAnswerById(long id) {
        return answerRepository.findById(id).orElseThrow(() -> new AnswerNotFoundException(id));
    }

    private Answer getVerifiedAnswer(CustomUserDetails customUserDetails, long id) {
        Answer answer = getAnswerById(id);
        answer.validateAnswerOwner(customUserDetails);

        if(answer.getBlindedAt() != null) {
            throw new ReportedAnswerException(answer.getId());
        }

        if (answer.isSelected()) {
            throw new SelectedAnswerForbiddenException();
        }

        return answer;
    }

    @Transactional
    public void updateAnswer(CustomUserDetails customUserDetails, long id, UpdateAnswerRequest updateAnswerRequest) {
        Answer answer = getVerifiedAnswer(customUserDetails, id);
        answer.updateAnswer(updateAnswerRequest.getContent());
    }

    @Transactional
    public void deleteAnswer(CustomUserDetails customUserDetails, long id) {
        Answer answer = getVerifiedAnswer(customUserDetails, id);
        answerRepository.delete(answer);
    }

    @Transactional
    public void selectBestAnswer(CustomUserDetails customUserDetails, long id) {
        Answer answer = getAnswerById(id);

        if(answer.getQuestion().isHasBestAnswer()) {
            throw new SelectionConflictException();
        }

        answer.getQuestion().validateQuestionOwner(customUserDetails);
        answer.getQuestion().selectBestAnswer();
        answer.setBestAnswer();
    }

    public Page<GetMyAnswersResponse> getMyAnswers(CustomUserDetails customUserDetails, Pageable pageable) {
        return answerRepository.findByWriter_Id(customUserDetails.getId(), pageable).map(EntityToDtoConverter::toMyAnswersResponse);
    }

    @Transactional
    public void setWriterToDeletedMember(Member member) {
        List<Answer> answers = answerRepository.findAllByWriter(member);
        Member deletedMember = memberRepository.findByEmail("deleted@user.com").orElseThrow(() -> new RuntimeException("탈퇴 사용자가 존재하지 않습니다."));

        for(Answer answer : answers) {
            answer.setWriterToDeletedMember(deletedMember);
        }
    }

    /*@Transactional
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
        if (answer.isHasBestAnswer()) {
            throw new SelectedAnswerForbiddenException();
        }
        return answer;
    }

    @Transactional
    public void select(Provider provider, long id) {
        Answer answer = findAnswer(id);
        //중복 채택 제한
        if(answer.getQuestion().isHasBestAnswer()) throw new SelectionConflictException();
        //채택 권한 확인
        answer.getQuestion().checkQuestionOwner(provider);
        answer.getQuestion().answerSelected();
        answer.select();
    }

    @Transactional(readOnly = true)
    public Page<MyAnswerResponse> getMyAnswers(Provider provider, Pageable pageable) {
        return answerRepository.findByWriter_Id(provider.id(), pageable).map(MyAnswerResponse::from);
    }*/
}
