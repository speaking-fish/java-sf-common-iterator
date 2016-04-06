package com.speakingfish.common.iterator;

import java.util.NoSuchElementException;

public class RepeatGenerator<T> extends AbstractConstIterator<T> {

    protected final T _value;

    protected int _counter;

    public RepeatGenerator(int count, T value) {
        _value   = value;
        _counter = count;
    }

    @Override public boolean hasNext() { return 0 < _counter; }

    @Override public T next() {
        if(hasNext()) {
            --_counter;
            return _value;
        } else {
            throw new NoSuchElementException();
        }
    }

}
