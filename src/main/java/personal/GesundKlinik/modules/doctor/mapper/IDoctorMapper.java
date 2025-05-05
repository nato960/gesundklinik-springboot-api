package personal.GesundKlinik.modules.doctor.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import personal.GesundKlinik.modules.doctor.dto.request.SaveDoctorRequest;
import personal.GesundKlinik.modules.doctor.dto.request.UpdateDoctorRequest;
import personal.GesundKlinik.modules.doctor.dto.response.*;
import personal.GesundKlinik.modules.doctor.entity.Doctor;

import java.util.List;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface IDoctorMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "active", expression = "java(true)")
    Doctor toEntity(final SaveDoctorRequest request);

    SaveDoctorResponse toSaveResponse(final Doctor entity);

    Doctor toEntity(final Long id, final UpdateDoctorRequest request);

    UpdateDoctorResponse toUpdateResponse(final Doctor entity);

    DetailDoctorResponse toDetailResponse(final Doctor entity);

    List<ListDoctorResponse> toListResponse(final List<Doctor> entities);

    PageDoctorResponse toPageResponse(final Doctor entity);

}
