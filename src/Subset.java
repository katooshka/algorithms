import edu.princeton.cs.algs4.StdIn;

/**
 * Author: katooshka
 * Date: 2/14/16.
 */
public class Subset {

    public static void main(String[] args) {
        int count = Integer.parseInt(args[0]);
        String[] input = StdIn.readAllStrings();
        RandomizedQueue<String> queue = new RandomizedQueue<String>();
        for (String s : input) {
            queue.enqueue(s);
        }
        for (int i = 0; i < count; i++) {
            System.out.println(queue.dequeue());
        }
    }
}
