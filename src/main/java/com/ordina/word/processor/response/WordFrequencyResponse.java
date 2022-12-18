package com.ordina.word.processor.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class WordFrequencyResponse {
    private int wordFrequency;
}
