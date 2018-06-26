package com.didispace.scca.ui.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by stone-jin on 2018/5/28.
 */
@Controller
@RequestMapping("/admin")
public class PageController {

    @GetMapping("")
    public String adminPage() {
        return "index";
    }

}
