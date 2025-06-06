package com.englishweb.h2t_backside.mapper.lesson;

import com.englishweb.h2t_backside.dto.lesson.WritingDTO;
import com.englishweb.h2t_backside.mapper.RouteNodeMapper;
import com.englishweb.h2t_backside.model.lesson.Preparation;
import com.englishweb.h2t_backside.model.lesson.Writing;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {PreparationMapper.class, RouteNodeMapper.class},
        builder = @Builder(disableBuilder = true)
)
public interface WritingMapper {

    // Convert DTO -> Entity
    @Mapping(target = "id", source = "dto.id")
    @Mapping(target = "status", source = "dto.status", defaultValue = "true")
    @Mapping(target = "title", source = "dto.title")
    @Mapping(target = "image", source = "dto.image")
    @Mapping(target = "description", source = "dto.description")
    @Mapping(target = "views", source = "dto.views", defaultValue = "0L")
    @Mapping(target = "topic", source = "dto.topic")
    @Mapping(target = "file", source = "dto.file")
    @Mapping(target = "paragraph", source = "dto.paragraph")
    @Mapping(target = "tips", source = "dto.tips")
    @Mapping(target = "preparation", source = "dto.preparationId", qualifiedByName = "mapPreparationIdToPreparation")
    @Mapping(target = "routeNode", ignore = true)
    Writing convertToEntity(WritingDTO dto);

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
    @Mapping(target = "file", source = "entity.file")
    @Mapping(target = "paragraph", source = "entity.paragraph")
    @Mapping(target = "tips", source = "entity.tips")
    @Mapping(target = "preparationId", source = "entity.preparation.id")
    @Mapping(target = "routeNode", source = "entity.routeNode")
    WritingDTO convertToDTO(Writing entity);

    // Cập nhật Entity từ DTO
    @Mapping(target = "id", source = "dto.id")
    @Mapping(target = "status", source = "dto.status")
    @Mapping(target = "title", source = "dto.title")
    @Mapping(target = "image", source = "dto.image")
    @Mapping(target = "description", source = "dto.description")
    @Mapping(target = "views", source = "dto.views")
    @Mapping(target = "topic", source = "dto.topic")
    @Mapping(target = "file", source = "dto.file")
    @Mapping(target = "paragraph", source = "dto.paragraph")
    @Mapping(target = "tips", source = "dto.tips")
    @Mapping(target = "preparation", source = "dto.preparationId", qualifiedByName = "mapPreparationIdToPreparation")
    @Mapping(target = "routeNode", ignore = true)
    void patchEntityFromDTO(WritingDTO dto, @MappingTarget Writing entity);

    @Named("mapPreparationIdToPreparation")
    default Preparation stringToLongList(Long preparationId) {
        if (preparationId == null) return null;
        Preparation preparation = new Preparation();
        preparation.setId(preparationId);
        return preparation;
    }
}
