package com.learning.processing;

import org.junit.jupiter.api.Test;

class ProcessingServiceTest {

    @Test
    void transactionsBetweenAccountsIsValid(){
        //проверка работоспособности транзакции между счетами разных пользователей
    }

    @Test
    void transactionsBetweenAccountsOfOneUserIsValid(){
        //проверка работоспособности транзакции между счетами одного пользователя
    }

    @Test
    void transactionRollbackByError(){
        //проверка, что транзакция откатывает изменения при ошибке передачи
    }

    @Test
    void transactionNotPermitIfNegativeDebt(){
        //проверка, что транзакция не осуществляется при отрицательном балансе счёта - отправителя
    }

}
