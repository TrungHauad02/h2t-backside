package com.englishweb.h2t_backside.mapper.lesson;

import com.englishweb.h2t_backside.dto.lesson.GrammarDTO;
import com.englishweb.h2t_backside.mapper.RouteNodeMapper;
import com.englishweb.h2t_backside.model.lesson.Grammar;
import com.englishweb.h2t_backside.utils.ParseData;
import org.mapstruct.*;

import java.util.List;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = RouteNodeMapper.class,
        builder = @Builder(disableBuilder = true)
)
public interface GrammarMapper {

    // Convert DTO (List<Long>) → Entity (String)
    @Mapping(target = "status", source = "dto.status", defaultValue = "true")
    @Mapping(target = "views", source = "dto.views", defaultValue = "0L")
    @Mapping(target = "id", source = "dto.id")
    @Mapping(target = "title", source = "dto.title")
    @Mapping(target = "image", source = "dto.image")
    @Mapping(target = "description", source = "dto.description")
    @Mapping(target = "routeNode", ignore = true)
    @Mapping(target = "questions", source = "dto.questions", qualifiedByName = "longListToString")
    @Mapping(target = "definition", source = "dto.definition")
    @Mapping(target = "tips", source = "dto.tips")
    @Mapping(target = "example", source = "dto.example")
    @Mapping(target = "file", source = "dto.file")
    Grammar convertToEntity(GrammarDTO dto);

    // Convert Entity (String) → DTO (List<Long>)
    @Mapping(target = "id", source = "entity.id")
    @Mapping(target = "status", source = "entity.status")
    @Mapping(target = "createdAt", source = "entity.createdAt")
    @Mapping(target = "updatedAt", source = "entity.updatedAt")
    @Mapping(target = "title", source = "entity.title")
    @Mapping(target = "image", source = "entity.image")
    @Mapping(target = "description", source = "entity.description")
    @Mapping(target = "views", source = "entity.views")
    @Mapping(target = "routeNode", source = "entity.routeNode")
    @Mapping(target = "questions", source = "entity.questions", qualifiedByName = "stringToLongList")
    @Mapping(target = "definition", source = "entity.definition")
    @Mapping(target = "tips", source = "entity.tips")
    @Mapping(target = "example", source = "entity.example")
    @Mapping(target = "file", source = "entity.file")
    GrammarDTO convertToDTO(Grammar entity);

    @Mapping(target = "status", source = "dto.status")
    @Mapping(target = "title", source = "dto.title")
    @Mapping(target = "image", source = "dto.image")
    @Mapping(target = "description", source = "dto.description")
    @Mapping(target = "views", source = "dto.views")
    @Mapping(target = "routeNode", ignore = true)
    @Mapping(target = "questions", source = "dto.questions", qualifiedByName = "longListToString")
    @Mapping(target = "definition", source = "dto.definition")
    @Mapping(target = "tips", source = "dto.tips")
    @Mapping(target = "example", source = "dto.example")
    @Mapping(target = "file", source = "dto.file")
    void patchEntityFromDTO(GrammarDTO dto, @MappingTarget Grammar entity);

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