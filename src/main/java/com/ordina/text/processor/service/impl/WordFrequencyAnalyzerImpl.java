package com.ordina.text.processor.service.impl;

import com.ordina.text.processor.exception.WordNotFoundException;
import com.ordina.text.processor.service.WordFrequency;
import com.ordina.text.processor.helper.TextProcessingHelper;
import com.ordina.text.processor.service.WordFrequencyAnalyzer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeSet;
import java.util.LinkedHashMap;
/**
 * Class providing method implementation of {@link WordFrequencyAnalyzer} interface
 *
 * @author Shruti Gautam
 */
@Service
public class WordFrequencyAnalyzerImpl implements WordFrequencyAnalyzer {
    @Autowired
    private TextProcessingHelper textProcessingHelper;

  /**
   * This method calculates and returns the frequency of most frequent word in input text.
   *
   * @param text Input text
   * @return highest frequency
   */
  @Override
  public int calculateHighestFrequency(String text) {
        List<String> wordsList = textProcessingHelper.convertTextIntoWordsList(text);
        Map<String,Long> wordsCountMap = textProcessingHelper.groupWordsByCount(wordsList);
        Optional<Map.Entry<String, Long>> highestFrequencyWordMap = wordsCountMap
                .entrySet().stream()
                .max(Map.Entry.comparingByValue());

        return highestFrequencyWordMap.get().getValue().intValue();
    }

    /**
     * This method calculates and returns the frequency of input word in the input text
     * @param text  Input text
     * @param word  word to be searched in text for calculating frequency
     * @return frequency of the word
     */
    @Override
    public int calculateFrequencyForWord(String text, String word) {
        List<String> wordList = textProcessingHelper.convertTextIntoWordsList(text);
        final int count = (int) wordList.stream().filter(w -> w.equalsIgnoreCase(word)).count();
        if(0==count){
            throw new WordNotFoundException();
        }
        return count;
    }

  /**
   * This method calculates and returns list of the most frequent n words in the input text,all the
   * words are returned in lower case. If several words have the same frequency, this method should
   * return them in ascendant alphabetical order
   *
   * @param text Input text
   * @param n first n words
   * @return WordFrequency[]
   */
  @Override
  public WordFrequency[] calculateMostFrequentNWords(String text, int n) {
        List<String> wordsList = textProcessingHelper.convertTextIntoWordsList(text);
        Map<String,Long> wordsCountMap = textProcessingHelper.groupWordsByCount(wordsList);
        Map<Long,TreeSet<String>> countWordsMap= new LinkedHashMap<>();
        WordFrequency[] wordFrequencies = new WordFrequency[n];
        wordsCountMap.entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue()
                        .reversed()).forEachOrdered(e -> {
            if(countWordsMap.containsKey(e.getValue())){
                countWordsMap.get(e.getValue()).add(e.getKey());
            }else
            {
                TreeSet<String> set=new TreeSet();
                set.add(e.getKey());
                countWordsMap.put(e.getValue(), set);
            }
        });
        int wordFrequencyIndex=0;
        boolean isNMostFrequentWordsFound=false;
        for (Map.Entry<Long,TreeSet<String>> entry : countWordsMap.entrySet()){
            if(isNMostFrequentWordsFound){
                break;
            }
            for(String word : entry.getValue()){
                WordFrequency wordFrequency = new WordFrequencyImpl(word,entry.getKey().intValue());
                wordFrequencies[wordFrequencyIndex]=wordFrequency;
                wordFrequencyIndex++;
                if(n==wordFrequencyIndex){
                    isNMostFrequentWordsFound=true;
                    break;
                }
            }
        }

        return wordFrequencies;
    }
}
