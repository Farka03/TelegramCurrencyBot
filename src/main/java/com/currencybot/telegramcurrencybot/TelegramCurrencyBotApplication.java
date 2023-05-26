package com.currencybot.telegramcurrencybot;

import org.apache.http.client.methods.HttpHead;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

import java.net.http.HttpClient;
import java.net.http.HttpHeaders;

@SpringBootApplication()
public class TelegramCurrencyBotApplication {
    public static void main(String[] args) {
        SpringApplication.run(TelegramCurrencyBotApplication.class, args);
    }
}


