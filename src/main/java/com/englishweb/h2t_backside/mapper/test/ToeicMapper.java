package com.englishweb.h2t_backside.mapper.test;

import com.englishweb.h2t_backside.dto.test.ToeicDTO;
import com.englishweb.h2t_backside.model.features.User;
import com.englishweb.h2t_backside.model.test.Toeic;
import com.englishweb.h2t_backside.utils.ParseData;
import org.mapstruct.*;

import java.util.List;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        builder = @Builder(disableBuilder = true)
)
public interface ToeicMapper {

    // DTO → Entity
    @Mapping(target = "id", source = "dto.id")
    @Mapping(target = "title", source = "dto.title")
    @Mapping(target = "duration", source = "dto.duration")
    @Mapping(target = "questionsPart1", source = "dto.questionsPart1", qualifiedByName = "longListToString")
    @Mapping(target = "questionsPart2", source = "dto.questionsPart2", qualifiedByName = "longListToString")
    @Mapping(target = "questionsPart3", source = "dto.questionsPart3", qualifiedByName = "longListToString")
    @Mapping(target = "questionsPart4", source = "dto.questionsPart4", qualifiedByName = "longListToString")
    @Mapping(target = "questionsPart5", source = "dto.questionsPart5", qualifiedByName = "longListToString")
    @Mapping(target = "questionsPart6", source = "dto.questionsPart6", qualifiedByName = "longListToString")
    @Mapping(target = "questionsPart7", source = "dto.questionsPart7", qualifiedByName = "longListToString")
    @Mapping(target = "status", source = "dto.status", defaultValue = "true")
    @Mapping(target = "owner", source = "dto.ownerId", qualifiedByName = "mapUserIdToUser")

    Toeic convertToEntity(ToeicDTO dto);

    // Entity → DTO
    @Mapping(target = "id", source = "entity.id")
    @Mapping(target = "title", source = "entity.title")
    @Mapping(target = "duration", source = "entity.duration")
    @Mapping(target = "questionsPart1", source = "entity.questionsPart1", qualifiedByName = "stringToLongList")
    @Mapping(target = "questionsPart2", source = "entity.questionsPart2", qualifiedByName = "stringToLongList")
    @Mapping(target = "questionsPart3", source = "entity.questionsPart3", qualifiedByName = "stringToLongList")
    @Mapping(target = "questionsPart4", source = "entity.questionsPart4", qualifiedByName = "stringToLongList")
    @Mapping(target = "questionsPart5", source = "entity.questionsPart5", qualifiedByName = "stringToLongList")
    @Mapping(target = "questionsPart6", source = "entity.questionsPart6", qualifiedByName = "stringToLongList")
    @Mapping(target = "questionsPart7", source = "entity.questionsPart7", qualifiedByName = "stringToLongList")
    @Mapping(target = "status", source = "entity.status")
    @Mapping(target = "createdAt", source = "entity.createdAt")
    @Mapping(target = "updatedAt", source = "entity.updatedAt")
    @Mapping(target = "ownerId", source = "entity.owner.id")

    ToeicDTO convertToDTO(Toeic entity);

    // Patch
    @Mapping(target = "title", source = "dto.title")
    @Mapping(target = "duration", source = "dto.duration")
    @Mapping(target = "questionsPart1", source = "dto.questionsPart1", qualifiedByName = "longListToString")
    @Mapping(target = "questionsPart2", source = "dto.questionsPart2", qualifiedByName = "longListToString")
    @Mapping(target = "questionsPart3", source = "dto.questionsPart3", qualifiedByName = "longListToString")
    @Mapping(target = "questionsPart4", source = "dto.questionsPart4", qualifiedByName = "longListToString")
    @Mapping(target = "questionsPart5", source = "dto.questionsPart5", qualifiedByName = "longListToString")
    @Mapping(target = "questionsPart6", source = "dto.questionsPart6", qualifiedByName = "longListToString")
    @Mapping(target = "questionsPart7", source = "dto.questionsPart7", qualifiedByName = "longListToString")
    @Mapping(target = "status", source = "dto.status")
    @Mapping(target = "owner", source = "dto.ownerId", qualifiedByName = "mapUserIdToUser")

    void patchEntityFromDTO(ToeicDTO dto, @MappingTarget Toeic entity);


    // Custom mapping methods
    @Named("mapUserIdToUser")
    default User mapUserIdToUser(Long userId) {
        if (userId == null) return null;
        User user = new User();
        user.setId(userId);
        return user;
    }
    // Custom converters
    @Named("stringToLongList")
    default List<Long> stringToLongList(String str) {
        return ParseData.parseStringToLongList(str);
    }

    @Named("longListToString")
    default String longListToString(List<Long> list) {
        return ParseData.parseLongListToString(list);
    }
}
