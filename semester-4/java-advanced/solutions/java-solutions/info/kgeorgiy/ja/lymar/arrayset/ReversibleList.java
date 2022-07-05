package info.kgeorgiy.ja.lymar.arrayset;

import java.util.AbstractList;
import java.util.List;
import java.util.RandomAccess;

/**
 * @author Pavel Lymar
 */
public class ReversibleList<T> extends AbstractList<T> implements RandomAccess {
    private final List<T> elements;
    private final Boolean isReversed;

    public ReversibleList(List<T> elements, Boolean isReversed) {
        this.elements = elements;
        this.isReversed = isReversed;
    }

    @Override
    public T get(int index) {
        return isReversed ? elements.get(size() - 1 - index) : elements.get(index);
    }

    @Override
    public int size() {
        return elements.size();
    }
}
