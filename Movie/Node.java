import java.util.NoSuchElementException;

public class Node<T> {
    public T item;
    public Node<T> next;

    public Node(T obj) {
        this.item = obj;
        this.next = null;
    }
    public final void setNext(Node<T> next) {
        this.next = next;
    }
    public final void setItem(T item_) {
        this.item = item_;
    }
    public Node<T> getNext() { return next; }
    public void insertNext(T obj) {
        Node<T> new_=new Node<>(obj);
        if (this.next == null) { this.next = new_; return; }
        new_.next=this.next;
        this.next=new_;
    }

    public void removeNext() {
        if (this.next == null) { throw new NoSuchElementException(); }
        this.next = this.next.next;
    }
}
