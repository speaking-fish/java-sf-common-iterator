package com.speakingfish.common.iterator;

import java.util.Iterator;

public class IterableIterator<T> extends AbstractIterator<T> implements Iterable<T> {
    
    public static <T> Iterable<T> create(final Iterator<T> src) {
        if(src instanceof IterableIterator) {
            return (IterableIterator<T>) src;
        } else {
            return new IterableIterator<T>(src);
        }
    }
    
    protected final Iterator<T> _origin;
    
    public IterableIterator(Iterator<T> origin) {
        super();
        _origin = origin;
    }

    public Iterator<T> iterator() { return this; }

    @Override public void    remove () {        _origin.remove (); }
    @Override public boolean hasNext() { return _origin.hasNext(); }
    @Override public T       next   () { return _origin.next   (); }

}
