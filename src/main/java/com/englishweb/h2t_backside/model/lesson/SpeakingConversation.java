package com.englishweb.h2t_backside.model.lesson;

import com.englishweb.h2t_backside.model.abstractmodel.AbstractBaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SpeakingConversation extends AbstractBaseEntity {

    @Column(nullable = false)
    @Comment("Name of person in the conversation")
    private String name;

    @Column(nullable = false)
    @Comment("Serial number indicating the order of the conversation")
    private int serial;

    @Column(nullable = false)
    @Comment("Content of the conversation")
    private String content;

    @Comment("Audio URL for the conversation")
    private String audioUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "speaking_id", nullable = false)
    @Comment("Speaking lesson to which this conversation belongs")
    private Speaking speaking;
}
