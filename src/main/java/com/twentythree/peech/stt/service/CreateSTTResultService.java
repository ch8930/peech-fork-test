package com.twentythree.peech.stt.service;

import com.twentythree.peech.stt.dto.response.STTSentenceDto;
import com.twentythree.peech.stt.dto.response.ClovaResponseDto;
import com.twentythree.peech.stt.dto.response.ParagraphDivideResponseDto;
import com.twentythree.peech.stt.dto.response.STTResultResponseDto;
import com.twentythree.peech.stt.utils.RealTimeUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CreateSTTResultService {

    public Mono<STTResultResponseDto> createSTTResultResponseDto(ClovaResponseDto clovaResponseDto, List<STTSentenceDto> sttSentenceDtos, ParagraphDivideResponseDto paragraphDivideResponseDto) {

        int timestamp;
        int paragraphId = 0;

        // 전체 대본 소요 시간
        LocalTime totalRealTime = clovaResponseDto.getTotalRealTime();

        // sttSentenceDto 리스트에 있는 타임 스탬프들을 가져옴
        List<Integer> sentencesRealTime = sttSentenceDtos
                .stream().map(STTSentenceDto::timestamp)
                .toList();

        // paragraphDevideResponseDto에서 문단별로 나눈 index를 가져옴
        List<List<Integer>> paragraphNumber = paragraphDivideResponseDto.getResult().getSpan();

        List<List<String>> paragraphs = paragraphDivideResponseDto.getResult().getParagraphs();
        
        List<String> paragraghContent = mergeParagraphSentences(paragraphs);

        // 문단별 측정된 시간 저장
        List<LocalTime> paragraphRealTime = new ArrayList<>();



        List<STTResultResponseDto.Paragraph> paragraphList = new ArrayList<>();

        for (List<Integer> paragraph : paragraphNumber) {
            List<STTResultResponseDto.Paragraph.Sentence> sentenceList = new ArrayList<>();
            timestamp = 0;
            for (Integer index : paragraph) {
                timestamp += sentencesRealTime.get(index);
                sentenceList.add(new STTResultResponseDto.Paragraph.Sentence(index, sttSentenceDtos.get(index).sentenceContent()));
            }
            paragraphList.add(new STTResultResponseDto.Paragraph(paragraphId++, sentenceList, RealTimeUtils.convertMsToTimeFormat(timestamp)));
        }

        return Mono.just(new STTResultResponseDto(totalRealTime, paragraphList));
    }

    // 문단에 포함된 문장들을 붙여서 하나의 String으로 바꿔줌
    private static List<String> mergeParagraphSentences(List < List < String >> paragraphs) {
        return paragraphs.stream()
                .map(sentence -> String.join(" ", sentence))
                .collect(Collectors.toList());
    }
}



