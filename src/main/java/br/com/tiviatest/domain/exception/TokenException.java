package br.com.tiviatest.domain.exception;


public class TokenException extends RuntimeException {

    public TokenException(String msg) {
        super(msg);
    }

    public TokenException(String msg, Throwable cause) {
        super(msg);
    }

}

