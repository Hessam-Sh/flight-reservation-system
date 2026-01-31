package com.blackshuck.bookFlight.exception;

import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler
{
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public String handleValidationErrors(MethodArgumentNotValidException exception, Model model)
    {
        BindingResult bindingResult = exception.getBindingResult();

        Object target = bindingResult.getTarget();

        model.addAttribute("passengerForm", target);

        model.addAttribute("org.springframework.validation.BindingResult.passengerForm", bindingResult);

        return "passenger-page";
    }
}
