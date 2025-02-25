package team.onepoom.idk.common;

import jakarta.persistence.MappedSuperclass;
import java.time.ZonedDateTime;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SourceType;

@MappedSuperclass
@Getter
public abstract class BaseEntity {

    @CreationTimestamp(source = SourceType.DB)
    private ZonedDateTime createdAt;

    @Setter
    private ZonedDateTime updatedAt;

}
