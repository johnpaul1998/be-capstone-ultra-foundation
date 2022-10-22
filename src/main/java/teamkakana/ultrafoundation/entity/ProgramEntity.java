package teamkakana.ultrafoundation.entity;

import lombok.*;
import org.hibernate.Hibernate;
import teamkakana.ultrafoundation.config.SchemaConfiguration;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@ToString
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(schema = SchemaConfiguration.SCHEMA_NAME, name = "PROGRAMS")
public class ProgramEntity {
    @Id
    private UUID programId;
    private String programName;
    private String description;
    private String imageLink;
    private int programTime;
    private int programDate;
    private float pointsToEarn;
    private float duration;
    private String location;
    private ZonedDateTime createdDate;
    private ZonedDateTime modifiedDate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        ProgramEntity that = (ProgramEntity) o;
        return programId != null && Objects.equals(programId, that.programId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
