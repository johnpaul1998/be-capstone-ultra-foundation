package teamkakana.ultrafoundation.dto;

import lombok.Data;

import java.time.ZonedDateTime;
import java.util.UUID;

@Data
public class UserDTO {

    private UUID userId;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String imageLink;
    private ZonedDateTime createdDate;
    private ZonedDateTime modifiedDate;
}
