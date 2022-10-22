package teamkakana.ultrafoundation.service;

import org.springframework.data.domain.Sort;
import org.springframework.web.multipart.MultipartFile;
import teamkakana.ultrafoundation.dto.UserDTO;
import teamkakana.ultrafoundation.entity.UserEntity;
import teamkakana.ultrafoundation.exception.UserAlreadyExist;
import teamkakana.ultrafoundation.model.UserRequest;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;
import teamkakana.ultrafoundation.repository.UserRepository;
import teamkakana.ultrafoundation.util.DateTimeUtil;
import teamkakana.ultrafoundation.util.S3StorageUtil;

import java.io.IOException;
import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final DateTimeUtil dateTimeUtil;
    private final ModelMapper modelMapper;
    private final S3StorageUtil s3StorageUtil;

    public List<UserDTO> getAllUsers(){
        // Get all data from database
        List<UserEntity> allUsers = userRepository.findAll(Sort.by(Sort.Direction.ASC,"createdDate"));
        // Initialize dto
        List<UserDTO> allUsersDTO = new ArrayList<>();

        allUsers.forEach(user ->{
            allUsersDTO.add(modelMapper.map(user , UserDTO.class));
        });


        return allUsersDTO;
    }

    public UserDTO saveUser(@NonNull UserRequest newUser) {

        // Check if email is existing
        if(userRepository.findByEmail(newUser.getEmail()) != null) {
            throw new UserAlreadyExist("Email already in used");
        }

        // Initialize user
        UserEntity user = UserEntity
                .builder()
                .userId(UUID.randomUUID())
                .firstName(newUser.getFirstName())
                .lastName(newUser.getLastName())
                .email(newUser.getEmail())
                .password(newUser.getPassword())
                .createdDate(dateTimeUtil.currentDate())
                .modifiedDate(dateTimeUtil.currentDate())
                .build();

        // Save to database
        userRepository.save(user);

        return modelMapper.map(user, UserDTO.class);
    }

    public String deleteUser(String email) {
        String response = "No data has been deleted";

        // Get user
        UserEntity user = userRepository.findByEmail(email);

        // Check if user exist
        if(user != null) {
            userRepository.deleteByEmail(user.getEmail());
            response = email + " has been successfully deleted";
        }

        return response;
    }

    public UserDTO updateUser(String oldEmail, UserRequest userRequest) {
        // Initialize user
        UserEntity user = userRepository.findByEmail(oldEmail);

        // Check if user is existing
        if(user == null) throw new UserAlreadyExist("User doesn't exist");

        // update user
        UserEntity updatedUser = UserEntity
                .builder()
                .userId(user.getUserId())
                .firstName(userRequest.getFirstName())
                .lastName(userRequest.getLastName())
                .email(userRequest.getEmail())
                .password(userRequest.getPassword())
                .createdDate(user.getCreatedDate())
                .modifiedDate(dateTimeUtil.currentDate())
                .build();

        // Check if new email exist
        if(userRepository.findByEmail(updatedUser.getEmail()) != null) {
            throw new UserAlreadyExist("Email already in used");
        }

        // save updated user
        userRepository.save(updatedUser);

        return modelMapper.map(updatedUser, UserDTO.class);
    }

    public UserDTO loginUser(@NonNull UserRequest activeUser) {
        // Initialize User
        UserEntity user = userRepository.findByEmail(activeUser.getEmail());

        // Check if user existing
        if (user == null) throw new UserAlreadyExist("User doesn't exist");

        // Check if email is existing
        if(!Objects.equals(user.getPassword(), activeUser.getPassword())) throw new UserAlreadyExist("Invalid password");

        return modelMapper.map(user, UserDTO.class);
    }

    public UserDTO loginByProvider(@NonNull String email) {
        // Initialize User
        UserEntity user = userRepository.findByEmail(email);

        // Check if email is existing
        if (user == null) {
            // Initialize new user
            UserEntity newUser = UserEntity
                    .builder()
                    .userId(UUID.randomUUID())
                    .email(email)
                    .password(null)
                    .createdDate(dateTimeUtil.currentDate())
                    .modifiedDate(dateTimeUtil.currentDate())
                    .build();

            // Save to database
            userRepository.save(newUser);

            return modelMapper.map(newUser, UserDTO.class);
        }

        return modelMapper.map(user, UserDTO.class);
    }

    public List<UserDTO> uploadUserImage(String email, MultipartFile file) {
        // Initialize program
        UserEntity user = userRepository.findByEmail(email);
        if (user == null) throw new IllegalStateException("doesn't exist");

        // Check if file validity
        s3StorageUtil.checkFile(file);

        // Grab some meta data
        Map<String, String> metadata = s3StorageUtil.getMetaData(file);

        // Store the image in S3
        String path = String.format("%s/%s", "ultrafoundation-capstone/users", email);
        String fileName = String.format("%s-%s", "user", file.getOriginalFilename());
        try {
            s3StorageUtil.save(path, fileName, Optional.of(metadata), file.getInputStream());
            userRepository.save(UserEntity
                    .builder()
                    .userId(user.getUserId())
                    .firstName(user.getFirstName())
                    .lastName(user.getLastName())
                    .email(user.getEmail())
                    .password(user.getPassword())
                    .imageLink(fileName)
                    .createdDate(user.getCreatedDate())
                    .modifiedDate(dateTimeUtil.currentDate())
                    .build());
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }

        return getAllUsers();
    }
    public byte[] downloadUserImage(String email) {
        // Initialize program
        UserEntity user = userRepository.findByEmail(email);
        if (user == null) throw new IllegalStateException("doesn't exist");

        String path = String.format("%s/%s", "ultrafoundation-capstone/users", email);

        return s3StorageUtil.download(path, user.getImageLink());
    }

}
