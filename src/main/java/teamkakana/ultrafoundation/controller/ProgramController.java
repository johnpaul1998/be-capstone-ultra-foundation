package teamkakana.ultrafoundation.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import teamkakana.ultrafoundation.dto.ProgramDTO;
import teamkakana.ultrafoundation.model.ProgramRequest;
import teamkakana.ultrafoundation.service.ProgramService;

import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin("*")
@RequestMapping("/program")
@RequiredArgsConstructor
public class ProgramController {
    private final ProgramService programService;

    @GetMapping("/getAll")
    public List<ProgramDTO> gelAllPrograms(){
        return programService.getAllPrograms();
    }
    @PutMapping("/add")
    public List<ProgramDTO> addProgram(@RequestBody ProgramRequest programRequest) {
        return programService.addProgram(programRequest);
    }

    @DeleteMapping("/delete/{programId}")
    public List<ProgramDTO> deleteProgram(@PathVariable UUID programId) {
        return programService.deleteProgram(programId);
    }

    @PutMapping(path = "/{programId}/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ProgramDTO> uploadProgramImage(@PathVariable UUID programId, @RequestParam("file") MultipartFile file) {
        return programService.uploadProgramImage(programId, file);
    }

    @GetMapping(path = "{programId}/download")
    public byte[] downloadProgramImage(@PathVariable UUID programId) {
        return programService.downloadProgramImage(programId);
    }
}
