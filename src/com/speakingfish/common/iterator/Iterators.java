package com.speakingfish.common.iterator;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.SortedMap;

import com.speakingfish.common.annotation.Compatibility.*;
import com.speakingfish.common.collection.CollectionHelper;
import com.speakingfish.common.function.Acceptor;
import com.speakingfish.common.function.Creator;
import com.speakingfish.common.function.Getter;
import com.speakingfish.common.function.Mapper;
import com.speakingfish.common.mapper.Mappers;
import com.speakingfish.common.type.Typecasts;
import com.speakingfish.common.type.Typed;

import static com.speakingfish.common.collection.CollectionHelper.*;
import static com.speakingfish.common.Compares.*;
import static com.speakingfish.common.Maps.*;

public class Iterators {
    
    public static <DEST, SRC> MapIterator<DEST, SRC> mapIterator(Iterator<SRC> src, Mapper<DEST, SRC> mapper) {
        return new MapIterator<DEST, SRC>(src, mapper);
    }
    
    public static <DEST, SRC> MapIterator<DEST, SRC> mapIterator(Mapper<DEST, SRC> mapper, Iterator<SRC> src) {
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
        return IterableIterator.create(src);
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

    public static <T, S> Iterator<T> lazyIterator(final Getter<Iterator<T>> getter) {
        return new LazyIterator<T>(getter);
    }

    public static <T, S> Getter<Iterator<T>> getterIteratorOf(final Getter<? extends Iterable<T>> getter) {
        return new Getter<Iterator<T>>() {
            public Iterator<T> get() {
                return getter.get().iterator();
            }};
    }
    
    public static <T> ReverseListIterator<T> reverseIterator(ListIterator<T> origin) {
        return new ReverseListIterator<T>(origin);
    }

    public static <T> ReverseListIterator<T> reverseIterator(List<T> origin) {
        return reverseIterator(origin.listIterator(origin.size()));
    }
    
    public static <T> RepeatGenerator<T> repeat(int count, T value) {
        return new RepeatGenerator<T>(count, value);
    }

    public static <
        T_DEST_COLLECTION extends Collection<T_DEST_ITEM>,
        T_DEST_ITEM,
        T_SRC_ITEM
    > Creator<T_DEST_COLLECTION, Iterator<T_SRC_ITEM>> collectCreator(
        final Getter<? extends T_DEST_COLLECTION> creator,
        final Mapper<T_DEST_ITEM, T_SRC_ITEM> mapper
    ) {
        return new Creator<T_DEST_COLLECTION, Iterator<T_SRC_ITEM>>() {
            public T_DEST_COLLECTION apply(Iterator<T_SRC_ITEM> src) {
                return collect(creator.get(), mapIterator(src, mapper));
            }
        };
    }
    
    public static <
        T_DEST_MAP extends Map<T_DEST_KEY, T_DEST_VALUE>,
        T_DEST_KEY                                      ,
        T_DEST_VALUE                                    ,
        T_SRC_KEY                                       ,
        T_SRC_VALUE
    > Creator<
        T_DEST_MAP,
        Iterator<Entry<T_SRC_KEY, T_SRC_VALUE>>
    > collectMapCreator(
        final Getter<? extends T_DEST_MAP> creator,
        final Mapper<Entry<T_DEST_KEY, T_DEST_VALUE>, Entry<T_SRC_KEY, T_SRC_VALUE>> mapper
    ) {
        return new Creator<T_DEST_MAP, Iterator<Entry<T_SRC_KEY, T_SRC_VALUE>>>() {
            public T_DEST_MAP apply(Iterator<Entry<T_SRC_KEY, T_SRC_VALUE>> src) {
                return collectMap(creator.get(), mapIterator(src, mapper));
            }
        };
    }
    
    public static <T extends Enum<T>> SortedMap<String, T> mapEnumIgnoreCase(Class<T> src) {
        return collectMap(
            CollectionHelper.<String, T>treeMap(COMPARATOR_STRING_IGNORE_CASE),
            mapIterator(
                listIterator(src.getEnumConstants()),
                makeEntryKeyMapper(Mappers.<T>mapperToString())
                )
            );
    }

    
    static { Dummy.dummy(); }

}
