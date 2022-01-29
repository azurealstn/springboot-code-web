package com.azurealstn.springbootcodeweb.web.guestbook.service.impl;

import com.azurealstn.springbootcodeweb.domain.guestbook.GuestBook;
import com.azurealstn.springbootcodeweb.domain.guestbook.GuestBookRepository;
import com.azurealstn.springbootcodeweb.web.guestbook.dto.GuestBookDTO;
import com.azurealstn.springbootcodeweb.web.guestbook.service.GuestBookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
}
