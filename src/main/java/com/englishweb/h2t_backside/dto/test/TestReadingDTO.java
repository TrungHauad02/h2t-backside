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
public class TestReadingDTO extends AbstractBaseDTO {

    @NotNull(message = "File cannot be null")
    private String file; // Lưu đường dẫn file docx của Reading

    private List<Long> questions; // Lưu danh sách ID các câu hỏi
}
