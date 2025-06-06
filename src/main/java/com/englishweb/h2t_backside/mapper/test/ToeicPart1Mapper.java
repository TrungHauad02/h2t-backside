package com.englishweb.h2t_backside.mapper.test;

import com.englishweb.h2t_backside.dto.test.ToeicPart1DTO;
import com.englishweb.h2t_backside.model.test.ToeicPart1;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        builder = @Builder(disableBuilder = true)
)
public interface ToeicPart1Mapper {

    // Chuyển đổi từ ToeicPart1DTO sang ToeicPart1 Entity
    @Mapping(target = "id", source = "dto.id")
    @Mapping(target = "image", source = "dto.image")
    @Mapping(target = "audio", source = "dto.audio")
    @Mapping(target = "transcript", source = "dto.transcript")
    @Mapping(target = "correctAnswer", source = "dto.correctAnswer")
    @Mapping(target = "status", source = "dto.status", defaultValue = "true")
    ToeicPart1 convertToEntity(ToeicPart1DTO dto);

    // Chuyển đổi từ ToeicPart1 Entity sang ToeicPart1DTO
    @Mapping(target = "id", source = "entity.id")
    @Mapping(target = "image", source = "entity.image")
    @Mapping(target = "audio", source = "entity.audio")
    @Mapping(target = "transcript", source = "entity.transcript")
    @Mapping(target = "correctAnswer", source = "entity.correctAnswer")
    @Mapping(target = "status", source = "entity.status")
    @Mapping(target = "createdAt", source = "entity.createdAt")
    @Mapping(target = "updatedAt", source = "entity.updatedAt")
    ToeicPart1DTO convertToDTO(ToeicPart1 entity);

    // Cập nhật dữ liệu từ DTO vào Entity (chỉ cập nhật trường có giá trị)
    @Mapping(target = "image", source = "dto.image")
    @Mapping(target = "audio", source = "dto.audio")
    @Mapping(target = "correctAnswer", source = "dto.correctAnswer")
    @Mapping(target = "status", source = "dto.status")
    void patchEntityFromDTO(ToeicPart1DTO dto, @MappingTarget ToeicPart1 entity);
}
