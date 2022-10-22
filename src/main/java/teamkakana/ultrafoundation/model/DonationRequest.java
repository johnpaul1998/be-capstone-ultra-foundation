package teamkakana.ultrafoundation.model;

import lombok.Builder;
import lombok.Data;

import java.time.ZonedDateTime;

@Data
@Builder
public class DonationRequest {
    private String firstName;
    private String lastName;
    private String email;
    private float amount;
}
