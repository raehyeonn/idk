package team.onepoom.idk.answer.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.ZonedDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team.onepoom.idk.common.exception.AnswerForbiddenException;
import team.onepoom.idk.common.BaseEntity;
import team.onepoom.idk.question.domain.Question;
import team.onepoom.idk.member.domain.Member;
import team.onepoom.idk.security.jwt.CustomUserDetails;

@Entity
@Table(name = "answers")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Answer extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "question_id")
    private Question question;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "writer_id")
    private Member writer;

    @Column(nullable = false, length = 1000)
    private String content;

    @Column(nullable = false)
    private boolean isSelected;

    private ZonedDateTime blindedAt;

    public Answer(Long id) {
        this.id = id;
    }

    public void validateAnswerOwner(CustomUserDetails customUserDetails) {
        if (customUserDetails.getId() != this.writer.getId()) {
            throw new AnswerForbiddenException(customUserDetails.getMember().getId());
        }
    }

    public void updateAnswer(String content) {
        if (content != null && !content.isBlank()) {
            this.content = content;
        }

        this.setUpdatedAt(ZonedDateTime.now());
    }

    public void setBestAnswer() {
        this.isSelected = true;
    }

    public void blindAnswer() {
        this.blindedAt = ZonedDateTime.now();
    }

    public void setWriterToDeletedMember(Member member) {
        this.writer = member;
    }

    /*

    @Builder
    Answer(Member writer, long questionId, String content) {
        this.writer = writer;
        this.question = new Question(questionId);
        this.content = content;
    }
    */

    /*




    public void isReported(Answer answer) {
        if(answer.blindedAt != null) {
            throw new ReportedAnswerException(answer.getId());
        }
    }

    //신고
    public void report() {
        reportedAt = ZonedDateTime.now();
    }

    //질문작성자 체크


    public void reported() {
        this.reportedAt = ZonedDateTime.now();
    }

    */
}

