package com.twentythree.peech.script.stt.dto;

import com.twentythree.peech.script.domain.ScriptEntity;

import java.time.LocalTime;

public record SaveSTTScriptDTO(
    ScriptEntity script,
    LocalTime totalRealTime
) { }
