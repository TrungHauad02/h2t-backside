package com.englishweb.h2t_backside.model.lesson;

import com.englishweb.h2t_backside.model.abstractmodel.AbstractBaseEntity;
import com.englishweb.h2t_backside.model.enummodel.PreparationEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import org.hibernate.annotations.Comment;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Preparation extends AbstractBaseEntity {

    @Column(nullable = false)
    @Comment("Title of the preparation step or activity")
    private String title;

    @Column(nullable = false)
    @Comment("Tips or instructions related to the preparation")
    private String tip;

    @Comment("IDs related to the preparation type")
    private String questions;

    @Enumerated(EnumType.STRING)
    @Comment("Type of the preparation, determines how the questions are used")
    private PreparationEnum type;
}

/*
 * type:
 *  - CLASSIFY: questions stores a list of IDs for preparationClassify
 *  - WORDS_MAKE_SENTENCES: questions stores a list of IDs for preparationMakeSentences
 *  - MATCH_WORD_WITH_SENTENCES: questions stores IDs related to word-sentence matching
 * */
