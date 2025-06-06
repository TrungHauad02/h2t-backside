package com.englishweb.h2t_backside.mapper.test;

import com.englishweb.h2t_backside.dto.test.SubmitToeicPart1DTO;
import com.englishweb.h2t_backside.model.test.SubmitToeic;
import com.englishweb.h2t_backside.model.test.SubmitToeicPart1;
import com.englishweb.h2t_backside.model.test.ToeicPart1;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        builder = @Builder(disableBuilder = true)
)
public interface SubmitToeicPart1Mapper {

    // Convert DTO -> Entity
    @Mapping(target = "id", source = "dto.id")
    @Mapping(target = "submitToeic", source = "dto.submitToeicId", qualifiedByName = "mapSubmitToeicIdToEntity")
    @Mapping(target = "toeicPart1", source = "dto.toeicPart1Id", qualifiedByName = "mapToeicPart1IdToEntity")
    @Mapping(target = "answer", source = "dto.answer")
    @Mapping(target = "status", source = "dto.status", defaultValue = "true")
    SubmitToeicPart1 convertToEntity(SubmitToeicPart1DTO dto);

    // Convert Entity -> DTO
    @Mapping(target = "id", source = "entity.id")
    @Mapping(target = "submitToeicId", source = "entity.submitToeic.id")
    @Mapping(target = "toeicPart1Id", source = "entity.toeicPart1.id")
    @Mapping(target = "answer", source = "entity.answer")
    @Mapping(target = "status", source = "entity.status")
    @Mapping(target = "createdAt", source = "entity.createdAt")
    @Mapping(target = "updatedAt", source = "entity.updatedAt")
    SubmitToeicPart1DTO convertToDTO(SubmitToeicPart1 entity);

    // Patch từ DTO -> Entity
    @Mapping(target = "submitToeic", source = "dto.submitToeicId", qualifiedByName = "mapSubmitToeicIdToEntity")
    @Mapping(target = "toeicPart1", source = "dto.toeicPart1Id", qualifiedByName = "mapToeicPart1IdToEntity")
    @Mapping(target = "answer", source = "dto.answer")
    @Mapping(target = "status", source = "dto.status")
    void patchEntityFromDTO(SubmitToeicPart1DTO dto, @MappingTarget SubmitToeicPart1 entity);

    @Named("mapSubmitToeicIdToEntity")
    default SubmitToeic mapSubmitToeicIdToEntity(Long id) {
        if (id == null) return null;
        SubmitToeic entity = new SubmitToeic();
        entity.setId(id);
        return entity;
    }

    @Named("mapToeicPart1IdToEntity")
    default ToeicPart1 mapToeicPart1IdToEntity(Long id) {
        if (id == null) return null;
        ToeicPart1 entity = new ToeicPart1();
        entity.setId(id);
        return entity;
    }
}
