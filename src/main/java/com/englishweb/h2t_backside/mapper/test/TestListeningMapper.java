package com.englishweb.h2t_backside.mapper.test;

import com.englishweb.h2t_backside.dto.test.TestListeningDTO;
import com.englishweb.h2t_backside.model.test.TestListening;
import com.englishweb.h2t_backside.utils.ParseData;
import org.mapstruct.*;

import java.util.List;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        builder = @Builder(disableBuilder = true)
)
public interface TestListeningMapper {

    // Convert DTO → Entity
    @Mapping(target = "id", source = "dto.id")
    @Mapping(target = "audio", source = "dto.audio")
    @Mapping(target = "transcript", source = "dto.transcript")
    @Mapping(target = "questions", source = "dto.questions", qualifiedByName = "longListToString")
    @Mapping(target = "status", source = "dto.status", defaultValue = "true")
    TestListening convertToEntity(TestListeningDTO dto);

    // Convert Entity → DTO
    @Mapping(target = "id", source = "entity.id")
    @Mapping(target = "audio", source = "entity.audio")
    @Mapping(target = "transcript", source = "entity.transcript")
    @Mapping(target = "questions", source = "entity.questions", qualifiedByName = "stringToLongList")
    @Mapping(target = "status", source = "entity.status")
    @Mapping(target = "createdAt", source = "entity.createdAt")
    @Mapping(target = "updatedAt", source = "entity.updatedAt")
    TestListeningDTO convertToDTO(TestListening entity);

    // Patch entity
    @Mapping(target = "audio", source = "dto.audio")
    @Mapping(target = "transcript", source = "dto.transcript")
    @Mapping(target = "questions", source = "dto.questions", qualifiedByName = "longListToString")
    @Mapping(target = "status", source = "dto.status")
    void patchEntityFromDTO(TestListeningDTO dto, @MappingTarget TestListening entity);

    // Mapping helpers
    @Named("stringToLongList")
    default List<Long> stringToLongList(String str) {
        return ParseData.parseStringToLongList(str);
    }

    @Named("longListToString")
    default String longListToString(List<Long> list) {
        return ParseData.parseLongListToString(list);
    }
}
