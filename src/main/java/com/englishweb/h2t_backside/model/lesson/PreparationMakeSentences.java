package com.englishweb.h2t_backside.model.lesson;

import com.englishweb.h2t_backside.model.abstractmodel.AbstractBaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;

import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PreparationMakeSentences extends AbstractBaseEntity {

    @ElementCollection
    @CollectionTable(
            name = "preparation_make_sentences_words",
            joinColumns = @JoinColumn(name = "sentence_id")
    )
    @Column(name = "sentence", nullable = false)
    @Comment("Sentences associated with this preparation step")
    private List<String> sentences;
}
