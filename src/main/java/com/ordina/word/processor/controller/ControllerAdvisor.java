package com.ordina.word.processor.controller;

import com.ordina.word.processor.exception.NoWordsFoundInInputTextException;
import com.ordina.word.processor.exception.WordNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
/**
 * ControllerAdvice for handling exceptions
 *
 * @author Shruti Gautam
 */
@Slf4j
@ControllerAdvice
public class ControllerAdvisor extends ResponseEntityExceptionHandler {
    private static final String TIME_STAMP="timestamp";
    private static final String MESSAGE="message";
    private static final String NO_WORDS_FOUND_IN_INPUT_TEXT_EXCEPTION_MESSAGE="Input text contains no words.";
    private static final String WORD_NOT_FOUND_EXCEPTION_MESSAGE="Word not found in input text.";
    private static final String ERRORS="errors";

    /**
     * This method handles {@link NoWordsFoundInInputTextException}.
     */
    @ExceptionHandler(NoWordsFoundInInputTextException.class)
    public ResponseEntity<Object> handleNoWordsFoundException() {

        Map<String, Object> body = new LinkedHashMap<>();
        body.put(TIME_STAMP, LocalDateTime.now());
        body.put(MESSAGE, NO_WORDS_FOUND_IN_INPUT_TEXT_EXCEPTION_MESSAGE);

        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }
    /**
     * This method handles {@link WordNotFoundException}.
     */
    @ExceptionHandler(WordNotFoundException.class)
    public ResponseEntity<Object> handleWordNotFoundException() {

        Map<String, Object> body = new LinkedHashMap<>();
        body.put(TIME_STAMP, LocalDateTime.now());
        body.put(MESSAGE, WORD_NOT_FOUND_EXCEPTION_MESSAGE);

        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }
    /**
     * This method handles User input validation and returns error messages.
     */
   @Override
   protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
          HttpHeaders headers, HttpStatus status, WebRequest request) {

       Map<String, Object> body = new LinkedHashMap<>();
        body.put(TIME_STAMP, LocalDateTime.now());
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());

        body.put(ERRORS, errors);

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }
}
