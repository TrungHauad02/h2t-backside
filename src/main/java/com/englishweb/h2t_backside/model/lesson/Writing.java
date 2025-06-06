package com.englishweb.h2t_backside.model.lesson;

import com.englishweb.h2t_backside.model.abstractmodel.AbstractLessonEntity;
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
public class Writing extends AbstractLessonEntity {

    @Column(nullable = false)
    @Comment("Topic of the writing lesson")
    private String topic;

    @Column(nullable = false)
    @Comment("Url to docx file for writing")
    private String file;

    @Lob
    @Column(nullable = false)
    @Comment("Paragraph content related to the writing lesson")
    private String paragraph;

    @OneToMany(mappedBy = "writing", cascade = CascadeType.REMOVE, orphanRemoval = false)
    @Comment("List of answers related to the writing lesson")
    private List<WritingAnswer> answers;

    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "preparation_id")
    @Comment("Preparation object associated with this writing lesson")
    private Preparation preparation;

    @ElementCollection
    @CollectionTable(
            name = "writing_tips",
            joinColumns = @JoinColumn(name = "writing_id")
    )
    @Column(name = "tip")
    @Comment("List of tips for writing skill")
    private List<String> tips;
}
