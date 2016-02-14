package com.speakingfish.common.iterator;

import java.util.Iterator;

public abstract class AbstractIterator<T> implements Iterator<T> {

    public abstract void remove();
    public abstract boolean hasNext();
    public abstract T next();
    
}
