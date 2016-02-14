package com.speakingfish.common.iterator;

import java.util.ListIterator;

public class ReverseListIterator<T> extends AbstractIterator<T> {
    
    protected final ListIterator<T> _origin;

    public ReverseListIterator(ListIterator<T> origin) {
        _origin = origin;
    }

    @Override public void remove() {
        _origin.remove();
    }

    @Override public boolean hasNext() { return _origin.hasPrevious(); }
    @Override public T          next() { return _origin.   previous(); }

}
