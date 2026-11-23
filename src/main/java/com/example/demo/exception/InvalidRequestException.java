package com.example.demo.exception;

/** Ошибка означает, что запрос содержит противоречивые или недопустимые данные. */
public class InvalidRequestException extends ApiException {
    public InvalidRequestException(String message) {
        this(message, "Проверьте обязательные поля, их формат и соответствие данных друг другу.");
    }

    public InvalidRequestException(String message, String hint) {
        super(message, hint);
    }
}
