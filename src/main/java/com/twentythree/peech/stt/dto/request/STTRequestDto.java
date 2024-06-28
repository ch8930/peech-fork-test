package com.twentythree.peech.stt.dto.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
public class STTRequestDto {
    private MultipartFile media;

    @JsonCreator
    public STTRequestDto(MultipartFile media) {
        this.media = media;
    }
}
