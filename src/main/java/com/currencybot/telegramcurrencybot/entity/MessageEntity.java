package com.currencybot.telegramcurrencybot.entity;

import jakarta.persistence.*;

//Класс-сущность, который будет связан с таблицей в бд
@Entity
@Table(name = "info") //Связка класса с таблицей в бд - info
public class MessageEntity {
    @Id //Первичный ключ
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Значения будут генерироваться автоматически бд
    @Column(name = "id") //Привязка поля к колонке в бд (id)
    private Long id;
    @Column(name = "message") //Привязка поля к колонке в бд (message)
    private String message;

    //Дальше конструкторы, геттеры, сеттеры
    public MessageEntity() {
    }

    public MessageEntity(Long id, String message) {
        this.id = id;
        this.message = message;
    }

    public Long getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
