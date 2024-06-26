package com.twentythree.peech.stt.dto.response;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true) // 원하는 json만 객체로 변환하기 위해 사용하는 어노테이션
@NoArgsConstructor
public class ClovaResponseDto {

    @JsonProperty("segments")
    private List<Segment> segments;
    @JsonProperty("text")
    private String text;

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Segment {
        @JsonProperty("start")
        private int start;
        @JsonProperty("end")
        private int end;
        @JsonProperty("text")
        private String text;

        // 문장마다 걸린 시간을 계산하는 메소드
        public int getSenteceRealTime() {
            int realTime = end - start;
            return convertMsToTimeFormat(realTime);
        }

        private String convertMsToTimeFormat(int realTime) {

        }
    }





}
