package com.ordina.word.processor.controller;

import com.ordina.word.processor.request.HighestFrequencyRequest;
import com.ordina.word.processor.request.MostFrequentNWordsRequest;
import com.ordina.word.processor.request.WordFrequencyRequest;
import com.ordina.word.processor.response.HighestFrequencyResponse;
import com.ordina.word.processor.response.MostFrequentNWordsResponse;
import com.ordina.word.processor.response.WordFrequencyResponse;
import com.ordina.word.processor.service.WordFrequency;
import com.ordina.word.processor.service.WordFrequencyAnalyzer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import javax.validation.Valid;

@RestController
@Slf4j
@RequestMapping("/api/text-processor/words")
public class TextProcessingController {

    @Autowired
    private WordFrequencyAnalyzer wordFrequencyAnalyzer;

    @PostMapping(value = "/word/highest-frequency")
    public ResponseEntity<HighestFrequencyResponse> calculateHighestFrequency(@Valid @RequestBody HighestFrequencyRequest request)
    {
        int highestFrequency=wordFrequencyAnalyzer.calculateHighestFrequency(request.getText());
        HighestFrequencyResponse highestFrequencyResponse = HighestFrequencyResponse.builder().highestFrequency(highestFrequency).build();
        return new ResponseEntity<>(highestFrequencyResponse,HttpStatus.OK);
    }

    @PostMapping(value = "/word/frequency")
    public ResponseEntity<WordFrequencyResponse> calculateFrequencyForWord(@Valid @RequestBody WordFrequencyRequest request)
    {
        int wordFrequency=wordFrequencyAnalyzer.calculateFrequencyForWord(request.getText(),request.getWord());
        WordFrequencyResponse wordFrequencyResponse = WordFrequencyResponse.builder().wordFrequency(wordFrequency).build();
        return new ResponseEntity<>(wordFrequencyResponse,HttpStatus.OK);
    }

    @PostMapping(value = "/most-frequent-n-words/{n}")
    public ResponseEntity<MostFrequentNWordsResponse> calculateMostFrequentNWords(@Valid @RequestBody MostFrequentNWordsRequest request, @PathVariable int n)
    {
        WordFrequency[] wordFrequencies= wordFrequencyAnalyzer.calculateMostFrequentNWords(request.getText(),n);
        MostFrequentNWordsResponse mostFrequentNWordsResponse = MostFrequentNWordsResponse.builder().wordFrequencies(wordFrequencies).build();
        return new ResponseEntity<>(mostFrequentNWordsResponse,HttpStatus.OK);
    }

}
