package me.qigan.abse.sync;

public class InvalidSoundFileException extends RuntimeException {
    public InvalidSoundFileException() {
        super();
    }

    public InvalidSoundFileException(String str) {
        super(str);
    }
}
