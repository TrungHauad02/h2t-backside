package com.englishweb.h2t_backside.dto.feature;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class VoiceDTO {
    private String voice;
    private String name;
    private byte[] fileData;
}
