package com.ordina.text.processor.request;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class WordFrequencyRequest {
    @NotEmpty(message = "Text cannot be empty.")
    private String text;
    @NotEmpty(message = "Word cannot be empty.")
    private String word;
}
