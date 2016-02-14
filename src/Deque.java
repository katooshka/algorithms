import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Author: katooshka
 * Date: 2/13/16.
 */

public class Deque<Item> implements Iterable<Item> {
    private Node<Item> first = null;
    private Node<Item> last = null;
    private int size = 0;
    private int version = 0;

    private static class Node<T> {
        private T item;
        private Node<T> left;
        private Node<T> right;

        Node(T item, Node<T> left, Node<T> right) {
            this.item = item;
            this.left = left;
            this.right = right;
        }
    }

    private static class DequeIterator<Item> implements Iterator<Item> {
        private final int initialVersion;
        private final Deque<Item> deque;
        private Node<Item> next;

        public DequeIterator(Node<Item> currentNode, int initialVersion, Deque<Item> deque) {
            this.initialVersion = initialVersion;
            this.next = currentNode;
            this.deque = deque;
        }

        @Override
        public boolean hasNext() {
            validateNotChanged();
            return next != null;
        }

        @Override
        public Item next() {
            validateNotChanged();
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            Item result = next.item;
            next = next.right;
            return result;
        }

        private void validateNotChanged() {
            if (initialVersion != deque.version) {
                throw new ConcurrentModificationException();
            }
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    public void addFirst(Item item) {
        validateNotNull(item);
        version++;
        if (isEmpty()) {
            size = 1;
            first = new Node<Item>(item, null, null);
            last = first;
            return;
        }
        size++;
        Node<Item> oldFirst = first;
        first = new Node<Item>(item, null, oldFirst);
        oldFirst.left = first;
    }

    public void addLast(Item item) {
        validateNotNull(item);
        version++;
        if (isEmpty()) {
            size = 1;
            last = new Node<Item>(item, null, null);
            first = last;
            return;
        }
        size++;
        Node<Item> oldLast = last;
        last = new Node<Item>(item, oldLast, null);
        oldLast.right = last;
    }

    public Item removeFirst() {
        validateNotEmpty();
        version++;
        if (size == 1) {
            size = 0;
            Item item = first.item;
            first = null;
            last = null;
            return item;
        }
        size--;
        Item item = first.item;
        first = first.right;
        first.left = null;
        return item;
    }

    public Item removeLast() {
        validateNotEmpty();
        version++;
        if (size == 1) {
            size = 0;
            Item item = last.item;
            first = null;
            last = null;
            return item;
        }
        size--;
        Item item = last.item;
        last = last.left;
        last.right = null;
        return item;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    private void validateNotNull(Item item) {
        if (item == null) {
            throw new NullPointerException();
        }
    }

    private void validateNotEmpty() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
    }

    public Iterator<Item> iterator() {
        return new DequeIterator<Item>(first, version, this);
    }

    public static void main(String[] args) {
        Deque<String> deque = new Deque<String>();
        deque.addFirst("katya");
        deque.addLast("jenia");
        deque.addFirst("aydar");
        for (String element : deque) {
            System.out.println(element);
        }
    }
}
