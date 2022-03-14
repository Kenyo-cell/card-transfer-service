package org.example.util.writer;

import org.example.exception.WriterException;

public interface Writer {
    boolean write(Object data) throws WriterException;
}
