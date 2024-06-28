package com.twentythree.peech.stt.controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.twentythree.peech.stt.dto.request.STTRequestDto;
import com.twentythree.peech.stt.dto.response.ClovaResponseDto;
import com.twentythree.peech.stt.service.STTService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/stt")
public class STTController implements SwaggerSTTController{

    private final STTService sttService;

    @Operation(summary = "stt 결과 반환",
            description = "음성녹음를 STTRequestDTO에 담아 요청하면 생성된 STT 결과물을 ClovaResponseDTO에 담아 응답한다.")
    @Override
    @PostMapping(value ="/recognize", consumes ="multipart/form-data")
    public ClovaResponseDto sttResult(@ModelAttribute STTRequestDto request) {

        ResponseEntity<String> result = sttService.speechToText(request.getMedia());
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(result.getBody(), ClovaResponseDto.class);
        } catch (IOException e) {
            throw new IllegalArgumentException("Response parsing error");
        }
    }
}

