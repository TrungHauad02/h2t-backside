package com.englishweb.h2t_backside.model.lesson;

import com.englishweb.h2t_backside.model.abstractmodel.AbstractBaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Column;
import lombok.*;
import org.hibernate.annotations.Comment;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PreparationMatchWordSentences extends AbstractBaseEntity {

    @Column(nullable = false)
    @Comment("Sentence associated with the word in this preparation step")
    private String sentence;

    @Column(nullable = false)
    @Comment("Word that is matched with the sentence in this preparation step")
    private String word;
}
