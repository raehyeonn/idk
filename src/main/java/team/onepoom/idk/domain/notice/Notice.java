package team.onepoom.idk.domain.notice;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team.onepoom.idk.domain.BaseEntity;
import team.onepoom.idk.domain.Provider;
import team.onepoom.idk.domain.user.User;

@Entity
@Table(name = "notices")
@NoArgsConstructor
@Getter
@AllArgsConstructor
public class Notice extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "writer_id")
    private User writer;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(nullable = false, length = 1000)
    private String content;

    @Column(nullable = false)
    private int views;

    // 공지사항 생성
    public Notice(Provider provider, String title, String content) {
        this.writer = new User(provider.id());
        this.title = title;
        this.content = content;
        this.views = 0;
    }

    // 공지사항 수정
    public Notice(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }

    // 조회수 증가
    public void incrementViews() {
        this.views++;
    }
}
