package com.ordina.word.processor.response;

import com.ordina.word.processor.service.WordFrequency;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MostFrequentNWordsResponse {
    private WordFrequency[] wordFrequencies;
}
