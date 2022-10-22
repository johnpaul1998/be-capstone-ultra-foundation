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
@RequiredArgsConstructor
@Entity
@Builder
@AllArgsConstructor
@Table(schema = SchemaConfiguration.SCHEMA_NAME, name = "DONATIONS")
public class DonationEntity {
    @Id
    private UUID donationId;
    private String firstName;
    private String lastName;
    private String email;
    private float amount;
    private ZonedDateTime createdDate;
    private ZonedDateTime modifiedDate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        DonationEntity that = (DonationEntity) o;
        return donationId != null && Objects.equals(donationId, that.donationId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
