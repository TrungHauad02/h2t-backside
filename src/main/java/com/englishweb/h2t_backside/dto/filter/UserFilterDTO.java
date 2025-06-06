package com.englishweb.h2t_backside.dto.filter;

import com.englishweb.h2t_backside.model.enummodel.LevelEnum;
import com.englishweb.h2t_backside.model.enummodel.RoleEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class UserFilterDTO extends BaseFilterDTO{
    private String name;
    private String email;
    private LevelEnum level;
    private List<RoleEnum> roleList;
}
