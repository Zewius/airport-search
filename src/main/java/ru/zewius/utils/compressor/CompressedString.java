package ru.zewius.utils.compressor;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * Класс, реализующий эффективный способ хранения строк с помощью сжатия (с возможностью распаковки сжатых строк).
 * В основе сжатия и распаковки лежит {@link java.util.zip GZIP}.
 */
public class CompressedString {
    private final byte[] compressed;

    public CompressedString(final String data) throws IOException {
        if (data == null || data.isEmpty()) {
            throw new IllegalArgumentException("Cannot compress null or empty string");
        }
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            GZIPOutputStream gzipOutputStream = new GZIPOutputStream(byteArrayOutputStream);
            gzipOutputStream.write(data.getBytes(StandardCharsets.UTF_8));
            gzipOutputStream.close();

            compressed = byteArrayOutputStream.toByteArray();
        }
    }

    public String unzip() throws IOException {
        try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(compressed)) {
            GZIPInputStream gzipInputStream = new GZIPInputStream(byteArrayInputStream);
            InputStreamReader inputStreamReader = new InputStreamReader(gzipInputStream, StandardCharsets.UTF_8);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            return bufferedReader.lines().collect(Collectors.joining());
        }
    }
}

