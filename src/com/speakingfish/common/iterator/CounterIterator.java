package com.speakingfish.common.iterator;

import java.util.Iterator;

public class CounterIterator<T> extends AbstractConstIterator<T> {

    protected final Iterator<T> _delegate;

    protected int _counter = 0;

    public CounterIterator(Iterator<T> delegate) {
        _delegate = delegate;
    }

    public int count() { return _counter; }

    @Override public boolean hasNext() { return _delegate.hasNext(); }

    @Override public T next() {
        final T result = _delegate.next();
        ++_counter;
        return result;
    }

}
