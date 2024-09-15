package team.onepoom.idk.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static team.onepoom.idk.util.TestSecurityContextFactory.authenticatedProvider;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.ZonedDateTime;
import java.util.Collections;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import team.onepoom.idk.common.exception.AnswerNotFoundException;
import team.onepoom.idk.domain.Provider;
import team.onepoom.idk.domain.answer.dto.AnswerDTO;
import team.onepoom.idk.domain.answer.dto.AnswerResponse;
import team.onepoom.idk.domain.answer.dto.CreateAnswerRequest;
import team.onepoom.idk.domain.answer.dto.ModifyAnswerRequest;
import team.onepoom.idk.domain.answer.dto.MyAnswerResponse;
import team.onepoom.idk.domain.user.dto.WriterDTO;
import team.onepoom.idk.service.AnswerService;

@MockBean(JpaMetamodelMappingContext.class)
@WebMvcTest(AnswerController.class)
class AnswerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AnswerService answerService;

    private final Provider provider = new Provider(1L, "test@example.com", "Test User",
        Collections.emptySet());

    @Test
    @WithMockUser(roles = "USER")
    void create_는_유효한_요청으로_답변을_생성한다() throws Exception {
        CreateAnswerRequest request = new CreateAnswerRequest(1L, "Valid content");
        AnswerDTO expectedAnswer = AnswerDTO.builder()
            .id(1L)
            .writer(new WriterDTO(provider.id(), provider.email(), provider.nickname()))
            .content("Valid content")
            .isSelected(false)
            .createdAt(ZonedDateTime.now())
            .updatedAt(ZonedDateTime.now())
            .build();
        when(answerService.create(any(Provider.class), any(CreateAnswerRequest.class))).thenReturn(expectedAnswer);

        mockMvc.perform(post("/api/answers")
                .with(csrf())
                .with(authenticatedProvider(provider))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").exists())
            .andExpect(jsonPath("$.content").value("Valid content"));

        verify(answerService).create(eq(provider), eq(request));
    }

    @Test
    @WithMockUser(roles = "USER")
    void create_는_questionId가_0이하일_때_400을_반환한다() throws Exception {
        CreateAnswerRequest request = new CreateAnswerRequest(0L, "Valid content");

        mockMvc.perform(post("/api/answers")
                .with(csrf())
                .with(authenticatedProvider(provider))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(roles = "USER")
    void create_는_content가_비어있을_때_400을_반환한다() throws Exception {
        CreateAnswerRequest request = new CreateAnswerRequest(1L, "");

        mockMvc.perform(post("/api/answers")
                .with(csrf())
                .with(authenticatedProvider(provider))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(roles = "USER")
    void create_는_content가_null일_때_400을_반환한다() throws Exception {
        CreateAnswerRequest request = new CreateAnswerRequest(1L, null);

        mockMvc.perform(post("/api/answers")
                .with(csrf())
                .with(authenticatedProvider(provider))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(roles = "USER")
    void create_는_유효하지_않은_요청으로_400_응답을_반환한다() throws Exception {
        CreateAnswerRequest request = new CreateAnswerRequest(-1L, "");

        mockMvc.perform(post("/api/answers")
                .with(csrf())
                .with(authenticatedProvider(provider))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
                .requestAttr("provider", provider))
            .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(roles = "USER")
    void modify_는_답변_수정_서비스_메서드를_호출한다() throws Exception {
        long answerId = 1L;
        ModifyAnswerRequest request = new ModifyAnswerRequest("Modified content");
        AnswerDTO expectedAnswer = AnswerDTO.builder()
            .id(1L)
            .writer(new WriterDTO(provider.id(), provider.email(), provider.nickname()))
            .content("Modified content")
            .isSelected(false)
            .createdAt(ZonedDateTime.now())
            .updatedAt(ZonedDateTime.now())
            .build();
        when(answerService.modify(any(Provider.class), eq(answerId),
            any(ModifyAnswerRequest.class))).thenReturn(expectedAnswer);

        mockMvc.perform(put("/api/answers/{id}", answerId)
                .with(csrf())
                .with(authenticatedProvider(provider))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
                .requestAttr("provider", provider))
            .andExpect(status().isOk());

        verify(answerService).modify(eq(provider), eq(answerId), any(ModifyAnswerRequest.class));
    }

    @Test
    @WithMockUser(roles = "USER")
    void modify_는_유효하지_않은_요청으로_400_응답을_반환한다() throws Exception {
        long answerId = 1L;
        ModifyAnswerRequest request = new ModifyAnswerRequest("");

        mockMvc.perform(put("/api/answers/{id}", answerId)
                .with(csrf())
                .with(authenticatedProvider(provider))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
                .requestAttr("provider", provider))
            .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(roles = "USER")
    void modify_는_존재하지_않는_답변에_대해_404_응답을_반환한다() throws Exception {
        long answerId = 999L;
        ModifyAnswerRequest request = new ModifyAnswerRequest("Modified content");
        when(
            answerService.modify(any(Provider.class), eq(answerId), any(ModifyAnswerRequest.class)))
            .thenThrow(new AnswerNotFoundException(answerId));

        mockMvc.perform(put("/api/answers/{id}", answerId)
                .with(csrf())
                .with(authenticatedProvider(provider))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
                .requestAttr("provider", provider))
            .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(roles = "USER")
    void delete_는_답변_삭제_서비스_메서드를_호출한다() throws Exception {
        long answerId = 1L;

        mockMvc.perform(delete("/api/answers/{id}", answerId)
                .with(csrf())
                .with(authenticatedProvider(provider))
                .requestAttr("provider", provider))
            .andExpect(status().isOk());

        verify(answerService).delete(eq(provider), eq(answerId));
    }

    @Test
    @WithMockUser(roles = "USER")
    void select_는_답변_선택_서비스_메서드를_호출한다() throws Exception {
        long answerId = 1L;

        mockMvc.perform(post("/api/answers/{id}/selections", answerId)
                .with(csrf())
                .with(authenticatedProvider(provider))
                .requestAttr("provider", provider))
            .andExpect(status().isOk());

        verify(answerService).select(eq(provider), eq(answerId));
    }

    @Test
    @WithMockUser(roles = "USER")
    void getMyAnswers_는_내_답변_목록_조회_서비스_메서드를_호출한다() throws Exception {
        MyAnswerResponse myAnswerResponse = new MyAnswerResponse(
            1L,
            new WriterDTO(1L, "test@email.com", "testuser"),
            "Question Title",
            "Question Content",
            false,
            1,
            new AnswerResponse(1L, new WriterDTO(1L, "test@email.com", "testuser"),
                "Answer Content", false, ZonedDateTime.now(), ZonedDateTime.now()),
            ZonedDateTime.now(),
            ZonedDateTime.now()
        );
        Page<MyAnswerResponse> page = new PageImpl<>(Collections.singletonList(myAnswerResponse));
        when(answerService.getMyAnswers(any(Provider.class), any(Pageable.class))).thenReturn(page);

        mockMvc.perform(get("/api/answers/me")
                .with(csrf())
                .with(authenticatedProvider(provider))
                .requestAttr("provider", provider))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.content[0].questionId").value(1))
            .andExpect(jsonPath("$.content[0].title").value("Question Title"))
            .andExpect(jsonPath("$.content[0].content").value("Question Content"))
            .andExpect(jsonPath("$.content[0].isSelected").value(false))
            .andExpect(jsonPath("$.content[0].answerCount").value(1))
            .andExpect(jsonPath("$.content[0].myAnswer.answerId").value(1))
            .andExpect(jsonPath("$.content[0].myAnswer.content").value("Answer Content"));

        verify(answerService).getMyAnswers(eq(provider), any(Pageable.class));
    }

    @Test
    @WithMockUser(roles = "USER")
    void getMyAnswers_는_답변이_없을_때_빈_페이지를_반환한다() throws Exception {
        Page<MyAnswerResponse> emptyPage = new PageImpl<>(Collections.emptyList());
        when(answerService.getMyAnswers(any(Provider.class), any(Pageable.class))).thenReturn(
            emptyPage);

        mockMvc.perform(get("/api/answers/me")
                .with(csrf())
                .with(authenticatedProvider(provider))
                .requestAttr("provider", provider))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.content").isArray())
            .andExpect(jsonPath("$.content").isEmpty());
    }
}