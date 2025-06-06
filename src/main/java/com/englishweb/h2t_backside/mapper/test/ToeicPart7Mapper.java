package com.englishweb.h2t_backside.mapper.test;

import com.englishweb.h2t_backside.dto.test.ToeicPart7DTO;
import com.englishweb.h2t_backside.model.test.ToeicPart7;
import com.englishweb.h2t_backside.utils.ParseData;
import org.mapstruct.*;

import java.util.List;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        builder = @Builder(disableBuilder = true)
)
public interface ToeicPart7Mapper {

    // Chuyển đổi từ DTO sang Entity
    @Mapping(target = "id", source = "dto.id")
    @Mapping(target = "file", source = "dto.file")
    @Mapping(target = "questions", source = "dto.questions", qualifiedByName = "longListToString")
    @Mapping(target = "status", source = "dto.status", defaultValue = "true")
    ToeicPart7 convertToEntity(ToeicPart7DTO dto);

    // Chuyển đổi từ Entity sang DTO
    @Mapping(target = "id", source = "entity.id")
    @Mapping(target = "file", source = "entity.file")
    @Mapping(target = "questions", source = "entity.questions", qualifiedByName = "stringToLongList")
    @Mapping(target = "status", source = "entity.status")
    @Mapping(target = "createdAt", source = "entity.createdAt")
    @Mapping(target = "updatedAt", source = "entity.updatedAt")
    ToeicPart7DTO convertToDTO(ToeicPart7 entity);

    // Cập nhật dữ liệu (patch) từ DTO sang Entity
    @Mapping(target = "file", source = "dto.file")
    @Mapping(target = "questions", source = "dto.questions", qualifiedByName = "longListToString")
    @Mapping(target = "status", source = "dto.status")
    void patchEntityFromDTO(ToeicPart7DTO dto, @MappingTarget ToeicPart7 entity);

  
    @Named("stringToLongList")
    default List<Long> stringToLongList(String str) {
        return ParseData.parseStringToLongList(str);
    }

    @Named("longListToString")
    default String longListToString(List<Long> list) {
        return ParseData.parseLongListToString(list);
    }
}
