package com.englishweb.h2t_backside.mapper.lesson;

import com.englishweb.h2t_backside.dto.lesson.WritingAnswerDTO;
import com.englishweb.h2t_backside.model.lesson.Writing;
import com.englishweb.h2t_backside.model.lesson.WritingAnswer;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        builder = @Builder(disableBuilder = true)
)
public interface WritingAnswerMapper {

    // Convert DTO -> Entity
    @Mapping(target = "id", source = "dto.id")
    @Mapping(target = "status", source = "dto.status", defaultValue = "true")
    @Mapping(target = "missingIndex", source = "dto.missingIndex")
    @Mapping(target = "correctAnswer", source = "dto.correctAnswer")
    @Mapping(target = "writing", source = "dto.writingId", qualifiedByName = "mapWritingIdToEntity")
    WritingAnswer convertToEntity(WritingAnswerDTO dto);

    // Convert Entity -> DTO
    @Mapping(target = "id", source = "entity.id")
    @Mapping(target = "status", source = "entity.status")
    @Mapping(target = "createdAt", source = "entity.createdAt")
    @Mapping(target = "updatedAt", source = "entity.updatedAt")
    @Mapping(target = "missingIndex", source = "entity.missingIndex")
    @Mapping(target = "correctAnswer", source = "entity.correctAnswer")
    @Mapping(target = "writingId", source = "entity.writing.id")
    WritingAnswerDTO convertToDTO(WritingAnswer entity);

    // Cập nhật Entity từ DTO
    @Mapping(target = "id", source = "dto.id")
    @Mapping(target = "status", source = "dto.status")
    @Mapping(target = "missingIndex", source = "dto.missingIndex")
    @Mapping(target = "correctAnswer", source = "dto.correctAnswer")
    @Mapping(target = "writing", source = "dto.writingId", qualifiedByName = "mapWritingIdToEntity")
    void patchEntityFromDTO(WritingAnswerDTO dto, @MappingTarget WritingAnswer entity);

    // Custom mapping methods
    @Named("mapWritingIdToEntity")
    default Writing mapWritingIdToEntity(Long writingId) {
        if (writingId == null) return null;
        Writing writing = new Writing();
        writing.setId(writingId);
        return writing;
    }
}
