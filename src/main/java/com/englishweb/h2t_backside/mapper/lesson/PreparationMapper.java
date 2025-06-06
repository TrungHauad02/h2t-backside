package com.englishweb.h2t_backside.mapper.lesson;

import com.englishweb.h2t_backside.dto.lesson.PreparationDTO;
import com.englishweb.h2t_backside.model.lesson.Preparation;
import com.englishweb.h2t_backside.utils.ParseData;
import org.mapstruct.*;

import java.util.List;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        builder = @Builder(disableBuilder = true)
)
public interface PreparationMapper {

    // Convert DTO (List<Long>) → Entity (String)
    @Mapping(target = "questions", source = "dto.questions", qualifiedByName = "longListToString")
    @Mapping(target = "id", source = "dto.id")
    @Mapping(target = "status", source = "dto.status", defaultValue = "true")
    @Mapping(target = "title", source = "dto.title")
    @Mapping(target = "tip", source = "dto.tip")
    @Mapping(target = "type", source = "dto.type")
    Preparation convertToEntity(PreparationDTO dto);

    // Convert Entity (String) → DTO (List<Long>)
    @Mapping(target = "questions", source = "entity.questions", qualifiedByName = "stringToLongList")
    @Mapping(target = "id", source = "entity.id")
    @Mapping(target = "status", source = "entity.status")
    @Mapping(target = "createdAt", source = "entity.createdAt")
    @Mapping(target = "updatedAt", source = "entity.updatedAt")
    @Mapping(target = "title", source = "entity.title")
    @Mapping(target = "tip", source = "entity.tip")
    @Mapping(target = "type", source = "entity.type")
    PreparationDTO convertToDTO(Preparation entity);

    // Patch Entity từ DTO
    @Mapping(target = "questions", source = "dto.questions", qualifiedByName = "longListToString")
    @Mapping(target = "id", source = "dto.id")
    @Mapping(target = "status", source = "dto.status")
    @Mapping(target = "title", source = "dto.title")
    @Mapping(target = "tip", source = "dto.tip")
    @Mapping(target = "type", source = "dto.type")
    void patchEntityFromDTO(PreparationDTO dto, @MappingTarget Preparation entity);

    // Custom mapping methods
    @Named("stringToLongList")
    default List<Long> stringToLongList(String str) {
        return ParseData.parseStringToLongList(str);
    }

    @Named("longListToString")
    default String longListToString(List<Long> list) {
        return ParseData.parseLongListToString(list);
    }
}