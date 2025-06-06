package com.englishweb.h2t_backside.model.interfacemodel;

import com.englishweb.h2t_backside.model.features.User;

public interface AIResponseEntity extends BaseEntity{
    String getRequest();
    void setRequest(String request);

    String getResponse();
    void setResponse(String response);

    String getEvaluate();
    void setEvaluate(String evaluate);

    User getUser();
    void setUser(User user);

}
