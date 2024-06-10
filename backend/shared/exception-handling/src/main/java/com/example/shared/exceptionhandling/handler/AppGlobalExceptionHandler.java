package com.example.shared.exceptionhandling.handler;

import com.example.shared.exceptionhandling.exception.*;
import com.example.shared.exceptionhandling.model.*;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.TypeMismatchException;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.*;

@RestControllerAdvice
@Slf4j
@ConditionalOnProperty(name = "exception-handling.enabled", havingValue = "true")
public class AppGlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(BaseNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError handleBaseNotFoundException(BaseNotFoundException ex, HttpServletRequest request) {
        log.debug("Handled BaseNotFoundException ex: {}", ex.getMessage());

        return ApiError.builder()
                .message(ex.getResponseMessage())
                .debugMessage(ex.getMessage())
                .errors(ex.getFieldErrors())
                .url(getUrl(request))
                .method(getMethod(request))
                .build();
    }

    @ExceptionHandler(BaseConflictExistException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError handleBaseConflictExistException(BaseConflictExistException ex, HttpServletRequest request) {
        log.debug("Handled BaseConflictExistException ex: {}", ex.getMessage());

        return ApiError.builder()
                .message(ex.getResponseMessage())
                .debugMessage(ex.getMessage())
                .errors(ex.getFieldErrors())
                .url(getUrl(request))
                .method(getMethod(request))
                .build();
    }

    @ExceptionHandler(BaseBadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleBadRequestException(BaseBadRequestException ex, HttpServletRequest request) {
        log.debug("Handled BaseBadRequestException ex: {}", ex.getMessage());

        return ApiError.builder()
                .message(ex.getResponseMessage())
                .debugMessage(ex.getMessage())
                .errors(ex.getFieldErrors())
                .url(getUrl(request))
                .method(getMethod(request))
                .build();
    }

    @ExceptionHandler(BasePaymentRequiredException.class)
    @ResponseStatus(HttpStatus.PAYMENT_REQUIRED)
    public ApiError handleBasePaymentRequiredException(BasePaymentRequiredException ex, HttpServletRequest request) {
        log.debug("Handled BasePaymentRequiredException ex: {}", ex.getMessage());

        return ApiError.builder()
                .message(ex.getMessage())
                .url(getUrl(request))
                .method(getMethod(request))
                .build();
    }

    @ExceptionHandler(BaseUnauthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ApiError handleBaseUnauthorizedException(BaseUnauthorizedException ex, HttpServletRequest request) {
        log.debug("Handled BaseUnauthorizedException ex: {}", ex.getMessage());

        return ApiError.builder()
                .message(ex.getResponseMessage())
                .debugMessage(ex.getMessage())
                .errors(ex.getFieldErrors())
                .url(getUrl(request))
                .method(getMethod(request))
                .build();
    }

    @ExceptionHandler(BaseForbiddenException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ApiError handleBaseForbiddenException(BaseForbiddenException ex, HttpServletRequest request) {
        log.debug("Handled BaseForbiddenException ex: {}", ex.getMessage());

        return ApiError.builder()
                .message(ex.getMessage())
                .url(getUrl(request))
                .method(getMethod(request))
                .build();
    }

    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ApiError handleAuthenticationException(AuthenticationException ex, HttpServletRequest request) {
        log.debug("Handled AuthenticationException ex: {}", ex.getMessage());

        return ApiError.builder()
                .message("Authentication failed")
                .debugMessage(ex.getMessage())
                .url(getUrl(request))
                .method(getMethod(request))
                .build();
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ApiError handleAccessDeniedException(AccessDeniedException ex, HttpServletRequest request) {
        log.debug("Handled AccessDeniedException ex: {}", ex.getMessage());

        return ApiError.builder()
                .message("Access denied")
                .debugMessage(ex.getMessage())
                .url(getUrl(request))
                .method(getMethod(request))
                .build();
    }

    @ExceptionHandler(ExpiredJwtException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ApiError handleExpiredJwtException(ExpiredJwtException ex) {
        log.debug("Handled ExpiredJwtException ex: {}", ex.getMessage());

        return ApiError.builder()
                .message("Your session has expired. Login again.")
                .debugMessage(ex.getMessage())
                .build();
    }

    @ExceptionHandler(JwtException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ApiError handleJwtException(JwtException ex) {
        log.debug("Handled JwtException ex: {}", ex.getMessage());

        return ApiError.builder()
                .message("Authentication failed. Invalid firstName.")
                .debugMessage(ex.getMessage())
                .build();
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleConstraintViolationException(ConstraintViolationException ex, HttpServletRequest request) {
        log.debug("Handled ConstraintViolationException ex: {}", ex.getMessage());

        Set<ConstraintViolation<?>> violations = ex.getConstraintViolations();
        List<FieldValidationError> errors = new ArrayList<>(violations.size());

        for(ConstraintViolation<?> violation : violations) {
            errors.add(new FieldValidationError(violation.getPropertyPath().toString(), violation.getMessage()));
        }
        return ApiError.builder()
                .message("Ошибка валидации")
                .errors(errors)
                .url(getUrl(request))
                .method(getMethod(request))
                .build();
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        log.debug("Handled MethodArgumentNotValidException ex: {}", ex.getMessage());

        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        List<FieldValidationError> errors = new ArrayList<>(fieldErrors.size());

        for(FieldError fieldError : fieldErrors) {
            errors.add(new FieldValidationError(fieldError.getField(), fieldError.getDefaultMessage()));
        }
        return ResponseEntity.badRequest().body(
                ApiError.builder()
                        .message("Ошибка валидации")
                        .errors(errors)
                        .url(getUrl(request))
                        .method(getMethod(request))
                        .build());
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        log.debug("Handled HttpMessageNotReadableException ex: {}", ex.getMessage());

        return ResponseEntity.badRequest().body(
                ApiError.builder()
                        .message("Error reading request. Check that the request format and content are correct.")
                        .debugMessage(ex.getMessage())
                        .url(getUrl(request))
                        .method(getMethod(request))
                        .build());
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        log.debug("Handled MissingServletRequestParameterException ex: {}", ex.getMessage());

        return ResponseEntity.badRequest().body(
                ApiError.builder()
                        .message("Missing required URL parameter " + ex.getParameterName())
                        .url(getUrl(request))
                        .method(getMethod(request))
                        .build());
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        log.debug("Handled TypeMismatchException ex: {}", ex.getMessage());

        Map<String, String> params = new LinkedHashMap<>(3);
        params.put("property", ex.getPropertyName());
        params.put("required_type", ex.getRequiredType().getSimpleName());
        params.put("provided_value", ex.getValue().toString());

        return ResponseEntity.badRequest().body(
                ApiError.builder()
                        .message("Data type mismatch")
                        .url(getUrl(request))
                        .method(getMethod(request))
                        .errors(params)
                        .build());
    }

    @ExceptionHandler(BaseUnexpectedException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiError handleBaseUnexpectedException(BaseUnexpectedException ex, HttpServletRequest request) {
        log.debug("Handled BaseUnexpectedException ex: {}", ex.getMessage());
        log.error(ex.getLocalizedMessage(), ex);

        return ApiError.builder()
                .message(ex.getResponseMessage())
                .debugMessage(ex.getMessage())
                .errors(ex.getFieldErrors())
                .url(getUrl(request))
                .method(getMethod(request))
                .build();
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleAllExceptions(Exception ex) {
        log.error(ex.getLocalizedMessage(), ex);
        return "Ошибка сервера. Повторите попытку позже.";
    }


    private String getUrl(HttpServletRequest request) {
        return request.getRequestURL().toString();
    }

    private String getUrl(WebRequest webRequest) {
        NativeWebRequest nativeWebRequest = (NativeWebRequest) webRequest;
        HttpServletRequest httpServletRequest = nativeWebRequest.getNativeRequest(HttpServletRequest.class);
        if(httpServletRequest == null) {
            String description = webRequest.getDescription(false);
            String substring = "uri=";
            int startIndex = description.indexOf(substring);
            return description.substring(startIndex + substring.length());
        }
        return getUrl(httpServletRequest);
    }

    private String getMethod(HttpServletRequest request) {
        return request.getMethod();
    }

    private String getMethod(WebRequest webRequest) {
        NativeWebRequest nativeWebRequest = (NativeWebRequest) webRequest;
        HttpServletRequest httpServletRequest = nativeWebRequest.getNativeRequest(HttpServletRequest.class);
        if(httpServletRequest == null) {
            return "Undefined";
        }
        return getMethod(httpServletRequest);
    }
}