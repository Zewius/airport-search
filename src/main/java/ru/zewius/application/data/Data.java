package ru.zewius.application.data;

import ru.zewius.utils.autocomplete.Autocomplete;
import ru.zewius.utils.compressor.CompressedString;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Класс-контейнер, предоставляющий удобный доступ ко всем необходимым модулям ({@link Autocomplete автозаполнение},
 * {@link CompressedString сжатые строк}) программы, а также отвечающий за обработку исходных данных.
 */
public class Data {
    private final Autocomplete<CompressedString> autocomplete;

    public Data() {
        this.autocomplete = new Autocomplete<>();
    }

    public void buildAutocomplete(String path, int columnNumber) throws IOException {
        try (BufferedReader fileReader = new BufferedReader(new FileReader(path))) {
            String inputLine;
            while ((inputLine = fileReader.readLine()) != null) {
                String selectedColumn = removeDoubleQuotes(inputLine.split(",")[columnNumber - 1]);
                autocomplete.insert(selectedColumn, new CompressedString(inputLine));
            }
        }
    }

    public Iterable<String> getKeysByPrefix(String prefix) {
        return autocomplete.keysWithPrefix(prefix);
    }

    public String getValueByKey(String key) throws IOException {
        return autocomplete.getValueByKey(key).unzip();
    }

    private static String removeDoubleQuotes(final String value) {
        if (value.charAt(0) == '\"' && value.charAt(value.length() - 1) == '\"') {
            return value.substring(1, value.length() - 1);
        }
        return value;
    }
}
