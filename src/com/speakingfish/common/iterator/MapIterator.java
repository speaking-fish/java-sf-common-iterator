package com.speakingfish.common.iterator;

import java.util.Iterator;

import com.speakingfish.common.function.Mapper;

public class MapIterator<D, S> implements Iterator<D> {

    protected final Iterator<S>  _src   ;
    protected final Mapper<D, S> _mapper;
    
    public MapIterator(Iterator<S> src, Mapper<D, S> mapper) {
        super();
        _src    = src   ;
        _mapper = mapper;
    }

    @Override public boolean hasNext() {
        return _src.hasNext();
    }

    @Override public D next() {
        return _mapper.apply(_src.next());
    }

    @Override public void remove() {
        _src.remove();
    }

}
