package com.ordina.word.processor.request;

import lombok.Data;

import javax.validation.constraints.NotEmpty;


@Data
public class HighestFrequencyRequest {
    @NotEmpty(message = "Text cannot be empty.")
    private String text;
}
