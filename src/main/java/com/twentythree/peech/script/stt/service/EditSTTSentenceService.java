package com.twentythree.peech.script.stt.service;

import com.twentythree.peech.script.stt.dto.response.STTSentenceDto;
import com.twentythree.peech.script.stt.dto.response.ClovaResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EditSTTSentenceService {

    public List<STTSentenceDto> processClovaResponse(ClovaResponseDto clovaResponseDto){

        List<STTSentenceDto> sttSentenceDtoList = new ArrayList<>();

        StringBuilder currentSentence = new StringBuilder();
        int calculatedTime = 0;
        int startTimestamp = 0;
        int endTimestamp = 0;
        int sentenceId = 0;

        // STT가 임의로 나눈 문장 가져오기
        for(ClovaResponseDto.Segment segment : clovaResponseDto.getSentences()) {
            List<List<Object>> words = segment.getWords();

            // 단어 단위로 검사 시작
            for (List<Object> word : words){
                String text = (String) word.get(2);

                if(currentSentence.isEmpty()) {
                    startTimestamp = (int) word.get(0);
                }

                if (currentSentence.length() > 0) {
                    // 첫 단어면 공백 필요 없음
                    currentSentence.append(" ");
                }
                currentSentence.append(text);


                if(text.contains(".")) {
                    endTimestamp = (int) word.get(1);
                    calculatedTime = endTimestamp - startTimestamp;
                    sttSentenceDtoList.add(new STTSentenceDto(sentenceId++, currentSentence.toString(), calculatedTime));
                    currentSentence.setLength(0);
                    calculatedTime = 0;
                }
            }
        }
    return sttSentenceDtoList;
    }

}
