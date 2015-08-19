package com.speakingfish.common.iterator;

import java.util.Iterator;

public abstract class AbstractConstIterator<T> implements Iterator<T>, ConstIterator<T> {

    public void remove() {
        throw new UnsupportedOperationException("This iterator is readonly.");
    }

}
