package com.englishweb.h2t_backside.repository.lesson;

import com.englishweb.h2t_backside.model.lesson.ListenAndWriteAWord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ListenAndWriteAWordRepository extends JpaRepository<ListenAndWriteAWord, Long> {

    List<ListenAndWriteAWord> findByListening_Id(Long listeningId);
}
