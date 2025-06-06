package com.englishweb.h2t_backside.mapper;

import com.englishweb.h2t_backside.dto.ai.AIResponseDTO;
import com.englishweb.h2t_backside.model.features.User;
import com.englishweb.h2t_backside.model.features.AIResponse;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface AIResponseMapper {

    @Mapping(target = "id", source = "dto.id")
    @Mapping(target = "status", source = "dto.status", defaultValue = "true")
    @Mapping(target = "user", source = "dto.userId", qualifiedByName = "mapUserIdToUser")
    @Mapping(target = "request", source = "dto.request")
    @Mapping(target = "response", source = "dto.response")
    @Mapping(target = "evaluate", source = "dto.evaluate")
    AIResponse convertToEntity(AIResponseDTO dto);

    @Mapping(target = "id", source = "entity.id")
    @Mapping(target = "status", source = "entity.status")
    @Mapping(target = "createdAt", source = "entity.createdAt")
    @Mapping(target = "updatedAt", source = "entity.updatedAt")
    @Mapping(target = "userId", source = "entity.user.id")
    @Mapping(target = "request", source = "entity.request")
    @Mapping(target = "response", source = "entity.response")
    @Mapping(target = "evaluate", source = "entity.evaluate")
    AIResponseDTO convertToDTO(AIResponse entity);

    @Mapping(target = "id", source = "dto.id")
    @Mapping(target = "status", source = "dto.status")
    @Mapping(target = "request", source = "dto.request")
    @Mapping(target = "response", source = "dto.response")
    @Mapping(target = "evaluate", source = "dto.evaluate")
    @Mapping(target = "user", source = "dto.userId", qualifiedByName = "mapUserIdToUser")
    void patchEntityFromDTO(AIResponseDTO dto, @MappingTarget AIResponse entity);

    @Named("mapUserIdToUser")
    default User mapUserIdToUser(Long userId) {
        if (userId == null) return null;
        User user = new User();
        user.setId(userId);
        return user;
    }
}

