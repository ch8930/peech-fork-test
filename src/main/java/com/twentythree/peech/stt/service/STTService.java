package com.twentythree.peech.stt.service;

import com.google.gson.Gson;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class STTService {

    @Value("${CLOVA_SPEECH_URL}")
    private String apiUrl;

    @Value("${CLOVA_SPEECH_SECRET_KEY}")
    private String secretKey;

    private final RestTemplate restTemplate;


    public ResponseEntity<String> speechToText(MultipartFile media) {
        try {
            //client에서 받은 파일을 임시파일로 변환
            File tempFile = File.createTempFile("temp", media.getOriginalFilename());
            media.transferTo(tempFile);

            System.out.println("tempFile = " + tempFile);
            // HTTP 헤더 설정
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.set("X-CLOVASPEECH-API-KEY", secretKey);
            httpHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);

            // HTTP body에 보낼 정보
            Map<String, String> params = new HashMap<>();
            params.put("language", "ko-KR");
            params.put("completion", "sync");

            // Map을 JSON으로 변환
            Gson gson = new Gson();
            String paramToJson = gson.toJson(params);

            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
            body.add("media", new FileSystemResource(tempFile));
            body.add("params", paramToJson);

            HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, httpHeaders);

            System.out.println("requestEntity = " + requestEntity);

            ResponseEntity<String> responseEntity = restTemplate.postForEntity(apiUrl + "/recognizer/upload", requestEntity, String.class);

            return responseEntity;
        } catch (IOException e) {
            throw new IllegalArgumentException("파일 변환 중 오류가 발생했습니다.");
        }
    }
}
