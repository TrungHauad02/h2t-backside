package com.englishweb.h2t_backside.dto.test;

import com.englishweb.h2t_backside.dto.abstractdto.AbstractBaseDTO;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class TestSpeakingDTO extends AbstractBaseDTO {
    @NotNull(message = "Title cannot be null")
    private String title;

    private List<Long> questions; // Lưu danh sách ID các câu hỏi
}
