package com.englishweb.h2t_backside.mapper;

import com.englishweb.h2t_backside.dto.feature.ErrorLogDTO;
import com.englishweb.h2t_backside.model.log.ErrorLog;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface ErrorLogMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "message", source = "message")
    @Mapping(target = "errorCode", source = "errorCode")
    @Mapping(target = "severity", source = "severity")
    @Mapping(target = "status", source = "status")
    @Mapping(target = "createdAt", source = "createdAt")
    ErrorLog convertToEntity(ErrorLogDTO dto);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "message", source = "message")
    @Mapping(target = "errorCode", source = "errorCode")
    @Mapping(target = "createdAt", source = "createdAt")
    @Mapping(target = "severity", source = "severity")
    @Mapping(target = "status", source = "status")
    @Mapping(target = "updatedAt", source = "updatedAt")
    ErrorLogDTO convertToDTO(ErrorLog entity);

    void patchEntityFromDTO(ErrorLogDTO dto, @MappingTarget ErrorLog entity);
}

