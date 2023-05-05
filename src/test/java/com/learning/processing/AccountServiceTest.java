package com.learning.processing;

import org.junit.jupiter.api.Test;
class AccountServiceTest {

    @Test
    void createdAccountIsBelongToUser(){
        //проверяет, что при создании счёта он действительно принадлежит нужному (конкретному) пользователю
    }

    @Test
    void nonAuthorizedUserHasNoAccessToAccount(){
        //проверяет, что неавторизованный пользователь или другой юзер не может получить доступ к чужому счёту
    }

    @Test
    void accountIsBelongToUser(){
        //проверка, что поле userId класса Account не пустое (то есть создаваемому счёт принадлежит какому-либо пользователю)
    }

}
