package teamkakana.ultrafoundation.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import teamkakana.ultrafoundation.dto.FundraiserDTO;
import teamkakana.ultrafoundation.model.FundraiserRequest;
import teamkakana.ultrafoundation.service.FundraiserService;

import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin("*")
@RequestMapping("/fundraiser")
@RequiredArgsConstructor
public class FundraiserController {

    private final FundraiserService fundraiserService;

    @GetMapping("/getAll")
    public List<FundraiserDTO> getAllFundraisers() {
        return fundraiserService.getAllFundraisers();
    }

    @PutMapping("/add")
    public List<FundraiserDTO> addFundraiser(@RequestBody FundraiserRequest fundraiserRequest) {
        return fundraiserService.addFundraiser(fundraiserRequest);
    }

    @DeleteMapping("/delete/{fundraiserId}")
    public List<FundraiserDTO> deleteFundraiser(@PathVariable UUID fundraiserId) {
        return fundraiserService.deleteFundraiser(fundraiserId);
    }

    @PutMapping(path = "/{fundraiserId}/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<FundraiserDTO> uploadFundraiserImage(@PathVariable UUID fundraiserId, @RequestParam("file") MultipartFile file) {
        return fundraiserService.uploadFundraiserImage(fundraiserId, file);
    }

    @GetMapping(path = "{fundraiserId}/download")
    public byte[] downloadFundraiserImage(@PathVariable UUID fundraiserId) {
        return fundraiserService.downloadFundraiserImage(fundraiserId);
    }
}
