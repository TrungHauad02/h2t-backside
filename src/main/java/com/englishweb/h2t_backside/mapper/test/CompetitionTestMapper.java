package com.englishweb.h2t_backside.mapper.test;

import com.englishweb.h2t_backside.dto.test.CompetitionTestDTO;
import com.englishweb.h2t_backside.model.features.User;
import com.englishweb.h2t_backside.model.test.CompetitionTest;
import com.englishweb.h2t_backside.utils.ParseData;
import org.mapstruct.*;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.List;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        builder = @Builder(disableBuilder = true)
)
public interface CompetitionTestMapper {

    ZoneId ZONE_ID = ZoneId.of("Asia/Ho_Chi_Minh");

    // DTO → Entity
    @Mapping(target = "id", source = "dto.id")
    @Mapping(target = "title", source = "dto.title")
    @Mapping(target = "duration", source = "dto.duration")
    @Mapping(target = "startTime", source = "dto.startTime", qualifiedByName = "offsetToLocal")
    @Mapping(target = "endTime", source = "dto.endTime", qualifiedByName = "offsetToLocal")
    @Mapping(target = "parts", source = "dto.parts", qualifiedByName = "longListToString")
    @Mapping(target = "status", source = "dto.status", defaultValue = "true")
    @Mapping(target = "owner", source = "dto.ownerId", qualifiedByName = "mapUserIdToUser")

    CompetitionTest convertToEntity(CompetitionTestDTO dto);

    // Entity → DTO
    @Mapping(target = "id", source = "entity.id")
    @Mapping(target = "title", source = "entity.title")
    @Mapping(target = "duration", source = "entity.duration")
    @Mapping(target = "startTime", source = "entity.startTime", qualifiedByName = "localToOffset")
    @Mapping(target = "endTime", source = "entity.endTime", qualifiedByName = "localToOffset")
    @Mapping(target = "parts", source = "entity.parts", qualifiedByName = "stringToLongList")
    @Mapping(target = "status", source = "entity.status")
    @Mapping(target = "createdAt", source = "entity.createdAt")
    @Mapping(target = "updatedAt", source = "entity.updatedAt")
    @Mapping(target = "ownerId", source = "entity.owner.id")

    CompetitionTestDTO convertToDTO(CompetitionTest entity);

    // Patch DTO → Entity
    @Mapping(target = "title", source = "dto.title")
    @Mapping(target = "duration", source = "dto.duration")
    @Mapping(target = "startTime", source = "dto.startTime", qualifiedByName = "offsetToLocal")
    @Mapping(target = "endTime", source = "dto.endTime", qualifiedByName = "offsetToLocal")
    @Mapping(target = "parts", source = "dto.parts", qualifiedByName = "longListToString")
    @Mapping(target = "status", source = "dto.status")
    @Mapping(target = "owner", source = "dto.ownerId", qualifiedByName = "mapUserIdToUser")

    void patchEntityFromDTO(CompetitionTestDTO dto, @MappingTarget CompetitionTest entity);

    // Custom mapping methods
    @Named("mapUserIdToUser")
    default User mapUserIdToUser(Long userId) {
        if (userId == null) return null;
        User user = new User();
        user.setId(userId);
        return user;
    }

    // Hàm convert thủ công
    @Named("stringToLongList")
    default List<Long> stringToLongList(String str) {
        return ParseData.parseStringToLongList(str);
    }

    @Named("longListToString")
    default String longListToString(List<Long> list) {
        return ParseData.parseLongListToString(list);
    }

    @Named("offsetToLocal")
    default LocalDateTime offsetToLocal(OffsetDateTime offsetDateTime) {
        if (offsetDateTime == null) return null;
        return offsetDateTime.atZoneSameInstant(ZONE_ID).toLocalDateTime();
    }

    @Named("localToOffset")
    default OffsetDateTime localToOffset(LocalDateTime localDateTime) {
        if (localDateTime == null) return null;
        return localDateTime.atZone(ZONE_ID).toOffsetDateTime();
    }
}
