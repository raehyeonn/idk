package team.onepoom.idk.domain.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;
import team.onepoom.idk.domain.user.CreateUser;

public record CreateUserRequest(@Email @NotBlank String email, @NotBlank @Length(min = 8, max = 16) String password,
                         @NotBlank @Length(min = 2, max = 8) String nickname) {
    public CreateUser toDomain() {
        return new CreateUser(email, password, nickname);
    }
}
