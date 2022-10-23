package teamkakana.ultrafoundation.model;

import lombok.Builder;
import lombok.Data;
import org.joda.time.DateTime;

import java.sql.Date;
import java.sql.Time;
import java.time.ZonedDateTime;
import java.util.TimeZone;


@Data
@Builder
public class ProgramRequest {
    private String programName;
    private String description;
    private String programTime;
    private String programDate;
    private float pointsToEarn;
    private float duration;
    private String location;
}
