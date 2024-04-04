package dictionary;

import java.util.Iterator;

public class HashDictionary<K, V> implements Dictionary<K, V> {

    private static class Entry<K, V> {
        K key;
        V value;
        Entry<K, V> next;
        Entry(K key, V value) {
            this.key = key;
            this.value = value;
            this.next = null;
        }
        Entry(K key, V value, Entry<K, V> next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }
    }

    private static final int DEFAULT_CAPACITY = 11;
    private int size;
    private Entry<K, V>[] data;

    public HashDictionary() {
        size = 0;
        data = new Entry[DEFAULT_CAPACITY];
    }

    public HashDictionary(int capacity) {
        size = 0;
        if (isPrime(capacity))
            data = new Entry[capacity];
        else
            data = new Entry[DEFAULT_CAPACITY];
    }

    private boolean isPrime(int n) {
            int factors = 0;
            for(int i = 1; i <= n; i++){
                if(n % i == 0) // ensure that you mod n not i
                    factors++;
            }
            // if factors count is equals to 2 then it is prime number else it's not prime number
            if(factors == 2)
                return true;
            else
                return false;
        }

    private void doubleCapacity() {
        // get next prime number
        int newCapacity = data.length * 2;
        while (!isPrime(newCapacity)) {
            newCapacity++;
        }
        Entry<K, V>[] tmp = new Entry[newCapacity];
        for (Entry<K, V> entry : data) {
            while (entry != null) {
                int hash = Math.floorMod(entry.key.hashCode(), tmp.length);
                tmp[hash] = new Entry<>(entry.key, entry.value, tmp[hash]);
                entry = entry.next;
            }
        }
        data = tmp;
    }

    @Override
    public V insert(K key, V value) {
        int hash = Math.floorMod(key.hashCode(), data.length);
        if (data[hash] == null) {
            data[hash] = new Entry<>(key, value);
        } else {
            Entry<K, V> entry = data[hash];
            while (true) {
                if (entry.key.equals(key)) {
                    V oldValue = entry.value;
                    entry.value = value;
                    return oldValue;
                }
                if (entry.next == null) {
                    break;
                }
                entry = entry.next;
            }
            entry.next = new Entry<>(key, value);
        }
        size++;
        if (size >= data.length / 2) {
            doubleCapacity();
        }
        return null;
    }

    @Override
    public V search(K key) {
        int hash = Math.floorMod(key.hashCode(), data.length);
        if (data[hash] == null) {
            return null;
        } else {
            while (data[hash] != null) {
                if (data[hash].key.equals(key)) {
                    return data[hash].value;
                }
                data[hash] = data[hash].next;
            }
        }
        return null;
    }

    @Override
    public V remove(K key) {
        int hash = Math.floorMod(key.hashCode(), data.length);
        if (data[hash] == null) {
            return null;
        } else {
            Entry<K, V> prev = null;
            Entry<K, V> entry = data[hash];
            while (entry != null) {
                if (entry.key.equals(key)) {
                    if (prev == null) {
                        data[hash] = entry.next;
                    } else {
                        prev.next = entry.next;
                    }
                    size--;
                    return entry.value;
                }
                prev = entry;
                entry = entry.next;
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
        return new Iterator<Dictionary.Entry<K, V>>() {

            private int i = 0;

            @Override
            public boolean hasNext() {
                for (int j = i; j < data.length; j++) {
                    if (data[j] != null) {
                        return true;
                    }
                }
                return false;
            }

            @Override
            public Dictionary.Entry<K, V> next() {
                while (data[i] == null) {
                    i++;
                }
                Entry<K, V> entry = data[i];
                i++;
                return new Dictionary.Entry<>(entry.key, entry.value);
            }
        };
    }
}
