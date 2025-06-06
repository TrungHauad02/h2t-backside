package com.englishweb.h2t_backside.mapper.test;

import com.englishweb.h2t_backside.dto.test.TestDTO;
import com.englishweb.h2t_backside.model.features.RouteNode;
import com.englishweb.h2t_backside.model.test.Test;
import com.englishweb.h2t_backside.utils.ParseData;
import org.mapstruct.*;

import java.util.List;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        builder = @Builder(disableBuilder = true)
)
public interface TestMapper {

    // DTO → Entity
    @Mapping(target = "id", source = "dto.id")
    @Mapping(target = "title", source = "dto.title")
    @Mapping(target = "description", source = "dto.description")
    @Mapping(target = "duration", source = "dto.duration")
    @Mapping(target = "type", source = "dto.type")
    @Mapping(target = "parts", source = "dto.parts", qualifiedByName = "longListToString")
    @Mapping(target = "status", source = "dto.status", defaultValue = "true")
    @Mapping(target = "routeNode", source = "dto.routeNodeId", qualifiedByName = "mapRouteNodeFromId")
    Test convertToEntity(TestDTO dto);

    // Entity → DTO
    @Mapping(target = "id", source = "entity.id")
    @Mapping(target = "title", source = "entity.title")
    @Mapping(target = "description", source = "entity.description")
    @Mapping(target = "duration", source = "entity.duration")
    @Mapping(target = "type", source = "entity.type")
    @Mapping(target = "parts", source = "entity.parts", qualifiedByName = "stringToLongList")
    @Mapping(target = "status", source = "entity.status")
    @Mapping(target = "createdAt", source = "entity.createdAt")
    @Mapping(target = "updatedAt", source = "entity.updatedAt")
    @Mapping(target = "routeNodeId", source = "entity.routeNode.id")
    TestDTO convertToDTO(Test entity);

    // Patch DTO → Entity
    @Mapping(target = "title", source = "dto.title")
    @Mapping(target = "description", source = "dto.description")
    @Mapping(target = "duration", source = "dto.duration")
    @Mapping(target = "type", source = "dto.type")
    @Mapping(target = "parts", source = "dto.parts", qualifiedByName = "longListToString")
    @Mapping(target = "status", source = "dto.status")
    @Mapping(target = "routeNode", source = "dto.routeNodeId", qualifiedByName = "mapRouteNodeFromId")
    void patchEntityFromDTO(TestDTO dto, @MappingTarget Test entity);

    // Custom converters
    @Named("stringToLongList")
    default List<Long> stringToLongList(String str) {
        return ParseData.parseStringToLongList(str);
    }

    @Named("longListToString")
    default String longListToString(List<Long> list) {
        return ParseData.parseLongListToString(list);
    }

    @Named("mapRouteNodeFromId")
    default RouteNode mapRouteNodeFromId(Long routeNodeId) {
        if (routeNodeId == null) return null;
        RouteNode node = new RouteNode();
        node.setId(routeNodeId);
        return node;
    }
}
