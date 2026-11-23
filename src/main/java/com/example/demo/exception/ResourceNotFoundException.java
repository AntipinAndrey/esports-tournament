package com.example.demo.exception;

/** Ошибка возникает, когда запрошенный объект отсутствует в базе. */
public class ResourceNotFoundException extends ApiException {
    public ResourceNotFoundException(String message) {
        this(message, "Проверьте идентификатор объекта или сначала получите список через GET-запрос.");
    }

    public ResourceNotFoundException(String message, String hint) {
        super(message, hint);
    }
}
