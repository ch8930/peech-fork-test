package com.twentythree.peech;

import com.twentythree.peech.stt.service.STTService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.multipart.MultipartFile;

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


        Path path = Paths.get("C:\\Users\\user\\Downloads\\speech-test.wav");
        String name = "sample.wav";
        String originalFileName = "sample.wav";
        String contentType = "audio/wav";
        byte[] content = null;

        try {
            content = Files.readAllBytes(path);
        }catch (final IOException e){
        }

        MultipartFile result = new MockMultipartFile(name, originalFileName, contentType, content);
        ResponseEntity<String> response = sttService.speechToText(result);

        System.out.println("response = " + response);

        assertNotNull(response);
    }
}
