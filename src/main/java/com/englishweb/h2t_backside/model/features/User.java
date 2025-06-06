package com.englishweb.h2t_backside.model.features;

import com.englishweb.h2t_backside.model.abstractmodel.AbstractBaseEntity;
import com.englishweb.h2t_backside.model.enummodel.LevelEnum;
import com.englishweb.h2t_backside.model.enummodel.RoleEnum;
import com.englishweb.h2t_backside.model.interfacemodel.UserEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Comment;

import java.time.LocalDate;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "USERS")
public class User extends AbstractBaseEntity implements UserEntity {
    @Column(nullable = false, length = 255)
    @Comment("Full name of the User")
    private String name;

    @Column(nullable = false, unique = true, length = 255, updatable = false)
    @Comment("Email address of the User")
    private String email;

    @Column(nullable = false, length = 255)
    @Comment("Password for the User account")
    private String password;

    @Lob
    @Column(columnDefinition = "TEXT COMMENT 'Avatar image URL for the User'")
    @Comment("Avatar image URL for the User")
    private String avatar;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    @Comment("Role of the User (Admin, Student, or Teacher)")
    @Builder.Default
    private RoleEnum role = RoleEnum.STUDENT;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    @Comment("Level of the User (e.g. Bachelor, Doctor, Master, or Professor)")
    private LevelEnum level;

    @Column(unique = true, length = 10)
    @Comment("Phone number of the User")
    private String phoneNumber;

    @Comment("Date of Birth of the User")
    private LocalDate dateOfBirth;

    @Column(length = 500)
    private String refreshToken;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "PROCESS",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "route_node_id"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "route_node_id"})
    )
    private List<RouteNode> routeNodes;
}
