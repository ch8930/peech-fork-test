package com.twentythree.peech.stt.dto.response;
import com.fasterxml.jackson.annotation.*;
import com.twentythree.peech.stt.utils.RealTimeUtils;
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

    @JsonProperty("totalRealTime")
    public LocalTime getTotalRealTime() {
        if(sentences == null || sentences.isEmpty()){
            throw new IllegalArgumentException("단어가 존재하지 않습니다.");
        }
        int last = sentences.size()-1;

        // 마지막 문장 가져오기
        Segment lastSegment = getSentences().get(last);
        // 마지막 문장의 마지막 단어 객체가져오기
        List<Object> lastWord = lastSegment.words.get(lastSegment.words.size()-1);

        return RealTimeUtils.convertMsToTimeFormat((int) lastWord.get(1));
    }

    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Segment {

        @JsonProperty("words")
        private List<List<Object>> words;


    }

    public ClovaResponseDto(List<Segment> sentences, String fullText) {
        this.sentences = sentences;
        this.fullText = fullText;
    }
}
