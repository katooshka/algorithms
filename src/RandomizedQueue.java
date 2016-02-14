import edu.princeton.cs.algs4.StdRandom;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Author: katooshka
 * Date: 2/14/16.
 */

public class RandomizedQueue<Item> implements Iterable<Item> {
    private static final int INITIAL_CAPACITY = 8;
    @SuppressWarnings("unchecked")
    private Item[] queue = (Item[]) new Object[INITIAL_CAPACITY];
    private int size = 0;
    private int version = 0;

    private static class RandomizedQueueIterator<Item> implements Iterator<Item> {
        private final int initialVersion;
        private final RandomizedQueue<Item> randomizedQueue;
        private final int[] indexMapping;
        private int index = 0;

        public RandomizedQueueIterator(int version, RandomizedQueue<Item> randomizedQueue) {
            this.initialVersion = version;
            this.randomizedQueue = randomizedQueue;
            this.indexMapping = randomPermutation(randomizedQueue.size);
        }

        private static int[] randomPermutation(int n) {
            int[] permutationArray = new int[n];
            for (int i = 0; i < n; i++) {
                permutationArray[i] = i;
            }
            StdRandom.shuffle(permutationArray);
            return permutationArray;
        }

        @Override
        public boolean hasNext() {
            validateNotChanged();
            return index != randomizedQueue.size();
        }

        @Override
        public Item next() {
            validateNotChanged();
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            Item result = randomizedQueue.queue[indexMapping[index]];
            index++;
            return result;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
        private void validateNotChanged() {
            if (initialVersion != randomizedQueue.version) {
                throw new ConcurrentModificationException();
            }
        }

    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void enqueue(Item item) {
        if (item == null) {
            throw new NullPointerException();
        }
        if (size == queue.length) {
            queue = doubleQueue();
        }
        queue[size] = item;
        size++;
        version++;
    }

    public Item dequeue() {
        if (size == 0) {
            throw new NoSuchElementException();
        }
        int randomIndex = StdRandom.uniform(size);
        Item randomElement = queue[randomIndex];
        queue[randomIndex] = queue[size - 1];
        queue[size - 1] = null;
        size--;
        version++;
        if (size <= queue.length / 4 && queue.length > INITIAL_CAPACITY) {
            queue = decreaseQueue();
        }
        return randomElement;
    }

    public Item sample() {
        if (size == 0) {
            throw new NoSuchElementException();
        }
        int randomIndex = StdRandom.uniform(size);
        return queue[randomIndex];
    }

    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator<Item>(version, this);
    }

    private Item[] doubleQueue() {
        @SuppressWarnings("unchecked")
        Item[] newQueue = (Item[]) new Object[queue.length * 2];
        for (int i = 0; i < size; i++) {
            newQueue[i] = queue[i];
        }
        return newQueue;
    }

    private Item[] decreaseQueue() {
        @SuppressWarnings("unchecked")
        Item[] newQueue = (Item[]) new Object[queue.length / 2];
        for (int i = 0; i < size; i++) {
            newQueue[i] = queue[i];
        }
        return newQueue;
    }

    public static void main(String[] args) {
        RandomizedQueue<String> queue = new RandomizedQueue<String>();
        queue.enqueue("a");
        queue.enqueue("b");
        queue.enqueue("c");
        for (String s : queue) {
            System.out.println(s);
        }
    }

}
