package com.englishweb.h2t_backside.mapper.lesson;
import com.englishweb.h2t_backside.dto.lesson.PreparationClassifyDTO;
import com.englishweb.h2t_backside.model.lesson.PreparationClassify;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        builder = @Builder(disableBuilder = true)
)
public interface PreparationClassifyMapper {

    // Convert DTO -> Entity
    @Mapping(target = "id", source = "dto.id")
    @Mapping(target = "status", source = "dto.status", defaultValue = "true")
    @Mapping(target = "groupName", source = "dto.groupName")
    @Mapping(target = "members", source = "dto.members")
    PreparationClassify convertToEntity(PreparationClassifyDTO dto);

    // Convert Entity -> DTO
    @Mapping(target = "id", source = "entity.id")
    @Mapping(target = "status", source = "entity.status")
    @Mapping(target = "createdAt", source = "entity.createdAt")
    @Mapping(target = "updatedAt", source = "entity.updatedAt")
    @Mapping(target = "groupName", source = "entity.groupName")
    @Mapping(target = "members", source = "entity.members")
    PreparationClassifyDTO convertToDTO(PreparationClassify entity);

    // Cập nhật Entity từ DTO
    @Mapping(target = "id", source = "dto.id")
    @Mapping(target = "status", source = "dto.status")
    @Mapping(target = "groupName", source = "dto.groupName")
    @Mapping(target = "members", source = "dto.members")
    void patchEntityFromDTO(PreparationClassifyDTO dto, @MappingTarget PreparationClassify entity);
}
