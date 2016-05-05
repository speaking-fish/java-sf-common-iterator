package com.speakingfish.common.iterator;

import java.util.Iterator;

import static com.speakingfish.common.iterator.Iterators.*;

public class ConstIteratorWrapper<T>  extends AbstractConstIterator<T> implements ConstIterator<T> {

    public static <T> ConstIterator<T> create(final Iterator<T> origin) {
        if(null == origin) {
            return emptyIterator();
        } else if(origin instanceof ConstIterator) {
            return (ConstIterator<T>) origin;
        } else if(origin instanceof IterableIterator) {
            return new IterableConstIteratorWrapper<T>(origin);
        } else {
            return new ConstIteratorWrapper<T>(origin);
        }
    }

    protected final Iterator<T> _origin;

    protected ConstIteratorWrapper(final Iterator<T> origin) {
        _origin = origin;
    }

    public boolean hasNext() {
        return (null != _origin) && _origin.hasNext();
    }

    public T next() {
        return _origin.next();
    }

}
