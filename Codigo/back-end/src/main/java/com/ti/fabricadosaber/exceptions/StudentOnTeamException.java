package com.ti.fabricadosaber.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class StudentOnTeamException extends RuntimeException{

    public StudentOnTeamException(String message) {
        super(message);
    }

}
