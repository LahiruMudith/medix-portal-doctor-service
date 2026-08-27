package lk.ijse.cms.doctorservice.controller;

import jakarta.validation.Valid;
import lk.ijse.cms.doctorservice.dto.DoctorRequestDTO;
import lk.ijse.cms.doctorservice.dto.DoctorResponseDTO;
import lk.ijse.cms.doctorservice.service.DoctorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/doctors")
@RequiredArgsConstructor
@Slf4j
@Validated
public class DoctorController {

    private final DoctorService doctorService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DoctorResponseDTO> createDoctor(@Valid @RequestBody DoctorRequestDTO dto) {
        log.info("POST /api/v1/doctors - Name: {}", dto.getName());
        return ResponseEntity.status(HttpStatus.CREATED).body(doctorService.createDoctor(dto));
    }

    @PutMapping(value = "/{doctorId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DoctorResponseDTO> updateDoctor(
            @PathVariable String doctorId,
            @Valid @RequestBody DoctorRequestDTO dto) {
        log.info("PUT /api/v1/doctors/{} - Updating doctor", doctorId);
        return ResponseEntity.ok(doctorService.updateDoctor(doctorId, dto));
    }

    @DeleteMapping("/{doctorId}")
    public ResponseEntity<Void> deleteDoctor(@PathVariable String doctorId) {
        log.info("DELETE /api/v1/doctors/{} - Deleting doctor", doctorId);
        doctorService.deleteDoctor(doctorId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/{doctorId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DoctorResponseDTO> getDoctor(@PathVariable String doctorId) {
        log.info("GET /api/v1/doctors/{} - Fetching doctor", doctorId);
        return ResponseEntity.ok(doctorService.getDoctor(doctorId));
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<DoctorResponseDTO>> getAllDoctors() {
        log.info("GET /api/v1/doctors - Fetching all doctors");
        return ResponseEntity.ok(doctorService.getAllDoctors());
    }
}
