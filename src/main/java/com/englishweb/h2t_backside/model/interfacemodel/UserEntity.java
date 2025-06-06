package com.englishweb.h2t_backside.model.interfacemodel;

import com.englishweb.h2t_backside.model.enummodel.LevelEnum;
import com.englishweb.h2t_backside.model.enummodel.RoleEnum;

import java.time.LocalDate;

public interface UserEntity extends BaseEntity {
    String getName();
    void setName(String name);

    String getEmail();
    void setEmail(String email);

    String getPassword();
    void setPassword(String password);

    String getAvatar();
    void setAvatar(String avatar);

    RoleEnum getRole();
    void setRole(RoleEnum role);

    LevelEnum getLevel();
    void setLevel(LevelEnum level);

    String getPhoneNumber();
    void setPhoneNumber(String phoneNumber);

    LocalDate getDateOfBirth();
    void setDateOfBirth(LocalDate dateOfBirth);
}

