package com.englishweb.h2t_backside.service.lesson.impl;

import com.englishweb.h2t_backside.dto.filter.VocabularyFilterDTO;
import com.englishweb.h2t_backside.dto.lesson.VocabularyDTO;
import com.englishweb.h2t_backside.exception.CreateResourceException;
import com.englishweb.h2t_backside.exception.ErrorApiCodeContent;
import com.englishweb.h2t_backside.exception.ResourceNotFoundException;
import com.englishweb.h2t_backside.exception.UpdateResourceException;
import com.englishweb.h2t_backside.mapper.lesson.VocabularyMapper;
import com.englishweb.h2t_backside.model.enummodel.SeverityEnum;
import com.englishweb.h2t_backside.model.lesson.Vocabulary;
import com.englishweb.h2t_backside.repository.lesson.VocabularyRepository;
import com.englishweb.h2t_backside.repository.specifications.VocabularySpecification;
import com.englishweb.h2t_backside.service.feature.DiscordNotifier;
import com.englishweb.h2t_backside.service.feature.impl.BaseServiceImpl;
import com.englishweb.h2t_backside.service.lesson.VocabularyService;
import com.englishweb.h2t_backside.utils.BaseFilterSpecification;
import com.englishweb.h2t_backside.utils.ParseData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class VocabularyServiceImpl extends BaseServiceImpl<VocabularyDTO, Vocabulary, VocabularyRepository> implements VocabularyService {

    private final VocabularyMapper mapper;

    public VocabularyServiceImpl(VocabularyRepository repository,
                                 DiscordNotifier discordNotifier,
                                 VocabularyMapper mapper) {
        super(repository, discordNotifier);
        this.mapper = mapper;
    }

    @Override
    protected void findByIdError(Long id) {
        String errorMessage = String.format("Vocabulary with ID '%d' not found", id);
        log.warn(errorMessage);
        throw new ResourceNotFoundException(id, errorMessage, SeverityEnum.LOW);
    }

    @Override
    protected void createError(VocabularyDTO dto, Exception ex) {
        log.error("Error creating vocabulary: {}", ex.getMessage());
        String errorMessage = "Failed to create vocabulary: " + ex.getMessage();
        throw new CreateResourceException(
                dto,
                errorMessage,
                ErrorApiCodeContent.VOCABULARY_CREATED_FAIL,
                HttpStatus.INTERNAL_SERVER_ERROR,
                SeverityEnum.HIGH
        );
    }

    @Override
    protected void updateError(VocabularyDTO dto, Long id, Exception ex) {
        log.error("Error updating vocabulary ID {}: {}", id, ex.getMessage());
        String errorMessage = "Failed to update vocabulary: " + ex.getMessage();
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        if (!this.isExist(id)) {
            errorMessage = String.format("Vocabulary with ID '%d' not found", id);
            status = HttpStatus.NOT_FOUND;
        }

        throw new UpdateResourceException(
                dto,
                errorMessage,
                ErrorApiCodeContent.VOCABULARY_UPDATED_FAIL,
                status,
                SeverityEnum.LOW
        );
    }

    @Override
    protected void patchEntityFromDTO(VocabularyDTO dto, Vocabulary entity) {
        mapper.patchEntityFromDTO(dto, entity);
    }

    @Override
    protected Vocabulary convertToEntity(VocabularyDTO dto) {
        return mapper.convertToEntity(dto);
    }

    @Override
    protected VocabularyDTO convertToDTO(Vocabulary entity) {
        return mapper.convertToDTO(entity);
    }

    public Page<VocabularyDTO> searchWithFiltersTopicId(int page, int size, String sortFields, VocabularyFilterDTO filter, Long topicId) {
        Pageable pageable = ParseData.parsePageArgs(page, size, sortFields, Vocabulary.class);

        Specification<Vocabulary> specification = Specification.where(VocabularySpecification.findByTopicId(topicId))
                .and(BaseFilterSpecification.applyBaseFilters(filter));

        if (filter != null && filter.getWord() != null && !filter.getWord().isEmpty()) {
            specification = specification.and(VocabularySpecification.findByWord(filter.getWord()));
        }

        if (filter != null && filter.getWordType() != null) {
            specification = specification.and(VocabularySpecification.findByWordType(filter.getWordType()));
        }

        return repository.findAll(specification, pageable).map(this::convertToDTO);
    }
}