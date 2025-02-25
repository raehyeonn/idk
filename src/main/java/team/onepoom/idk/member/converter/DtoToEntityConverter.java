package team.onepoom.idk.member.converter;

import team.onepoom.idk.member.domain.Member;
import team.onepoom.idk.member.domain.Role;
import team.onepoom.idk.member.dto.RegisterMemberRequest;

public class DtoToEntityConverter {

    public static Member toEntity(RegisterMemberRequest registerMemberRequest, String encodedPassword) {
        return Member.builder()
        .email(registerMemberRequest.getEmail())
        .password(encodedPassword)
        .nickname(registerMemberRequest.getNickname())
        .role(Role.MEMBER)
        .build();
    }

}
