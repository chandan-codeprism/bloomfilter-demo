package com.example.bloomfilterdemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controller for the root path
 */
@Controller
public class RootController {

    /**
     * Redirect from the root path to the Bloom Filter UI
     * @return A redirect to the Bloom Filter UI
     */
    @GetMapping("/")
    public String redirectToBloomFilter() {
        return "redirect:/bloom-filter";
    }
}