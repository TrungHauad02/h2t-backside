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
public class TestListeningDTO extends AbstractBaseDTO {

    @NotNull(message = "Audio cannot be null")
    private String audio; // Lưu đường dẫn file âm thanh của Reading

    @NotNull(message = "Transcript cannot be null")
    private String transcript;

    private List<Long> questions; // Lưu danh sách ID các câu hỏi
}
