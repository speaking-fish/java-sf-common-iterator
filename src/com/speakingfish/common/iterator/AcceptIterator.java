package com.speakingfish.common.iterator;

import java.util.Iterator;
import java.util.NoSuchElementException;

import com.speakingfish.common.function.Acceptor;

/**
 * Note: Null values allowed.
 *
 * @param <T>
 */
public class AcceptIterator<T> extends AbstractIterator<T> {

    protected final Iterator<T> _src     ;
    protected final Acceptor<T> _acceptor;
    
    protected boolean _hasNextAssigned = false;
    protected boolean _hasNext         = false;
    protected boolean _nextAssigned    = false; // required for allowing null values
    protected boolean _allowRemove     = false;
    protected T       _next            = null ;
    
    public AcceptIterator(
        Iterator<T> src     ,
        Acceptor<T> acceptor
    ) {
        super();
        _src    = src   ;
        _acceptor = acceptor;
    }

    void prepareHasNext() {
        _allowRemove = false;
        if(!_hasNextAssigned) {
            _nextAssigned = false;
            while(true) {
                if(!_src.hasNext()) {
                    _hasNext         = false;
                    _hasNextAssigned = true;
                    break;
                    
                } else {
                    T value;
                    try {
                        value = _src.next();
                    } catch(NoSuchElementException e) {
                        _hasNext         = false;
                        _hasNextAssigned = true;
                        break;
                        
                    }
                    if(_acceptor.test(value)) {
                        _hasNext         = true;
                        _hasNextAssigned = true;
                        
                        _next            = value;
                        _nextAssigned    = true;
                        break;
                        
                    }
                }
            }
        }
    }
    
    @Override public boolean hasNext() {
        prepareHasNext();
        return _hasNext;
    }
    
    void prepareNext() {
        _hasNext         = false;
        _hasNextAssigned = false;
        if(!_nextAssigned) {
            while(true) {
                T value = _src.next(); // passthrough NoSuchElementException
                if(_acceptor.test(value)) {
                    _next         = value;
                    _nextAssigned = true;
                    break;
                    
                }
            }
        }
    }

    @Override public T next() {
        prepareNext();
        T result      = _next;
        _next         = null;
        _nextAssigned = false;
        _allowRemove  = true;
        return result;
    }

    @Override public void remove() {
        if(_allowRemove) {
            _src.remove();
        } else {
            throw new IllegalStateException();
        }
    }

}
