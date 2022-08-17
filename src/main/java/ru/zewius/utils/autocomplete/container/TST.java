package ru.zewius.utils.autocomplete.container;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * Класс, реализующий <b>Тернарное дерево поиска (Ternary Search Tree)</b> с возможностью хранения дополнительных
 * данных. Исходный алгоритм был взят <a href="https://algs4.cs.princeton.edu/52trie/">отсюда</a>.
 *
 * @author Robert Sedgewick
 * @author Kevin Wayne
 * @param <V> тип значения, которое будет закреплено за ключом.
 */
public class TST<V> {
    private int n;
    private Node<V> root;

    private static class Node<V> {
        private char character;
        private Node<V> left;
        private Node<V> mid;
        private Node<V> right;
        private V value;
    }

    public int size() {
        return n;
    }

    public boolean contains(String key) {
        if (key == null) {
            throw new IllegalArgumentException("argument to contains() is null");
        }
        return get(key) != null;
    }

    public V get(String key) {
        if (key == null) {
            throw new IllegalArgumentException("calls get() with null argument");
        }
        if (key.length() == 0) {
            throw new IllegalArgumentException("key must have length >= 1");
        }
        Node<V> x = get(root, key, 0);
        if (x == null) {
            return null;
        }
        return x.value;
    }

    private Node<V> get(Node<V> x, String key, int d) {
        if (x == null) {
            return null;
        }
        if (key.length() == 0) {
            throw new IllegalArgumentException("key must have length >= 1");
        }

        char c = key.charAt(d);

        if (c < x.character) {
            return get(x.left, key, d);
        } else if (c > x.character) {
            return get(x.right, key, d);
        } else if (d < key.length() - 1) {
            return get(x.mid, key, d + 1);
        } else {
            return x;
        }
    }

    public void put(String key, V val) {
        if (key == null || key.isEmpty()) {
            return;
        }
        if (!contains(key)) {
            n++;
        } else if (val == null) {
            n--;
        }
        root = put(root, key, val, 0);
    }

    private Node<V> put(Node<V> x, String key, V val, int d) {
        char c = key.charAt(d);

        if (x == null) {
            x = new Node<>();
            x.character = c;
        }

        if (c < x.character) {
            x.left = put(x.left, key, val, d);
        } else if (c > x.character) {
            x.right = put(x.right, key, val, d);
        } else if (d < key.length() - 1) {
            x.mid = put(x.mid, key, val, d + 1);
        } else {
            x.value = val;
        }
        return x;
    }

    public Queue<String> keys() {
        Queue<String> queue = new ArrayDeque<>();
        collect(root, new StringBuilder(), queue);
        return queue;
    }

    public Queue<String> keysWithPrefix(String prefix) {
        if (prefix == null) {
            throw new IllegalArgumentException("calls keysWithPrefix() with null argument");
        }
        Queue<String> queue = new ArrayDeque<>();
        Node<V> x = get(root, prefix, 0);
        if (x == null) {
            return queue;
        }
        if (x.value != null) {
            queue.offer(prefix);
        }
        collect(x.mid, new StringBuilder(prefix), queue);
        return queue;
    }

    private void collect(Node<V> x, StringBuilder prefix, Queue<String> queue) {
        if (x == null) {
            return;
        }
        collect(x.left, prefix, queue);
        if (x.value != null) {
            queue.offer(prefix.toString() + x.character);
        }
        collect(x.mid, prefix.append(x.character), queue);
        prefix.deleteCharAt(prefix.length() - 1);
        collect(x.right, prefix, queue);
    }
}
