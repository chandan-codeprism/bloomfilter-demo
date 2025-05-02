package com.example.bloomfilterdemo.controller;

import com.example.bloomfilterdemo.service.BloomFilterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class BloomFilterController {

    @Autowired
    private BloomFilterService bloomFilterService;

    @PostMapping("/check-name")
    public String checkOrAddName(@RequestParam String name) {
        boolean exists = bloomFilterService.checkOrAddName(name);
        return exists ? "Name might already exist." : "Name added.";
    }
}
