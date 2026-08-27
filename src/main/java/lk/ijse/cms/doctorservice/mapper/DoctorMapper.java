package lk.ijse.cms.doctorservice.mapper;

import lk.ijse.cms.doctorservice.dto.DoctorRequestDTO;
import lk.ijse.cms.doctorservice.dto.DoctorResponseDTO;
import lk.ijse.cms.doctorservice.entity.Doctor;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public abstract class DoctorMapper {

    public abstract DoctorResponseDTO toResponseDto(Doctor doctor);

    public abstract Doctor toEntity(DoctorRequestDTO dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "doctorId", ignore = true)
    public abstract void updateEntity(DoctorRequestDTO dto, @MappingTarget Doctor doctor);
}
