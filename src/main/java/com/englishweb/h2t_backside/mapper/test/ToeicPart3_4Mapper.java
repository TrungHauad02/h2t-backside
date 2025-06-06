package com.englishweb.h2t_backside.mapper.test;

import com.englishweb.h2t_backside.dto.test.ToeicPart3_4DTO;
import com.englishweb.h2t_backside.model.test.ToeicPart3_4;
import com.englishweb.h2t_backside.utils.ParseData;
import org.mapstruct.*;

import java.util.List;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        builder = @Builder(disableBuilder = true)
)
public interface ToeicPart3_4Mapper {

    @Mapping(target = "questions", source = "dto.questions", qualifiedByName = "longListToString")
    ToeicPart3_4 convertToEntity(ToeicPart3_4DTO dto);

    @Mapping(target = "questions", source = "entity.questions", qualifiedByName = "stringToLongList")
    ToeicPart3_4DTO convertToDTO(ToeicPart3_4 entity);

    @Mapping(target = "questions", source = "dto.questions", qualifiedByName = "longListToString")
    void patchEntityFromDTO(ToeicPart3_4DTO dto, @MappingTarget ToeicPart3_4 entity);

    @Named("stringToLongList")
    default List<Long> stringToLongList(String str) {
        return ParseData.parseStringToLongList(str);
    }

    @Named("longListToString")
    default String longListToString(List<Long> list) {
        return ParseData.parseLongListToString(list);
    }
}
