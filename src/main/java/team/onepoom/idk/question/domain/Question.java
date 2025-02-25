package team.onepoom.idk.question.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;
import team.onepoom.idk.common.BaseEntity;
import team.onepoom.idk.answer.domain.Answer;
import team.onepoom.idk.common.exception.QuestionForbiddenException;
import team.onepoom.idk.member.domain.Member;
import team.onepoom.idk.security.jwt.CustomUserDetails;

@Entity
@Table(name = "questions")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Question extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "writer_id")
    private Member writer;

    @Column(nullable = false, length = 50)
    private String title;

    @Column(nullable = false, length = 1000)
    private String content;

    @OneToMany(mappedBy = "question")
    @BatchSize(size = 100)
    private List<Answer> answers = new ArrayList<>();

    @Column(nullable = false)
    private boolean hasBestAnswer;

    @Column(nullable = false)
    private int viewCount;

    private ZonedDateTime blindedAt;

    public Question(Long id) {
        this.id = id;
    }

    public void validateQuestionOwner(CustomUserDetails customUserDetails) {
        if (customUserDetails.getId() != this.getWriter().getId()) {
            throw new QuestionForbiddenException(this.getId());
        }
    }

    public void setWriterToDeletedMember(Member member) {
        this.writer = member;
    }

    public void updateQuestionTitle(String title) {
        if (title != null && !title.isBlank()) {
            this.title = title;
        }

        this.setUpdatedAt(ZonedDateTime.now());
    }

    public void updateQuestionContent(String content) {
        if (content != null && !content.isBlank()) {
            this.content = content;
        }

        this.setUpdatedAt(ZonedDateTime.now());
    }

    public void selectBestAnswer() {
        this.hasBestAnswer = true;
    }

    public void incrementViewCount() {
        this.viewCount++;
    }

    public void blindQuestion() {
        this.blindedAt = ZonedDateTime.now();
    }

}
