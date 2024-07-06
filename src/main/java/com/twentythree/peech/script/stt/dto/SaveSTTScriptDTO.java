package com.twentythree.peech.script.stt.dto;

import com.twentythree.peech.script.domain.ScriptEntity;

import java.time.LocalTime;

// mysql에 저장할 STT 결과를 담는 스크립트 테이블 DTO
public record SaveSTTScriptDTO(
    ScriptEntity script,
    LocalTime totalRealTime
) { }
