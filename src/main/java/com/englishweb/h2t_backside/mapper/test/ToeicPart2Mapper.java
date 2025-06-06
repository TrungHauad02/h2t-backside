package com.englishweb.h2t_backside.mapper.test;

import com.englishweb.h2t_backside.dto.test.ToeicPart2DTO;
import com.englishweb.h2t_backside.model.test.ToeicPart2;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        builder = @Builder(disableBuilder = true)
)
public interface ToeicPart2Mapper {

    // Chuyển đổi từ ToeicPart2DTO sang ToeicPart2 Entity
    @Mapping(target = "id", source = "dto.id")
    @Mapping(target = "audio", source = "dto.audio")
    @Mapping(target = "correctAnswer", source = "dto.correctAnswer")
    @Mapping(target = "status", source = "dto.status", defaultValue = "true")
    ToeicPart2 convertToEntity(ToeicPart2DTO dto);

    // Chuyển đổi từ ToeicPart2 Entity sang ToeicPart2DTO
    @Mapping(target = "id", source = "entity.id")
    @Mapping(target = "audio", source = "entity.audio")
    @Mapping(target = "correctAnswer", source = "entity.correctAnswer")
    @Mapping(target = "status", source = "entity.status")
    @Mapping(target = "createdAt", source = "entity.createdAt")
    @Mapping(target = "updatedAt", source = "entity.updatedAt")
    ToeicPart2DTO convertToDTO(ToeicPart2 entity);

    // Cập nhật dữ liệu từ DTO vào Entity (chỉ cập nhật trường có giá trị)
    @Mapping(target = "audio", source = "dto.audio")
    @Mapping(target = "correctAnswer", source = "dto.correctAnswer")
    @Mapping(target = "status", source = "dto.status")
    void patchEntityFromDTO(ToeicPart2DTO dto, @MappingTarget ToeicPart2 entity);
}
