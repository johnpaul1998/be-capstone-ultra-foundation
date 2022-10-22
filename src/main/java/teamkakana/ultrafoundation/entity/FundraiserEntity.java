package teamkakana.ultrafoundation.entity;

import lombok.*;
import teamkakana.ultrafoundation.config.SchemaConfiguration;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.ZonedDateTime;
import java.util.UUID;

@Getter
@Setter
@ToString
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(schema = SchemaConfiguration.SCHEMA_NAME, name = "FUNDRAISERS")
public class FundraiserEntity {
    @Id
    private UUID fundraiserId;
    private String fundraiserName;
    private String description;
    private float targetAmount;
    private float amountGenerated;
    private String imageLink;
    private ZonedDateTime createdDate;
    private ZonedDateTime modifiedDate;
}
