package com.englishweb.h2t_backside.mapper.test;

import com.englishweb.h2t_backside.dto.test.TestPartDTO;
import com.englishweb.h2t_backside.model.test.TestPart;
import com.englishweb.h2t_backside.utils.ParseData;
import org.mapstruct.*;

import java.util.List;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        builder = @Builder(disableBuilder = true)
)
public interface TestPartMapper {

    @Mapping(target = "id", source = "dto.id")
    @Mapping(target = "questions", source = "dto.questions", qualifiedByName = "longListToString")
    @Mapping(target = "type", source = "dto.type")
    @Mapping(target = "status", source = "dto.status", defaultValue = "true")
    TestPart convertToEntity(TestPartDTO dto);

    @Mapping(target = "id", source = "entity.id")
    @Mapping(target = "questions", source = "entity.questions", qualifiedByName = "stringToLongList")
    @Mapping(target = "type", source = "entity.type")
    @Mapping(target = "status", source = "entity.status")
    @Mapping(target = "createdAt", source = "entity.createdAt")
    @Mapping(target = "updatedAt", source = "entity.updatedAt")
    TestPartDTO convertToDTO(TestPart entity);

    @Mapping(target = "questions", source = "dto.questions", qualifiedByName = "longListToString")
    @Mapping(target = "type", source = "dto.type")
    @Mapping(target = "status", source = "dto.status")
    void patchEntityFromDTO(TestPartDTO dto, @MappingTarget TestPart entity);

    @Named("stringToLongList")
    default List<Long> stringToLongList(String str) {
        return ParseData.parseStringToLongList(str);
    }

    @Named("longListToString")
    default String longListToString(List<Long> list) {
        return ParseData.parseLongListToString(list);
    }
}
