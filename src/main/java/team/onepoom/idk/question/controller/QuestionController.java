package team.onepoom.idk.question.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import team.onepoom.idk.question.dto.CreateQuestionResponse;
import team.onepoom.idk.question.dto.GetAllQuestionsResponse;
import team.onepoom.idk.question.dto.CreateQuestionRequest;
import team.onepoom.idk.question.dto.GetMyQuestionResponse;
import team.onepoom.idk.question.dto.GetQuestionDetailResponse;
import team.onepoom.idk.question.dto.UpdateQuestionRequest;
import team.onepoom.idk.question.service.QuestionService;
import team.onepoom.idk.security.jwt.CustomUserDetails;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/questions")
class QuestionController {

    private final QuestionService questionService;

    @PostMapping
    public CreateQuestionResponse create(@AuthenticationPrincipal CustomUserDetails customUserDetails, @Valid @RequestBody CreateQuestionRequest createQuestionRequest) {
        return questionService.createQuestion(customUserDetails, createQuestionRequest);
    }

    @PatchMapping("/{id}")
    public void update(@AuthenticationPrincipal CustomUserDetails customUserDetails, @PathVariable long id, @Valid @RequestBody UpdateQuestionRequest updateQuestionRequest) {
        questionService.updateQuestion(customUserDetails, id, updateQuestionRequest);
    }

    @DeleteMapping("/{id}")
    public void delete(@AuthenticationPrincipal CustomUserDetails customUserDetails, @PathVariable long id) {
        questionService.deleteQuestion(customUserDetails, id);
    }

    @GetMapping
    public Page<GetAllQuestionsResponse> getAll(@RequestParam(value = "title", required = false) String title, Pageable pageable) {
        return questionService.getAllQuestions(title, pageable);
    }

    @GetMapping("/{id}")
    public GetQuestionDetailResponse getDetail(@PathVariable long id) {
        return questionService.getQuestionDetail(id);
    }

    @GetMapping("/me")
    public Page<GetMyQuestionResponse> getMine(@AuthenticationPrincipal CustomUserDetails customUserDetails, Pageable pageable) {
        return questionService.getMyQuestions(customUserDetails, pageable);
    }

}
