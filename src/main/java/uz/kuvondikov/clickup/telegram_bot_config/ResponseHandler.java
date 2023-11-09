package uz.kuvondikov.clickup.telegram_bot_config;


import org.telegram.abilitybots.api.sender.SilentSender;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;


public class ResponseHandler {
    private final SilentSender sender;

    public ResponseHandler(SilentSender sender) {
        this.sender = sender;
    }


    public void replyToStart(long chatId, Message msg) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText("Welcome to our bot " + msg.getFrom().getFirstName());
        sender.execute(message);
    }

    public void replyToButtons(long chatId, Message message) {
        switch (message.getText()) {
            case "/start" -> replyToStart(chatId, message);
            case "/stop" -> stopChat(chatId);
            default -> unexpectedMessage(chatId);
        }
    }

    private void unexpectedMessage(long chatId) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText("I did not expect that.");
        sender.execute(sendMessage);
    }

    private void stopChat(long chatId) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText("Thank you for your order. See you soon!\nPress /start to order again");
        sendMessage.setReplyMarkup(new ReplyKeyboardRemove(true));
        sender.execute(sendMessage);
    }
}
