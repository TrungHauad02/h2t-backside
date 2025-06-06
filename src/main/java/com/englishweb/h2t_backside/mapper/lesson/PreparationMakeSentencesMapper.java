package com.englishweb.h2t_backside.mapper.lesson;

import com.englishweb.h2t_backside.dto.lesson.PreparationMakeSentencesDTO;
import com.englishweb.h2t_backside.model.lesson.PreparationMakeSentences;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        builder = @Builder(disableBuilder = true)
)
public interface PreparationMakeSentencesMapper {

    // Convert DTO -> Entity
    @Mapping(target = "id", source = "dto.id")
    @Mapping(target = "status", source = "dto.status", defaultValue = "true")
    @Mapping(target = "sentences", source = "dto.sentences")
    PreparationMakeSentences convertToEntity(PreparationMakeSentencesDTO dto);

    // Convert Entity -> DTO
    @Mapping(target = "id", source = "entity.id")
    @Mapping(target = "status", source = "entity.status")
    @Mapping(target = "createdAt", source = "entity.createdAt")
    @Mapping(target = "updatedAt", source = "entity.updatedAt")
    @Mapping(target = "sentences", source = "entity.sentences")
    PreparationMakeSentencesDTO convertToDTO(PreparationMakeSentences entity);

    // Cập nhật Entity từ DTO
    @Mapping(target = "id", source = "dto.id")
    @Mapping(target = "status", source = "dto.status")
    @Mapping(target = "sentences", source = "dto.sentences")
    void patchEntityFromDTO(PreparationMakeSentencesDTO dto, @MappingTarget PreparationMakeSentences entity);
}
