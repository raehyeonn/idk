package team.onepoom.idk.notice.domain;

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
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team.onepoom.idk.common.BaseEntity;
import team.onepoom.idk.member.domain.Member;

@Entity
@Table(name = "notices")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Notice extends BaseEntity {

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

    @Column(nullable = false)
    private int viewCount;

    public void updateNoticeTitle(String title) {
        if (title != null && !title.isBlank()) {
            this.title = title;
        }

        this.setUpdatedAt(ZonedDateTime.now());
    }

    public void updateNoticeContent(String content) {
        if (content != null && !content.isBlank()) {
            this.content = content;
        }

        this.setUpdatedAt(ZonedDateTime.now());
    }

    public void incrementViewCount() {
        this.viewCount++;
    }

}
