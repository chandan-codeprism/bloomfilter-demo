package com.example.bloomfilterdemo.service;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.BitSet;

@Service
public class BloomFilterService {

    @Value("${bloom.filter.size:10000}")
    private int size;

    @Value("${bloom.filter.file.path:bitArray.txt}")
    private String filePath;

    @Value("${bloom.filter.hash.functions:4}")
    private int numHashFunctions;

    // Using BitSet for more efficient storage
    private BitSet bitSet;

    private File file;

    @PostConstruct
    public void init() throws IOException {
        bitSet = new BitSet(size);
        file = new File(filePath);

        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line = reader.readLine();
                if (line != null && line.length() >= size) {
                    // Load the bit array from the file (it's represented as a string of 0s and 1s)
                    for (int i = 0; i < size; i++) {
                        if (line.charAt(i) == '1') {
                            bitSet.set(i);
                        }
                    }
                }
            }
        } else {
            boolean created = file.createNewFile();
            if (!created) throw new IOException("Failed to create " + filePath);
            // Initialize the file with zeros
            saveToFile();
        }
    }

    private int[] getHashValues(String input) {
        int[] hashes = new int[numHashFunctions];

        // The First two hash functions remain the same
        hashes[0] = Math.abs(input.hashCode()) % size;
        hashes[1] = Math.abs((input + "salt").hashCode()) % size;

        // Additional hash functions for better distribution
        if (numHashFunctions > 2) {
            hashes[2] = Math.abs((input + "pepper").hashCode()) % size;
        }
        if (numHashFunctions > 3) {
            hashes[3] = Math.abs((input + "sugar").hashCode()) % size;
        }

        return hashes;
    }

    private void addToBitArray(String name) {
        if (name == null) {
            throw new IllegalArgumentException("Name cannot be null");
        }

        int[] hashes = getHashValues(name);
        for (int hash : hashes) {
            bitSet.set(hash);
        }
    }

    private boolean mightContain(String name) {
        if (name == null) {
            return false;
        }

        int[] hashes = getHashValues(name);
        for (int hash : hashes) {
            if (!bitSet.get(hash)) {
                return false;
            }
        }
        return true;
    }

    public boolean checkOrAddName(String name) {
        boolean exists = mightContain(name);
        if (!exists) {
            addToBitArray(name);
            saveToFile();
        }
        return exists;
    }

    private void saveToFile() {
        try (FileWriter fw = new FileWriter(file)) {
            StringBuilder sb = new StringBuilder(size);
            for (int i = 0; i < size; i++) {
                sb.append(bitSet.get(i) ? '1' : '0');
            }
            fw.write(sb.toString());
        } catch (IOException e) {
            throw new RuntimeException("Failed to write to file", e);
        }
    }

    public void clear() {
        bitSet.clear();
        saveToFile();
    }
}
