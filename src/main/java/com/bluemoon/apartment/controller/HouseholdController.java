package com.bluemoon.apartment.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/household")
public class HouseholdController {

    @GetMapping
    public String list() {
        return "household/list";
    }

    @GetMapping("/create")
    public String create() {
        return "household/create";
    }

    @GetMapping("/edit")
    public String edit() {
        return "household/edit";
    }

    @GetMapping("/detail")
    public String detail() {
        return "household/detail";
    }
}