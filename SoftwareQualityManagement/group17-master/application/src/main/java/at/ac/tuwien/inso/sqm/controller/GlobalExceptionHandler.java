package at.ac.tuwien.inso.sqm.controller;

import at.ac.tuwien.inso.sqm.exception.ActionNotAllowedException;
import at.ac.tuwien.inso.sqm.exception.BusinessObjectNotFoundException;
import at.ac.tuwien.inso.sqm.exception.UserFacingException;
import at.ac.tuwien.inso.sqm.exception.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;


@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @Autowired
    private MessageSource messageSource;

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ModelAndView handlerExceptions(HttpServletRequest request,
            Exception exception) {
        LOGGER.warn("Arbitrary exception happened", exception);
        ModelAndView mav = new ModelAndView();
        mav.setViewName("error");
        return mav;
    }

    @ExceptionHandler(DataAccessException.class)
    @ResponseStatus(value = HttpStatus.CONFLICT)
    public ModelAndView handleDataAccessExceptions(HttpServletRequest request,
            DataAccessException ex) {
        LOGGER.warn("DataAccessException: " + request.getRequestURL(), ex);
        ModelAndView mav = new ModelAndView();
        mav.setViewName("error");
        return mav;
    }

    @ExceptionHandler(BusinessObjectNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ModelAndView handleBusinessObjectNotFoundExceptions(
            HttpServletRequest request, BusinessObjectNotFoundException ex) {
        LOGGER.warn(
                "BusinessObjectNotFoundException: " + request.getRequestURL(),
                ex);
        ModelAndView mav = new ModelAndView();
        mav.addObject("message", ex.getMessage());
        mav.setViewName("error");
        return mav;
    }

    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ModelAndView handleValidationExceptions(HttpServletRequest request,
            ValidationException ex) {
        LOGGER.warn("ValidationException: " + request.getRequestURL(), ex);
        ModelAndView mav = new ModelAndView();
        mav.setViewName("error");
        return mav;
    }

    @ExceptionHandler(ActionNotAllowedException.class)
    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    public ModelAndView handleActionNotAllowedExceptions(
            HttpServletRequest request, ActionNotAllowedException ex) {
        LOGGER.warn("ActionNotAllowedException: " + request.getRequestURL(),
                ex);
        ModelAndView mav = new ModelAndView();
        mav.setViewName("error");
        return mav;
    }

    @ExceptionHandler(TypeMismatchException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ModelAndView handleTypeMismatchExceptions(HttpServletRequest request,
            TypeMismatchException ex) {
        LOGGER.warn("TypeMismatchRequest: " + request.getRequestURL(), ex);
        ModelAndView mav = new ModelAndView();
        mav.setViewName("error");
        return mav;
    }

    @ExceptionHandler(UserFacingException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ModelAndView handleUserFacingException(HttpServletRequest request,
            UserFacingException ex) {
        LOGGER.warn("UserFacingException: " + request.getRequestURL(), ex);

        ModelAndView mav = new ModelAndView();

        Locale locale = LocaleContextHolder.getLocale();
        String msg = messageSource.getMessage(ex.getMessage(), null, locale);
        mav.addObject("message", msg);

        mav.setViewName("error");
        return mav;
    }
}
