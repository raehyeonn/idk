package team.onepoom.idk.member.domain;

public class DeletedMember {

    public static Member createDeletedMember() {
        return new Member(1L, "deleted@user.com", "deletedPassword", "탈퇴한 사용자", Role.DELETED);
    }

}
