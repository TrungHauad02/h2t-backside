package com.englishweb.h2t_backside.mapper.lesson;

import com.englishweb.h2t_backside.dto.lesson.ReadingDTO;
import com.englishweb.h2t_backside.mapper.RouteNodeMapper;
import com.englishweb.h2t_backside.model.lesson.Preparation;
import com.englishweb.h2t_backside.model.lesson.Reading;
import com.englishweb.h2t_backside.utils.ParseData;
import org.mapstruct.*;

import java.util.List;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {PreparationMapper.class, RouteNodeMapper.class},
        builder = @Builder(disableBuilder = true)
)
public interface ReadingMapper {

    // Convert DTO -> Entity
    @Mapping(target = "id", source = "dto.id")
    @Mapping(target = "status", source = "dto.status", defaultValue = "true")
    @Mapping(target = "title", source = "dto.title")
    @Mapping(target = "image", source = "dto.image")
    @Mapping(target = "description", source = "dto.description")
    @Mapping(target = "views", source = "dto.views", defaultValue = "0L")
    @Mapping(target = "file", source = "dto.file")
    @Mapping(target = "questions", source = "dto.questions", qualifiedByName = "longListToString")
    @Mapping(target = "preparation", source = "dto.preparationId", qualifiedByName = "mapPreparationIdToPreparation")
    @Mapping(target = "routeNode", ignore = true)
    Reading convertToEntity(ReadingDTO dto);

    // Convert Entity -> DTO
    @Mapping(target = "id", source = "entity.id")
    @Mapping(target = "status", source = "entity.status")
    @Mapping(target = "createdAt", source = "entity.createdAt")
    @Mapping(target = "updatedAt", source = "entity.updatedAt")
    @Mapping(target = "title", source = "entity.title")
    @Mapping(target = "image", source = "entity.image")
    @Mapping(target = "description", source = "entity.description")
    @Mapping(target = "views", source = "entity.views")
    @Mapping(target = "file", source = "entity.file")
    @Mapping(target = "questions", source = "entity.questions", qualifiedByName = "stringToLongList") // Đã khai báo bên trong PreparationMapper.class
    @Mapping(target = "preparationId", source = "entity.preparation.id")
    @Mapping(target = "routeNode", source = "entity.routeNode")
    ReadingDTO convertToDTO(Reading entity);

    // Cập nhật Entity từ DTO
    @Mapping(target = "id", source = "dto.id")
    @Mapping(target = "status", source = "dto.status")
    @Mapping(target = "title", source = "dto.title")
    @Mapping(target = "image", source = "dto.image")
    @Mapping(target = "description", source = "dto.description")
    @Mapping(target = "views", source = "dto.views")
    @Mapping(target = "file", source = "dto.file")
    @Mapping(target = "questions", source = "dto.questions", qualifiedByName = "longListToString") // Đã khai báo bên trong PreparationMapper.class
    @Mapping(target = "preparation", source = "dto.preparationId", qualifiedByName = "mapPreparationIdToPreparation")
    @Mapping(target = "routeNode", ignore = true)
    void patchEntityFromDTO(ReadingDTO dto, @MappingTarget Reading entity);

    @Named("mapPreparationIdToPreparation")
    default Preparation stringToLongList(Long preparationId) {
        if (preparationId == null) return null;
        Preparation preparation = new Preparation();
        preparation.setId(preparationId);
        return preparation;
    }

}
