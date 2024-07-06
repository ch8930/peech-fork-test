package com.twentythree.peech.stt.dto.response;

// clova speech api를 거친 결과를 문장 단위로 수정해주는 DTO
public record STTSentenceDto(
        int sentenceId,
        String sentenceContent,
        int timestamp
){}
