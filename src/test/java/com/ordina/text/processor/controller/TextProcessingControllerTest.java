package com.ordina.text.processor.controller;

import com.google.gson.Gson;
import com.ordina.text.processor.request.HighestFrequencyRequest;
import com.ordina.text.processor.request.MostFrequentNWordsRequest;
import com.ordina.text.processor.request.WordFrequencyRequest;
import com.ordina.text.processor.service.WordFrequencyAnalyzer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
public class TextProcessingControllerTest {

    @InjectMocks
    private TextProcessingController textProcessingController;

    @Mock
    private WordFrequencyAnalyzer wordFrequencyAnalyzer;

    private MockMvc mockMvc;

    private static String TEXT="Sun shines brightly today.";

    @BeforeEach
    public void setup() {
        mockMvc =
                MockMvcBuilders.standaloneSetup(textProcessingController)
                        .build();
    }

    @Test
    public void testCalculateHighestFrequency() throws Exception {
        Gson gson= new Gson();
        HighestFrequencyRequest request = new HighestFrequencyRequest();
        when(wordFrequencyAnalyzer.calculateHighestFrequency(anyString())).thenReturn(5);
        request.setText(TEXT);
        ResultActions result = mockMvc.perform(post("/api/text-processor/words/word/highest-frequency")
                .content(gson.toJson(request))
                .contentType(MediaType.APPLICATION_JSON));
        result.andExpect(status().isOk());
    }

    @Test
    public void testCalculateFrequencyForWord() throws Exception {
        Gson gson= new Gson();
        WordFrequencyRequest request = new WordFrequencyRequest();
        request.setText(TEXT);
        request.setWord("shines");
        when(wordFrequencyAnalyzer.calculateFrequencyForWord(anyString(),anyString())).thenReturn(1);
        ResultActions result = mockMvc.perform(post("/api/text-processor/words/word/frequency")
                .content(gson.toJson(request))
                .contentType(MediaType.APPLICATION_JSON));
        result.andExpect(status().isOk());
    }

    @Test
    public void testCalculateMostFrequentNWords()  throws Exception{
        Gson gson= new Gson();
        MostFrequentNWordsRequest request=new MostFrequentNWordsRequest();
        request.setText(TEXT);
        ResultActions result = mockMvc.perform(post("/api/text-processor/words/most-frequent-n-words/2")
                .content(gson.toJson(request))
                .contentType(MediaType.APPLICATION_JSON));
        result.andExpect(status().isOk());
    }
}
