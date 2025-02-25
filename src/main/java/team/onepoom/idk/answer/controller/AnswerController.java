package team.onepoom.idk.answer.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import team.onepoom.idk.answer.dto.CreateAnswerRequest;
import team.onepoom.idk.answer.dto.GetMyAnswersResponse;
import team.onepoom.idk.answer.dto.UpdateAnswerRequest;
import team.onepoom.idk.answer.service.AnswerService;
import team.onepoom.idk.security.jwt.CustomUserDetails;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/answers")
@Validated
public class AnswerController {

    private final AnswerService answerService;

    @PostMapping
    public void create(@AuthenticationPrincipal CustomUserDetails customUserDetails, @Valid @RequestBody CreateAnswerRequest request) {
        answerService.createAnswer(customUserDetails, request);
    }

    @PatchMapping("/{id}")
    public void update(@AuthenticationPrincipal CustomUserDetails customUserDetails, @Positive @PathVariable long id, @Valid @RequestBody UpdateAnswerRequest updateAnswerRequest) {
        answerService.updateAnswer(customUserDetails, id, updateAnswerRequest);
    }

    @DeleteMapping("/{id}")
    public void delete(@AuthenticationPrincipal CustomUserDetails customUserDetails, @Positive @PathVariable long id) {
        answerService.deleteAnswer(customUserDetails, id);
    }

    @PostMapping("/{id}/best")
    public void selectBest(@AuthenticationPrincipal CustomUserDetails customUserDetails, @PathVariable long id) {
        answerService.selectBestAnswer(customUserDetails, id);
    }

    @GetMapping("/me")
    public Page<GetMyAnswersResponse> getMine(@AuthenticationPrincipal CustomUserDetails customUserDetails, Pageable pageable) {
        return answerService.getMyAnswers(customUserDetails, pageable);
    }

}
