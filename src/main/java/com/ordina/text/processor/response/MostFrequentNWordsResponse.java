package com.ordina.text.processor.response;

import com.ordina.text.processor.service.WordFrequency;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MostFrequentNWordsResponse {
    private WordFrequency[] wordFrequencies;
}
