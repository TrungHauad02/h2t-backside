package com.englishweb.h2t_backside.mapper.test;
import com.englishweb.h2t_backside.dto.lesson.LessonQuestionDTO;
import com.englishweb.h2t_backside.dto.test.QuestionDTO;
import com.englishweb.h2t_backside.model.lesson.LessonQuestion;
import com.englishweb.h2t_backside.model.test.Question;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = AnswerMapper.class,
        builder = @Builder(disableBuilder = true)
)
public interface QuestionMapper {

    // Chuyển đổi từ DTO sang Entity
    @Mapping(target = "id", source = "dto.id")
    @Mapping(target = "status", source = "dto.status", defaultValue = "true")
    @Mapping(target = "content", source = "dto.content")
    @Mapping(target = "explanation", source = "dto.explanation")
    @Mapping(target = "answers", source = "dto.answers")
    Question convertToEntity(QuestionDTO dto);

    // Chuyển đổi từ Entity sang DTO
    @Mapping(target = "id", source = "entity.id")
    @Mapping(target = "status", source = "entity.status")
    @Mapping(target = "createdAt", source = "entity.createdAt")
    @Mapping(target = "updatedAt", source = "entity.updatedAt")
    @Mapping(target = "content", source = "entity.content")
    @Mapping(target = "explanation", source = "entity.explanation")
    @Mapping(target = "answers", source = "entity.answers")
    QuestionDTO convertToDTO(Question entity);

    // Cập nhật dữ liệu từ DTO vào Entity (bỏ qua trường null)
    @Mapping(target = "id", source = "dto.id")
    @Mapping(target = "status", source = "dto.status")
    @Mapping(target = "content", source = "dto.content")
    @Mapping(target = "explanation", source = "dto.explanation")
    @Mapping(target = "answers", source = "dto.answers")
    void patchEntityFromDTO(QuestionDTO dto, @MappingTarget Question entity);
    @AfterMapping
    default void afterConvertToEntity(QuestionDTO dto, @MappingTarget Question entity) {
        if (entity.getAnswers() != null) {
            entity.getAnswers().forEach(answer -> answer.setQuestion(entity));
        }
    }

}
