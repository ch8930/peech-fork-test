package com.twentythree.peech.stt.dto.response;
import com.fasterxml.jackson.annotation.*;
import com.twentythree.peech.utils.RealTimeUtils;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.List;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true) // response에서 필요하지 않은 것은 무시
@NoArgsConstructor
public class ClovaResponseDto {

    @JsonProperty("sentences")
    @JsonAlias("segments")
    private List<Segment> sentences;

    @JsonProperty("fulltext")
    @JsonAlias("text")
    private String fullText;

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Segment {

        @JsonProperty("start")
        private int start;

        @JsonProperty("end")
        private int end;

        @JsonProperty("content")
        @JsonAlias("text")
        private String content;

        @JsonProperty("startTime")
        public LocalTime getStartLocalTime() {
            return RealTimeUtils.convertMsToTimeFormat(this.start);
        }
        @JsonProperty("endTime")
        public LocalTime getEndLocalTime() {
            return RealTimeUtils.convertMsToTimeFormat(this.end);
        }

        @JsonProperty("realTime")
        public LocalTime getRealTime() {
            return RealTimeUtils.getSentenceRealTime(start, end);
        }
    }

    @JsonProperty("totalRealTime")
    public LocalTime getLastSentenceEndTime() {
        if(sentences == null || sentences.isEmpty()){
            throw new IllegalArgumentException("sentences is empty");
        }
        int last = sentences.size() - 1;

        return sentences.get(last).getEndLocalTime();
    }

    public ClovaResponseDto(List<Segment> sentences, String fullText) {
        this.sentences = sentences;
        this.fullText = fullText;

    }
}
