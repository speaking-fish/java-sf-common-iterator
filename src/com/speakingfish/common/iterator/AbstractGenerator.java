package com.speakingfish.common.iterator;

import java.util.NoSuchElementException;

public abstract class AbstractGenerator<T> extends AbstractConstIterator<T>{
    
    protected boolean          _hasValue  = false;
    protected T                _value     = null;
    protected RuntimeException _exception = null;

    @Override public boolean hasNext() {
        if(null != _exception) {
            return false;
            
        }
        if(_hasValue) {
            return true;
            
        }
        
        try {
            _value = next();
            _hasValue = true;
        } catch(RuntimeException e) {
            _hasValue = false;
        }
        
        return _hasValue;
    }

    @Override public T next() {
        if(_hasValue) {
            _hasValue = false;
            try {
                return _value;
                
            } finally {
                _value = null;
            }
            
        }
        
        if(null != _exception) {
            final NoSuchElementException e = new NoSuchElementException();
            e.initCause(_exception);
            throw e;
            
        }
        
        try {
            return nextValue();
            
        } catch(NoSuchElementException e) {
            _exception = e;
            throw e;
            
        } catch(RuntimeException catched) {
            _exception = catched;
            final NoSuchElementException e = new NoSuchElementException();
            e.initCause(_exception);
            throw e;
            
        }
        
    }

    protected abstract T nextValue();

}
