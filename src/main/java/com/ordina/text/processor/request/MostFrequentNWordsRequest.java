package com.ordina.text.processor.request;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class MostFrequentNWordsRequest {
    @NotEmpty(message = "Text cannot be empty.")
    private String text;

}
