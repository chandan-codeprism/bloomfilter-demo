package com.example.bloomfilterdemo.service;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.io.*;

@Service
public class BloomFilterService {

    private static final int SIZE = 10000;

    // Using an array to represent the Bloom Filter's bit array
    private final int[] bitArray = new int[SIZE];

    private final File file = new File("bitArray.txt");  // New file to store the bit array

    @PostConstruct
    public void init() throws IOException {
        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line = reader.readLine();
                if (line != null) {
                    // Load the bit array from the file (it's represented as a string of 0s and 1s)
                    for (int i = 0; i < SIZE; i++) {
                        bitArray[i] = line.charAt(i) == '1' ? 1 : 0;
                    }
                }
            }
        } else {
            boolean created = file.createNewFile();
            if (!created) throw new IOException("Failed to create bitArray.txt");
        }
    }

    private int hash1(String input) {
        return Math.abs(input.hashCode()) % SIZE;
    }

    private int hash2(String input) {
        return Math.abs((input + "salt").hashCode()) % SIZE;
    }

    private void addToBitArray(String name) {
        bitArray[hash1(name)] = 1;
        bitArray[hash2(name)] = 1;
    }

    private boolean mightContain(String name) {
        return bitArray[hash1(name)] == 1 && bitArray[hash2(name)] == 1;
    }

    public boolean checkOrAddName(String name) {
        boolean exists = mightContain(name);
        if (!exists) {
            addToBitArray(name);
            // Persist the updated bit array to the file (as a string of 0s and 1s)
            try (FileWriter fw = new FileWriter(file)) {
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < SIZE; i++) {
                    sb.append(bitArray[i]);
                }
                fw.write(sb.toString());
            } catch (IOException e) {
                throw new RuntimeException("Failed to write to file", e);
            }
        }
        return exists;
    }
}
