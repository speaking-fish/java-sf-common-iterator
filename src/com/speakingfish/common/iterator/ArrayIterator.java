package com.speakingfish.common.iterator;

import java.util.NoSuchElementException;

import static com.speakingfish.common.iterator.Iterators.*;

public class ArrayIterator<T> extends AbstractConstIterator<T> {

    public static <T> ConstIterator<T> create(T[] values) {
        if((null == values) || (0 == values.length)) {
            return noneIterator();
        } else {
            return new ArrayIterator<T>(values);
        }
    }

    private final T[] _values;

    private int _index = 0;

    protected ArrayIterator(T[] values) {
        _values = values;
    }

    public boolean hasNext() {
        return _index < _values.length;
    }

    public T next() {
        try {
            return _values[_index++];
        } catch(Exception e) {
            throw new NoSuchElementException();
        }
    }

}
