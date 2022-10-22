package teamkakana.ultrafoundation.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import teamkakana.ultrafoundation.dto.ProgramDTO;
import teamkakana.ultrafoundation.entity.ProgramEntity;
import teamkakana.ultrafoundation.exception.UserAlreadyExist;
import teamkakana.ultrafoundation.model.ProgramRequest;
import teamkakana.ultrafoundation.repository.ProgramRepository;
import teamkakana.ultrafoundation.util.DateTimeUtil;
import teamkakana.ultrafoundation.util.S3StorageUtil;

import java.io.IOException;
import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProgramService {
    private final ProgramRepository programRepository;
    private final ModelMapper modelMapper;
    private final DateTimeUtil dateTimeUtil;
    private final S3StorageUtil s3StorageUtil;

    public List<ProgramDTO> getAllPrograms(){
        // Get all data from database
        List<ProgramEntity> allPrograms = programRepository.findAll(Sort.by(Sort.Direction.ASC,"createdDate"));
        // Initialize dto
        List<ProgramDTO> allProgramsDTO = new ArrayList<>();

        allPrograms.forEach(program ->{
            allProgramsDTO.add(modelMapper.map(program , ProgramDTO.class));
        });


        return allProgramsDTO;
    }

    public List<ProgramDTO> addProgram(ProgramRequest newProgram) {

        // Save new program to database
        programRepository.save(ProgramEntity
                .builder()
                .programId(UUID.randomUUID())
                .programName(newProgram.getProgramName())
                .description(newProgram.getDescription())
                .imageLink(null)
                .programTime(newProgram.getProgramTime())
                .programDate(newProgram.getProgramDate())
                .pointsToEarn(newProgram.getPointsToEarn())
                .duration(newProgram.getDuration())
                .location(newProgram.getLocation())
                .createdDate(dateTimeUtil.currentDate())
                .modifiedDate(dateTimeUtil.currentDate())
                .build());

        return getAllPrograms();
    }

    public List<ProgramDTO> deleteProgram(UUID programId) {

        // Get program
        ProgramEntity program = programRepository.findByProgramId(programId);

        // Check if product exist
        if(program == null) throw new UserAlreadyExist("doesn't exist");

        // Delete program
        programRepository.deleteByProgramId(programId);

        return getAllPrograms();
    }
    public List<ProgramDTO> uploadProgramImage(UUID programId, MultipartFile file) {
        // Initialize program
        ProgramEntity program = programRepository.findByProgramId(programId);
        if (program == null) throw new IllegalStateException("doesn't exist");

        // Check if file validity
        s3StorageUtil.checkFile(file);

        // Grab some meta data
        Map<String, String> metadata = s3StorageUtil.getMetaData(file);

        // Store the image in S3
        String path = String.format("%s/%s", "ultrafoundation-capstone/programs", programId);
        String fileName = String.format("%s-%s", "program", file.getOriginalFilename());
        try {
            s3StorageUtil.save(path, fileName, Optional.of(metadata), file.getInputStream());
            programRepository.save(ProgramEntity
                    .builder()
                    .programId(program.getProgramId())
                    .programName(program.getProgramName())
                    .description(program.getDescription())
                    .imageLink(fileName)
                    .programTime(program.getProgramTime())
                    .programDate(program.getProgramDate())
                    .pointsToEarn(program.getPointsToEarn())
                    .duration(program.getDuration())
                    .location(program.getLocation())
                    .createdDate(program.getCreatedDate())
                    .modifiedDate(dateTimeUtil.currentDate())
                    .build());
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }

        return getAllPrograms();
    }
    public byte[] downloadProgramImage(UUID programId) {
        // Initialize program
        ProgramEntity program = programRepository.findByProgramId(programId);
        if (program == null) throw new IllegalStateException("doesn't exist");

        String path = String.format("%s/%s", "ultrafoundation-capstone/programs", programId);

        return s3StorageUtil.download(path, program.getImageLink());
    }
}
