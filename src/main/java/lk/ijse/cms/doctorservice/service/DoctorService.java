package lk.ijse.cms.doctorservice.service;

import lk.ijse.cms.doctorservice.dto.DoctorRequestDTO;
import lk.ijse.cms.doctorservice.dto.DoctorResponseDTO;

import java.util.List;

public interface DoctorService {
    DoctorResponseDTO createDoctor(DoctorRequestDTO dto);
    DoctorResponseDTO updateDoctor(String doctorId, DoctorRequestDTO dto);
    void deleteDoctor(String doctorId);
    DoctorResponseDTO getDoctor(String doctorId);
    List<DoctorResponseDTO> getAllDoctors();
}
