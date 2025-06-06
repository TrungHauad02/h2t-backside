package com.englishweb.h2t_backside.model.features;

import com.englishweb.h2t_backside.model.abstractmodel.AbstractBaseEntity;
import com.englishweb.h2t_backside.model.enummodel.RouteNodeEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class RouteNode extends AbstractBaseEntity {
    @Column(nullable = false)
    private Long nodeId;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private RouteNodeEnum type;

    @Column(nullable = false)
    private int serial;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "route_id")
    private Route route;
}
