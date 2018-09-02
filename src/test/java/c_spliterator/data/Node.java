package c_spliterator.data;

import java.util.Objects;
import java.util.stream.Stream;

public class Node<T> {
    private final T value;
    private final Node<T> left;
    private final Node<T> right;

    public Node(T value, Node<T> left, Node<T> right) {
        this.value = Objects.requireNonNull(value);
        this.left = left;
        this.right = right;
    }

    public T getValue() {
        return value;
    }

    public Node<T> getLeft() {
        return left;
    }

    public Node<T> getRight() {
        return right;
    }

    public Stream<T> stream() {
        // TODO
        throw new UnsupportedOperationException();
        //return StreamSupport.stream(new NodeSplitertor(this));
    }
}
