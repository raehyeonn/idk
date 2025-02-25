package team.onepoom.idk.member.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team.onepoom.idk.member.domain.Member;

@Getter
@NoArgsConstructor
public class WriterDto {

    private Long id;
    private String email;
    private String nickname;

    public WriterDto(Member writer) {
        this.id = writer.getId();
        this.email = writer.getEmail();
        this.nickname = writer.getNickname();
    }

    @QueryProjection
    public WriterDto(long id, String email, String nickName) {
        this.id = id;
        this.email = email;
        this.nickname = nickName;
    }

}
