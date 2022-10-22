package teamkakana.ultrafoundation.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FundraiserRequest {

    private String fundraiserName;
    private String description;
    private float targetAmount;
    private float amountGenerated;
}