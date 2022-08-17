package ru.zewius.utils.autocomplete;

import ru.zewius.utils.autocomplete.container.TST;

import java.util.Queue;

/**
 * Класс, предоставляющий функцию автозаполнения (autocomplete). Использует {@link TST тернарное дерево поиска}
 * в качестве контейнера данных.
 *
 * @param <V> тип значения, которое будет закреплено за ключом.
 */
public class Autocomplete<V> {
    private final TST<V> container;

    public Autocomplete() {
        this.container = new TST<>();
    }

    public void insert(String key, V value) {
        container.put(key, value);
    }


    public V getValueByKey(String key) {
        return container.get(key);
    }

    public int size() {
        return container.size();
    }

    public Queue<String> keys() {
        return container.keys();
    }

    public Queue<String> keysWithPrefix(String prefix) {
        return container.keysWithPrefix(prefix);
    }
}
