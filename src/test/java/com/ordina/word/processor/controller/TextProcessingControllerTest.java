package com.ordina.word.processor.controller;

import com.google.gson.Gson;
import com.ordina.word.processor.request.HighestFrequencyRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@RunWith(MockitoJUnitRunner.class)
public class TextProcessingControllerTest {

    @InjectMocks
    TextProcessingController textProcessingController;
    @Test
    public void testAddCar() throws Exception {
        HighestFrequencyRequest request= new HighestFrequencyRequest();
        Gson gson= new Gson();
        standaloneSetup(textProcessingController)
                .build()
                .perform(post("/api/cars")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(request))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }

}
