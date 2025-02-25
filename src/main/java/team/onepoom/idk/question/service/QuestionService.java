package team.onepoom.idk.question.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.onepoom.idk.answer.domain.Answer;
import team.onepoom.idk.answer.repository.AnswerRepository;
import team.onepoom.idk.common.exception.QuestionHasAnswersException;
import team.onepoom.idk.common.exception.QuestionNotFoundException;
import team.onepoom.idk.member.domain.Member;
import team.onepoom.idk.member.repository.MemberRepository;
import team.onepoom.idk.question.converter.DtoToEntityConverter;
import team.onepoom.idk.question.dto.CreateQuestionResponse;
import team.onepoom.idk.question.dto.GetAllQuestionsResponse;
import team.onepoom.idk.question.dto.CreateQuestionRequest;
import team.onepoom.idk.question.domain.Question;
import team.onepoom.idk.question.dto.GetMyQuestionResponse;
import team.onepoom.idk.question.dto.GetQuestionDetailResponse;
import team.onepoom.idk.question.dto.UpdateQuestionRequest;
import team.onepoom.idk.question.repository.CustomQuestionRepository;
import team.onepoom.idk.question.repository.QuestionRepository;
import team.onepoom.idk.security.jwt.CustomUserDetails;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final CustomQuestionRepository customQuestionRepository;
    private final MemberRepository memberRepository;
    private final AnswerRepository answerRepository;

    @Transactional
    public CreateQuestionResponse createQuestion(CustomUserDetails customUserDetails, CreateQuestionRequest createQuestionRequest) {
        Question question = DtoToEntityConverter.toEntity(customUserDetails, createQuestionRequest);
        questionRepository.save(question);
        return new CreateQuestionResponse(question.getId());
    }

    public Question getQuestionById(long id) {
        return questionRepository.findById(id).orElseThrow(() -> new QuestionNotFoundException(id));
    }

    private Question getVerifiedQuestion(CustomUserDetails customUserDetails, long id) {
        if (customQuestionRepository.existAnswer(id)) {
            throw new QuestionHasAnswersException();
        }

        Question question = getQuestionById(id);
        question.validateQuestionOwner(customUserDetails);

        return question;
    }

    @Transactional
    public void setWriterToDeletedMember(Member member) {
        List<Question> questions = questionRepository.findAllByWriter(member);
        Member deletedMember = memberRepository.findByEmail("deleted@user.com").orElseThrow(() -> new RuntimeException("탈퇴 사용자가 존재하지 않습니다."));

        for(Question question : questions) {
            question.setWriterToDeletedMember(deletedMember);
        }
    }

    @Transactional
    public void updateQuestion(CustomUserDetails customUserDetails, long id, UpdateQuestionRequest updateQuestionRequest) {
        Question question = getVerifiedQuestion(customUserDetails, id);
        question.updateQuestionTitle(updateQuestionRequest.getTitle());
        question.updateQuestionContent(updateQuestionRequest.getContent());
    }

    @Transactional
    public void deleteQuestion(CustomUserDetails customUserDetails, long id) {
        Question question = getVerifiedQuestion(customUserDetails, id);
        questionRepository.delete(question);
    }

    public Page<GetAllQuestionsResponse> getAllQuestions(String title, Pageable pageable) {
        return customQuestionRepository.findQuestions(title, pageable);
    }

    @Transactional
    public GetQuestionDetailResponse getQuestionDetail(long id) {
        Question question = customQuestionRepository.findQuestion(id);
        question.incrementViewCount();

        return new GetQuestionDetailResponse(question);
    }

    public Page<GetMyQuestionResponse> getMyQuestions(CustomUserDetails customUserDetails, Pageable pageable) {
        long writerId = customUserDetails.getId();
        return customQuestionRepository.findMyQuestions(writerId, pageable);
    }

}
