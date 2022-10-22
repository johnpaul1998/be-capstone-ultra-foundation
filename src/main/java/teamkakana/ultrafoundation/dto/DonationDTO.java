package teamkakana.ultrafoundation.dto;

import lombok.Data;

import java.time.ZonedDateTime;
import java.util.UUID;

@Data
public class DonationDTO {
    private UUID donationId;
    private String firstName;
    private String lastName;
    private String email;
    private float amount;
    private ZonedDateTime createdDate;
    private ZonedDateTime modifiedDate;
}
