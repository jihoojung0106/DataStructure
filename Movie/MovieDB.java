import java.util.Iterator;
import java.util.NoSuchElementException;

public class MovieDB {
	public MyLinkedList<MovieDBItem> mainlist;
	public MyLinkedListIterator<MovieDBItem> itr;
	public MovieDB() {
		mainlist=new MyLinkedList<>();
	}
	public void insert(MovieDBItem item) {
		Node<MovieDBItem> gg = mainlist.head;
		while (true) {
			Node<MovieDBItem> next = gg.getNext();
			if (next == null) { break; }
			int ha = item.compareTo(next.item);
			if (ha == 0) { return; }
			if (ha < 0) { break; }
			gg= next;
		}
		gg.insertNext(item);
		mainlist.numItems++;
	}

	public void delete(MovieDBItem item) {
		itr=new MyLinkedListIterator<>(mainlist);
		while (itr.hasNext()) {
			MovieDBItem yal = itr.next();
			if (yal.title.equals(item.title) && yal.genre.equals(item.genre)) {
				itr.remove();return;}
		}
	}

	public MyLinkedList<MovieDBItem> items() { return mainlist; }

	public MyLinkedList<MovieDBItem> search(String term) {
		MyLinkedList<MovieDBItem> results = new MyLinkedList<MovieDBItem>();
		for (MovieDBItem item : mainlist) {
			if (item.title.contains(term)) { results.add(item); }
		}
		return results;
	}
}
