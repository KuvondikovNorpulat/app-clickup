package uz.kuvondikov.clickup.exception;

public class TelegramBotException extends RuntimeException {
    public TelegramBotException(String message) {
        super(message);
    }
}
