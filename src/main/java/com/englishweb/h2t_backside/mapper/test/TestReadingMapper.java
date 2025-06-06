package com.englishweb.h2t_backside.mapper.test;

import com.englishweb.h2t_backside.dto.test.TestReadingDTO;
import com.englishweb.h2t_backside.model.test.TestReading;
import com.englishweb.h2t_backside.utils.ParseData;
import org.mapstruct.*;

import java.util.List;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        builder = @Builder(disableBuilder = true)
)
public interface TestReadingMapper {

    // Convert DTO → Entity
    @Mapping(target = "id", source = "dto.id")
    @Mapping(target = "file", source = "dto.file")
    @Mapping(target = "questions", source = "dto.questions", qualifiedByName = "longListToString")
    @Mapping(target = "status", source = "dto.status", defaultValue = "true")
    TestReading convertToEntity(TestReadingDTO dto);

    // Convert Entity → DTO
    @Mapping(target = "id", source = "entity.id")
    @Mapping(target = "file", source = "entity.file")
    @Mapping(target = "questions", source = "entity.questions", qualifiedByName = "stringToLongList")
    @Mapping(target = "status", source = "entity.status")
    @Mapping(target = "createdAt", source = "entity.createdAt")
    @Mapping(target = "updatedAt", source = "entity.updatedAt")
    TestReadingDTO convertToDTO(TestReading entity);

    // Patch
    @Mapping(target = "file", source = "dto.file")
    @Mapping(target = "questions", source = "dto.questions", qualifiedByName = "longListToString")
    @Mapping(target = "status", source = "dto.status")
    void patchEntityFromDTO(TestReadingDTO dto, @MappingTarget TestReading entity);

    @Named("stringToLongList")
    default List<Long> stringToLongList(String str) {
        return ParseData.parseStringToLongList(str);
    }

    @Named("longListToString")
    default String longListToString(List<Long> list) {
        return ParseData.parseLongListToString(list);
    }
}
