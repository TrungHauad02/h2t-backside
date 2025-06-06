package com.englishweb.h2t_backside.service.test;

import com.englishweb.h2t_backside.dto.filter.SubmitCompetitionFilterDTO;
import com.englishweb.h2t_backside.dto.test.SubmitCompetitionDTO;
import com.englishweb.h2t_backside.model.test.SubmitCompetition;
import com.englishweb.h2t_backside.service.feature.BaseService;
import org.springframework.data.domain.Page;

import java.util.List;

public interface SubmitCompetitionService extends BaseService<SubmitCompetitionDTO> {

    int countSubmitByUserId(Long userId);

    double totalScoreByUserId(Long userId);

    SubmitCompetitionDTO findByTestIdAndUserIdAndStatus(Long testId, Long userId, Boolean status);

    Page<SubmitCompetitionDTO> searchWithFilters(int page, int size, String sortFields, SubmitCompetitionFilterDTO filter, Long userId);

    List<SubmitCompetitionDTO> getLeaderboard();
    List<SubmitCompetitionDTO> findByTestIdAndStatus(Long testId, Boolean status);
    List<SubmitCompetitionDTO> findByTestId(Long testId);
}
