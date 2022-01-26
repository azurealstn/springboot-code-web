package com.azurealstn.springbootcodeweb.domain.sample;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MemoRepositoryTest {

    @Autowired
    MemoRepository memoRepository;

    @Test
    void testClass() {
        System.out.println(memoRepository.getClass().getName());
    }

    @Test
    void testInsertDummies() {
        IntStream.rangeClosed(1, 100).forEach(i -> {
            Memo memo = Memo.builder().memoText("Sample..." + i).build();
            memoRepository.save(memo);
        });
    }

    @Test
    void testSelect() {

        //DB에 존재하는 mno
        Long mno = 100L;

        Optional<Memo> result = memoRepository.findById(mno);

        System.out.println("============================================");

        if (result.isPresent()) {
            Memo memo = result.get();
            System.out.println(memo);
        }
    }

    @Transactional
    @Test
    void testSelect2() {

        //DB에 존재하는 mno
        Long mno = 100L;

        Memo memo = memoRepository.getOne(mno);

        System.out.println("============================================");

        System.out.println(memo);
    }

    @Test
    void testUpdate() {
        Memo memo = Memo.builder()
                .mno(100L)
                .memoText("Update Text")
                .build();

        System.out.println(memoRepository.save(memo));
    }

    @Test
    void testDelete() {
        Long mno = 100L;

        memoRepository.deleteById(mno);
    }

    @Test
    void testPageDefault() {
        //1페이지 10개
        Pageable pageable = PageRequest.of(0, 10);
        Page<Memo> result = memoRepository.findAll(pageable);
        System.out.println(result);

        System.out.println("========================================");

        System.out.println("Total Page: " + result.getTotalPages()); //총 페이지수
        System.out.println("Total Count: " + result.getTotalElements()); //전체 개수
        System.out.println("Page Number: " + result.getNumber()); //현재 페이지 번호
        System.out.println("Page Size: " + result.getSize()); //페이지당 데이터 개수
        System.out.println("has next page?: " + result.hasNext()); //다음 페이지 존재여부
        System.out.println("first page?: " + result.isFirst()); //시작 페이지(0) 여부
        System.out.println("last page?: " + result.isLast()); //끝 페이지 여부

        System.out.println("========================================");

        for (Memo memo : result.getContent()) {
            System.out.println(memo);
        }
    }

    @Test
    void testSort() {
        Sort sort1 = Sort.by("mno").descending();
        Sort sort2 = Sort.by("memoText").ascending();
        Sort sortAll = sort1.and(sort2);
        Pageable pageable = PageRequest.of(0, 10, sortAll);
        Page<Memo> result = memoRepository.findAll(pageable);

        result.get().forEach(memo -> System.out.println(memo));
    }

    @Test
    void testQueryMethods() {
        List<Memo> list = memoRepository.findByMnoBetweenOrderByMnoDesc(70L, 80L);
        for (Memo memo : list) {
            System.out.println(memo);
        }
    }

    @Test
    void testQueryMethodWithPageable() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("mno").descending());
        Page<Memo> result = memoRepository.findByMnoBetween(70L, 80L, pageable);
        result.get().forEach(memo -> {
            System.out.println(memo);
        });
    }

    @Commit
    @Transactional
    @Test
    void testDeleteQueryMethods() {
        memoRepository.deleteMemoByMnoLessThan(10L);
    }


}