package com.englishweb.h2t_backside.model.features;

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
public class Route extends AbstractBaseEntity {

    @Column(nullable = false)
    @Comment("Title for Route")
    private String title;

    @Column(nullable = false)
    @Comment("Image url for Route")
    private String image;

    @Column(nullable = false)
    @Comment("Description for route")
    private String description;

    @OneToMany(mappedBy = "route", cascade = CascadeType.ALL, orphanRemoval = true)
    @Comment("List of lessons associated with this route")
    private List<RouteNode> routeNodes;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    @Comment("Owner of this route")
    private User owner;
}
