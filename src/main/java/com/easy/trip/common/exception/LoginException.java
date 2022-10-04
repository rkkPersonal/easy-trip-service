package com.easy.trip.common.exception;

/**
 * @author Steven
 * @date 2022年10月05日 4:02
 */
public class LoginException extends RuntimeException{

    public LoginException(String message) {
        super(message);
    }
}
