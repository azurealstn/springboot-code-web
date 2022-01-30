package com.azurealstn.springbootcodeweb.web.guestbook.service;

import com.azurealstn.springbootcodeweb.domain.guestbook.GuestBook;
import com.azurealstn.springbootcodeweb.web.guestbook.dto.GuestBookDTO;
import com.azurealstn.springbootcodeweb.web.guestbook.dto.PageRequestDTO;
import com.azurealstn.springbootcodeweb.web.guestbook.dto.PageResultDTO;

public interface GuestBookService {

    Long register(GuestBookDTO dto);

    PageResultDTO<GuestBookDTO, GuestBook> getList(PageRequestDTO requestDTO);

    GuestBookDTO read(Long gno);

    void remove(Long gno);

    void modify(GuestBookDTO dto);

    default GuestBook dtoToEntity(GuestBookDTO dto) {
        GuestBook entity = GuestBook.builder()
                .gno(dto.getGno())
                .title(dto.getTitle())
                .content(dto.getContent())
                .writer(dto.getWriter())
                .build();
        return entity;
    }

    default GuestBookDTO entityToDto(GuestBook entity) {
        GuestBookDTO dto = GuestBookDTO.builder()
                .gno(entity.getGno())
                .title(entity.getTitle())
                .content(entity.getContent())
                .writer(entity.getWriter())
                .regDate(entity.getRegDate())
                .modDate(entity.getModDate())
                .build();
        return dto;
    }
}
