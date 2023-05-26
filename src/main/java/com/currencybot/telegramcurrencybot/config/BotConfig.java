package com.currencybot.telegramcurrencybot.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

//Конфигурационный класс Spring
@Configuration
@Data //Нужна для автоматической генерации геттеров, сеттеров и других методов полей класса
@PropertySource("application.properties") //Указывает на файл свойства application.properties для значений полей класса
public class BotConfig {
    //Поле (имя бота) будет загружено из свойств файла application.properties
    @Value("${bot.name}")
    private String botName;
    //Поле (токен бота) будет загружено из свойств файла application.properties
    @Value("${bot.token}")
    private String token;
}
