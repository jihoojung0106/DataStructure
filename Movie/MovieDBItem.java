public class MovieDBItem implements Comparable<MovieDBItem> {

    public final String genre;
    public final String title;

    public MovieDBItem(String genre, String title) {
        if (genre == null) { throw new NullPointerException("`genre` should not be null"); }
        if (title == null) { throw new NullPointerException("`title` should not be null"); }
        this.genre = genre;
        this.title = title;
    }

    @Override
    public int compareTo(MovieDBItem other) {
        int is_same= genre.compareTo(other.genre);
        if (is_same != 0) { return is_same; }
        return title.compareTo(other.title);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        MovieDBItem other = (MovieDBItem) obj;
        if (genre == null) {
            if (other.genre != null)
                return false;
        } else if (!genre.equals(other.genre))
            return false;
        if (title == null) {
            if (other.title != null)
                return false;
        } else if (!title.equals(other.title))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((genre == null) ? 0 : genre.hashCode());
        result = prime * result + ((title == null) ? 0 : title.hashCode());
        return result;
    }
}