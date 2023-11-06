package com.example.demo.mapper;

import org.springframework.batch.item.file.FlatFileFooterCallback;
import org.springframework.batch.item.file.FlatFileHeaderCallback;

import java.io.IOException;
import java.io.Writer;

public class StudentHeaderFooterCallBack implements FlatFileHeaderCallback, FlatFileFooterCallback {
    private static final String OUTPUT_HEADER = "#Students";
    private static final String OUTPUT_FOOTER = "#eof";

    @Override
    public void writeHeader(Writer writer) throws IOException {
        writer.write(OUTPUT_HEADER + AppUtils.LINE_SEPARATOR);
    }

    @Override
    public void writeFooter(Writer writer) throws IOException {
        writer.write(OUTPUT_FOOTER);
    }

}
