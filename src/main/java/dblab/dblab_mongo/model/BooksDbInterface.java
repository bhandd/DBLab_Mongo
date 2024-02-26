package dblab.dblab_mongo.model;

import dblab.dblab_mongo.model.entityClasses.Book;
import dblab.dblab_mongo.model.exceptions.BooksDbException;

import java.sql.SQLException;
import java.util.Date;
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
    public void updateGrade(int grade, String title);

    public void deleteBook(String title) throws SQLException;
    public void EndConnection() throws BooksDbException, SQLException;

    List<Book> getBookList() throws SQLException;

    List<Book> searchBookByTitle(String query/* SearchMode mode*/) throws BooksDbException;
    public List<Book> searchBookByISBN(String searchFor/*, SearchMode mode*/ ) throws BooksDbException;

    public List<Book> searchBookByAuthor(String searchFor/*, SearchMode mode*/ ) throws BooksDbException;
    /**
     * Adds a new book to the database. (Rating is not done here?)
     *
     * @param title The title of the book.
     * @param isbn  The ISBN of the book.
     * @param genre The genre of the book.
     * @throws Exception If an error occurs during the insertion.
     */

    //public void addBook(String isbn, String title, String genre, String fullName, Date publish, String grade) throws RuntimeException;
    public void addBook(String isbn, String title, String fullName, String publish, String genre) throws RuntimeException;
    //  public List<Book> getBookByAuthor(String name) throws SQLException, BooksDbException;

}
