package com.ordina.word.processor.exception;
/**
 * Custom Exception to throw when the input text contains no words but
 * only special characters.
 *
 * @author Shruti Gautam
 *
 */
public class NoWordsFoundInInputTextException extends RuntimeException{
    public NoWordsFoundInInputTextException()
    {
        super();
    }
}
