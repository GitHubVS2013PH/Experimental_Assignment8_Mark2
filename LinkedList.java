import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * One object of this class contains a linked list of Generic data Nodes.
 */
public class LinkedList<E> implements Iterable<E> {
    private int size = 0;
    private Node<E> head = null, tail = null;

    /**
     * Constructor.
     */
    public LinkedList() { } // No additional initialization required

    /**
     * Adds Generic object to linked list of Generic Nodes.
     * @param data Generic object to add to linked list.
     * @return true if add is successful, otherwise false.
     */
    public boolean add(E data) {
        if (data == null)
            return false;

        if (head == null)
            head = tail = new Node<>(data);
        else
            tail = tail.setNext(new Node<>(data));
        ++size;
        return true;
    }

    /**
     * Returns number of nodes in linked list.
     * @return specified number.
     */
    public int size() {
        return size;
    }

    /**
     * Returns Generic object if it is contained in the linked list or null otherwise.
     * @param data Generic object being sought in linked list.
     * @return specified Generic object or null.
     */
    public E contains(E data) {
        if (data == null)
            return null;

        for (Node<E> node = head; node != null; node = node.getNext()) {
            if (data.equals(node.getData()))
                return node.getData();
        }
        return null;
    }

    /**
     * Returns Country object at index in linked list or null if index is out
     * of bounds.
     * @param index Position of Country object in linked list.
     * @return Specified Country object or null.
     */
    public E getIndex(int index) {
        if (index < 0 || index >= size)
            return null;

        Node<E> node = head;
        for (int position = 0; index != position; node = node.getNext(), ++position)
            continue; // intentionally empty loop
        return node.getData();
    }

    /**
     * Inserts Generic object into linked list at the position index.
     * @param data Generic object to be inserted into linked list.
     * @param index position in linked list to insert Generic object.
     */
    public void insertAtIndex(E data, int index) {
        if (index < 0)
            throw new IndexOutOfBoundsException("Negative index given to insertAtIndex(): " + index);

        if (head == null) // insert into empty list             // COULD USE ADD METHOD FOR NULL & LAST
            head = tail = new Node<>(data);

        else if (index >= size) // insert at end of list
            tail = tail.setNext(new Node<>(data));

        else if (index == 0) // insert at head of non-empty list
            head = new Node<>(data, head);
        else { // insert in middle of list
            Node<E> node = head;
            for (int pos = 0; pos < index - 1; ++pos, node = node.getNext())
                continue; // intentionally empty loop
            node.setNext(new Node<>(data, node.getNext()));
        }
        ++size;
    }

    /**
     * Returns String representation of linked list.
     * @return specified String.
     */
    @Override
    public String toString() {
        String rtnVal = "";

        Iterator<E> iter = iterator();
        while (iter.hasNext())
            rtnVal += iter.next().toString() + "\n";
/*
        for (Node<E> node = head; node != null; node = node.getNext())
            rtnVal += node.getData().toString() + "\n";*/

        return rtnVal;
    }

    /**
     * Returns iterator for ListIterator object.
     * @return specified iterator.
     */
    @Override
    public Iterator<E> iterator() { return new ListIterator(); }

    // inner class
    /**
     * One object of this class implements Iterator for LinkedList.
     */
    private class ListIterator implements Iterator<E> {
        private Node<E> current = head;

        /**
         * Constructor.
         */
        private ListIterator() { } // no additional initializations required

        /**
         * Returns true if LinkedList has another node during iteration.
         * @return specified boolean.
         */
        @Override
        public boolean hasNext() { return current != null; }

        /**
         * Returns data from next node in LinkedList.
         * @return specified data.
         * @throws NoSuchElementException if at end of LinkedList iteration.
         */
        @Override
        public E next() throws NoSuchElementException {
            if (!hasNext())
                throw new NoSuchElementException("LinkedList has no more elements.");

            E rtnVal = current.getData();
            current = current.getNext();
            return rtnVal;
        }
    } // end of inner class ListIterator
} // end of class LinkedList
