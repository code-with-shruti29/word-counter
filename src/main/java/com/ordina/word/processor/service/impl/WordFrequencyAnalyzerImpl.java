package com.ordina.word.processor.service.impl;

import com.ordina.word.processor.exception.WordNotFoundException;
import com.ordina.word.processor.helper.TextProcessingHelper;
import com.ordina.word.processor.service.WordFrequency;
import com.ordina.word.processor.service.WordFrequencyAnalyzer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeSet;
import java.util.LinkedHashMap;

@Service
public class WordFrequencyAnalyzerImpl implements WordFrequencyAnalyzer {
    @Autowired
    private TextProcessingHelper textProcessingHelper;
    /*should return the highest frequency in the text (several words might actually have this frequency)
    * */
    @Override
    public int calculateHighestFrequency(String text) {
        List<String> wordsList = textProcessingHelper.convertTextIntoWordsList(text);
        Map<String,Long> wordsCountMap = textProcessingHelper.groupWordsByCount(wordsList);
        Optional<Map.Entry<String, Long>> highestFrequencyWordMap = wordsCountMap
                .entrySet().stream()
                .max(Map.Entry.comparingByValue());

        return highestFrequencyWordMap.get().getValue().intValue();
    }
    /*
    should return the frequency of the specified word
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
    /*
        should return a list of the most frequent „n" words in the input text, all the words returned in lower case. If several words have the same frequency, this method should return them in ascendant alphabetical order (for input text "The sun shines over the lake" and n = 3, it should return the list {("the", 2), ("lake", 1), ("over", 1) }
        Implementation
        * */
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
        for (Map.Entry<Long,TreeSet<String>> entry : countWordsMap.entrySet()){
            for(String word : entry.getValue()){
                WordFrequency wordFrequency = new WordFrequencyImpl(word,entry.getKey().intValue());
                wordFrequencies[wordFrequencyIndex]=wordFrequency;
                wordFrequencyIndex++;
                if(n==wordFrequencyIndex){
                    break;
                }
            }
        }

        return wordFrequencies;
    }
}
