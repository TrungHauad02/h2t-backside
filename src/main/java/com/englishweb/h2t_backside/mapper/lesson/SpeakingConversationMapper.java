package com.englishweb.h2t_backside.mapper.lesson;

import com.englishweb.h2t_backside.dto.lesson.SpeakingConversationDTO;
import com.englishweb.h2t_backside.model.lesson.Speaking;
import com.englishweb.h2t_backside.model.lesson.SpeakingConversation;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        builder = @Builder(disableBuilder = true)
)
public interface SpeakingConversationMapper {

    // Convert DTO -> Entity
    @Mapping(target = "id", source = "dto.id")
    @Mapping(target = "status", source = "dto.status", defaultValue = "true")
    @Mapping(target = "name", source = "dto.name")
    @Mapping(target = "serial", source = "dto.serial")
    @Mapping(target = "content", source = "dto.content")
    @Mapping(target = "audioUrl", source = "dto.audioUrl")
    @Mapping(target = "speaking", source = "dto.speakingId", qualifiedByName = "mapSpeakingIdToEntity")
    SpeakingConversation convertToEntity(SpeakingConversationDTO dto);

    // Convert Entity -> DTO
    @Mapping(target = "id", source = "entity.id")
    @Mapping(target = "status", source = "entity.status")
    @Mapping(target = "createdAt", source = "entity.createdAt")
    @Mapping(target = "updatedAt", source = "entity.updatedAt")
    @Mapping(target = "name", source = "entity.name")
    @Mapping(target = "serial", source = "entity.serial")
    @Mapping(target = "content", source = "entity.content")
    @Mapping(target = "audioUrl", source = "entity.audioUrl")
    @Mapping(target = "speakingId", source = "entity.speaking.id")
    SpeakingConversationDTO convertToDTO(SpeakingConversation entity);

    // Cập nhật Entity từ DTO
    @Mapping(target = "id", source = "dto.id")
    @Mapping(target = "status", source = "dto.status")
    @Mapping(target = "name", source = "dto.name")
    @Mapping(target = "serial", source = "dto.serial")
    @Mapping(target = "content", source = "dto.content")
    @Mapping(target = "audioUrl", source = "dto.audioUrl")
    @Mapping(target = "speaking", source = "dto.speakingId", qualifiedByName = "mapSpeakingIdToEntity")
    void patchEntityFromDTO(SpeakingConversationDTO dto, @MappingTarget SpeakingConversation entity);

    @Named("mapSpeakingIdToEntity")
    default Speaking mapSpeakingIdToEntity(Long speakingId) {
        if (speakingId == null) return null;
        Speaking speaking = new Speaking();
        speaking.setId(speakingId);
        return speaking;
    }
}