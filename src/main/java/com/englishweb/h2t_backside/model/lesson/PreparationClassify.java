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
public class PreparationClassify extends AbstractBaseEntity {

    @Column(nullable = false)
    @Comment("Group name or category for the preparation classify")
    private String groupName;

    @ElementCollection
    @CollectionTable(
            name = "preparation_classify_members",
            joinColumns = @JoinColumn(name = "classify_id")
    )
    @Column(name = "member", nullable = false)
    @Comment("Members associated with this group")
    private List<String> members;
}
