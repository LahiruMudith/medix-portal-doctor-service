package lk.ijse.cms.doctorservice.service.impl;

import lk.ijse.cms.doctorservice.dto.DoctorRequestDTO;
import lk.ijse.cms.doctorservice.dto.DoctorResponseDTO;
import lk.ijse.cms.doctorservice.entity.Doctor;
import lk.ijse.cms.doctorservice.exception.DoctorNotFoundException;
import lk.ijse.cms.doctorservice.exception.DuplicateDoctorException;
import lk.ijse.cms.doctorservice.mapper.DoctorMapper;
import lk.ijse.cms.doctorservice.repository.DoctorRepository;
import lk.ijse.cms.doctorservice.service.DoctorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepository doctorRepository;
    private final DoctorMapper doctorMapper;

    @Override
    @Transactional
    public DoctorResponseDTO createDoctor(DoctorRequestDTO dto) {
        log.debug("Creating doctor with name: {}", dto.getName());

        String doctorId = dto.getDoctorId();
        if (doctorId != null && !doctorId.isBlank()) {
            if (doctorRepository.existsById(doctorId)) {
                log.warn("Duplicate doctor ID: {}", doctorId);
                throw new DuplicateDoctorException(doctorId);
            }
        } else {
            doctorId = "DOC-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        }

        Doctor doctor = doctorMapper.toEntity(dto);
        doctor.setDoctorId(doctorId);

        Doctor saved = doctorRepository.save(doctor);
        log.info("Doctor created: {}", saved.getDoctorId());
        return doctorMapper.toResponseDto(saved);
    }

    @Override
    @Transactional
    public DoctorResponseDTO updateDoctor(String doctorId, DoctorRequestDTO dto) {
        log.debug("Updating doctor with ID: {}", doctorId);
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new DoctorNotFoundException(doctorId));

        doctorMapper.updateEntity(dto, doctor);
        doctor.setDoctorId(doctorId);

        Doctor updated = doctorRepository.save(doctor);
        log.info("Doctor updated: {}", doctorId);
        return doctorMapper.toResponseDto(updated);
    }

    @Override
    @Transactional
    public void deleteDoctor(String doctorId) {
        log.debug("Deleting doctor with ID: {}", doctorId);
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new DoctorNotFoundException(doctorId));
        doctorRepository.delete(doctor);
        log.info("Doctor deleted: {}", doctorId);
    }

    @Override
    @Transactional(readOnly = true)
    public DoctorResponseDTO getDoctor(String doctorId) {
        log.debug("Fetching doctor with ID: {}", doctorId);
        return doctorRepository.findById(doctorId)
                .map(doctorMapper::toResponseDto)
                .orElseThrow(() -> new DoctorNotFoundException(doctorId));
    }

    @Override
    @Transactional(readOnly = true)
    public List<DoctorResponseDTO> getAllDoctors() {
        log.debug("Fetching all doctors");
        return doctorRepository.findAll()
                .stream()
                .map(doctorMapper::toResponseDto)
                .collect(Collectors.toList());
    }
}
