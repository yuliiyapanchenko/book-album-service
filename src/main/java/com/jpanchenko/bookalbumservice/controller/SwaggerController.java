package com.jpanchenko.bookalbumservice.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RequestMapping("/")
public class SwaggerController {

    @ResponseBody
    @RequestMapping("/")
    public RedirectView index() {
        return documentation();
    }

    @ResponseBody
    @RequestMapping("/api-doc")
    public RedirectView documentation() {
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl("/swagger-ui.html");
        return redirectView;
    }
}
