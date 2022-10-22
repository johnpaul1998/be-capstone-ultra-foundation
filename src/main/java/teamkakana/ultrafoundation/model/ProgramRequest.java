package teamkakana.ultrafoundation.model;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class ProgramRequest {
    private String programName;
    private String description;
    private int programTime;
    private int programDate;
    private float pointsToEarn;
    private float duration;
    private String location;
}
