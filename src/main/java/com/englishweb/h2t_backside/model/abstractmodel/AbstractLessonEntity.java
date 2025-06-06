package com.englishweb.h2t_backside.model.abstractmodel;

import com.englishweb.h2t_backside.model.features.RouteNode;
import com.englishweb.h2t_backside.model.interfacemodel.LessonEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Comment;

@MappedSuperclass
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public abstract class AbstractLessonEntity extends AbstractBaseEntity implements LessonEntity {
    @Column(nullable = false)
    @Comment("Title of the Lesson")
    private String title;

    @Column(nullable = false)
    @Comment("Image associated with the Lesson")
    private String image;

    @Column(nullable = false)
    @Comment("Description of the Lesson")
    private String description;

    @Column(nullable = false)
    @ColumnDefault("0")
    @Comment("Number of views for Lesson")
    @Builder.Default
    private Long views = 0L;

    @OneToOne
    @JoinColumn(name = "route_node_id", referencedColumnName = "id", nullable = true)
    @Comment("Associated Route node for the Lesson")
    private RouteNode routeNode;
}
