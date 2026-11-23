package com.example.demo.exception;

/** Ошибка означает нарушение правила предметной области. */
public class BusinessRuleException extends ApiException {
    public BusinessRuleException(String message) {
        this(message, "Проверьте связанные объекты и правила операции перед повторной отправкой запроса.");
    }

    public BusinessRuleException(String message, String hint) {
        super(message, hint);
    }
}
