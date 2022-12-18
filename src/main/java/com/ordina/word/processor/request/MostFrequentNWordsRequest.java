package com.ordina.word.processor.request;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class MostFrequentNWordsRequest {
    @NotEmpty(message = "Text cannot be empty.")
    private String text;
   /* @Positive(message = "Value of number of words should be positive.")
    private int numberOfWords;*/
}
