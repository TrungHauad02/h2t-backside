package com.englishweb.h2t_backside.model.test;

import com.englishweb.h2t_backside.model.features.User;
import com.englishweb.h2t_backside.model.abstractmodel.AbstractBaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Comment;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class CompetitionTest extends AbstractBaseEntity {

    @Column(nullable = false)
    @Comment("Title of the Competition")
    private String title;

    @Column(nullable = false)
    @Comment("Duration of the Competition in minutes")
    private int duration;

    @Column(nullable = false)
    @Comment("Start time of the Competition")
    private LocalDateTime startTime;

    @Column(nullable = false)
    @Comment("End time of the Competition")
    private LocalDateTime endTime;


    @ColumnDefault("''")
    @Builder.Default
    private String parts = "";

    @ManyToOne
    @JoinColumn(name = "owner_id")
    @Comment("Owner of this competition")
    private User owner;
}
