package com.hildabur.bambikbaby.exceptions;

public class UserAlreadyExistException extends Exception{
    public UserAlreadyExistException() {
        super();
    }

    public UserAlreadyExistException(String message) {
        super(message);
    }
}
