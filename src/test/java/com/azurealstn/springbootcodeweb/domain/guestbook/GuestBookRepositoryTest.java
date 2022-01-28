package com.azurealstn.springbootcodeweb.domain.guestbook;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class GuestBookRepositoryTest {

    @Autowired
    private GuestBookRepository guestBookRepository;

    @Test
    void insertDummies() {
        IntStream.rangeClosed(1, 300).forEach(i -> {
            GuestBook guestBook = GuestBook.builder()
                    .title("Title..." + i)
                    .content("Content..." + i)
                    .writer("Writer..." + i)
                    .build();
            System.out.println(guestBookRepository.save(guestBook));
        });
    }

    @Test
    void updateTest() {
        Optional<GuestBook> result = guestBookRepository.findById(300L);
        if (result.isPresent()) {
            GuestBook guestBook = result.get();
            guestBook.changeTitle("Changed Title...");
            guestBook.changeContent("Changed Content...");
            guestBookRepository.save(guestBook);
        }
    }

}