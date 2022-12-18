package com.ordina.word.processor.service;

import com.ordina.word.processor.exception.WordNotFoundException;
import com.ordina.word.processor.helper.TextProcessingHelper;
import com.ordina.word.processor.service.impl.WordFrequencyAnalyzerImpl;
import com.ordina.word.processor.service.impl.WordFrequencyImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class WordFrequencyAnalyzerImplTest {
    @InjectMocks
    private WordFrequencyAnalyzerImpl wordFrequencyAnalyzer;

    @Mock
    private TextProcessingHelper textProcessingHelper;

    @Test
    public void test_calculateHighestFrequency_emptyText_throwException(){
        String text="The sun shines over the lake";
        int expectedResult=2;
        Map<String,Long> wordCountMap= new HashMap<>();
        wordCountMap.put("the",2l);
        wordCountMap.put("sun",1l);
        wordCountMap.put("shines",1l);
        wordCountMap.put("over",1l);
        wordCountMap.put("lake",1l);
        when(textProcessingHelper.convertTextIntoWordsList(anyString())).thenReturn(getWordsList());
        when(textProcessingHelper.groupWordsByCount(anyList())).thenReturn(wordCountMap);
        int actualResult = wordFrequencyAnalyzer.calculateHighestFrequency(text);
        assertEquals(expectedResult,actualResult);
    }

    @Test
    public void test_calculateHighestFrequency_validText(){
        String text="The sun shines over the lake";
        int expectedResult=2;
        when(textProcessingHelper.convertTextIntoWordsList(anyString())).thenReturn(getWordsList());
        when(textProcessingHelper.groupWordsByCount(anyList())).thenReturn(getExpectedWordCountMap());
        int actualResult = wordFrequencyAnalyzer.calculateHighestFrequency(text);
        assertEquals(expectedResult,actualResult);
    }

    @Test(expected = WordNotFoundException.class)
    public void test_calculateFrequencyForWord_wordNotFoundInText_throwException(){
        String text="The sun shines over the lake";
        when(textProcessingHelper.convertTextIntoWordsList(anyString())).thenReturn(getWordsList());
        wordFrequencyAnalyzer.calculateFrequencyForWord(text,"mountain");
    }

    @Test
    public void test_calculateFrequencyForWord_wordFoundInText(){
        String text="The sun shines over the lake";
        when(textProcessingHelper.convertTextIntoWordsList(anyString())).thenReturn(getWordsList());
        int wordFrequency=wordFrequencyAnalyzer.calculateFrequencyForWord(text,"the");
        int expectedFrequency=2;
        assertEquals(expectedFrequency,wordFrequency);
    }


    @Test
    public void test_calculateMostFrequentNWords(){
        String text="The sun shines over the lake";
        when(textProcessingHelper.convertTextIntoWordsList(anyString())).thenReturn(getWordsList());
        when(textProcessingHelper.groupWordsByCount(anyList())).thenReturn(getExpectedWordCountMap());
        WordFrequency[] wordFrequencies=wordFrequencyAnalyzer.calculateMostFrequentNWords(text,3);
        WordFrequency[] expectedWordFrequencies = new WordFrequency[]
                        {new WordFrequencyImpl("the",2),
                        new WordFrequencyImpl("lake",1),
                        new WordFrequencyImpl("over",1)};
        assertEquals(expectedWordFrequencies.length,wordFrequencies.length);
        assertEquals(expectedWordFrequencies[0].getWord(),wordFrequencies[0].getWord());
        assertEquals(expectedWordFrequencies[0].getFrequency(),wordFrequencies[0].getFrequency());
        assertEquals(expectedWordFrequencies[1].getWord(),wordFrequencies[1].getWord());
        assertEquals(expectedWordFrequencies[1].getFrequency(),wordFrequencies[1].getFrequency());
        assertEquals(expectedWordFrequencies[2].getWord(),wordFrequencies[2].getWord());
        assertEquals(expectedWordFrequencies[2].getFrequency(),wordFrequencies[2].getFrequency());

    }

    private List<String> getWordsList() {
        String text="The sun shines over the lake";
        String inputTextArray[] = new String[] { "The", "sun", "shines", "over","the","lake" };
        return Arrays.asList(inputTextArray);
    }

    private Map<String,Long> getExpectedWordCountMap(){Map<String,Long> wordCountMap= new HashMap<>();
        wordCountMap.put("the",2l);
        wordCountMap.put("sun",1l);
        wordCountMap.put("shines",1l);
        wordCountMap.put("over",1l);
        wordCountMap.put("lake",1l);
        return wordCountMap;
    }
}
