package lk.ijse.cms.doctorservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DoctorResponseDTO {
    private String doctorId;
    private String name;
    private String specialization;
    private String qualifications;
    private String contactNumber;
    private String email;
    private List<String> availableDays;
}
