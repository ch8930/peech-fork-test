package com.twentythree.peech.stt.dto.response;

import java.time.LocalTime;
import java.util.List;

// 최종으로 들어갈 STT 결과를 담는 DTO
public record STTResultResponseDto(
        LocalTime realTimePerScript,
        List<Paragraph> paragraphs
) {
    public record Paragraph(
            int paragraphId,
            List<Sentence> sentences,
            LocalTime realTimePerParagraph
    ){
        public record Sentence(
                int sentenceId,
                String sentenceContent
        ){}
    }
}
