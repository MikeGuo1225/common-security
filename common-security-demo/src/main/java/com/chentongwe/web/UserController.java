package com.chentongwe.web;

import org.springframework.web.bind.annotation.*;

/**
 * @author chentongwei@bshf360.com 2018-05-16 18:20
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @GetMapping
    public String getUser() {
        return "getUser";
    }

    @PutMapping
    public String putUser() {
        return "putUser";
    }

    @PostMapping
    public String postUser() {
        return "postUser";
    }

    @DeleteMapping
    public String deleteUser() {
        return "deleteUser";
    }

    @GetMapping("/1")
    public String getUser1() {
        return "getUser1";
    }

    @PutMapping("/1")
    public String putUser1() {
        return "putUser1";
    }

    @PostMapping("/1")
    public String postUser1() {
        return "postUser1";
    }

    @DeleteMapping("/1")
    public String deleteUser1() {
        return "deleteUser1";
    }

    @GetMapping("/1/1")
    public String getUser11() {
        return "getUser1/1";
    }

    @PutMapping("/1/1")
    public String putUser11() {
        return "putUser1/1";
    }

    @PostMapping("/1/1")
    public String postUser11() {
        return "postUser1/1";
    }

    @DeleteMapping("/1/1")
    public String deleteUser11() {
        return "deleteUser1/1";
    }

}
