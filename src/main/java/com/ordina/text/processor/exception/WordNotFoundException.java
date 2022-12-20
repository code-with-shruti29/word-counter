package com.ordina.text.processor.exception;
/**
 * Custom Exception to throw when the input word is not found in the input text.
 *
 * @author Shruti Gautam
 *
 */
public class WordNotFoundException extends RuntimeException{
    public WordNotFoundException(){
        super();
    }
}
