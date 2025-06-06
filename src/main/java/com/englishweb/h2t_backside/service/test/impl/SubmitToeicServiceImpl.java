package com.englishweb.h2t_backside.service.test.impl;

import com.englishweb.h2t_backside.dto.filter.SubmitToeicFilterDTO;
import com.englishweb.h2t_backside.dto.test.*;
import com.englishweb.h2t_backside.exception.CreateResourceException;
import com.englishweb.h2t_backside.exception.ErrorApiCodeContent;
import com.englishweb.h2t_backside.exception.ResourceNotFoundException;
import com.englishweb.h2t_backside.exception.UpdateResourceException;
import com.englishweb.h2t_backside.mapper.test.SubmitToeicMapper;
import com.englishweb.h2t_backside.model.enummodel.SeverityEnum;
import com.englishweb.h2t_backside.model.test.SubmitTest;
import com.englishweb.h2t_backside.model.test.SubmitToeic;
import com.englishweb.h2t_backside.repository.test.SubmitToeicRepository;
import com.englishweb.h2t_backside.service.feature.DiscordNotifier;
import com.englishweb.h2t_backside.service.feature.impl.BaseServiceImpl;
import com.englishweb.h2t_backside.service.test.SubmitToeicAnswerService;
import com.englishweb.h2t_backside.service.test.SubmitToeicPart1Service;
import com.englishweb.h2t_backside.service.test.SubmitToeicPart2Service;
import com.englishweb.h2t_backside.service.test.SubmitToeicService;
import com.englishweb.h2t_backside.utils.BaseFilterSpecification;
import com.englishweb.h2t_backside.utils.ParseData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
@Slf4j
public class SubmitToeicServiceImpl extends BaseServiceImpl<SubmitToeicDTO, SubmitToeic, SubmitToeicRepository> implements SubmitToeicService {
    private final SubmitToeicMapper mapper;
    private final SubmitToeicAnswerService submitToeicAnswerService;
    private final SubmitToeicPart2Service submitToeicPart2Service;
    private final SubmitToeicPart1Service submitToeicPart1Service;

    public SubmitToeicServiceImpl(SubmitToeicRepository repository, DiscordNotifier discordNotifier, SubmitToeicMapper mapper,@Lazy SubmitToeicAnswerService submitToeicAnswerService,@Lazy SubmitToeicPart2Service submitToeicPart2Service,@Lazy SubmitToeicPart1Service submitToeicPart1Service) {
        super(repository, discordNotifier);
        this.mapper = mapper;
        this.submitToeicAnswerService = submitToeicAnswerService;
        this.submitToeicPart2Service = submitToeicPart2Service;
        this.submitToeicPart1Service = submitToeicPart1Service;
    }

    @Override
    protected void findByIdError(Long id) {
        String errorMessage = String.format("SubmitToeic with ID '%d' not found.", id);
        log.warn(errorMessage);
        throw new ResourceNotFoundException(id, errorMessage, SeverityEnum.LOW);
    }

    @Override
    public List<SubmitToeicDTO> findByToeicId(Long toeicId) {
        return repository.findByToeicId(toeicId)
                .stream()
                .map(this::convertToDTO)
                .toList();
    }

    @Override
    protected void createError(SubmitToeicDTO dto, Exception ex) {
        log.error("Error creating submit toeic: {}", ex.getMessage());
        String errorMessage = "Unexpected error creating submit toeic: " + ex.getMessage();
        String errorCode = ErrorApiCodeContent.LESSON_CREATED_FAIL;
        throw new CreateResourceException(dto, errorMessage, errorCode, HttpStatus.INTERNAL_SERVER_ERROR, SeverityEnum.HIGH);
    }

    @Override
    protected void updateError(SubmitToeicDTO dto, Long id, Exception ex) {
        log.error("Error updating submit toeic: {}", ex.getMessage());
        String errorMessage = "Unexpected error updating submit toeic: " + ex.getMessage();
        String errorCode = ErrorApiCodeContent.LESSON_UPDATED_FAIL;
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        if (!this.isExist(id)) {
            errorMessage = String.format("SubmitToeic with ID '%d' not found.", id);
            status = HttpStatus.NOT_FOUND;
        }

        throw new UpdateResourceException(dto, errorMessage, errorCode, status, SeverityEnum.LOW);
    }
    @Override
    public boolean delete(Long id) {
        SubmitToeicDTO dto = super.findById(id);
        if (dto == null) return false;

        List<SubmitToeicAnswerDTO> answerList = submitToeicAnswerService.findBySubmitToeicId(id);
        if (answerList != null) {
            for (SubmitToeicAnswerDTO answer : answerList) {
                submitToeicAnswerService.delete(answer.getId());
            }
        }

        List<SubmitToeicPart1DTO> part1List = submitToeicPart1Service.findBySubmitToeicId(id);
        if (part1List != null) {
            for (SubmitToeicPart1DTO part1 : part1List) {
                submitToeicPart1Service.delete(part1.getId());
            }
        }

        List<SubmitToeicPart2DTO> part2List = submitToeicPart2Service.findBySubmitToeicId(id);
        if (part2List != null) {
            for (SubmitToeicPart2DTO part2 : part2List) {
                submitToeicPart2Service.delete(part2.getId());
            }
        }

        // Cuối cùng xoá chính bài submit Toeic này
        return super.delete(id);
    }


    @Override
    protected void patchEntityFromDTO(SubmitToeicDTO dto, SubmitToeic entity) {
        mapper.patchEntityFromDTO(dto, entity);
    }
    @Override
    public SubmitToeicDTO findByToeicIdAndUserIdAndStatusFalse(Long toeicId, Long userId) {
        SubmitToeic submitToeic = repository.findByToeicIdAndUserIdAndStatusFalse(toeicId, userId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        toeicId,
                        String.format("SubmitToeic with test ID '%d', userId '%d' and status=false not found.", toeicId, userId),
                        SeverityEnum.LOW
                ));
        return convertToDTO(submitToeic);
    }

    @Override
    protected SubmitToeic convertToEntity(SubmitToeicDTO dto) {
        return mapper.convertToEntity(dto);
    }

    @Override
    protected SubmitToeicDTO convertToDTO(SubmitToeic entity) {
        return mapper.convertToDTO(entity);
    }
    @Override
    public Double getScoreOfLastTestByUserAndToeic(Long userId,Long toeicId) {
        List<SubmitToeic> submits = repository.findByUserIdAndToeicIdAndStatusTrue(userId,toeicId);

        return Double.valueOf(submits.stream()
                .max(Comparator.comparing(SubmitToeic::getCreatedAt))
                .map(SubmitToeic::getScore)
                .orElse((int) 0));
    }

    @Override
    public Page<SubmitToeicDTO> searchWithFilters(int page, int size, String sortFields, SubmitToeicFilterDTO filter, Long userId) {
        Pageable pageable = ParseData.parsePageArgs(page, size, sortFields, SubmitToeic.class);

        Specification<SubmitToeic> spec = BaseFilterSpecification.applyBaseFilters(filter);

        if (userId != null) {
            spec = spec.and((root, query, cb) ->
                    cb.equal(root.get("user").get("id"), userId));
        }

        if (filter.getTitle() != null && !filter.getTitle().isEmpty()) {
            spec = spec.and((root, query, cb) ->
                    cb.like(cb.lower(root.join("toeic").get("title")), "%" + filter.getTitle().toLowerCase() + "%"));
        }

        return repository.findAll(spec, pageable)
                .map(mapper::convertToDTO);
    }

    @Override
    public int countSubmitByUserId(Long userId) {
        return repository.countByUserIdAndStatusTrue(userId);
    }

    @Override
    public double totalScoreByUserId(Long userId) {
        return repository.sumScoreByUserIdAndStatusTrue(userId);
    }
}
