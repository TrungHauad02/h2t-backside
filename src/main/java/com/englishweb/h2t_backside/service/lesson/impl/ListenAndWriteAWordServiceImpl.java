package com.englishweb.h2t_backside.service.lesson.impl;

import com.englishweb.h2t_backside.dto.lesson.ListenAndWriteAWordDTO;
import com.englishweb.h2t_backside.exception.CreateResourceException;
import com.englishweb.h2t_backside.exception.ErrorApiCodeContent;
import com.englishweb.h2t_backside.exception.ResourceNotFoundException;
import com.englishweb.h2t_backside.exception.UpdateResourceException;
import com.englishweb.h2t_backside.mapper.lesson.ListenAndWriteAWordMapper;
import com.englishweb.h2t_backside.model.enummodel.SeverityEnum;
import com.englishweb.h2t_backside.model.lesson.ListenAndWriteAWord;
import com.englishweb.h2t_backside.repository.lesson.ListenAndWriteAWordRepository;
import com.englishweb.h2t_backside.service.feature.impl.BaseServiceImpl;
import com.englishweb.h2t_backside.service.feature.impl.DiscordNotifierImpl;
import com.englishweb.h2t_backside.service.lesson.ListenAndWriteAWordService;
import com.englishweb.h2t_backside.service.lesson.ListeningService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ListenAndWriteAWordServiceImpl
        extends BaseServiceImpl<ListenAndWriteAWordDTO, ListenAndWriteAWord, ListenAndWriteAWordRepository>
        implements ListenAndWriteAWordService {

    private final ListenAndWriteAWordMapper mapper;
    private final ListeningService listeningService;

    public ListenAndWriteAWordServiceImpl(ListenAndWriteAWordRepository repository,
                                          DiscordNotifierImpl discordNotifier,
                                          ListenAndWriteAWordMapper mapper, ListeningService listeningService) {
        super(repository, discordNotifier);
        this.mapper = mapper;
        this.listeningService = listeningService;
    }

    @Override
    protected void findByIdError(Long id) {
        String errorMessage = String.format("ListenAndWriteAWord with ID '%d' not found.", id);
        log.warn(errorMessage);
        throw new ResourceNotFoundException(id, errorMessage, SeverityEnum.LOW);
    }

    @Override
    protected void createError(ListenAndWriteAWordDTO dto, Exception ex) {
        log.error("Error creating ListenAndWriteAWord: {}", ex.getMessage());
        String errorMessage = "Unexpected error creating ListenAndWriteAWord: " + ex.getMessage();
        String errorCode = ErrorApiCodeContent.LESSON_CREATED_FAIL;
        throw new CreateResourceException(dto, errorMessage, errorCode, HttpStatus.INTERNAL_SERVER_ERROR, SeverityEnum.HIGH);
    }

    @Override
    protected void updateError(ListenAndWriteAWordDTO dto, Long id, Exception ex) {
        log.error("Error updating ListenAndWriteAWord: {}", ex.getMessage());
        String errorMessage = "Unexpected error updating ListenAndWriteAWord: " + ex.getMessage();
        String errorCode = ErrorApiCodeContent.LESSON_UPDATED_FAIL;
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        if (!this.isExist(id)) {
            errorMessage = String.format("ListenAndWriteAWord with ID '%d' not found.", id);
            status = HttpStatus.NOT_FOUND;
        }

        throw new UpdateResourceException(dto, errorMessage, errorCode, status, SeverityEnum.LOW);
    }

    @Override
    protected void patchEntityFromDTO(ListenAndWriteAWordDTO dto, ListenAndWriteAWord entity) {
        mapper.patchEntityFromDTO(dto, entity);
    }

    @Override
    protected ListenAndWriteAWord convertToEntity(ListenAndWriteAWordDTO dto) {
        return mapper.convertToEntity(dto);
    }

    @Override
    protected ListenAndWriteAWordDTO convertToDTO(ListenAndWriteAWord entity) {
        return mapper.convertToDTO(entity);
    }

    @Override
    public List<ListenAndWriteAWordDTO> findByListeningId(Long listeningId) {
        if (!listeningService.isExist(listeningId)) {
            throw new ResourceNotFoundException(listeningId, String.format("Listening with ID '%d' not found.", listeningId), SeverityEnum.LOW);
        }
        return repository.findByListening_Id(listeningId).stream().map(this::convertToDTO).toList();
    }
}
