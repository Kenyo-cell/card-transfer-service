package org.example.util.generator;

import java.util.concurrent.atomic.AtomicLong;

public class OperationIdGenerator {
    private final AtomicLong id;

    public OperationIdGenerator() {
        id = new AtomicLong();
    }

    public String generateId() {
        return String.valueOf(id.getAndIncrement());
    }
}
