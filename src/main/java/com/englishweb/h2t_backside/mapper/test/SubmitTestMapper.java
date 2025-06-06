package com.englishweb.h2t_backside.mapper.test;

import com.englishweb.h2t_backside.dto.test.SubmitTestDTO;
import com.englishweb.h2t_backside.model.test.SubmitTest;
import com.englishweb.h2t_backside.model.test.Test;
import com.englishweb.h2t_backside.model.features.User;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        builder = @Builder(disableBuilder = true)
)
public interface SubmitTestMapper {

    // DTO → Entity
    @Mapping(target = "id", source = "id")
    @Mapping(target = "user", source = "user_id", qualifiedByName = "mapUserId")
    @Mapping(target = "test", source = "test_id", qualifiedByName = "mapTestId")
    @Mapping(target = "score", source = "score")
    @Mapping(target = "comment", source = "comment", defaultValue = "")
    @Mapping(target = "status", source = "status", defaultValue = "true")
    SubmitTest convertToEntity(SubmitTestDTO dto);

    // Entity → DTO
    @Mapping(target = "id", source = "id")
    @Mapping(target = "user_id", source = "user.id")
    @Mapping(target = "test_id", source = "test.id")
    @Mapping(target = "score", source = "score")
    @Mapping(target = "comment", source = "comment")
    @Mapping(target = "status", source = "status")
    @Mapping(target = "createdAt", source = "createdAt")
    @Mapping(target = "updatedAt", source = "updatedAt")
    @Mapping(target = "test_type", source = "test.type")
    @Mapping(target = "test_title", source = "test.title")
    @Mapping(target = "test_duration", source = "test.duration")
    SubmitTestDTO convertToDTO(SubmitTest entity);

    // PATCH DTO → Entity
    @Mapping(target = "user", source = "user_id", qualifiedByName = "mapUserId")
    @Mapping(target = "test", source = "test_id", qualifiedByName = "mapTestId")
    @Mapping(target = "score", source = "score")
    @Mapping(target = "comment", source = "comment")
    @Mapping(target = "status", source = "status")
    void patchEntityFromDTO(SubmitTestDTO dto, @MappingTarget SubmitTest entity);

    @Named("mapUserId")
    default User mapUserId(Long id) {
        if (id == null) return null;
        User user = new User();
        user.setId(id);
        return user;
    }

    @Named("mapTestId")
    default Test mapTestId(Long id) {
        if (id == null) return null;
        Test test = new Test();
        test.setId(id);
        return test;
    }
}
