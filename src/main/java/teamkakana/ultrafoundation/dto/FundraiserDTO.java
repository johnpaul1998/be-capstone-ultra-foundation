package teamkakana.ultrafoundation.dto;

import lombok.Data;

import java.time.ZonedDateTime;
import java.util.UUID;

@Data
public class FundraiserDTO {
    private UUID fundraiserId;
    private String fundraiserName;
    private String description;
    private float targetAmount;
    private float amountGenerated;
    private String imageLink;
    private ZonedDateTime createdDate;
    private ZonedDateTime modifiedDate;
}
