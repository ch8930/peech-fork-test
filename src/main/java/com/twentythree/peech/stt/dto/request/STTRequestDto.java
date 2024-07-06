package com.twentythree.peech.stt.dto.request;

import org.springframework.web.multipart.MultipartFile;

public record STTRequestDto(
        Long themeId,
        MultipartFile media
) { }
