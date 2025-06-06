package com.englishweb.h2t_backside.dto.interfacedto;

public interface LessonDTO extends BaseDTO {
    String getTitle();
    void setTitle(String title);

    String getDescription();
    void setDescription(String description);

    String getImage();
    void setImage(String image);

    Long getViews();
    void setViews(Long views);
}
