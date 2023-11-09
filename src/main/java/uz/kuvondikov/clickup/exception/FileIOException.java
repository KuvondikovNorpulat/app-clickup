package uz.kuvondikov.clickup.exception;

import org.springframework.core.NestedRuntimeException;

public class FileIOException extends NestedRuntimeException {

    public FileIOException(String msg) {
        super(msg);
    }
}
