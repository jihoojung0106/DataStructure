import java.util.Iterator;
import java.util.NoSuchElementException;

public class MyLinkedList<T> implements ListInterface<T> {
	// Dummy head
	Node<T> head;
	int numItems;
	public MyLinkedList() {
		head = new Node<T>(null);
	}
	@Override
	public final Iterator<T> iterator() {
		return new MyLinkedListIterator<T>(this);
	}

	@Override public boolean isEmpty() {return head.getNext() == null; }
	@Override public int size() { return numItems; }
	@Override public T first() { return head.getNext().item; }


	@Override
	public void add(T item) {
		Node<T> last = head;
		while (last.getNext() != null) {
			last = last.getNext();
		}
		last.insertNext(item);
		numItems += 1;
	}

	public void insert(T lst,T item) {
		System.out.println(lst);
		Node<T> last = head;
		while (!last.equals(lst)) { last = last.getNext(); }
		System.out.println("insert");
		last.insertNext(item);
		numItems += 1;
	}

	@Override
	public void removeAll() {
		head.setItem(null);
		while (head.getNext() != null) { head.setNext(null); head=head.getNext();}
	}
}


class MyLinkedListIterator<T> implements Iterator<T> {
	public MyLinkedList<T> list;
	public Node<T> curr;
	public Node<T> prev;

	public MyLinkedListIterator(MyLinkedList<T> list) {
		this.list = list;
		this.curr = list.head;
		this.prev = null;
	}

	@Override
	public boolean hasNext() { return curr.getNext() != null; }

	@Override
	public T next() {
		if (!hasNext()) { throw new NoSuchElementException(); }
		prev = curr;
		curr = curr.getNext();
		return curr.item;
	}

	@Override
	public void remove() {
		if (prev == null) { throw new IllegalStateException("next() should be called first"); }
		if (curr == null) { throw new NoSuchElementException(); }
		prev.removeNext();
		list.numItems -= 1;
		curr = prev;
		prev = null;
	}
}