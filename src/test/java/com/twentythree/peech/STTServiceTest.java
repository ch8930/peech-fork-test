package com.twentythree.peech;

import com.twentythree.peech.stt.dto.request.STTRequestDto;
import com.twentythree.peech.stt.dto.response.ClovaResponseDto;
import com.twentythree.peech.stt.service.STTService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class STTServiceTest {

    @Autowired
    private STTService sttService;

    @Test
    public void testSpeechToText() throws IOException {

        Path path = Paths.get("/Users/choijihyeon/Music/Music/Media.localized/Music/Unknown Artist/Unknown Album/감정은 습관이다- 1분 오디오북#책읽어주는여자 (320).mp3");
        String name = "test.mp3";
        String originalFileName = "test.mp3";
        String contentType = "audio/mpeg";
        byte[] content = Files.readAllBytes(path);
        MultipartFile file = new MockMultipartFile(name, originalFileName, contentType, content);
        Long themeId = 1L;

        STTRequestDto request = new STTRequestDto(themeId, file);

        Mono<ClovaResponseDto> response = sttService.requestClovaSpeechApi(request, themeId);
        response.subscribe(clovaResponseDto -> {
            System.out.println("clovaResponseDto = " + clovaResponseDto);
        });
        assertNotNull(response);
    }
}
