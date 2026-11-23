package com.example.demo.controller;

import com.example.demo.exception.BusinessRuleException;
import com.example.demo.exception.DuplicateResourceException;
import com.example.demo.exception.InvalidRequestException;
import com.example.demo.exception.ResourceNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.stream.Collectors;

/**
 * Централизованно преобразует разные исключения в понятные HTTP-ответы.
 * Для каждого типа ошибки используется отдельный обработчик.
 */
@RestControllerAdvice
public class ApiExceptionHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(
            ApiExceptionHandler.class
    );

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(
            ResourceNotFoundException exception,
            HttpServletRequest request
    ) {
        return response(
                HttpStatus.NOT_FOUND,
                "Ресурс не найден",
                exception.getMessage(),
                exception.getHint(),
                request
        );
    }

    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<ErrorResponse> handleDuplicate(
            DuplicateResourceException exception,
            HttpServletRequest request
    ) {
        return response(
                HttpStatus.CONFLICT,
                "Дубликат ресурса",
                exception.getMessage(),
                exception.getHint(),
                request
        );
    }

    @ExceptionHandler(BusinessRuleException.class)
    public ResponseEntity<ErrorResponse> handleBusinessRule(
            BusinessRuleException exception,
            HttpServletRequest request
    ) {
        return response(
                HttpStatus.CONFLICT,
                "Нарушено правило предметной области",
                exception.getMessage(),
                exception.getHint(),
                request
        );
    }

    @ExceptionHandler(InvalidRequestException.class)
    public ResponseEntity<ErrorResponse> handleInvalidRequest(
            InvalidRequestException exception,
            HttpServletRequest request
    ) {
        return response(
                HttpStatus.BAD_REQUEST,
                "Некорректный запрос",
                exception.getMessage(),
                exception.getHint(),
                request
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(
            MethodArgumentNotValidException exception,
            HttpServletRequest request
    ) {
        String message = exception.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining(", "));
        return response(
                HttpStatus.BAD_REQUEST,
                "Ошибка валидации данных",
                message,
                "Заполните обязательные поля и проверьте формат email, дат и числовых значений.",
                request
        );
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleUnreadableBody(
            HttpMessageNotReadableException exception,
            HttpServletRequest request
    ) {
        return response(
                HttpStatus.BAD_REQUEST,
                "Невозможно прочитать тело запроса",
                "JSON имеет неверный формат или содержит значение неподходящего типа.",
                "Проверьте Body → raw → JSON, названия полей и даты в формате ГГГГ-ММ-ДД.",
                request
        );
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolation(
            ConstraintViolationException exception,
            HttpServletRequest request
    ) {
        return response(
                HttpStatus.BAD_REQUEST,
                "Нарушено ограничение данных",
                exception.getMessage(),
                "Проверьте значения параметров запроса и ограничения модели.",
                request
        );
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleDatabaseConstraint(
            DataIntegrityViolationException exception,
            HttpServletRequest request
    ) {
        return response(
                HttpStatus.CONFLICT,
                "Нарушено ограничение базы данных",
                "Операция конфликтует с существующими связанными данными.",
                "Проверьте уникальность ключей и связи между организаторами, командами, игроками, турнирами и матчами.",
                request
        );
    }

    /** Обработчик старых внутренних ошибок, которые ещё могут возникнуть в моделях. */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgument(
            IllegalArgumentException exception,
            HttpServletRequest request
    ) {
        return response(
                HttpStatus.BAD_REQUEST,
                "Недопустимый аргумент",
                exception.getMessage(),
                "Проверьте значения полей запроса и их формат.",
                request
        );
    }

    /** Отдельная обработка некорректного состояния доменного объекта. */
    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ErrorResponse> handleIllegalState(
            IllegalStateException exception,
            HttpServletRequest request
    ) {
        return response(
                HttpStatus.CONFLICT,
                "Недопустимое состояние объекта",
                exception.getMessage(),
                "Проверьте связанные данные и соблюдение правил предметной области.",
                request
        );
    }

    /** Последний обработчик не скрывает специализированные обработчики выше. */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleUnexpected(
            Exception exception,
            HttpServletRequest request
    ) {
        LOGGER.error("Unexpected API error at {}", request.getRequestURI(), exception);
        return response(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Внутренняя ошибка сервера",
                "Сервер не смог завершить операцию.",
                "Проверьте данные запроса и журнал приложения; повторите запрос после исправления причины.",
                request
        );
    }

    private ResponseEntity<ErrorResponse> response(
            HttpStatus status,
            String error,
            String message,
            String hint,
            HttpServletRequest request
    ) {
        ErrorResponse body = new ErrorResponse(
                Instant.now(),
                status.value(),
                error,
                message,
                hint,
                request.getRequestURI()
        );
        return ResponseEntity.status(status).body(body);
    }
}
