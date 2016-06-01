package com.speakingfish.common.iterator;

import java.util.Iterator;

import com.speakingfish.common.function.Getter;
import com.speakingfish.common.value.util.AbstractSimpleSingleton;

public class LazyIterator<T> extends AbstractIterator<T> {

    final AbstractSimpleSingleton<Iterator<T>> _preserve;
    
    public LazyIterator(final Getter<Iterator<T>> getter) {
        _preserve = new AbstractSimpleSingleton<Iterator<T>>() {
            @Override protected Iterator<T> create() {
                return getter.get();
            }
        };    
    }
    
    @Override public boolean hasNext() {
        return _preserve.get().hasNext();
    }

    @Override public T next() {
        return _preserve.get().next();
    }

    @Override public void remove() {
        _preserve.get().remove();
    }

}
