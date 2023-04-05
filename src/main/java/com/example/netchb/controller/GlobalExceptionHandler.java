package com.example.netchb.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
@ControllerAdvice
public class GlobalExceptionHandler {
    Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    @ExceptionHandler(value = Exception.class)
    public ModelAndView globalExceptionHandler(Exception ex, WebRequest request) {
        String errorMessage = "Error: " + ex.getMessage() + " Description: " + request.getDescription(false);
        logger.error(errorMessage);

        ModelAndView model = new ModelAndView("Home");
        model.addObject("errorMessage", errorMessage);
        return model;
    }
}
