package com.azurealstn.springbootcodeweb.web.guestbook.service.impl;

import com.azurealstn.springbootcodeweb.domain.guestbook.GuestBook;
import com.azurealstn.springbootcodeweb.domain.guestbook.GuestBookRepository;
import com.azurealstn.springbootcodeweb.domain.guestbook.QGuestBook;
import com.azurealstn.springbootcodeweb.web.guestbook.dto.GuestBookDTO;
import com.azurealstn.springbootcodeweb.web.guestbook.dto.PageRequestDTO;
import com.azurealstn.springbootcodeweb.web.guestbook.dto.PageResultDTO;
import com.azurealstn.springbootcodeweb.web.guestbook.service.GuestBookService;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.function.Function;

@Slf4j
@RequiredArgsConstructor
@Service
public class GuestBookServiceImpl implements GuestBookService {

    private final GuestBookRepository guestBookRepository;

    @Override
    public Long register(GuestBookDTO dto) {

        log.info("DTO = {}", dto);
        GuestBook entity = dtoToEntity(dto);
        log.info("entity={}", entity);
        guestBookRepository.save(entity);
        return entity.getGno();
    }

    @Override
    public PageResultDTO<GuestBookDTO, GuestBook> getList(PageRequestDTO requestDTO) {
        Pageable pageable = requestDTO.getPageable(Sort.by("gno").descending());
        BooleanBuilder booleanBuilder = getSearch(requestDTO); //검색 조건 처리
        Page<GuestBook> result = guestBookRepository.findAll(booleanBuilder, pageable);
        Function<GuestBook, GuestBookDTO> fn = (entity -> entityToDto(entity));
        return new PageResultDTO<>(result, fn);
    }

    @Override
    public GuestBookDTO read(Long gno) {
        Optional<GuestBook> result = guestBookRepository.findById(gno);
        log.info("read gno={}", gno);
        log.info("read result={}", result);
        return result.isPresent() ? entityToDto(result.get()) : null;
    }

    @Override
    public void remove(Long gno) {
        guestBookRepository.deleteById(gno);
    }

    @Override
    public void modify(GuestBookDTO dto) {
        //업데이트 하는 항목: 제목, 내용
        Optional<GuestBook> result = guestBookRepository.findById(dto.getGno());
        if (result.isPresent()) {
            GuestBook entity = result.get();
            entity.changeTitle(dto.getTitle());
            entity.changeContent(dto.getContent());
            guestBookRepository.save(entity);
        }
    }

    //Quertdsl 처리
    private BooleanBuilder getSearch(PageRequestDTO pageRequestDTO) {
        String type = pageRequestDTO.getType();
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        QGuestBook qGuestBook = QGuestBook.guestBook;
        String keyword = pageRequestDTO.getKeyword();
        BooleanExpression expression = qGuestBook.gno.gt(0L); //gno > 0
        booleanBuilder.and(expression);

        if (type == null || type.trim().length() == 0) {
            return booleanBuilder;
        }

        //검색조건 작성하기
        BooleanBuilder conditionBuilder = new BooleanBuilder();
        if (type.contains("t")) {
            conditionBuilder.or(qGuestBook.title.contains(keyword));
        }
        if (type.contains("c")) {
            conditionBuilder.or(qGuestBook.content.contains(keyword));
        }
        if (type.contains("w")) {
            conditionBuilder.or(qGuestBook.writer.contains(keyword));
        }

        booleanBuilder.and(conditionBuilder);

        return booleanBuilder;
    }

}
