package org.example.util.writer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SequenceWriter;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import org.example.exception.WriterException;

import java.io.IOException;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CSVWriterImpl implements Writer {
    private final char SEPARATOR = ';';
    private final String logDirectoryPath = "";
    private final SequenceWriter writer;

    public CSVWriterImpl(Class<?> clazz, PrintStream output) throws IOException {
        CsvSchema.Builder builder = CsvSchema.builder();
        List<Field> fields = Arrays.stream(clazz.getDeclaredFields())
                .collect(Collectors.toList());

        fields.forEach(field -> builder.addArrayColumn(field.getName()));

        CsvSchema schema = builder.build()
                .withHeader()
                .withColumnSeparator(SEPARATOR);

        writer = new CsvMapper()
                .configure(JsonGenerator.Feature.AUTO_CLOSE_TARGET, false)
                .writerFor(clazz)
                .with(schema)
                .writeValues(output);
    }

    @Override
    public void write(Object data) throws WriterException {
        try {
            writer.write(data);
        } catch (IOException e) {
            throw new WriterException();
        }
    }
}
