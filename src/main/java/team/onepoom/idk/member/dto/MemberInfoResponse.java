package team.onepoom.idk.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberInfoResponse {

    private long id;
    private String email;
    private String nickname;

}
