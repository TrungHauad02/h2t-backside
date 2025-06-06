package com.englishweb.h2t_backside.mapper.test;

import com.englishweb.h2t_backside.dto.test.ToeicPart6DTO;
import com.englishweb.h2t_backside.model.test.ToeicPart6;
import com.englishweb.h2t_backside.utils.ParseData;
import org.mapstruct.*;

import java.util.List;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        builder = @Builder(disableBuilder = true)
)
public interface ToeicPart6Mapper {

    @Mapping(target = "questions", source = "dto.questions", qualifiedByName = "longListToString")
    ToeicPart6 convertToEntity(ToeicPart6DTO dto);

    @Mapping(target = "questions", source = "entity.questions", qualifiedByName = "stringToLongList")
    ToeicPart6DTO convertToDTO(ToeicPart6 entity);

    @Mapping(target = "questions", source = "dto.questions", qualifiedByName = "longListToString")
    void patchEntityFromDTO(ToeicPart6DTO dto, @MappingTarget ToeicPart6 entity);

    @Named("stringToLongList")
    default List<Long> stringToLongList(String str) {
        return ParseData.parseStringToLongList(str);
    }

    @Named("longListToString")
    default String longListToString(List<Long> list) {
        return ParseData.parseLongListToString(list);
    }
}
