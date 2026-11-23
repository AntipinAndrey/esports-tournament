package com.example.demo.exception;

/**
 * Базовый класс ошибок предметной области.
 * Вместе с сообщением хранит подсказку, которая помогает исправить запрос.
 */
public abstract class ApiException extends RuntimeException {
    private final String hint;

    protected ApiException(String message, String hint) {
        super(message);
        this.hint = hint;
    }

    public String getHint() {
        return hint;
    }
}
