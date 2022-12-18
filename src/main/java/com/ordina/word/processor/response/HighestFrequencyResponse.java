package com.ordina.word.processor.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class HighestFrequencyResponse {
    private int highestFrequency;
}
