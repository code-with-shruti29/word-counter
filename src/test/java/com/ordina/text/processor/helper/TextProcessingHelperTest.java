package com.ordina.text.processor.helper;

import com.ordina.text.processor.exception.NoWordsFoundInInputTextException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class TextProcessingHelperTest {
    @InjectMocks
    private TextProcessingHelper textProcessingHelper;

    @Test
    public void test_validText_convertTextIntoWordsList(){
        String text="The sun shines over the lake";
        List<String> wordsList=textProcessingHelper.convertTextIntoWordsList(text);
        assertEquals(getWordsList(),wordsList);
    }

    @Test
    void test_invalidText_throw_NoWordsFoundInInputTextException() {
        String text="-- &&****@@@@@!!!!!";
       assertThrows(NoWordsFoundInInputTextException.class, () -> {
            textProcessingHelper.convertTextIntoWordsList(text);
        });
    }
    @Test
    public void test_groupWordsByCount(){
        Map<String,Long> wordCountMap=textProcessingHelper.groupWordsByCount(getWordsList());
        assertEquals(getExpectedWordCountMap(),wordCountMap);
    }

    private List<String> getWordsList() {
        String inputTextArray[] = new String[] { "The", "sun", "shines", "over","the","lake" };
        return Arrays.asList(inputTextArray);
    }

    private Map<String,Long> getExpectedWordCountMap(){Map<String,Long> wordCountMap= new HashMap<>();
        wordCountMap.put("the",2L);
        wordCountMap.put("sun",1L);
        wordCountMap.put("shines",1L);
        wordCountMap.put("over",1L);
        wordCountMap.put("lake",1L);
        return wordCountMap;
    }

}
