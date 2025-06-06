package com.englishweb.h2t_backside.model.interfacemodel;

public interface LessonEntity extends BaseEntity {
    String getTitle();
    void setTitle(String title);

    String getDescription();
    void setDescription(String description);

    String getImage();
    void setImage(String image);

    Long getViews();
    void setViews(Long views);
}
