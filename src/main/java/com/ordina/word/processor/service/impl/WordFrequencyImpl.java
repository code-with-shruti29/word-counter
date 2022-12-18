package com.ordina.word.processor.service.impl;

import com.ordina.word.processor.service.WordFrequency;
import lombok.EqualsAndHashCode;
import org.springframework.stereotype.Service;

@Service
@EqualsAndHashCode
public class WordFrequencyImpl implements WordFrequency {

    private String word;
    private int frequency;

    @Override
    public String getWord() {
        return word;
    }

    @Override
    public int getFrequency() {
        return frequency;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }
    public WordFrequencyImpl() {
    }

    public WordFrequencyImpl(String word, int frequency) {
        this.word = word;
        this.frequency = frequency;
    }


}
