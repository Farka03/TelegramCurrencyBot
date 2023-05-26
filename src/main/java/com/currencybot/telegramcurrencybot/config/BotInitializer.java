package com.currencybot.telegramcurrencybot.config;

import com.currencybot.telegramcurrencybot.config.service.TelegramBot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

//Класс для инициализации и регистрации Telegram-бота при запуске контекста приложения
@Component
public class BotInitializer {
    @Autowired
    private TelegramBot bot; //Внедряем TelegramBot

    //Метод должен быть вызван при событии ContextRefreshedEvent, которое происходит при запуске контекста Spring
    @EventListener({ContextRefreshedEvent.class})
    public void init() throws TelegramApiException {
        //Создание экземпляра TelegramBotsApi, который является основным классом для регистрации ботов Telegram
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        try {
            telegramBotsApi.registerBot(bot); //Регистрация бота
        } catch (TelegramApiException e) {
            throw new RuntimeException(e); //Обработка исключения
        }
    }
}
