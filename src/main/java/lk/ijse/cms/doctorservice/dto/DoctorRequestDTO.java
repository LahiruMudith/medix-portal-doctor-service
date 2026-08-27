package lk.ijse.cms.doctorservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DoctorRequestDTO {

    private String doctorId;

    @NotBlank(message = "Name is required")
    @Pattern(regexp = "^[a-zA-Z][a-zA-Z .]*$", message = "Name can only contain letters, dots, and spaces")
    private String name;

    @NotBlank(message = "Specialization is required")
    private String specialization;

    @NotBlank(message = "Qualifications are required")
    private String qualifications;

    @NotBlank(message = "Contact number is required")
    private String contactNumber;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotNull(message = "Available days list cannot be null")
    @NotEmpty(message = "At least one available day is required")
    private List<String> availableDays;
}
