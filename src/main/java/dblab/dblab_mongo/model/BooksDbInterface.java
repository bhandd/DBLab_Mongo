package dblab.dblab_mongo.model;
import dblab.dblab_mongo.model.entityClasses.Book;
import dblab.dblab_mongo.model.exceptions.BooksDbException;
import java.util.List;

/**
 * This interface declares methods for querying a Books database.
 * Different implementations of this interface handles the connection and
 * queries to a specific DBMS and database, for example a MySQL or a MongoDB
 * database.
 *
 * NB! The methods in the implementation must catch the SQL/MongoDBExceptions thrown
 * by the underlying driver, wrap in a BooksDbException and then re-throw the latter
 * exception. This way the interface is the same for both implementations, because the
 * exception type in the method signatures is the same. More info in BooksDbException.java.
 * 
 * @author anderslm@kth.se
 */
public interface BooksDbInterface {

    /**
     * Connect to the database.
     *
     * @return true on successful connection.
     */
    public boolean connect() throws Exception;
    public void updateGrade(/*int grade*/String grade, String title)throws BooksDbException;

    public void deleteBook(String title) throws BooksDbException;
    public void EndConnection() throws BooksDbException;

    List<Book> getBookList() throws BooksDbException;

    List<Book> searchBookByTitle(String query/* SearchMode mode*/) throws BooksDbException;
    public List<Book> searchBookByISBN(String searchFor/*, SearchMode mode*/ ) throws BooksDbException;

    public List<Book> searchBookByAuthor(String searchFor/*, SearchMode mode*/ ) throws BooksDbException;

  public void addBook(String isbn, String title, String fullName, String publish, String genre, String Grade) throws BooksDbException;

}
