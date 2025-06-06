package com.englishweb.h2t_backside.mapper;

import com.englishweb.h2t_backside.dto.feature.RouteNodeDTO;
import com.englishweb.h2t_backside.dto.interfacedto.LessonDTO;
import com.englishweb.h2t_backside.dto.lesson.*;
import com.englishweb.h2t_backside.dto.test.TestDTO;
import com.englishweb.h2t_backside.model.features.Route;
import com.englishweb.h2t_backside.model.features.RouteNode;
import com.englishweb.h2t_backside.service.lesson.*;
import com.englishweb.h2t_backside.service.test.TestService;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        builder = @Builder(disableBuilder = true)
)
public abstract class RouteNodeMapper {
    @Autowired
    protected TopicService topicService;

    @Autowired
    protected GrammarService grammarService;

    @Autowired
    protected ReadingService readingService;

    @Autowired
    protected WritingService writingService;

    @Autowired
    protected SpeakingService speakingService;

    @Autowired
    protected ListeningService listeningService;

    @Autowired
    protected TestService testService;

    // Existing mapping methods remain the same
    @Mapping(target = "id", source = "dto.id")
    @Mapping(target = "status", source = "dto.status", defaultValue = "true")
    @Mapping(target = "nodeId", source = "dto.nodeId")
    @Mapping(target = "type", source = "dto.type")
    @Mapping(target = "serial", source = "dto.serial")
    @Mapping(target = "route", source = "dto.routeId", qualifiedByName = "mapRouteIdToRoute")
    public abstract RouteNode convertToEntity(RouteNodeDTO dto);

    @Mapping(target = "id", source = "entity.id")
    @Mapping(target = "status", source = "entity.status")
    @Mapping(target = "createdAt", source = "entity.createdAt")
    @Mapping(target = "updatedAt", source = "entity.updatedAt")
    @Mapping(target = "nodeId", source = "entity.nodeId")
    @Mapping(target = "type", source = "entity.type")
    @Mapping(target = "serial", source = "entity.serial")
    @Mapping(target = "routeId", source = "entity.route.id")
    public abstract RouteNodeDTO convertToDTO(RouteNode entity);

    @Mapping(target = "id", source = "dto.id")
    @Mapping(target = "status", source = "dto.status")
    @Mapping(target = "nodeId", source = "dto.nodeId")
    @Mapping(target = "type", source = "dto.type")
    @Mapping(target = "serial", source = "dto.serial")
    @Mapping(target = "route", source = "dto.routeId", qualifiedByName = "mapRouteIdToRoute")
    public abstract void patchEntityFromDTO(RouteNodeDTO dto, @MappingTarget RouteNode entity);

    @Named("mapRouteIdToRoute")
    protected Route mapRouteIdToRoute(Long routeId) {
        if (routeId == null) return null;
        Route route = new Route();
        route.setId(routeId);
        return route;
    }

    // New method to enrich RouteNodeDTO with additional details based on node type
    @AfterMapping
    protected void enrichRouteNodeDTO(@MappingTarget RouteNodeDTO routeNodeDTO) {
        if (routeNodeDTO == null || routeNodeDTO.getNodeId() == null || routeNodeDTO.getType() == null) {
            return;
        }

        switch (routeNodeDTO.getType()) {
            case VOCABULARY:
                processTopic(routeNodeDTO);
                break;
            case GRAMMAR:
                processGrammar(routeNodeDTO);
                break;
            case READING:
                processReading(routeNodeDTO);
                break;
            case WRITING:
                processWriting(routeNodeDTO);
                break;
            case SPEAKING:
                processSpeaking(routeNodeDTO);
                break;
            case LISTENING:
                processListening(routeNodeDTO);
                break;
            case READING_TEST:
            case WRITING_TEST:
            case SPEAKING_TEST:
            case LISTENING_TEST:
            case MIXING_TEST:
                processTest(routeNodeDTO);
                break;
        }
    }

    private void processTopic(RouteNodeDTO node) {
        TopicDTO dto = topicService.findById(node.getNodeId());
        setCommonFields(node, dto);
    }

    private void processGrammar(RouteNodeDTO node) {
        GrammarDTO dto = grammarService.findById(node.getNodeId());
        setCommonFields(node, dto);
    }

    private void processReading(RouteNodeDTO node) {
        ReadingDTO dto = readingService.findById(node.getNodeId());
        setCommonFields(node, dto);
    }

    private void processWriting(RouteNodeDTO node) {
        WritingDTO dto = writingService.findById(node.getNodeId());
        setCommonFields(node, dto);
    }

    private void processSpeaking(RouteNodeDTO node) {
        SpeakingDTO dto = speakingService.findById(node.getNodeId());
        setCommonFields(node, dto);
    }

    private void processListening(RouteNodeDTO node) {
        ListeningDTO dto = listeningService.findById(node.getNodeId());
        setCommonFields(node, dto);
    }

    private void processTest(RouteNodeDTO node) {
        TestDTO dto = testService.findById(node.getNodeId());
        node.setTitle(dto.getTitle());
        node.setDescription(dto.getDescription());
        node.setImage(null);
        node.setStatus(dto.getStatus());
    }

    private void setCommonFields(RouteNodeDTO node, LessonDTO dto) {
        node.setTitle(dto.getTitle());
        node.setDescription(dto.getDescription());
        node.setImage(dto.getImage());
        node.setStatus(dto.getStatus());
    }
}