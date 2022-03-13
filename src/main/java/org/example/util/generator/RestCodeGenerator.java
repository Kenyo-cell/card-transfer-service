package org.example.util.generator;

import java.util.Random;

public class RestCodeGenerator implements CodeGenerator {
    private Random random;
    private final int MAX_VALUE = 9999;

    public RestCodeGenerator() {
        random = new Random();
    }


    @Override
    public String generate() {
        return String.format("%4s", random.nextInt(MAX_VALUE)).replace(' ', '0');
    }
}
