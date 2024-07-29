package team.onepoom.idk.domain.answer;

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
import lombok.Builder;
import lombok.NoArgsConstructor;
import team.onepoom.idk.domain.BaseEntity;
import team.onepoom.idk.domain.Provider;
import team.onepoom.idk.domain.Question;
import team.onepoom.idk.domain.user.User;

@Table(name = "answers")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Answer extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "writer_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private User writer;

    @JoinColumn(name = "question_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Question question;

    @Column(length = 1000, nullable = false)
    private String content;

    @Column(nullable = true)
    private ZonedDateTime reportedAt;

    @Column(nullable = false)
    private boolean isSelected;

    @Builder
    Answer(Provider provider, long questionId, String content) {
        this.writer = new User(provider.id());
        this.question = new Question(questionId);
        this.content = content;
    }

    //수정
    public void updateAnswer(String content) {
        this.content = content;
    }

    //채택
    public void select() {
        this.isSelected = true;
    }

//    게시글 상태변경
//    public void hide(Provider provider) {
//        if(!provider.roles().contains(Role.ADMIN)) throw new RuntimeException("Forbidden");
//    }

    //질문작성자 체크
    public boolean isOwner(Provider provider) {
        if (provider.id() != writer.getId()) {
            throw new RuntimeException("Forbidden");
        }
        return true;
    }
}
