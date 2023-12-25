package me.qigan.abse.sync;

public class InvalidSoundFileException extends Throwable {
    @Override
    public String getMessage() {
        return "InvalidSoundFileException";
    }
}
