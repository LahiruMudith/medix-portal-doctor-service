package lk.ijse.cms.doctorservice.mapper;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import lk.ijse.cms.doctorservice.dto.DoctorRequestDTO;
import lk.ijse.cms.doctorservice.dto.DoctorResponseDTO;
import lk.ijse.cms.doctorservice.entity.Doctor;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-08-26T13:25:58+0530",
    comments = "version: 1.6.3, compiler: javac, environment: Java 25.0.4 (Microsoft)"
)
@Component
public class DoctorMapperImpl extends DoctorMapper {

    @Override
    public DoctorResponseDTO toResponseDto(Doctor doctor) {
        if ( doctor == null ) {
            return null;
        }

        DoctorResponseDTO.DoctorResponseDTOBuilder doctorResponseDTO = DoctorResponseDTO.builder();

        doctorResponseDTO.doctorId( doctor.getDoctorId() );
        doctorResponseDTO.name( doctor.getName() );
        doctorResponseDTO.specialization( doctor.getSpecialization() );
        doctorResponseDTO.qualifications( doctor.getQualifications() );
        doctorResponseDTO.contactNumber( doctor.getContactNumber() );
        doctorResponseDTO.email( doctor.getEmail() );
        List<String> list = doctor.getAvailableDays();
        if ( list != null ) {
            doctorResponseDTO.availableDays( new ArrayList<String>( list ) );
        }

        return doctorResponseDTO.build();
    }

    @Override
    public Doctor toEntity(DoctorRequestDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Doctor.DoctorBuilder doctor = Doctor.builder();

        doctor.doctorId( dto.getDoctorId() );
        doctor.name( dto.getName() );
        doctor.specialization( dto.getSpecialization() );
        doctor.qualifications( dto.getQualifications() );
        doctor.contactNumber( dto.getContactNumber() );
        doctor.email( dto.getEmail() );
        List<String> list = dto.getAvailableDays();
        if ( list != null ) {
            doctor.availableDays( new ArrayList<String>( list ) );
        }

        return doctor.build();
    }

    @Override
    public void updateEntity(DoctorRequestDTO dto, Doctor doctor) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getName() != null ) {
            doctor.setName( dto.getName() );
        }
        if ( dto.getSpecialization() != null ) {
            doctor.setSpecialization( dto.getSpecialization() );
        }
        if ( dto.getQualifications() != null ) {
            doctor.setQualifications( dto.getQualifications() );
        }
        if ( dto.getContactNumber() != null ) {
            doctor.setContactNumber( dto.getContactNumber() );
        }
        if ( dto.getEmail() != null ) {
            doctor.setEmail( dto.getEmail() );
        }
        if ( doctor.getAvailableDays() != null ) {
            List<String> list = dto.getAvailableDays();
            if ( list != null ) {
                doctor.getAvailableDays().clear();
                doctor.getAvailableDays().addAll( list );
            }
        }
        else {
            List<String> list = dto.getAvailableDays();
            if ( list != null ) {
                doctor.setAvailableDays( new ArrayList<String>( list ) );
            }
        }
    }
}
