/**
 * One object of this class contains .............
 */
public class Node<E> {
    private E data;
    private Node<E> next;

    /**
     * Constructor constructs a linked list node with generic object and with
     * the specified node being next.
     * @param data Generic object for Node.
     * @param node Next node in linked list.
     */
    Node(E data, Node<E> node) {
        this.data = data;
        next = node;
    }

    /**
     * Constructor constructs a linked list node with country object and next
     * node being end of linked list.
     * @param data Generic object for node.
     */
    Node(E data) { this(data , null); }

    /**
     * Returns data in the node.
     * @return specified country.
     */
    public E getData() {
        return data;
    }

    /**
     * Returns reference to next node in linked list.
     * @return specified CountryNode reference.
     */
    public Node<E> getNext() {
        return next;
    }

    /**
     * Sets reference to next node in linked list.
     * @param next reference to next node in linked list.
     * @return the next node reference.
     */
    public Node<E> setNext(Node<E> next) { return this.next = next; }

    /**
     * Returns String representation of Generic data.
     * @return specified String.
     */
    @Override
    public String toString() { return data.toString(); }
}
