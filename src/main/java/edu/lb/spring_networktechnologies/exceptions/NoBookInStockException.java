package edu.lb.spring_networktechnologies.exceptions;

public class NoBookInStockException extends RuntimeException {
    public NoBookInStockException(String s) {
        super(s);
    }
}