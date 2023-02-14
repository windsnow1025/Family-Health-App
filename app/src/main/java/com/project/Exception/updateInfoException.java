package com.project.Exception;

public class updateInfoException extends Exception{
    private String message;
    public updateInfoException(String message)
    {
        super(message);
        this.message=message;
    }
}
