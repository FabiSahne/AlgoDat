package dictionary;

import java.util.Comparator;
import java.util.Iterator;
import java.util.Objects;

public class SortedArrayDictionary<K, V> implements Dictionary<K, V>{

    private final Comparator<? super K> cmp;

    // Entry
    private static class Entry<K, V> {
        K key;
        V value;
        Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }
    private static final int DEF_CAPACITY = 16;
    private int size;
    private Entry<K, V>[] data;

    public SortedArrayDictionary() {
        // Natural ordering default
        cmp = (k1, k2) -> ((Comparable<K>) k1).compareTo(k2);
        size = 0;
        data = new Entry[DEF_CAPACITY];
    }

    @Override
    public V insert(K key, V value) {
        // if key already exists, update value
        for (int i = 0; i < size; i++) {
            if (cmp.compare(data[i].key, key) == 0) {
                V oldValue = data[i].value;
                data[i].value = value;
                return oldValue;
            }
        }
        // if array is full, double size
        if (size == data.length) {
            Entry[] tmp = new Entry[data.length * 2];
            System.arraycopy(data, 0, tmp, 0, data.length);
            data = tmp;
        }
        // find position to insert
        int i = 0;
        while (i < size && cmp.compare(data[i].key, key) < 0) {
            i++;
        }
        // shift elements to the right
        for (int j = size; j > i; j--) {
            data[j] = data[j - 1];
        }
        // insert new element
        data[i] = new Entry<>(key, value);
        size++;
        return null;
    }

    @Override
    public V search(K key) {
        for (int i = 0; i < size; i++) {
            if (cmp.compare(data[i].key, key) == 0) {
                return data[i].value;
            }
        }
        return null;
    }

    @Override
    public V remove(K key) {
        for (int i = 0; i < size; i++) {
            if (cmp.compare(data[i].key, key) == 0) {
                V value = data[i].value;
                for (int j = i; j < size - 1; j++) {
                    data[j] = data[j + 1];
                }
                size--;
                return value;
            }
        }
        return null;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Iterator<Dictionary.Entry<K, V>> iterator() {
        return new Iterator<>() {
            private int i = 0;

            @Override
            public boolean hasNext() {
                return i < size;
            }

            @Override
            public Dictionary.Entry<K, V> next() {
                if (i >= size) {
                    throw new IndexOutOfBoundsException();
                }
                return new Dictionary.Entry<>(data[i].key, data[i++].value);
            }
        };
    }
}
