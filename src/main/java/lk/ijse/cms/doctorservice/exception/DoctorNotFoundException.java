package lk.ijse.cms.doctorservice.exception;

public class DoctorNotFoundException extends RuntimeException {
    public DoctorNotFoundException(String id) {
        super("Doctor not found with ID: " + id);
    }
}
