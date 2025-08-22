package com.milos.numeric.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidEmailException.class)
    public String handleInvalidEmail(InvalidEmailException ex, RedirectAttributes redirectAttributes, HttpServletRequest request) {
        redirectAttributes.addFlashAttribute("email-error", "Zadaný email nie je validný!");

        String referer = request.getHeader("Referer");

        return "redirect:" + referer;
    }

    @ExceptionHandler(EmailNotFoundException.class)
    public String handleEmailNotFound(EmailNotFoundException ex, RedirectAttributes redirectAttributes, HttpServletRequest request) {
        redirectAttributes.addFlashAttribute("email-error", "Zadaný email nebol nájdený!");

        String referer = request.getHeader("Referer");


        return "redirect:" + referer;
    }


}
