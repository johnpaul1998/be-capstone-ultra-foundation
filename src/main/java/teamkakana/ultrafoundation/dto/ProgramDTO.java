package teamkakana.ultrafoundation.dto;

import lombok.Data;

import java.time.ZonedDateTime;
import java.util.UUID;

@Data
public class ProgramDTO {
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
}
