package com.azurealstn.springbootcodeweb.web.guestbook.service;

import com.azurealstn.springbootcodeweb.domain.guestbook.GuestBook;
import com.azurealstn.springbootcodeweb.web.guestbook.dto.GuestBookDTO;

public interface GuestBookService {

    Long register(GuestBookDTO dto);

    default GuestBook dtoToEntity(GuestBookDTO dto) {
        GuestBook entity = GuestBook.builder()
                .gno(dto.getGno())
                .title(dto.getTitle())
                .content(dto.getContent())
                .writer(dto.getWriter())
                .build();
        return entity;
    }
}
