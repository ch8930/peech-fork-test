package com.twentythree.peech.stt.controller;
import com.twentythree.peech.stt.dto.response.STTSentenceDto;
import com.twentythree.peech.stt.dto.request.STTRequestDto;
import com.twentythree.peech.stt.dto.response.ClovaResponseDto;
import com.twentythree.peech.stt.dto.response.STTResultResponseDto;
import com.twentythree.peech.stt.service.CreateParagraphService;
import com.twentythree.peech.stt.service.CreateSTTResultService;
import com.twentythree.peech.stt.service.EditSTTSentenceService;
import com.twentythree.peech.stt.service.STTService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class STTController implements SwaggerSTTController{

    private final STTService sttService;
    private final CreateParagraphService createParagraphService;
    private final CreateSTTResultService createSTTResultService;
    private final EditSTTSentenceService editSTTSentenceService;

    @Value("${CLOVA_SPEECH_CALLBACK_URL}")
    private String callbackUrl;

    // 사용자가 음성 녹음을 요청하면 -> 문단별로 나눠서 전체 시간과 문단별 시간을 반환
    @Operation(summary = "stt 결과 반환",
            description = "음성녹음를 STTRequestDTO에 담아 요청하면 생성된 STT 결과물을 ClovaResponseDTO에 담아 응답한다.")
    @PostMapping(value ="/api/v1/themes/{themeId}/speech/script", consumes ="multipart/form-data")
    public Mono<STTResultResponseDto> sttResult(@ModelAttribute STTRequestDto request, @PathVariable("themeId") Long themeId) {
        return sttService.requestClovaSpeechApi(request, themeId)
                .flatMap(clovaResponseDto -> {
                    //  반환 결과를 하나의 문자열 처리
                    List<STTSentenceDto> sttSentenceDtos = editSTTSentenceService.processClovaResponse(clovaResponseDto);

                    String totalText = sttSentenceDtos
                            .stream().map(STTSentenceDto::sentenceContent)
                            .collect(Collectors.joining("\\n"));

                    // 문단 나누기 api 호출 & Mono로 변환 -> 클라이언트에게 보낼 결과 반환
                    return Mono.fromFuture(createParagraphService.requestClovaParagraphApi(totalText)
                            .thenApply(paragraphDevideResponseDto ->
                                    createSTTResultService.createSTTResultResponseDto(clovaResponseDto ,sttSentenceDtos, paragraphDevideResponseDto).block()
                            )
                    );
                });
    }

    // Callback URL로 STT 결과를 받아온 후 DB에 저장
    @PostMapping(value = "/api/v1/themes/{themeId}/speech/script/result", consumes = "application/json")
    public ResponseEntity<String> sttResultCallback(@RequestBody ClovaResponseDto clovaResponseDto, @PathVariable Long themeId) {
    //
        return ResponseEntity.status(HttpStatus.CREATED).body("Entity successfully saved");
    }

}



