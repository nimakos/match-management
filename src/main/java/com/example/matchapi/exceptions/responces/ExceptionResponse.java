package com.example.matchapi.exceptions.responces;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
public class ExceptionResponse {
    private Date timeStamp;
    private String message;
    private String details;
    private HttpStatus status;
}
