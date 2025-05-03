package com.example.bloomfilterdemo.controller;

import com.example.bloomfilterdemo.service.BloomFilterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for Bloom Filter operations
 */
@RestController
@RequestMapping("/api/bloom-filter")
public class BloomFilterController {

    @Autowired
    private BloomFilterService bloomFilterService;

    /**
     * Check if a name might exist in the Bloom Filter and add it if it doesn't
     * @param name The name to check or add
     * @return A message indicating whether the name might already exist or was added
     */
    @PostMapping("/check-name")
    public ResponseEntity<String> checkOrAddName(@RequestParam String name) {
        if (name == null || name.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Name cannot be empty");
        }

        boolean exists = bloomFilterService.checkOrAddName(name);
        return ResponseEntity.ok(exists ? "Name might already exist." : "Name added.");
    }

    /**
     * Clear the Bloom Filter
     * @return A message indicating the Bloom Filter was cleared
     */
    @PostMapping("/clear")
    public ResponseEntity<String> clear() {
        bloomFilterService.clear();
        return ResponseEntity.ok("Bloom Filter cleared.");
    }

    /**
     * Health check endpoint
     * @return A message indicating the service is running
     */
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Bloom Filter service is running.");
    }
}
