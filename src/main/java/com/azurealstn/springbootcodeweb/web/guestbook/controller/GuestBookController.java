package com.azurealstn.springbootcodeweb.web.guestbook.controller;

import com.azurealstn.springbootcodeweb.web.guestbook.dto.GuestBookDTO;
import com.azurealstn.springbootcodeweb.web.guestbook.dto.PageRequestDTO;
import com.azurealstn.springbootcodeweb.web.guestbook.service.GuestBookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/guestbook")
public class GuestBookController {

    private final GuestBookService guestBookService;

    @GetMapping("/")
    public String index() {
        return "redirect:/guestbook/list";
    }

    @GetMapping("/list")
    public String list(PageRequestDTO pageRequestDTO, Model model) {
        log.info("list ... {}", pageRequestDTO);
        model.addAttribute("result", guestBookService.getList(pageRequestDTO));
        return "guestbook/list";
    }

    @GetMapping("/register")
    public String register() {
        log.info("register get...");
        return "/guestbook/register";
    }

    @PostMapping("/register")
    public String registerPost(GuestBookDTO dto, RedirectAttributes redirectAttributes) {
        log.info("dto={}", dto);
        Long gno = guestBookService.register(dto);
        redirectAttributes.addFlashAttribute("msg", gno);
        return "redirect:/guestbook/list";
    }

    @GetMapping("/read")
    public void read(Long gno, @ModelAttribute PageRequestDTO pageRequestDTO, Model model) {
        log.info("gno={}", gno);
        GuestBookDTO dto = guestBookService.read(gno);
        model.addAttribute("dto", dto);
    }

    @GetMapping("/modify")
    public String modify(Long gno, @ModelAttribute PageRequestDTO pageRequestDTO, Model model) {
        log.info("gno={}", gno);
        GuestBookDTO dto = guestBookService.read(gno);
        model.addAttribute("dto", dto);
        return "guestbook/modify";
    }

    @PostMapping("/modify")
    public String modify(GuestBookDTO dto, @ModelAttribute PageRequestDTO pageRequestDTO, RedirectAttributes redirectAttributes) {

        log.info("post modify dto={}", dto);

        guestBookService.modify(dto);
        redirectAttributes.addAttribute("gno", dto.getGno());
        redirectAttributes.addAttribute("page", pageRequestDTO.getPage());
        redirectAttributes.addAttribute("type", pageRequestDTO.getType());
        redirectAttributes.addAttribute("keyword", pageRequestDTO.getKeyword());

        log.info("redirect: {}", dto.getGno());
        log.info("redirect: {}", pageRequestDTO.getPage());

        return "redirect:/guestbook/read";
    }

    @PostMapping("/remove")
    public String remove(Long gno, RedirectAttributes redirectAttributes) {
        guestBookService.remove(gno);
        redirectAttributes.addFlashAttribute("msg", gno);
        return "redirect:/guestbook/list";
    }
}
