package org.example.util.writer;

import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import org.example.exception.WriterException;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CSVWriterImpl implements Writer {
    private final char SEPARATOR = ';';

    @Override
    public boolean write(Object data) throws WriterException {
        Class<?> clazz = data.getClass();
        List<Field> fields = Arrays.stream(clazz.getDeclaredFields()).collect(Collectors.toList());

        CsvSchema.Builder builder = CsvSchema.builder();

        fields.forEach(field -> builder.addArrayColumn(field.getName()));

        CsvSchema schema = builder.build()
                .withHeader()
                .withColumnSeparator(SEPARATOR);

        CsvMapper mapper = new CsvMapper();

        try {
            mapper.writerFor(clazz)
                    .with(schema)
                    .writeValue(System.out, data);
        } catch (IOException e) {
            throw new WriterException();
        }

        return true;
    }
}
