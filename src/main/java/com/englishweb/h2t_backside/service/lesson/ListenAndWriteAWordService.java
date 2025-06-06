package com.englishweb.h2t_backside.service.lesson;

import com.englishweb.h2t_backside.dto.lesson.ListenAndWriteAWordDTO;
import com.englishweb.h2t_backside.service.feature.BaseService;

import java.util.List;

public interface ListenAndWriteAWordService extends BaseService<ListenAndWriteAWordDTO> {

    List<ListenAndWriteAWordDTO> findByListeningId(Long listeningId);
}
