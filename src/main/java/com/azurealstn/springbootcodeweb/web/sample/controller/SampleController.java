package com.azurealstn.springbootcodeweb.web.sample.controller;

import com.azurealstn.springbootcodeweb.domain.sample.SampleDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
@Controller
@RequestMapping("/sample")
public class SampleController {

    @GetMapping("/ex1")
    public String ex1() {
        return "sample/ex1";
    }

    @GetMapping("/ex2")
    public String exModel(Model model) {
        List<SampleDTO> list = IntStream.rangeClosed(1, 20).asLongStream().mapToObj(i -> {
            SampleDTO dto = SampleDTO.builder()
                    .sno(i)
                    .first("first.." + i)
                    .last("last.." + i)
                    .regTime(LocalDateTime.now())
                    .build();
            return dto;
        }).collect(Collectors.toList());

        model.addAttribute("list", list);
        return "sample/ex2";
    }

    @GetMapping("/exTemplate")
    public String exTemplate() {
        return "sample/exTemplate";
    }

    @GetMapping("/exSidebar")
    public String exSidebar() {
        return "sample/exSidebar";
    }
}
