package com.englishweb.h2t_backside.mapper.test;

import com.englishweb.h2t_backside.dto.test.SubmitCompetitionDTO;
import com.englishweb.h2t_backside.model.test.CompetitionTest;
import com.englishweb.h2t_backside.model.test.SubmitCompetition;
import com.englishweb.h2t_backside.model.features.User;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        builder = @Builder(disableBuilder = true)
)
public interface SubmitCompetitionMapper {

    // DTO → Entity
    @Mapping(target = "id", source = "dto.id")
    @Mapping(target = "score", source = "dto.score")
    @Mapping(target = "user", source = "dto.user_id", qualifiedByName = "mapUserId")
    @Mapping(target = "test", source = "dto.competition_id", qualifiedByName = "mapTestId")
    @Mapping(target = "status", source = "dto.status", defaultValue = "true")
    SubmitCompetition convertToEntity(SubmitCompetitionDTO dto);

    // Entity → DTO
    @Mapping(target = "id", source = "entity.id")
    @Mapping(target = "score", source = "entity.score")
    @Mapping(target = "user_id", source = "entity.user.id")
    @Mapping(target = "competition_id", source = "entity.test.id")
    @Mapping(target = "status", source = "entity.status")
    @Mapping(target = "createdAt", source = "entity.createdAt")
    @Mapping(target = "updatedAt", source = "entity.updatedAt")
    @Mapping(target = "competition_title", source = "entity.test.title")
    @Mapping(target = "competition_duration", source = "entity.test.duration")
    SubmitCompetitionDTO convertToDTO(SubmitCompetition entity);

    // Patch DTO → Entity
    @Mapping(target = "score", source = "dto.score")
    @Mapping(target = "user", source = "dto.user_id", qualifiedByName = "mapUserId")
    @Mapping(target = "test", source = "dto.competition_id", qualifiedByName = "mapTestId")
    @Mapping(target = "status", source = "dto.status")
    void patchEntityFromDTO(SubmitCompetitionDTO dto, @MappingTarget SubmitCompetition entity);


    @Named("mapUserId")
    default User mapUserId(Long id) {
        if (id == null) return null;
        User user = new User();
        user.setId(id);
        return user;
    }

    @Named("mapTestId")
    default CompetitionTest mapTestId(Long id) {
        if (id == null) return null;
        CompetitionTest test = new CompetitionTest();
        test.setId(id);
        return test;
    }
}
