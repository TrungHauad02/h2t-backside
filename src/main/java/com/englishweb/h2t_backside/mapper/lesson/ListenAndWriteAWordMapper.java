package com.englishweb.h2t_backside.mapper.lesson;

import com.englishweb.h2t_backside.dto.lesson.ListenAndWriteAWordDTO;
import com.englishweb.h2t_backside.model.lesson.ListenAndWriteAWord;
import com.englishweb.h2t_backside.model.lesson.Listening;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        builder = @Builder(disableBuilder = true)
)
public interface ListenAndWriteAWordMapper {

    @Mapping(target = "id", source = "dto.id")
    @Mapping(target = "status", source = "dto.status", defaultValue = "true")
    @Mapping(target = "serial", source = "dto.serial")
    @Mapping(target = "audio", source = "dto.audio")
    @Mapping(target = "sentence", source = "dto.sentence")
    @Mapping(target = "missingIndex", source = "dto.missingIndex")
    @Mapping(target = "correctAnswer", source = "dto.correctAnswer")
    @Mapping(target = "listening", source = "dto.listeningId", qualifiedByName = "mapListeningIdToEntity")
    ListenAndWriteAWord convertToEntity(ListenAndWriteAWordDTO dto);

    @Mapping(target = "id", source = "entity.id")
    @Mapping(target = "status", source = "entity.status")
    @Mapping(target = "createdAt", source = "entity.createdAt")
    @Mapping(target = "updatedAt", source = "entity.updatedAt")
    @Mapping(target = "serial", source = "entity.serial")
    @Mapping(target = "audio", source = "entity.audio")
    @Mapping(target = "sentence", source = "entity.sentence")
    @Mapping(target = "missingIndex", source = "entity.missingIndex")
    @Mapping(target = "correctAnswer", source = "entity.correctAnswer")
    @Mapping(target = "listeningId", source = "entity.listening.id")
    ListenAndWriteAWordDTO convertToDTO(ListenAndWriteAWord entity);

    @Mapping(target = "id", source = "dto.id")
    @Mapping(target = "status", source = "dto.status")
    @Mapping(target = "serial", source = "dto.serial")
    @Mapping(target = "audio", source = "dto.audio")
    @Mapping(target = "sentence", source = "dto.sentence")
    @Mapping(target = "missingIndex", source = "dto.missingIndex")
    @Mapping(target = "correctAnswer", source = "dto.correctAnswer")
    void patchEntityFromDTO(ListenAndWriteAWordDTO dto, @MappingTarget ListenAndWriteAWord entity);

    // Phương thức tạo Entity Listening từ ID (Long)
    @Named("mapListeningIdToEntity")
    default Listening mapListeningIdToEntity(Long listeningId) {
        if (listeningId == null) return null;
        Listening listening = new Listening();
        listening.setId(listeningId);
        return listening;
    }
}
