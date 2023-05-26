package com.currencybot.telegramcurrencybot.repository;

import com.currencybot.telegramcurrencybot.entity.MessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//Интерфейс, который является репозиторием для работы с сущностями сообщений.
//Использует Spring Data JPA и расширяет интерфейс JpaRepository для наследования основных методов доступа к данным
@Repository
public interface MessageRepository extends JpaRepository<MessageEntity, Long> {
    //Параметр MessageEntity - класс Entity нашего проекта
    //Параметр Long - тип id
}
