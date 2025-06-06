package com.englishweb.h2t_backside.mapper.lesson;

import com.englishweb.h2t_backside.dto.lesson.PreparationMatchWordSentencesDTO;
import com.englishweb.h2t_backside.model.lesson.PreparationMatchWordSentences;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        builder = @Builder(disableBuilder = true)
)
public interface PreparationMatchWordSentencesMapper {

    // Convert DTO -> Entity
    @Mapping(target = "id", source = "dto.id")
    @Mapping(target = "status", source = "dto.status", defaultValue = "true")
    @Mapping(target = "sentence", source = "dto.sentence")
    @Mapping(target = "word", source = "dto.word")
    PreparationMatchWordSentences convertToEntity(PreparationMatchWordSentencesDTO dto);

    // Convert Entity -> DTO
    @Mapping(target = "id", source = "entity.id")
    @Mapping(target = "status", source = "entity.status")
    @Mapping(target = "createdAt", source = "entity.createdAt")
    @Mapping(target = "updatedAt", source = "entity.updatedAt")
    @Mapping(target = "sentence", source = "entity.sentence")
    @Mapping(target = "word", source = "entity.word")
    PreparationMatchWordSentencesDTO convertToDTO(PreparationMatchWordSentences entity);

    // Cập nhật Entity từ DTO
    @Mapping(target = "id", source = "dto.id")
    @Mapping(target = "status", source = "dto.status")
    @Mapping(target = "sentence", source = "dto.sentence")
    @Mapping(target = "word", source = "dto.word")
    void patchEntityFromDTO(PreparationMatchWordSentencesDTO dto, @MappingTarget PreparationMatchWordSentences entity);
}
