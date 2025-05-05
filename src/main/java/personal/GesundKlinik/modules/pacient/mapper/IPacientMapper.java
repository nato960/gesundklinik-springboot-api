package personal.GesundKlinik.modules.pacient.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import personal.GesundKlinik.modules.pacient.dto.request.SavePacientRequest;
import personal.GesundKlinik.modules.pacient.dto.request.UpdatePacientRequest;
import personal.GesundKlinik.modules.pacient.dto.response.*;
import personal.GesundKlinik.modules.pacient.entity.Pacient;

import java.util.List;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface IPacientMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "active", expression = "java(true)")
    Pacient toEntity(final SavePacientRequest request);

    SavePacientResponse toSaveResponse(final Pacient entity);

    Pacient toEntity(final Long id, final UpdatePacientRequest request);

    UpdatePacientResponse toUpdateResponse(final Pacient entity);

    DetailPacientResponse toDetailResponse(final Pacient entity);

    List<ListPacientResponse> toListResponse(final List<Pacient> entities);

    PagePacientResponse toPageResponse(final Pacient entity);
}