package com.englishweb.h2t_backside.mapper.test;

import com.englishweb.h2t_backside.dto.test.SubmitCompetitionWritingDTO;
import com.englishweb.h2t_backside.model.test.SubmitCompetition;
import com.englishweb.h2t_backside.model.test.SubmitCompetitionWriting;
import com.englishweb.h2t_backside.model.test.TestWriting;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring", // Tích hợp với Spring
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, // Bỏ qua các giá trị null khi map
        builder = @Builder(disableBuilder = true) // Không sử dụng builder khi map
)
public interface SubmitCompetitionWritingMapper {

    // Chuyển từ DTO → Entity
    @Mapping(target = "id", source = "dto.id")
    @Mapping(target = "submitCompetition", source = "dto.submitCompetition_id", qualifiedByName = "mapSubmitCompetitionId")
    @Mapping(target = "testWriting", source = "dto.competitionWriting_id", qualifiedByName = "mapTestWritingId")
    @Mapping(target = "content", source = "dto.content", defaultValue = "") // Nếu content null thì gán ""
    @Mapping(target = "score", source = "dto.score")
    @Mapping(target = "status", source = "dto.status", defaultValue = "true") // Nếu status null thì gán true
    SubmitCompetitionWriting convertToEntity(SubmitCompetitionWritingDTO dto);

    // Chuyển từ Entity → DTO
    @Mapping(target = "id", source = "entity.id")
    @Mapping(target = "submitCompetition_id", source = "entity.submitCompetition.id")
    @Mapping(target = "competitionWriting_id", source = "entity.testWriting.id")
    @Mapping(target = "content", source = "entity.content")
    @Mapping(target = "score", source = "entity.score")
    @Mapping(target = "status", source = "entity.status")
    @Mapping(target = "createdAt", source = "entity.createdAt")
    @Mapping(target = "updatedAt", source = "entity.updatedAt")
    SubmitCompetitionWritingDTO convertToDTO(SubmitCompetitionWriting entity);

    // Cập nhật entity từ DTO (patch)
    @Mapping(target = "submitCompetition", source = "dto.submitCompetition_id", qualifiedByName = "mapSubmitCompetitionId")
    @Mapping(target = "testWriting", source = "dto.competitionWriting_id", qualifiedByName = "mapTestWritingId")
    @Mapping(target = "content", source = "dto.content")
    @Mapping(target = "score", source = "dto.score")
    @Mapping(target = "status", source = "dto.status")
    void patchEntityFromDTO(SubmitCompetitionWritingDTO dto, @MappingTarget SubmitCompetitionWriting entity);


    @Named("mapSubmitCompetitionId")
    default SubmitCompetition mapSubmitCompetitionId(Long id) {
        if (id == null) return null;
        SubmitCompetition entity = new SubmitCompetition();
        entity.setId(id);
        return entity;
    }


    @Named("mapTestWritingId")
    default TestWriting mapTestWritingId(Long id) {
        if (id == null) return null;
        TestWriting entity = new TestWriting();
        entity.setId(id);
        return entity;
    }
}
