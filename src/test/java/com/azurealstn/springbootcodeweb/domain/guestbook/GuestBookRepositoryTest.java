package com.azurealstn.springbootcodeweb.domain.guestbook;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

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

    @Test
    void testQuery1() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("gno").descending());
        QGuestBook qGuestBook = QGuestBook.guestBook;
        String keyword = "1";
        BooleanBuilder builder = new BooleanBuilder();
        BooleanExpression expression = qGuestBook.title.contains(keyword);
        builder.and(expression);
        Page<GuestBook> result = guestBookRepository.findAll(builder, pageable);
        result.stream().forEach(guestBook -> {
            System.out.println(guestBook);
        });
    }

    @Test
    void testQuery2() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("gno").descending());
        QGuestBook qGuestBook = QGuestBook.guestBook;
        String keyword = "1";
        BooleanBuilder builder = new BooleanBuilder();
        BooleanExpression exTitle = qGuestBook.title.contains(keyword);
        BooleanExpression exContent = qGuestBook.content.contains(keyword);
        BooleanExpression exAll = exTitle.or(exContent);
        builder.and(exAll);
        builder.and(qGuestBook.gno.gt(0L));
        Page<GuestBook> result = guestBookRepository.findAll(builder, pageable);

        result.stream().forEach(guestBook -> {
            System.out.println(guestBook);
        });
    }

}