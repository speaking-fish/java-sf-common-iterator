package com.speakingfish.common.iterator;

import static com.speakingfish.common.iterator.Iterators.reverseIterator;

import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import com.speakingfish.common.function.Acceptor;
import com.speakingfish.common.function.Getter;
import com.speakingfish.common.function.Mapper;
import com.speakingfish.common.mapper.Mappers;
import com.speakingfish.common.type.Typecasts;
import com.speakingfish.common.type.Typed;

public class Iterators {
    
    public static <DEST, SRC> MapIterator<DEST, SRC> mapIterator(Iterator<SRC> src, Mapper<DEST, SRC> mapper) {
        return new MapIterator<DEST, SRC>(src, mapper);
    }
    
    public static <T> AcceptIterator<T> acceptIterator(Iterator<T> src, Acceptor<T> acceptor) {
        return new AcceptIterator<T>(src, acceptor);
    }

    public static <T> AcceptIterator<T> acceptIterator(Acceptor<T> acceptor, Iterator<T> src) {
        return new AcceptIterator<T>(src, acceptor);
    }
    
    public static <T> AcceptIterator<T> acceptAssigned(Iterator<T> src) {
        return acceptIterator(Mappers.<T>acceptAssigned(), src);
    }
    
    public static <T> Iterable<T> iterableOf(final Iterator<T> src) {
        return new Iterable<T>() {
            public Iterator<T> iterator() {
                return src;
            }};
    }

    public static <T> ConstIterator<T> asConst(Iterator<T> src) {
        return ConstIteratorWrapper.create(src);
    }

    public static <T> ConstIterator<T> emptyIterator(Typed<T> proto) {
        return castConstIterator(EmptyIterator.INSTANCE, proto);
    }

    public static <T> ConstIterator<T> emptyIterator() {
        return emptyIterator(Typecasts.<T>typed());
    }

    public static <T> ConstIterator<T> singleIterator(T value) {
        return new SingleIterator<T>(value);
    }

    @SafeVarargs
    public static <T> ConstIterator<T> listIterator(T... values) { return ArrayIterator.create(values); }
    
    @SuppressWarnings("unchecked")
    public static <T> Iterator<T> castIterator(Iterator<?> src, Typed<T> proto) {
        return (Iterator<T>) src;
    }
    
    @SuppressWarnings("unchecked")
    public static <T> ConstIterator<T> castConstIterator(ConstIterator<?> src, Typed<T> proto) {
        return (ConstIterator<T>) src;
    }
    
    public static <T> ChainIterator<T> iteratorChain(Iterator<Iterator<T>> iterators) {
        return new ChainIterator<T>(iterators);
    }
    
    public static <T> ChainIterator<T> iteratorChain(Iterable<Iterator<T>> iterators) {
        return new ChainIterator<T>(iterators.iterator());
    }
    
    public static <T, S> Iterator<T> generator(final S fixedValue, final Mapper<T, S> mapper) {
        return new AbstractGenerator<T>() {
            @Override protected T nextValue() {
                return mapper.apply(fixedValue);
            }};
    }

    public static <T, S> Iterator<T> lazyGenerator(final Getter<T> getter) {
        return new AbstractGenerator<T>() {
            @Override protected T nextValue() {
                return getter.get();
            }};
    }
    
    public static <T> ReverseListIterator<T> reverseIterator(ListIterator<T> origin) {
        return new ReverseListIterator<T>(origin);
    }

    public static <T> ReverseListIterator<T> reverseIterator(List<T> origin) {
        return reverseIterator(origin.listIterator(origin.size()));
    }
    

}
