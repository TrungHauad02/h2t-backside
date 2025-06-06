package com.englishweb.h2t_backside.model.lesson;

import com.englishweb.h2t_backside.model.abstractmodel.AbstractBaseEntity;
import com.englishweb.h2t_backside.model.enummodel.WordTypeEnum;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Comment;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Vocabulary extends AbstractBaseEntity {

    @Lob
    @Column(nullable = false)
    @Comment("Example sentence using the vocabulary word")
    private String example;

    @Column(nullable = false)
    @Comment("Image related to the vocabulary word (URL)")
    private String image;

    @Column(nullable = false)
    @Comment("Word itself (e.g., house, run)")
    private String word;

    @Column(nullable = false)
    @Comment("Meaning of the vocabulary word")
    private String meaning;

    @Column(nullable = false)
    @Comment("Phonetic transcription of the word")
    private String phonetic;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Comment("Type of the word (e.g., Noun, Verb, etc.)")
    @Builder.Default
    private WordTypeEnum wordType = WordTypeEnum.NOUN;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "topic_id", nullable = false)
    @Comment("Topic to which this vocabulary belongs")
    private Topic topic;
}
