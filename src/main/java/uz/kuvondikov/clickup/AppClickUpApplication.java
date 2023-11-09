package uz.kuvondikov.clickup;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.telegram.abilitybots.api.bot.AbilityBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import uz.kuvondikov.clickup.constant.ErrorMessages;
import uz.kuvondikov.clickup.exception.TelegramBotException;
@SpringBootApplication
public class AppClickUpApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = SpringApplication.run(AppClickUpApplication.class, args);
        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(ctx.getBean("telegramBotConfig", AbilityBot.class));
        } catch (TelegramApiException e) {
            throw new TelegramBotException(ErrorMessages.TELEGRAM_BOT_CONFIG_EXCEPTION);
        }
    }
}