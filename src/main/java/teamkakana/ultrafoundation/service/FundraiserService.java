package teamkakana.ultrafoundation.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import teamkakana.ultrafoundation.dto.FundraiserDTO;
import teamkakana.ultrafoundation.entity.FundraiserEntity;
import teamkakana.ultrafoundation.exception.UserAlreadyExist;
import teamkakana.ultrafoundation.model.FundraiserRequest;
import teamkakana.ultrafoundation.repository.FundraiserRepository;
import teamkakana.ultrafoundation.util.DateTimeUtil;
import teamkakana.ultrafoundation.util.S3StorageUtil;

import java.io.IOException;
import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class FundraiserService {

    private final FundraiserRepository fundraiserRepository;
    private final ModelMapper modelMapper;
    private final DateTimeUtil dateTimeUtil;
    private final S3StorageUtil s3StorageUtil;

    public List<FundraiserDTO> getAllFundraisers() {

        // Get all data from database
        List<FundraiserEntity> allFundraisers = fundraiserRepository.findAll(Sort.by(Sort.Direction.ASC, "createdDate"));

        // Initialize dto
        List<FundraiserDTO> allFundraisersDTO = new ArrayList<>();

        allFundraisers.forEach(product -> {
            allFundraisersDTO.add(modelMapper.map(product, FundraiserDTO.class));
        });

        return allFundraisersDTO;
    }

    public List<FundraiserDTO> addFundraiser(FundraiserRequest newFundraiser) {

        // Save new blog to database
        fundraiserRepository.save(FundraiserEntity
                .builder()
                .fundraiserId(UUID.randomUUID())
                .fundraiserName(newFundraiser.getFundraiserName())
                .description(newFundraiser.getDescription())
                .targetAmount(newFundraiser.getTargetAmount())
                .amountGenerated(newFundraiser.getAmountGenerated())
                .imageLink(null)
                .createdDate(dateTimeUtil.currentDate())
                .modifiedDate(dateTimeUtil.currentDate())
                .build());

        return getAllFundraisers();
    }

    public List<FundraiserDTO> deleteFundraiser(UUID fundraiserId) {

        // Get blog
        FundraiserEntity fundraiser = fundraiserRepository.findByFundraiserId(fundraiserId);

        // Check if product exist
        if(fundraiser == null) throw new UserAlreadyExist("Blog doesn't exist");

        // Delete product
        fundraiserRepository.deleteByFundraiserId(fundraiserId);

        return getAllFundraisers();
    }

    public List<FundraiserDTO> uploadFundraiserImage(UUID fundraiserId, MultipartFile file) {
        // Initialize blog
        FundraiserEntity fundraiser = fundraiserRepository.findByFundraiserId(fundraiserId);
        if (fundraiser == null) throw new IllegalStateException("Blog doesn't exist");

        // Check if file validity
        s3StorageUtil.checkFile(file);

        // Grab some meta data
        Map<String, String> metadata = s3StorageUtil.getMetaData(file);

        // Store the image in S3
        String path = String.format("%s/%s", "ultrafoundation-capstone/blogs", fundraiserId);
        String fileName = String.format("%s-%s", "blog", file.getOriginalFilename());
        try {
            s3StorageUtil.save(path, fileName, Optional.of(metadata), file.getInputStream());
            fundraiserRepository.save(FundraiserEntity
                    .builder()
                    .fundraiserId(fundraiser.getFundraiserId())
                    .fundraiserName(fundraiser.getFundraiserName())
                    .description(fundraiser.getDescription())
                    .targetAmount(fundraiser.getTargetAmount())
                    .amountGenerated(fundraiser.getAmountGenerated())
                    .imageLink(fileName)
                    .createdDate(fundraiser.getCreatedDate())
                    .modifiedDate(dateTimeUtil.currentDate())
                    .build());
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }

        return getAllFundraisers();
    }

    public byte[] downloadFundraiserImage(UUID fundraiserId) {
        // Initialize blog
        FundraiserEntity fundraiser = fundraiserRepository.findByFundraiserId(fundraiserId);
        if (fundraiser == null) throw new IllegalStateException("Blog doesn't exist");

        String path = String.format("%s/%s", "ultrafoundation-capstone/blogs", fundraiserId);

        return s3StorageUtil.download(path, fundraiser.getImageLink());
    }

}

