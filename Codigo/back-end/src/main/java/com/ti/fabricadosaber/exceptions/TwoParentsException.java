package com.ti.fabricadosaber.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class TwoParentsException extends RuntimeException{

    public TwoParentsException(String message) {
        super(message);
    }

}
