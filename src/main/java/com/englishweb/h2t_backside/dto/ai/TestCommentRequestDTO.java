package com.englishweb.h2t_backside.dto.ai;

import lombok.Data;
import java.util.List;

@Data
public class TestCommentRequestDTO {
    private List<ChoiceQuestion> vocabulary;
    private List<ChoiceQuestion> grammar;
    private List<ReadingSection> reading;
    private List<ListeningSection> listening;
    private List<SpeakingQuestion> speaking;
    private List<WritingQuestion> writing;

    @Data
    public static class ChoiceQuestion {
        private String question;
        private List<String> choices;
        private String userAnswer;
    }

    @Data
    public static class ReadingSection {
        private String passage;
        private List<ChoiceQuestion> questions;
    }

    @Data
    public static class ListeningSection {
        private String transcript;
        private List<ChoiceQuestion> questions;
    }

    @Data
    public static class SpeakingQuestion {
        private String question;
        private String transcript;
    }

    @Data
    public static class WritingQuestion {
        private String topic;
        private String userAnswer;
    }
}
