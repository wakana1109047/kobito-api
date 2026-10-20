package com.example.kobitoapi.exception;

public class KobitoNotFoundException extends RuntimeException {
    public KobitoNotFoundException(long id) {
        super("指定されたIDのノートが見つかりません" + id);
    }
}
