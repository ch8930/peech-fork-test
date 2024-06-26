package com.twentythree.peech.stt.controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.twentythree.peech.stt.dto.STTRequestDto;
import com.twentythree.peech.stt.dto.response.ClovaResponseDto;
import com.twentythree.peech.stt.service.STTService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/stt")
public class STTController {

    private final STTService sttService;

    @PostMapping("/recognize")
    public ResponseEntity<ClovaResponseDto> recognize(@RequestParam(name = "media") MultipartFile media) throws IOException {

        ResponseEntity<String> result = sttService.speechToText(media);
        System.out.println("result = " + result.getBody());

        ObjectMapper objectMapper = new ObjectMapper();
        ClovaResponseDto clovaResponseDto = objectMapper.readValue(result.getBody(), ClovaResponseDto.class);

        return ResponseEntity.ok(clovaResponseDto);
    }
}

