package com.englishweb.h2t_backside.mapper.test;

import com.englishweb.h2t_backside.dto.test.SubmitToeicPart2DTO;
import com.englishweb.h2t_backside.model.test.SubmitToeic;
import com.englishweb.h2t_backside.model.test.SubmitToeicPart2;
import com.englishweb.h2t_backside.model.test.ToeicPart2;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        builder = @Builder(disableBuilder = true)
)
public interface SubmitToeicPart2Mapper {

    // DTO → Entity
    @Mapping(target = "id", source = "dto.id")
    @Mapping(target = "submitToeic", source = "dto.submitToeicId", qualifiedByName = "mapSubmitToeicId")
    @Mapping(target = "toeicPart2", source = "dto.toeicPart2Id", qualifiedByName = "mapToeicPart2Id")
    @Mapping(target = "answer", source = "dto.answer")
    @Mapping(target = "status", source = "dto.status", defaultValue = "true")
    SubmitToeicPart2 convertToEntity(SubmitToeicPart2DTO dto);

    // Entity → DTO
    @Mapping(target = "id", source = "entity.id")
    @Mapping(target = "submitToeicId", source = "entity.submitToeic.id")
    @Mapping(target = "toeicPart2Id", source = "entity.toeicPart2.id")
    @Mapping(target = "answer", source = "entity.answer")
    @Mapping(target = "status", source = "entity.status")
    @Mapping(target = "createdAt", source = "entity.createdAt")
    @Mapping(target = "updatedAt", source = "entity.updatedAt")
    SubmitToeicPart2DTO convertToDTO(SubmitToeicPart2 entity);

    // Patch DTO → Entity
    @Mapping(target = "submitToeic", source = "dto.submitToeicId", qualifiedByName = "mapSubmitToeicId")
    @Mapping(target = "toeicPart2", source = "dto.toeicPart2Id", qualifiedByName = "mapToeicPart2Id")
    @Mapping(target = "answer", source = "dto.answer")
    @Mapping(target = "status", source = "dto.status")
    void patchEntityFromDTO(SubmitToeicPart2DTO dto, @MappingTarget SubmitToeicPart2 entity);


    @Named("mapSubmitToeicId")
    default SubmitToeic mapSubmitToeicId(Long id) {
        if (id == null) return null;
        SubmitToeic entity = new SubmitToeic();
        entity.setId(id);
        return entity;
    }

    @Named("mapToeicPart2Id")
    default ToeicPart2 mapToeicPart2Id(Long id) {
        if (id == null) return null;
        ToeicPart2 entity = new ToeicPart2();
        entity.setId(id);
        return entity;
    }
}
