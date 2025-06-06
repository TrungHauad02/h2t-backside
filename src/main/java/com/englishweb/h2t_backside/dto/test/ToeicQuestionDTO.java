    package com.englishweb.h2t_backside.dto.test;

    import com.englishweb.h2t_backside.dto.abstractdto.AbstractBaseDTO;
    import jakarta.validation.constraints.NotBlank;
    import jakarta.validation.constraints.NotNull;
    import lombok.*;
    import lombok.experimental.SuperBuilder;

    import java.util.List;

    @Getter
    @Setter
    @SuperBuilder
    @NoArgsConstructor
    @AllArgsConstructor
    public class ToeicQuestionDTO extends AbstractBaseDTO {

        private String content;

        @NotBlank(message = "Explanation cannot be blank")
        private String explanation;

        private List<ToeicAnswerDTO> answers;
    }
