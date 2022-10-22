package teamkakana.ultrafoundation.controller;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import teamkakana.ultrafoundation.dto.UserDTO;
import teamkakana.ultrafoundation.model.UserRequest;
import teamkakana.ultrafoundation.service.UserService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @GetMapping("/getAll")
    public List<UserDTO> getAllUsers(){
        return userService.getAllUsers();
    }

    @PutMapping("/signup")
    public UserDTO registerUser(@RequestBody @NonNull UserRequest userRequest) {
        return userService.saveUser(userRequest);
    }

    @DeleteMapping("/{email}")
    public String deleteUser(@PathVariable String email) {
        return userService.deleteUser(email);
    }

    @PostMapping("/update/{oldEmail}")
    public UserDTO updateUser(@PathVariable String oldEmail, @RequestBody UserRequest userRequest) {
        return userService.updateUser(oldEmail, userRequest);
    }

    @PostMapping("/login")
    public UserDTO loginUser(@RequestBody @NonNull UserRequest userRequest) {
        return userService.loginUser(userRequest);
    }

    @PostMapping("/loginByProvider/{email}")
    public UserDTO loginByProvider(@PathVariable String email) {
        return userService.loginByProvider(email);
    }

    @PutMapping(path = "/{email}/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<UserDTO> uploadUserImage(@PathVariable String email, @RequestParam("file") MultipartFile file) {
        return userService.uploadUserImage(email, file);
    }

    @GetMapping(path = "{email}/download")
    public byte[] downloadUserImage(@PathVariable String email) {
        return userService.downloadUserImage(email);
    }
}
