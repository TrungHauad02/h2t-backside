package com.englishweb.h2t_backside.mapper.test;

import com.englishweb.h2t_backside.dto.test.SubmitToeicDTO;
import com.englishweb.h2t_backside.model.test.SubmitToeic;
import com.englishweb.h2t_backside.model.test.Toeic;
import com.englishweb.h2t_backside.model.features.User;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        builder = @Builder(disableBuilder = true)
)
public interface SubmitToeicMapper {

    // DTO → Entity
    @Mapping(target = "id", source = "dto.id")
    @Mapping(target = "score", source = "dto.score")
    @Mapping(target = "comment", source = "dto.comment")
    @Mapping(target = "toeic", source = "dto.toeic_id", qualifiedByName = "mapToeicId")
    @Mapping(target = "user", source = "dto.user_id", qualifiedByName = "mapUserId")
    @Mapping(target = "status", source = "dto.status", defaultValue = "true")
    SubmitToeic convertToEntity(SubmitToeicDTO dto);

    // Entity → DTO
    @Mapping(target = "id", source = "entity.id")
    @Mapping(target = "score", source = "entity.score")
    @Mapping(target = "comment", source = "entity.comment")
    @Mapping(target = "toeic_id", source = "entity.toeic.id")
    @Mapping(target = "user_id", source = "entity.user.id")
    @Mapping(target = "status", source = "entity.status")
    @Mapping(target = "createdAt", source = "entity.createdAt")
    @Mapping(target = "updatedAt", source = "entity.updatedAt")
    @Mapping(target = "toeic_title", source = "entity.toeic.title")
    @Mapping(target = "toeic_duration", source = "entity.toeic.duration")
    SubmitToeicDTO convertToDTO(SubmitToeic entity);

    // Patch DTO → Entity
    @Mapping(target = "score", source = "dto.score")
    @Mapping(target = "comment", source = "dto.comment")
    @Mapping(target = "toeic", source = "dto.toeic_id", qualifiedByName = "mapToeicId")
    @Mapping(target = "user", source = "dto.user_id", qualifiedByName = "mapUserId")
    @Mapping(target = "status", source = "dto.status")
    void patchEntityFromDTO(SubmitToeicDTO dto, @MappingTarget SubmitToeic entity);


    @Named("mapUserId")
    default User mapUserId(Long id) {
        if (id == null) return null;
        User user = new User();
        user.setId(id);
        return user;
    }

    @Named("mapToeicId")
    default Toeic mapToeicId(Long id) {
        if (id == null) return null;
        Toeic toeic = new Toeic();
        toeic.setId(id);
        return toeic;
    }
}
