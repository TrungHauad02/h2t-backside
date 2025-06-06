package com.englishweb.h2t_backside.dto.abstractdto;

import com.englishweb.h2t_backside.dto.interfacedto.BaseDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class AbstractBaseDTO implements BaseDTO {
    private Long id;
    private Boolean status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
