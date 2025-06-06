package com.englishweb.h2t_backside.mapper;

import com.englishweb.h2t_backside.dto.feature.RouteDTO;
import com.englishweb.h2t_backside.model.features.Route;
import com.englishweb.h2t_backside.model.features.User;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {RouteNodeMapper.class},
        builder = @Builder(disableBuilder = true)
)
public interface RouteMapper {

    // Convert DTO -> Entity
    @Mapping(target = "id", source = "dto.id")
    @Mapping(target = "status", source = "dto.status", defaultValue = "true")
    @Mapping(target = "title", source = "dto.title")
    @Mapping(target = "image", source = "dto.image")
    @Mapping(target = "description", source = "dto.description")
    @Mapping(target = "routeNodes", source = "dto.routeNodes")
    @Mapping(target = "owner", source = "dto.ownerId", qualifiedByName = "mapUserIdToUser")
    Route convertToEntity(RouteDTO dto);

    // Convert Entity -> DTO
    @Mapping(target = "id", source = "entity.id")
    @Mapping(target = "status", source = "entity.status")
    @Mapping(target = "createdAt", source = "entity.createdAt")
    @Mapping(target = "updatedAt", source = "entity.updatedAt")
    @Mapping(target = "title", source = "entity.title")
    @Mapping(target = "image", source = "entity.image")
    @Mapping(target = "description", source = "entity.description")
    @Mapping(target = "routeNodes", source = "entity.routeNodes")
    @Mapping(target = "ownerId", source = "entity.owner.id")
    RouteDTO convertToDTO(Route entity);

    // Cập nhật Entity từ DTO
    @Mapping(target = "id", source = "dto.id")
    @Mapping(target = "status", source = "dto.status")
    @Mapping(target = "title", source = "dto.title")
    @Mapping(target = "image", source = "dto.image")
    @Mapping(target = "description", source = "dto.description")
    @Mapping(target = "routeNodes", source = "dto.routeNodes")
    @Mapping(target = "owner", source = "dto.ownerId", qualifiedByName = "mapUserIdToUser")
    void patchEntityFromDTO(RouteDTO dto, @MappingTarget Route entity);

    // Custom mapping methods
    @Named("mapUserIdToUser")
    default User mapUserIdToUser(Long userId) {
        if (userId == null) return null;
        User user = new User();
        user.setId(userId);
        return user;
    }

    // Xử lý thiết lập quan hệ hai chiều cho routeNodes
    @AfterMapping
    default void afterConvertToEntity(RouteDTO dto, @MappingTarget Route entity) {
        if (entity.getRouteNodes() != null) {
            entity.getRouteNodes().forEach(routeNode -> routeNode.setRoute(entity));
        }
    }
}