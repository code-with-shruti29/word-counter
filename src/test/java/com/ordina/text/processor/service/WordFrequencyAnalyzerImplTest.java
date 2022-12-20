package com.ordina.text.processor.service;

import com.ordina.text.processor.exception.WordNotFoundException;
import com.ordina.text.processor.helper.TextProcessingHelper;
import com.ordina.text.processor.service.impl.WordFrequencyAnalyzerImpl;
import com.ordina.text.processor.service.impl.WordFrequencyImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class WordFrequencyAnalyzerImplTest {
  @InjectMocks
  private WordFrequencyAnalyzerImpl wordFrequencyAnalyzer;

  @Mock
  private TextProcessingHelper textProcessingHelper;

  private static final String TEXT = "The sun shines over the lake";
  private static final String TEXT_WITH_SPECIAL_CHARACTERS = "The sun &&&&shines----- over !!!!!!the lake";


  @Test
  public void test_calculateHighestFrequency_validText() {

    int expectedResult = 2;
    when(textProcessingHelper.convertTextIntoWordsList(anyString())).thenReturn(getWordsList());
    when(textProcessingHelper.groupWordsByCount(anyList())).thenReturn(getExpectedWordCountMap());
    int actualResult = wordFrequencyAnalyzer.calculateHighestFrequency(TEXT);
    assertEquals(expectedResult, actualResult);
  }

  @Test
  public void test_calculateHighestFrequency_textWithSpecialCharacters() {

    int expectedResult = 2;
    when(textProcessingHelper.convertTextIntoWordsList(anyString())).thenReturn(getWordsList());
    when(textProcessingHelper.groupWordsByCount(anyList())).thenReturn(getExpectedWordCountMap());
    int actualResult = wordFrequencyAnalyzer.calculateHighestFrequency(TEXT_WITH_SPECIAL_CHARACTERS);
    assertEquals(expectedResult, actualResult);
  }

  @Test
  void test_wordNotInInputText_throw_WordNotFoundException() {

    when(textProcessingHelper.convertTextIntoWordsList(anyString())).thenReturn(getWordsList());
    assertThrows(
        WordNotFoundException.class,
        () -> wordFrequencyAnalyzer.calculateFrequencyForWord(TEXT, "mountain"));
  }

  @Test
  public void test_calculateFrequencyForWord_wordFoundInText() {
    when(textProcessingHelper.convertTextIntoWordsList(anyString())).thenReturn(getWordsList());
    int wordFrequency = wordFrequencyAnalyzer.calculateFrequencyForWord(TEXT, "the");
    int expectedFrequency = 2;
    assertEquals(expectedFrequency, wordFrequency);
  }

  @Test
  public void test_calculateFrequencyForWord_wordFoundInTextWithSpecialCharacters() {
    when(textProcessingHelper.convertTextIntoWordsList(anyString())).thenReturn(getWordsList());
    int wordFrequency = wordFrequencyAnalyzer.calculateFrequencyForWord(TEXT_WITH_SPECIAL_CHARACTERS, "the");
    int expectedFrequency = 2;
    assertEquals(expectedFrequency, wordFrequency);
  }

  @Test
  public void test_calculateMostFrequentNWords() {
    when(textProcessingHelper.convertTextIntoWordsList(anyString())).thenReturn(getWordsList());
    when(textProcessingHelper.groupWordsByCount(anyList())).thenReturn(getExpectedWordCountMap());
    WordFrequency[] wordFrequencies = wordFrequencyAnalyzer.calculateMostFrequentNWords(TEXT, 3);
    WordFrequency[] expectedWordFrequencies =
        new WordFrequency[] {
          new WordFrequencyImpl("the", 2),
          new WordFrequencyImpl("lake", 1),
          new WordFrequencyImpl("over", 1)
        };
    assertEquals(expectedWordFrequencies.length, wordFrequencies.length);
    assertEquals(expectedWordFrequencies[0].getWord(), wordFrequencies[0].getWord());
    assertEquals(expectedWordFrequencies[0].getFrequency(), wordFrequencies[0].getFrequency());
    assertEquals(expectedWordFrequencies[1].getWord(), wordFrequencies[1].getWord());
    assertEquals(expectedWordFrequencies[1].getFrequency(), wordFrequencies[1].getFrequency());
    assertEquals(expectedWordFrequencies[2].getWord(), wordFrequencies[2].getWord());
    assertEquals(expectedWordFrequencies[2].getFrequency(), wordFrequencies[2].getFrequency());
  }

  private List<String> getWordsList() {
    String[] inputTextArray = new String[] {"The", "sun", "shines", "over", "the", "lake"};
    return Arrays.asList(inputTextArray);
  }

  private Map<String, Long> getExpectedWordCountMap() {
    Map<String, Long> wordCountMap = new HashMap<>();
    wordCountMap.put("the", 2L);
    wordCountMap.put("sun", 1L);
    wordCountMap.put("shines", 1L);
    wordCountMap.put("over", 1L);
    wordCountMap.put("lake", 1L);
    return wordCountMap;
  }
}
