package com.englishweb.h2t_backside.model.test;

import com.englishweb.h2t_backside.model.features.User;
import com.englishweb.h2t_backside.model.abstractmodel.AbstractBaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Comment;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Toeic extends AbstractBaseEntity {


    @Comment("Title of the TOEIC test")
    private String title;


    @Builder.Default
    @Comment("Total duration of the TOEIC test in minutes")
    private int duration = 120;


    @ColumnDefault("''")
    @Builder.Default
    @Comment("List of question IDs for TOEIC Part 1")
    private String questionsPart1 = "";


    @ColumnDefault("''")
    @Builder.Default
    @Comment("List of question IDs for TOEIC Part 2")
    private String questionsPart2 = "";


    @ColumnDefault("''")
    @Builder.Default
    @Comment("List of question IDs for TOEIC Part 3")
    private String questionsPart3 = "";


    @ColumnDefault("''")
    @Builder.Default
    @Comment("List of question IDs for TOEIC Part 4")
    private String questionsPart4 = "";


    @ColumnDefault("''")
    @Builder.Default
    @Comment("List of question IDs for TOEIC Part 5")
    private String questionsPart5 = "";


    @ColumnDefault("''")
    @Builder.Default
    @Comment("List of question IDs for TOEIC Part 6")
    private String questionsPart6 = "";


    @ColumnDefault("''")
    @Builder.Default
    @Comment("List of question IDs for TOEIC Part 7")
    private String questionsPart7 = "";

    @ManyToOne
    @JoinColumn(name = "owner_id")
    @Comment("Owner of this Toeic")
    private User owner;
}
