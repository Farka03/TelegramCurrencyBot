package com.currencybot.telegramcurrencybot.config.service;

import com.currencybot.telegramcurrencybot.config.BotConfig;
import com.currencybot.telegramcurrencybot.entity.MessageEntity;
import com.currencybot.telegramcurrencybot.repository.MessageRepository;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

//анный класс реализует логику бота для обработки сообщений, которые приходят из Telegram
@Component
public class TelegramBot extends TelegramLongPollingBot {
    private BotConfig botConfig;

    public TelegramBot(BotConfig botConfig) {
        this.botConfig = botConfig;
    }

    @Autowired
    private MessageRepository messageRepository;

    //Метод возвращает имя бота, которое берется из BotConfig
    @Override
    public String getBotUsername() {
        return botConfig.getBotName();
    }

    //Метод возвращает токен бота, которое берется из BotConfig
    @Override
    public String getBotToken() {
        return botConfig.getToken();
    }

    //Метод вызывается при получении оновления от Telegram и обрабатывает сообщение
    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();

            //Создаем объект MessageEntity и сохраняем текст сообщения в репозитории
            MessageEntity messageEntity = new MessageEntity();
            messageEntity.setMessage(messageText);
            messageRepository.save(messageEntity);

            try {
                //Конвертируем валюту
                double convertedAmount = convertCurrency(messageText);
                String response = String.format("Converted amount: %.2f", convertedAmount);
                sendMessage(chatId, response);
            } catch (IOException e) {
                //Отправляем сообщение об ошибке, если она возникла
                sendMessage(chatId, "Failed to convert currency.");
            }
        }
    }

    //Метод для конвертации валюты
    private double convertCurrency(String input) throws IOException {
        String[] parts = input.split(" ");
        double amount = Double.parseDouble(parts[0]);
        String currency = parts[1].toUpperCase();

        //Создаем URL-запрос к API с акутальными курсами валют
        String requestUrl = "https://openexchangerates.org/api/latest.json?app_id=40b0039ed375490db2855b3d9bfc0fb0";
        URL url = new URL(requestUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
            //Читаем ответ API и парсим JSON
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            Gson gson = new Gson();
            JsonObject jsonResponse = gson.fromJson(response.toString(), JsonObject.class);
            JsonObject rates = jsonResponse.getAsJsonObject("rates");

            double kztRate = rates.get("KZT").getAsDouble();

            //Если в бота ввели ... USD, то конвертация в KZT. Если введи ...KZT, конвертация в USD
            if (currency.equals("USD")) {
                return amount * kztRate;
            } else if (currency.equals("KZT")) {
                return amount / kztRate;
            }
        }
        return amount;
    }

    //Метод для отправки сообщения в чат
    private void sendMessage(long chatId, String textToSend) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chatId));
        sendMessage.setText(textToSend);

        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            //Обрабатываем возможную ошибку при отправке сообщения
        }
    }
}
