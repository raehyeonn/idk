package team.onepoom.idk.domain.user;

public record CreateUser(String email, String password, String nickname) {
    public CreateUser insertEncodedPassword(String encoded) {
        return new CreateUser(email, encoded, nickname);
    }
}
