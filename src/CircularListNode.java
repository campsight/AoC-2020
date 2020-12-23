import java.util.Objects;

public class CircularListNode<T> {
    public CircularListNode<T> next = this;
    public CircularListNode<T> previous = this;

    public final T value;

    public CircularListNode(T value) {
        this.value = value;
    }

    public CircularListNode<T> remove() {
        previous.next = next;
        next.previous = previous;

        return this;
    }

    public CircularListNode<T> getNextNode() {
        return this.next;
    }

    public CircularListNode<T> getPreviousNode() {
        return this.previous;
    }

    public CircularListNode<T> getNextNode(int count) {
        CircularListNode<T> rv = this;
        for (int i = 0; i < count; i++) {
            rv = rv.next;
        }
        return rv;
    }

    public CircularListNode<T> getPreviousNode(int count) {
        CircularListNode<T> rv = this;
        for (int i = 0; i < count; i++) {
            rv = rv.previous;
        }
        return rv;
    }

    public CircularListNode<T> insertAfter(CircularListNode<T> node) {
        next.previous = node;
        node.previous = this;
        node.next = next;
        this.next = node;

        return node;
    }

    public CircularListNode<T> insertBefore(CircularListNode<T> node) {
        previous.next = node;
        node.previous = previous;
        node.next = this;
        this.previous = node;

        return node;
    }

    @Override
    public String toString() {
        return next.toString(new StringBuilder(), this);
    }

    public String toString(StringBuilder prefix, CircularListNode<T> stop) {
        if (Objects.equals(this, stop)) {
            return prefix.toString();
        } else {
            prefix.append(',').append(value);
            return next.toString(prefix, stop);
        }
    }
}
