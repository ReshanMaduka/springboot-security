package com.edu.springboot.exception;


import com.edu.springboot.dto.response.ErrorMessageResponseDTO;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

import static com.edu.springboot.constant.ApplicationConstant.APPLICATION_ERROR_OCCURRED_MESSAGE;


@ControllerAdvice
public class AppExceptionsHandler extends ResponseEntityExceptionHandler {

    private static final Logger LOGGER = LogManager.getLogger(AppExceptionsHandler.class);

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity handleAnyException(Exception ex, WebRequest webRequest) {
        LOGGER.error("Function handleAnyException : " + ex.getMessage());
        return new ResponseEntity<>(new ErrorMessageResponseDTO(false, 100, APPLICATION_ERROR_OCCURRED_MESSAGE), HttpStatus.EXPECTATION_FAILED);
    }


    @ExceptionHandler(value = {ApplicationServiceException.class})
    public ResponseEntity handleFoodOrderingServiceException(ApplicationServiceException ex, WebRequest webRequest) {
        LOGGER.error("Function ApplicationServiceException : " + ex.getMessage());
        return new ResponseEntity<>(new ErrorMessageResponseDTO(false, ex.getStatus(), ex.getMessage()), HttpStatus.OK);
    }

    @ExceptionHandler({HttpUnauthorizedException.class})
    @ResponseBody
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    Map<String, String> unauthorizedAccess(Exception e) {
        Map<String, String> exception = new HashMap<String, String>();
        exception.put("code", "401");
        exception.put("reason", "Invalid Token");

        return exception;
    }

}
