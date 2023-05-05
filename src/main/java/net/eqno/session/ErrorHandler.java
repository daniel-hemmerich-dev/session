package net.eqno.session;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorHandler {
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    ValidationErrorResponse onConstraintValidationException(ConstraintViolationException constraintViolationException) {
        ValidationErrorResponse validationErrorResponse = new ValidationErrorResponse();

        constraintViolationException.getConstraintViolations().forEach(constraintViolation -> {
            Violation violation = new Violation();
            violation.setFieldName(constraintViolation.getPropertyPath().toString());
            violation.setMessage(constraintViolation.getMessage());
            validationErrorResponse.getViolations().add(violation);
        });

        return validationErrorResponse;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    ValidationErrorResponse onMethodArgumentNotValidException(MethodArgumentNotValidException methodArgumentNotValidException) {
        ValidationErrorResponse validationErrorResponse = new ValidationErrorResponse();

        for (FieldError fieldError : methodArgumentNotValidException.getBindingResult().getFieldErrors()) {
            Violation violation = new Violation();
            violation.setFieldName(fieldError.getField());
            violation.setMessage(fieldError.getDefaultMessage());
            validationErrorResponse.getViolations().add(violation);
        }

        return validationErrorResponse;
    }
}
