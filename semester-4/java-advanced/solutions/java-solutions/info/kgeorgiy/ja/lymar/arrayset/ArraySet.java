package info.kgeorgiy.ja.lymar.arrayset;

import java.util.*;

/**
 * @author Pavel Lymar
 */
public class ArraySet<T> extends AbstractSet<T> implements NavigableSet<T> {
    private final ReversibleList<T> elements;
    private final Comparator<? super T> comparator;

    // :NOTE: Collections.emptyList()
    public ArraySet() {
        this(Collections.emptyList());
    }

    // :NOTE: redundant TreeSet
    public ArraySet(Collection<? extends T> collection) {
        this(collection, null);
    }

    public ArraySet(Comparator<? super T> comparator) {
        this(Collections.emptyList(), comparator);
    }

    public ArraySet(Collection<? extends T> collection, Comparator<? super T> comparator) {
        this.comparator = comparator;
        TreeSet<T> tree = new TreeSet<>(comparator);
        tree.addAll(collection);
        elements = new ReversibleList<>(new ArrayList<>(tree), false);
    }

    private T getOrNull(int index) {
        return 0 <= index && index < size() ? elements.get(index) : null;
    }

    private int findElementIndex(T t, boolean isLower, boolean inclusive) {
        int id = Collections.binarySearch(elements, t, comparator);
        return id < 0 ? -id - 1 + (isLower ? -1 : 0) : id + (isLower ? -1 : 1) * (inclusive ? 0 : 1);
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean contains(Object o) {
        return Collections.binarySearch(elements, (T) o, comparator) >= 0;
    }

    @Override
    public T lower(T t) {
        return getOrNull(findElementIndex(t, true, false));
    }

    @Override
    public T floor(T t) {
        return getOrNull(findElementIndex(t, true, true));
    }

    @Override
    public T ceiling(T t) {
        return getOrNull(findElementIndex(t, false, true));
    }

    @Override
    public T higher(T t) {
        return getOrNull(findElementIndex(t, false, false));
    }

    @Override
    // :NOTE: immutable
    public T pollFirst() {
        throw new UnsupportedOperationException("This is immutable set");
    }

    @Override
    public T pollLast() {
        throw new UnsupportedOperationException("This is immutable set");
    }

    @Override
    public Iterator<T> iterator() {
        return elements.iterator();
    }

    @Override
    public NavigableSet<T> descendingSet() {
        return new ArraySet<>(new ReversibleList<>(elements, true), comparator.reversed());
    }

    @Override
    public Iterator<T> descendingIterator() {
        return descendingSet().iterator();
    }

    private NavigableSet<T> getSubSet(T fromElement, boolean fromInclusive,
                                      T toElement, boolean toInclusive, boolean flag) {
        int fromId = findElementIndex(fromElement, false, fromInclusive);
        int toId = findElementIndex(toElement, true, toInclusive);
        if (flag && fromId >= toId) {
            throw new IllegalArgumentException();
        }
        return fromId > toId ? new ArraySet<>(comparator) :
                new ArraySet<>(elements.subList(fromId, toId + 1), comparator);
    }

    @Override
    public NavigableSet<T> subSet(T fromElement, boolean fromInclusive, T toElement, boolean toInclusive) {
        return getSubSet(fromElement, fromInclusive, toElement, toInclusive, true);
    }

    @Override
    // :NOTE: subSet
    public NavigableSet<T> headSet(T toElement, boolean inclusive) {
        return isEmpty() ? new ArraySet<>(comparator) :
                getSubSet(first(), true, toElement, inclusive, false);
    }

    @Override
    public NavigableSet<T> tailSet(T fromElement, boolean inclusive) {
        return isEmpty() ? new ArraySet<>(comparator) :
                getSubSet(fromElement, inclusive, last(), true, false);
    }

    @Override
    public Comparator<? super T> comparator() {
        return comparator;
    }

    @Override
    public SortedSet<T> subSet(T fromElement, T toElement) {
        return subSet(fromElement, true, toElement, false);
    }

    @Override
    public SortedSet<T> headSet(T toElement) {
        return headSet(toElement, false);
    }

    @Override
    public SortedSet<T> tailSet(T fromElement) {
        return tailSet(fromElement, true);
    }

    @Override
    public T first() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        return elements.get(0);
    }

    @Override
    public T last() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        return elements.get(size() - 1);
    }

    @Override
    public int size() {
        return elements.size();
    }
}
