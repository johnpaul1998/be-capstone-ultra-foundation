package teamkakana.ultrafoundation.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import teamkakana.ultrafoundation.dto.DonationDTO;
import teamkakana.ultrafoundation.entity.DonationEntity;
import teamkakana.ultrafoundation.exception.UserAlreadyExist;
import teamkakana.ultrafoundation.model.DonationRequest;
import teamkakana.ultrafoundation.repository.DonationRepository;
import teamkakana.ultrafoundation.util.DateTimeUtil;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class DonationService {
    private final DonationRepository donationRepository;
    private final ModelMapper modelMapper;
    private final DateTimeUtil dateTimeUtil;


    public List<DonationDTO> getAllDonations(){
        // Get all data from database
        List<DonationEntity> allPrograms = donationRepository.findAll(Sort.by(Sort.Direction.ASC,"createdDate"));
        // Initialize dto
        List<DonationDTO> allDonationsDTO = new ArrayList<>();

        allPrograms.forEach(donation ->{
            allDonationsDTO.add(modelMapper.map(donation , DonationDTO.class));
        });


        return allDonationsDTO;
    }

    public DonationDTO saveDonate(@NonNull DonationRequest newDonate) {

        // Check if email is existing
        if(donationRepository.findByEmail(newDonate.getEmail()) != null) {
            throw new UserAlreadyExist("Email already in used");
        }

        // Initialize user
        DonationEntity donation = DonationEntity
                .builder()
                .donationId(UUID.randomUUID())
                .firstName(newDonate.getFirstName())
                .lastName(newDonate.getLastName())
                .email(newDonate.getEmail())
                .amount(newDonate.getAmount())
                .createdDate(dateTimeUtil.currentDate())
                .modifiedDate(dateTimeUtil.currentDate())
                .build();

        // Save to database
        donationRepository.save(donation);

        return modelMapper.map(donation, DonationDTO.class);
    }
    public String deleteDonation(String email) {
        String response = "No data has been deleted";

        // Get user
        DonationEntity donate = donationRepository.findByEmail(email);

        // Check if user exist
        if(donate != null) {
            donationRepository.deleteByEmail(donate.getEmail());
            response = email + " has been successfully deleted";
        }

        return response;
    }
    public DonationDTO updateDonation(String oldEmail, DonationRequest donationRequest) {
        // Initialize user
        DonationEntity donate = donationRepository.findByEmail(oldEmail);

        // Check if user is existing
        if(donate == null) throw new UserAlreadyExist("User doesn't exist");

        // update user
        DonationEntity updateDonation = DonationEntity
                .builder()
                .donationId(donate.getDonationId())
                .firstName(donate.getFirstName())
                .lastName(donate.getLastName())
                .email(donationRequest.getEmail())
                .amount(donate.getAmount())
                .createdDate(donate.getCreatedDate())
                .modifiedDate(dateTimeUtil.currentDate())
                .build();

        // Check if new email exist
        if(donationRepository.findByEmail(updateDonation.getEmail()) != null) {
            throw new UserAlreadyExist("Email already in used");
        }

        // save updated user
        donationRepository.save(updateDonation);

        return modelMapper.map(updateDonation, DonationDTO.class);
    }
}
