package personal.GesundKlinik.modules.patient.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import personal.GesundKlinik.modules.patient.dto.request.SavePatientRequest;
import personal.GesundKlinik.modules.patient.dto.request.UpdatePatientRequest;
import personal.GesundKlinik.modules.patient.dto.response.*;
import personal.GesundKlinik.modules.patient.entity.Patient;

import java.util.List;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface IPatientMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "active", expression = "java(true)")
    Patient toEntity(final SavePatientRequest request);

    SavePatientResponse toSaveResponse(final Patient entity);

    Patient toEntity(final Long id, final UpdatePatientRequest request);

    UpdatePatientResponse toUpdateResponse(final Patient entity);

    DetailPatientResponse toDetailResponse(final Patient entity);

    List<ListPatientResponse> toListResponse(final List<Patient> entities);

    PagePatientResponse toPageResponse(final Patient entity);
}