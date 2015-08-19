package com.speakingfish.common.iterator;

import java.util.NoSuchElementException;

public class EmptyIterator<T> extends AbstractConstIterator<T> {

    public static final ConstIterator<?> INSTANCE = new EmptyIterator<Object>();

    protected EmptyIterator() { }

    public boolean hasNext() {
        return false;
    }

    public T next() {
        throw new NoSuchElementException();
    }

}
