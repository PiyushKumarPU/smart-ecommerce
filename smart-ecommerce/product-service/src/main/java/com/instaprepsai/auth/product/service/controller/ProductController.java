package com.instaprepsai.auth.product.service.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
public class ProductController {

    @GetMapping("/hello")
    public String hello() {
        return "Hello from Product Service";
    }

    @GetMapping("/featured")
    public String featured() {
        return "Featured products will be listed here";
    }
}
