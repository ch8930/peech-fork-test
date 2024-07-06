package com.twentythree.peech.stt.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.twentythree.peech.script.respository.ScriptRepository;
import com.twentythree.peech.script.respository.SentenceRepository;
import com.twentythree.peech.stt.dto.request.STTRequestDto;
import com.twentythree.peech.stt.dto.response.ClovaResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class STTService {

    @Value("${clova.speech-api.url}")
    private String clovaSpeechApiUrl;

    @Value("${clova.speech-api.secret}")
    private String secretKey;

    @Value("${clova.speech-api.callback}")
    private String callbackUrl;

    private final WebClient.Builder webClientBuilder;

    private final ScriptRepository scriptRepository;

    private final SentenceRepository sentenceRepository;

    public Mono<ClovaResponseDto> requestClovaSpeechApi(STTRequestDto request, Long themeId) {

        //client에서 받은 파일을 임시파일로 변환
        File tempFile;
        try {
            tempFile = File.createTempFile("temp", request.media().getOriginalFilename());
            request.media().transferTo(tempFile);
        } catch (IOException e) {
            throw new IllegalArgumentException("파일 변환 중 오류가 발생했습니다.");
        }

        try {
            // HTTP 헤더 설정
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.set("Accept", "application/json;UTF-8");
            httpHeaders.set("X-CLOVASPEECH-API-KEY", secretKey);
            httpHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);

            // HTTP body에 보낼 정보
            Map<String, String> params = new HashMap<>();
            params.put("language", "ko-KR");
            params.put("completion", "sync");
            params.put("callback", callbackUrl);
            params.put("resultToObs", "false");

            ObjectMapper objectMapper = new ObjectMapper();
            String paramsJson = objectMapper.writeValueAsString(params);
            HttpHeaders jsonHeader = new HttpHeaders();
            jsonHeader.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> jsonEntity = new HttpEntity<>(paramsJson, jsonHeader);

            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
            body.add("media", new FileSystemResource(tempFile));
            body.add("params", jsonEntity);

            System.out.println("body = " + body);

            // Weblcient를 이용한 비동기 통신
            WebClient webClient = webClientBuilder.baseUrl(clovaSpeechApiUrl).build();
            return (Mono<ClovaResponseDto>) webClient.post()
                    .uri("/recognizer/upload")
                    .headers(h -> h.addAll(httpHeaders))
                    .bodyValue(body)
                    .retrieve() // 응답을 받아오는 메소드
                    .bodyToMono(ClovaResponseDto.class);


        } catch (Exception e) {
            throw new IllegalArgumentException("Clova Speech API 호출 중 오류가 발생했습니다.");
        }

    }


    public void saveToDatabase(ClovaResponseDto response) {

    }

    public void saveToRedis(ClovaResponseDto response) {

    }

    public ClovaResponseDto showResult(ClovaResponseDto response) {
        return response;
    }

}
