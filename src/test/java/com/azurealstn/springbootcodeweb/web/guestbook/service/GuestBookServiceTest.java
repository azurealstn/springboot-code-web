package com.azurealstn.springbootcodeweb.web.guestbook.service;

import com.azurealstn.springbootcodeweb.web.guestbook.dto.GuestBookDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class GuestBookServiceTest {

    @Autowired
    private GuestBookService guestBookService;

    @Test
    void testRegister() {
        GuestBookDTO guestBookDTO = GuestBookDTO.builder()
                .title("Sample Title...")
                .content("Sample Content...")
                .writer("user0")
                .build();

        System.out.println(guestBookService.register(guestBookDTO));
    }

}