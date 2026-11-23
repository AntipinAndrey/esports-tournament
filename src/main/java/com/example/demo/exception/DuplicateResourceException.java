package com.example.demo.exception;

/** Ошибка возникает при попытке повторно создать объект с тем же естественным ключом. */
public class DuplicateResourceException extends ApiException {
    public DuplicateResourceException(String message) {
        this(message, "Используйте другое значение естественного ключа или измените существующий объект через PUT.");
    }

    public DuplicateResourceException(String message, String hint) {
        super(message, hint);
    }
}
