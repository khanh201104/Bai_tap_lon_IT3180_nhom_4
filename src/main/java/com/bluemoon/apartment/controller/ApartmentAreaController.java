package com.bluemoon.apartment.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/apartment-area")
public class ApartmentAreaController {

    @GetMapping
    public String list() {
        return "apartment-area/list";
    }

    @GetMapping("/edit")
    public String edit() {
        return "apartment-area/edit";
    }
}