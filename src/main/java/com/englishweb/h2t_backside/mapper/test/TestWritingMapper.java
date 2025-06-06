package com.englishweb.h2t_backside.mapper.test;

import com.englishweb.h2t_backside.dto.test.TestWritingDTO;
import com.englishweb.h2t_backside.model.test.TestWriting;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        builder = @Builder(disableBuilder = true)
)
public interface TestWritingMapper {

    // Chuyển đổi từ TestWritingDTO sang TestWriting Entity
    @Mapping(target = "id", source = "dto.id")
    @Mapping(target = "topic", source = "dto.topic")
    @Mapping(target = "maxWords", source = "dto.maxWords")
    @Mapping(target = "minWords", source = "dto.minWords")
    @Mapping(target = "status", source = "dto.status", defaultValue = "true")
    TestWriting convertToEntity(TestWritingDTO dto);

    // Chuyển đổi từ TestWriting Entity sang TestWritingDTO
    @Mapping(target = "id", source = "entity.id")
    @Mapping(target = "topic", source = "entity.topic")
    @Mapping(target = "maxWords", source = "entity.maxWords")
    @Mapping(target = "minWords", source = "entity.minWords")
    @Mapping(target = "status", source = "entity.status")
    @Mapping(target = "createdAt", source = "entity.createdAt")
    @Mapping(target = "updatedAt", source = "entity.updatedAt")
    TestWritingDTO convertToDTO(TestWriting entity);

    // Cập nhật dữ liệu từ DTO vào Entity (chỉ cập nhật trường có giá trị)
    @Mapping(target = "topic", source = "dto.topic")
    @Mapping(target = "maxWords", source = "dto.maxWords")
    @Mapping(target = "minWords", source = "dto.minWords")
    @Mapping(target = "status", source = "dto.status")
    void patchEntityFromDTO(TestWritingDTO dto, @MappingTarget TestWriting entity);
}
