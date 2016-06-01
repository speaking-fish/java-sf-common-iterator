package com.speakingfish.common.iterator;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class ChainIterator<T> extends AbstractIterator<T> {
    
    protected final Iterator<Iterator<T>> _iterators;
    protected                Iterator<T>  _current = null;

    public ChainIterator(Iterator<Iterator<T>> iterators) {
        super();
        _iterators = iterators;
    }
    
    @Override public boolean hasNext() {
        if(null == _current) {
            if(!_iterators.hasNext()) {
                return false;
                
            }
            
            try {
                _current = _iterators.next();
            } catch(NoSuchElementException e) {
                return false;
                
            }
        }
        while(!_current.hasNext()) {
            if(!_iterators.hasNext()) {
                return false;
                
            }
            
            _current = null;
            
            try {
                _current = _iterators.next();
            } catch(NoSuchElementException e) {
                return false;
                
            }
        }
        return true;
    }

    @Override public T next() {
        if(null == _current) {
            _current = _iterators.next();
        }
        while(!_current.hasNext()) {
            _current = _iterators.next();
        }
        return _current.next();
    }

    @Override public void remove() {
        if(null == _current) {
            throw new IllegalStateException();
        } else {
            _current.remove();
        }
    }
    
    

}
