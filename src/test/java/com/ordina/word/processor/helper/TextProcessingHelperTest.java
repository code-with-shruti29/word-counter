package com.ordina.word.processor.helper;

import com.ordina.word.processor.exception.NoWordsFoundInInputTextException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class TextProcessingHelperTest {
    @InjectMocks
    private TextProcessingHelper textProcessingHelper;

    @Test
    public void test_convertTextIntoWordsList_validText(){
        String text="The sun shines over the lake";
        List<String> wordsList=textProcessingHelper.convertTextIntoWordsList(text);
        assertEquals(getWordsList(),wordsList);
    }

    @Test(expected = NoWordsFoundInInputTextException.class)
    public void test_convertTextIntoWordsList_textContainsNoWords_throwException(){
        String text="-- &&****@@@@@!!!!!";
        textProcessingHelper.convertTextIntoWordsList(text);
    }

    @Test
    public void test_groupWordsByCount(){
        Map<String,Long> wordCountMap=textProcessingHelper.groupWordsByCount(getWordsList());
        assertEquals(getExpectedWordCountMap(),wordCountMap);
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
