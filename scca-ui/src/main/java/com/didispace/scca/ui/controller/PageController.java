package com.didispace.scca.ui.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by stone-jin on 2018/5/28.
 */
@Controller
@RequestMapping("")
public class PageController {

    @GetMapping("/admin")
    public String adminPage() {
        return "index";
    }

    @GetMapping("/")
    public void indexPage(HttpServletResponse response) throws IOException {
        response.sendRedirect("/admin");
    }
}
