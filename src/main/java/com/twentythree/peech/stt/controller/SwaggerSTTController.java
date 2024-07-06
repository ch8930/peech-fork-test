package com.twentythree.peech.stt.controller;

import com.twentythree.peech.stt.dto.request.STTRequestDto;
import com.twentythree.peech.stt.dto.response.ClovaResponseDto;
import com.twentythree.peech.stt.dto.response.STTResultResponseDto;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import reactor.core.publisher.Mono;

public interface SwaggerSTTController {
    @ApiResponse(responseCode = "201", description = "성공", content = {@Content(schema = @Schema(implementation = ClovaResponseDto.class), mediaType = "application/json")})
    @ApiResponse(responseCode = "400", description = "실패", content = {@Content(schema = @Schema(implementation = Error.class), mediaType = "application/json")})
    Mono<STTResultResponseDto> sttResult(@ModelAttribute STTRequestDto request, @PathVariable Long themeId);
}

