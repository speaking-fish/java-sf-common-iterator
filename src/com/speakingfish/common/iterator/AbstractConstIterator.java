package com.speakingfish.common.iterator;

public abstract class AbstractConstIterator<T> extends AbstractIterator<T>  implements ConstIterator<T> {

    @Override public void remove() {
        throw new UnsupportedOperationException("This iterator is readonly.");
    }

}
