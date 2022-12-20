package com.ordina.word.processor.helper;

import com.ordina.word.processor.exception.NoWordsFoundInInputTextException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
/**
 * Class containing common methods for processing text.
 *
 * @author Shruti Gautam
 */
@Component
@Slf4j
public class TextProcessingHelper {
    private static final String REMOVE_SPECIAL_CHARACTERS_REGEX_EXPRESSIONS = "[^a-zA-Z]+";
    private static final String NO_WORDS_FOUND_IN_INPUT_TEXT_EXCEPTION_MESSAGE= "Input text contains No words.";
    /**
     * This method converts input text into a list of words after removing
     * all the special characters from input text.
     *
     * @param text input text
     * @return List<String>
     */
    public List<String> convertTextIntoWordsList(String text) {
        List<String> wordsList = Arrays.asList(text.split(REMOVE_SPECIAL_CHARACTERS_REGEX_EXPRESSIONS));
        if(wordsList.isEmpty()){
            log.error(NO_WORDS_FOUND_IN_INPUT_TEXT_EXCEPTION_MESSAGE);
            throw new NoWordsFoundInInputTextException();
        }
        return wordsList;
    }
    /**
     * This method converts a list of words into a Map of word as
     * key and frequency of the word as value.
     *
     * @param wordsList List of words from the input text
     * @return Map<String, Long>
     */
    public Map<String, Long>  groupWordsByCount(final List<String> wordsList){
    return wordsList.stream()
        .map(String::toLowerCase)
        .collect(Collectors.groupingBy(w -> w, Collectors.counting()));
    }

}
