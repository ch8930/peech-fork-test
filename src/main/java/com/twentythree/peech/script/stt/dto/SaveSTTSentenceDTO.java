package com.twentythree.peech.script.stt.dto;

import com.twentythree.peech.script.domain.SentenceEntity;

import java.time.LocalTime;

// mysql에 저장할 STT 결과를 담는 문장 테이블 DTO
public record SaveSTTSentenceDTO(
        SentenceEntity sentence,
        LocalTime realTime
) {
}
