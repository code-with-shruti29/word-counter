package com.ordina.word.processor.helper;

import com.ordina.word.processor.exception.NoWordsFoundInInputTextException;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class TextProcessingHelper {
    private static String REMOVE_SPECIAL_CHARACTERS_REGEX_EXPRESSIONS = "[^a-zA-Z]+";

    public List<String> convertTextIntoWordsList(String text) {
        List<String> wordsList = Arrays.asList(text.split(REMOVE_SPECIAL_CHARACTERS_REGEX_EXPRESSIONS));
        if(wordsList.isEmpty()){
            throw new NoWordsFoundInInputTextException();
        }
        return wordsList;
    }

    public Map<String, Long>  groupWordsByCount(final List<String> wordsList){
        return wordsList.stream()
                .map(word->word.toLowerCase())
                .collect(Collectors.groupingBy(w -> w, Collectors.counting()));
    }

}
