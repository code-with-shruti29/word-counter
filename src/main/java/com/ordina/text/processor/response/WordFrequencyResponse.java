package com.ordina.text.processor.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class WordFrequencyResponse {
    private int wordFrequency;
}
