package team.onepoom.idk.domain.user;

import jakarta.persistence.AttributeConverter;
import org.springframework.stereotype.Component;

@Component
class UserRoleConverter implements
    AttributeConverter<Role, String> {

    @Override
    public String convertToDatabaseColumn(Role role) {
        return role.name();
    }

    @Override
    public Role convertToEntityAttribute(String s) {
        return Role.valueOf(s);
    }
}
