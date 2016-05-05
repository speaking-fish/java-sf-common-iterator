package com.speakingfish.common.iterator;

import static com.speakingfish.common.iterator.Iterators.*;

import java.util.Iterator;

public class IterableConstIteratorWrapper<T> extends IterableIterator<T> implements ConstIterator<T> {

    public static final IterableConstIteratorWrapper<?> INSTANCE = new IterableConstIteratorWrapper<Object>(emptyIterator());
    
    @SuppressWarnings("unchecked")
    public static <T> Iterable<T> create(final Iterator<T> src) {
        if(null == src) {
            return (Iterable<T>) INSTANCE;
        } else if(src instanceof IterableConstIteratorWrapper) {
            return (IterableConstIteratorWrapper<T>) src;
        } else {
            return new IterableConstIteratorWrapper<T>(src);
        }
    }
    
    public IterableConstIteratorWrapper(Iterator<T> origin) {
        super(origin);
    }

    @Override public void remove() {
        throw new UnsupportedOperationException("This iterator is readonly.");
    }

}
