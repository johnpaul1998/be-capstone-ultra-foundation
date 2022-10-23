package teamkakana.ultrafoundation.dto;

import lombok.Data;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.TimeZone;
import java.util.UUID;

@Data
public class ProgramDTO {
    private UUID programId;
    private String programName;
    private String description;
    private String imageLink;
    private String programTime;
    private String programDate;
    private float pointsToEarn;
    private float duration;
    private String location;
    private ZonedDateTime createdDate;
    private ZonedDateTime modifiedDate;
}
