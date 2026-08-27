package lk.ijse.cms.doctorservice.exception;

public class DuplicateDoctorException extends RuntimeException {
    public DuplicateDoctorException(String id) {
        super("Doctor already exists with ID: " + id);
    }
}
