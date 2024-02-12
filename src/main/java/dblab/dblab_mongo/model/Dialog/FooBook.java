package dblab.dblab_mongo.model.Dialog;
import java.util.regex.Pattern;

/**
 * An dummy implementation of a book model, to 
 * demonstrate a JavaFX form.
 * 
 * @author anderslm@kth.se
 */
public class FooBook {
    
    public enum Genre { Crime, Mystery, Romance, Drama, Memoir}

    private String title;
    private String isbn; // check format
    private Genre genre;

    // A simplified regexp for isbn, 10 digit number, 
    // last digit may also be 'X' 
    private static final Pattern ISBN_PATTERN = 
            Pattern.compile("^\\d{9}[\\d|X]$"); 
    
    public static boolean isValidIsbn(String isbn) {
        return ISBN_PATTERN.matcher(isbn).matches();
    }
    
    public FooBook(String title, String isbn, Genre genre) {
        if(!isValidIsbn(isbn)) 
            throw new IllegalArgumentException("not a valid isbn");
        this.isbn = isbn;
        this.title = title;
        this.genre = genre;
    }
    
    public String getIsbn()     { return isbn; }
    public String getTitle()    { return title; }
    public Genre getGenre()     { return genre; }
    
    @Override
    public String toString() {
        return title + ", " + isbn +", " + genre;
    }
}
