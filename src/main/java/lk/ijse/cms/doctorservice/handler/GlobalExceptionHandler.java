package lk.ijse.cms.doctorservice.handler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import lk.ijse.cms.doctorservice.exception.DoctorNotFoundException;
import lk.ijse.cms.doctorservice.exception.DuplicateDoctorException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.URI;
import java.time.Instant;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
@Order(-1)
public class GlobalExceptionHandler {

    @ExceptionHandler(DoctorNotFoundException.class)
    public ResponseEntity<ProblemDetail> handleNotFound(DoctorNotFoundException ex, HttpServletRequest req) {
        log.warn("Doctor not found: {}", ex.getMessage());
        return problemResponse(HttpStatus.NOT_FOUND, "Doctor Not Found", ex.getMessage(), req);
    }

    @ExceptionHandler(DuplicateDoctorException.class)
    public ResponseEntity<ProblemDetail> handleDuplicate(DuplicateDoctorException ex, HttpServletRequest req) {
        log.warn("Duplicate doctor: {}", ex.getMessage());
        return problemResponse(HttpStatus.CONFLICT, "Duplicate Doctor", ex.getMessage(), req);
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<ProblemDetail> handleBind(BindException ex, HttpServletRequest req) {
        String detail = ex.getBindingResult().getFieldErrors().stream()
                .map(fe -> fe.getField() + ": " + fe.getDefaultMessage())
                .collect(Collectors.joining("; "));
        log.warn("Validation failed [{}]: {}", req.getRequestURI(), detail);
        ProblemDetail pd = buildProblemDetail(HttpStatus.BAD_REQUEST, "Validation Error", detail, req.getRequestURI());
        ex.getBindingResult().getFieldErrors().forEach(fe -> pd.setProperty(fe.getField(), fe.getDefaultMessage()));
        return problemResponse(HttpStatus.BAD_REQUEST, pd);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ProblemDetail> handleConstraint(ConstraintViolationException ex, HttpServletRequest req) {
        String detail = ex.getConstraintViolations().stream()
                .map(cv -> {
                    String path = cv.getPropertyPath().toString();
                    String field = path.contains(".") ? path.substring(path.lastIndexOf('.') + 1) : path;
                    return field + ": " + cv.getMessage();
                }).collect(Collectors.joining("; "));
        log.warn("Constraint violation [{}]: {}", req.getRequestURI(), detail);
        ProblemDetail pd = buildProblemDetail(HttpStatus.UNPROCESSABLE_ENTITY, "Validation Error", detail, req.getRequestURI());
        ex.getConstraintViolations().forEach(cv -> {
            String path = cv.getPropertyPath().toString();
            String field = path.contains(".") ? path.substring(path.lastIndexOf('.') + 1) : path;
            pd.setProperty(field, cv.getMessage());
        });
        return problemResponse(HttpStatus.UNPROCESSABLE_ENTITY, pd);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ProblemDetail> handleGeneric(Exception ex, HttpServletRequest req) {
        log.error("Unhandled exception [{}]: {}", req.getRequestURI(), ex.getMessage(), ex);
        return problemResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error",
                "An unexpected error occurred. Please try again later.", req);
    }

    private ResponseEntity<ProblemDetail> problemResponse(HttpStatus status, String title, String detail, HttpServletRequest req) {
        ProblemDetail pd = buildProblemDetail(status, title, detail, req.getRequestURI());
        return problemResponse(status, pd);
    }

    private ProblemDetail buildProblemDetail(HttpStatus status, String title, String detail, String instance) {
        ProblemDetail pd = ProblemDetail.forStatusAndDetail(status, detail);
        pd.setTitle(title);
        pd.setInstance(URI.create(instance));
        pd.setProperty("timestamp", Instant.now().toString());
        return pd;
    }

    private ResponseEntity<ProblemDetail> problemResponse(HttpStatus status, ProblemDetail pd) {
        return ResponseEntity.status(status)
                .contentType(MediaType.APPLICATION_PROBLEM_JSON)
                .body(pd);
    }
}
