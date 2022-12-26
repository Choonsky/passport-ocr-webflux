package com.nemirovsky.passportocrwebflux.controller;

import jakarta.annotation.security.RolesAllowed;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    @GetMapping("/")
    public String mainPage() {
        return "Hello Everybody on Main page!!";
    }
    @RolesAllowed("ROLE_ADMIN")
    @GetMapping("/admin")
    public String admin() {
        return "Hello Admin!";
    }

    @RolesAllowed({ "ROLE_ADMIN", "ROLE_USER" })
    @GetMapping("/users")
    public String resources() {
        return "Hello User!";
    }

    @GetMapping("/about")
    public String about() {
        return "Hello Everybody on about page!";
    }

    @GetMapping("/news")
    public String news() {
        return "Hello Everybody on news page!";
    }



}
