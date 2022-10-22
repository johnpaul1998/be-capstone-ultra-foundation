package teamkakana.ultrafoundation.controller;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import teamkakana.ultrafoundation.dto.DonationDTO;
import teamkakana.ultrafoundation.model.DonationRequest;
import teamkakana.ultrafoundation.service.DonationService;

import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin("*")
@RequestMapping("/donation")
@RequiredArgsConstructor
public class DonationController {
    private final DonationService donationService;

    @GetMapping("/getAll")
    public List<DonationDTO> gelAllDonations(){
        return donationService.getAllDonations();
    }
    @PutMapping("/add")
    public DonationDTO addDonate(@RequestBody @NonNull DonationRequest donationRequest) {
        return donationService.saveDonate(donationRequest);
    }

    @DeleteMapping("/{email}")
    public String deleteUser(@PathVariable String email) {
        return donationService.deleteDonation(email);
    }

    @PostMapping("/update/{oldEmail}")
    public DonationDTO updateDonation(@PathVariable String oldEmail, @RequestBody DonationRequest donationRequest) {
        return donationService.updateDonation(oldEmail, donationRequest);
    }
}
