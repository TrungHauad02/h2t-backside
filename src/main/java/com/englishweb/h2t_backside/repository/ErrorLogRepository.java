package com.englishweb.h2t_backside.repository;

import com.englishweb.h2t_backside.model.enummodel.SeverityEnum;
import com.englishweb.h2t_backside.model.log.ErrorLog;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface ErrorLogRepository extends JpaRepository<ErrorLog, Long>, JpaSpecificationExecutor<ErrorLog> {

    @Transactional
    void deleteAllByStatusFalseAndSeverityIn(List<SeverityEnum> severityList);
}

