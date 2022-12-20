package com.ordina.text.processor.controller;

import com.ordina.text.processor.request.HighestFrequencyRequest;
import com.ordina.text.processor.request.MostFrequentNWordsRequest;
import com.ordina.text.processor.request.WordFrequencyRequest;
import com.ordina.text.processor.response.HighestFrequencyResponse;
import com.ordina.text.processor.response.MostFrequentNWordsResponse;
import com.ordina.text.processor.response.WordFrequencyResponse;
import com.ordina.text.processor.service.WordFrequency;
import com.ordina.text.processor.service.WordFrequencyAnalyzer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Class for handling incoming requests for Text Processor API
 *
 * @author Shruti Gautam
 */
@RestController
@Slf4j
@RequestMapping("/api/text-processor/words")
public class TextProcessingController {

  @Autowired
  private WordFrequencyAnalyzer wordFrequencyAnalyzer;
  private static final String INPUT_MESSAGE="Input text - \n";
  /**
   * This method handles the incoming request to calculate and return the frequency of most frequent
   * word in input text.
   *
   * @param request HighestFrequencyRequest
   * @return ResponseEntity<HighestFrequencyResponse>
   */
  @PostMapping(value = "/word/highest-frequency")
  public ResponseEntity<HighestFrequencyResponse> calculateHighestFrequency(
      @Valid @RequestBody HighestFrequencyRequest request) {
    log.debug("Received request to calculate highest frequency in Input text - start");
    log.debug(INPUT_MESSAGE+" {}", request.getText());
    int highestFrequency = wordFrequencyAnalyzer.calculateHighestFrequency(request.getText());
    log.debug("Highest frequency - {}", highestFrequency);
    HighestFrequencyResponse highestFrequencyResponse =
        HighestFrequencyResponse.builder().highestFrequency(highestFrequency).build();
    log.debug("Request to calculate highest frequency in input text completed.");
    return new ResponseEntity<>(highestFrequencyResponse, HttpStatus.OK);
  }
  /**
   * This method handles the incoming request to calculate frequency of an input word in input text.
   *
   * @param request WordFrequencyRequest
   * @return ResponseEntity<WordFrequencyResponse>
   */
  @PostMapping(value = "/word/frequency")
  public ResponseEntity<WordFrequencyResponse> calculateFrequencyForWord(
      @Valid @RequestBody WordFrequencyRequest request) {
    log.debug(
        "Received request to calculate highest frequency for word - {} in input text - start");
    log.debug(INPUT_MESSAGE+" {}", request.getText());
    int wordFrequency =
        wordFrequencyAnalyzer.calculateFrequencyForWord(request.getText(), request.getWord());
    log.debug("Frequency of word - {} in input text is - {}", request.getWord(), wordFrequency);
    WordFrequencyResponse wordFrequencyResponse =
        WordFrequencyResponse.builder().wordFrequency(wordFrequency).build();
    log.debug("Request to calculate highest frequency for word - {} completed.");
    return new ResponseEntity<>(wordFrequencyResponse, HttpStatus.OK);
  }
  /**
   * This method handles the incoming request to calculate most frequent n words in an input text.
   *
   * @param request MostFrequentNWordsRequest
   * @return ResponseEntity<MostFrequentNWordsResponse>
   */
  @PostMapping(value = "/most-frequent-n-words/{n}")
  public ResponseEntity<MostFrequentNWordsResponse> calculateMostFrequentNWords(
      @Valid @RequestBody MostFrequentNWordsRequest request, @PathVariable("n") int n) {
    log.debug("Received request to calculate most frequent {} words in input text - start", n);
    log.debug(INPUT_MESSAGE+" {}", request.getText());
    WordFrequency[] wordFrequencies =
        wordFrequencyAnalyzer.calculateMostFrequentNWords(request.getText(), n);
    MostFrequentNWordsResponse mostFrequentNWordsResponse =
        MostFrequentNWordsResponse.builder().wordFrequencies(wordFrequencies).build();
    log.debug("Request to calculate most frequent {} words in input text completed.", n);
    return new ResponseEntity<>(mostFrequentNWordsResponse, HttpStatus.OK);
  }
}
