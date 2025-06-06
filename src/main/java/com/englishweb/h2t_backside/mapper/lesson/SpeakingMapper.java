package com.englishweb.h2t_backside.mapper.lesson;

import com.englishweb.h2t_backside.dto.lesson.SpeakingDTO;
import com.englishweb.h2t_backside.mapper.RouteNodeMapper;
import com.englishweb.h2t_backside.model.lesson.Preparation;
import com.englishweb.h2t_backside.model.lesson.Speaking;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {PreparationMapper.class, RouteNodeMapper.class},
        builder = @Builder(disableBuilder = true)
)
public interface SpeakingMapper {

    // Convert DTO -> Entity
    @Mapping(target = "id", source = "dto.id")
    @Mapping(target = "status", source = "dto.status", defaultValue = "true")
    @Mapping(target = "title", source = "dto.title")
    @Mapping(target = "image", source = "dto.image")
    @Mapping(target = "description", source = "dto.description")
    @Mapping(target = "views", source = "dto.views", defaultValue = "0L")
    @Mapping(target = "topic", source = "dto.topic")
    @Mapping(target = "duration", source = "dto.duration")
    @Mapping(target = "preparation", source = "dto.preparationId", qualifiedByName = "mapPreparationIdToPreparation")
    @Mapping(target = "routeNode", ignore = true)
    Speaking convertToEntity(SpeakingDTO dto);

    // Convert Entity -> DTO
    @Mapping(target = "id", source = "entity.id")
    @Mapping(target = "status", source = "entity.status")
    @Mapping(target = "createdAt", source = "entity.createdAt")
    @Mapping(target = "updatedAt", source = "entity.updatedAt")
    @Mapping(target = "title", source = "entity.title")
    @Mapping(target = "image", source = "entity.image")
    @Mapping(target = "description", source = "entity.description")
    @Mapping(target = "views", source = "entity.views")
    @Mapping(target = "topic", source = "entity.topic")
    @Mapping(target = "duration", source = "entity.duration")
    @Mapping(target = "preparationId", source = "entity.preparation.id")
    @Mapping(target = "routeNode", source = "entity.routeNode")
    SpeakingDTO convertToDTO(Speaking entity);

    // Cập nhật Entity từ DTO
    @Mapping(target = "id", source = "dto.id")
    @Mapping(target = "status", source = "dto.status")
    @Mapping(target = "title", source = "dto.title")
    @Mapping(target = "image", source = "dto.image")
    @Mapping(target = "description", source = "dto.description")
    @Mapping(target = "views", source = "dto.views")
    @Mapping(target = "topic", source = "dto.topic")
    @Mapping(target = "duration", source = "dto.duration")
    @Mapping(target = "preparation", source = "dto.preparationId", qualifiedByName = "mapPreparationIdToPreparation")
    @Mapping(target = "routeNode", ignore = true)
    void patchEntityFromDTO(SpeakingDTO dto, @MappingTarget Speaking entity);

    @Named("mapPreparationIdToPreparation")
    default Preparation stringToLongList(Long preparationId) {
        if (preparationId == null) return null;
        Preparation preparation = new Preparation();
        preparation.setId(preparationId);
        return preparation;
    }
}