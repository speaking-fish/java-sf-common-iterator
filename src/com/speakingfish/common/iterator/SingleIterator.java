package com.speakingfish.common.iterator;

import java.util.NoSuchElementException;

public class SingleIterator<T> extends AbstractConstIterator<T> {

    private final T _value;
    private boolean _accessed = false;

    public SingleIterator(T value) {
        _value = value;
    }

    public boolean hasNext() {
        return !_accessed;
    }

    public T next() {
        if(!_accessed) {
            _accessed = true;
            return _value;
        } else {
            throw new NoSuchElementException();
        }
    }

}
