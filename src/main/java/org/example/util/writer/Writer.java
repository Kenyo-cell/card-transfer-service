package org.example.util.writer;

import org.example.exception.WriterException;

public interface Writer {
    void write(Object data) throws WriterException;
}
