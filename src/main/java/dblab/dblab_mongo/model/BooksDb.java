/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dblab.dblab_mongo.model;

import com.mongodb.MongoException;
import com.mongodb.client.*;
import dblab.dblab_mongo.model.entityClasses.Author;
import dblab.dblab_mongo.model.entityClasses.Book;
import dblab.dblab_mongo.model.exceptions.BooksDbException;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import static com.mongodb.client.model.Filters.eq;

/**
 * A mock implementation of the BooksDBInterface interface to demonstrate how to
 * use it together with the user interface.
 * <p>
 * Your implementation must access a real database.
 *
 * @author anderslm@kth.se
 */
public class BooksDb implements BooksDbInterface {


    private static MongoClient mongoClient = null;
    private static MongoDatabase mongoDb;
    /**
     * A class that represents a connection to a database.
     *
     *
     */
    @Override
    public boolean connect() throws Exception {
        return StartConnection() != null;
    }

    /**
     * Closes an existing connection to the database.
     *
     * @throws BooksDbException if an error occurs during connection closure.
     * @throws SQLException if an error occurs during database interaction.
     */
    public void disconnect() throws BooksDbException, SQLException {
        EndConnection();
    }


    /**
     * Establishes a connection to the MySQL database.
     *
     * @throws Exception If there is an error connecting to the database.
     * @return The connection to the database.
     */
    public static MongoClient StartConnection() throws Exception {

        try {
          //  MongoClient mongoClient;
            MongoDatabase mongoDb;
            mongoClient = MongoClients.create("mongodb://localhost:27017");
            //TODO: för test, ta bort sen
            mongoDb = mongoClient.getDatabase("Library");
            MongoCollection<Document> collection = mongoDb.getCollection("Books");
            Document  result = collection.find(eq("author" , "Tom Sten")).first();
            System.out.println(result);
            return mongoClient;

        }  catch (MongoException e) {
            System.err.println("Connection failed. Error message: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }


    /**End the connection to the database
     *
     *
     * */
    @Override
    public void EndConnection() throws SQLException {
        mongoClient.close();
        System.out.println("Connection closed.");
    }

/** get a connection
 * */
    public static MongoClient getConnection() {
        return mongoClient;
    }

    //TODO
    /*
    private int getAuthorIdByName(String name) throws RuntimeException{
        int authorId = -1;
        String query= "SELECT aut_id FROM T_author WHERE fullName LIKE'%" + name + "%';";
        try (Statement stmt = getConnection().createStatement()) {
            // Execute the SQL statement
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                authorId = rs.getInt("aut_id");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return authorId;
    }
*/

    //TODO
    /*
    private int getBookIdFromAuthorId(int authorId) throws RuntimeException{
        int bookId= -1;
        String query= "SELECT book_id FROM book_author WHERE author_id ="+ authorId + ";";
        try (Statement stmt = getConnection().createStatement()) {
            // Execute the SQL statement
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                bookId = rs.getInt("book_id");
            }
            rs.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return bookId;
    }
*/


    //TODO
    /**
     * Retrieves a list of author IDs for a specified book ID from the database.
     *
     * @param bookId The ID of the book for which to retrieve author IDs.
     * @return A list of author IDs for the specified book.
     * @throws RuntimeException If an error occurs during database interaction.
     */
/*
    private static int getAuthorIdFromBookId(int bookId) throws RuntimeException{
        // List<Integer> authorIds = new ArrayList<>();
        int authorId = -1;
        String query= "SELECT author_id\n" +
                "FROM book_author\n" +
                "WHERE book_id ="+ bookId + ";";

        try (Statement stmt = getConnection().createStatement()) {
            // Execute the SQL statement
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                //  authorIds.add(rs.getInt("author_id"));
                authorId = rs.getInt("author_id");
            }
          //  rs.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return authorId;
    }
*/

    //TODO
    /**
     * Retrieves a list of authors for a specified author ID from the database.
     *
     * @param authorId The ID of the author for which to retrieve author information.
     * @return A list of `Author` objects for the specified author.
     * @throws RuntimeException If an error occurs during database interaction.
     */
    /*
    public static ArrayList<Author> getAuthorsById(int authorId) throws RuntimeException{
        ArrayList<Author> authors =new ArrayList<>();
        String query= "SELECT * FROM T_author WHERE aut_id =" + authorId + ";";

//hämta alla author ID från databas
        try (Statement stmt = getConnection().createStatement()) {
            // Execute the SQL statement
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                authors.add(new Author(rs.getInt("aut_id"), rs.getString("fullName")));
            }
            rs.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return authors;
    }
*/

    //TODO
    /**
     * Get the full name of an author by their ID from the database.
     *
     * @param authorId The ID of the author to retrieve.
     * @return The full name of the author, or an empty string if the author is not found.
     * @throws RuntimeException If there is an error retrieving the author from the database.
     */
    /*
    private static String getAuthorNameById(int authorId) throws RuntimeException{
        String author;
        String query= "SELECT fullname FROM T_author WHERE aut_id =" + authorId + ";";
//hämta author från databas
        try (Statement stmt = getConnection().createStatement()) {
            // Execute the SQL statement
            ResultSet rs = stmt.executeQuery(query);
            rs.next();
            author = rs.getString("fullname");
            rs.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return author;
    }
*/
    /**
     * Retrieves a list of all books from the database and returns them as a {@code List<Book>} object.
     *
     * @return A list of all books in the database, or an empty list if there are no books.
     * @throws SQLException If there is an error retrieving the books from the database.
     */
    @Override
    public List<Book> getBookList() throws SQLException {

        List<Book> books = new ArrayList<>();

        String connectionString = "mongodb://localhost:27017";

        try  {

            MongoDatabase database = mongoClient.getDatabase("Library");

            MongoCollection<Document> collection = database.getCollection("Books");

            FindIterable<Document> documents = collection.find();

            for (Document document : documents) {
                // Retrieve book attributes from the document
                String isbn = document.getString("isbn");
                String title = document.getString("title");
                String author = document.getString("author");
                String published = document.getString("published");
                String genre = document.getString("genre");
                String grade = document.getString("grade"); // Uncomment if needed

                Book book = new Book(isbn, title, author, published, genre, grade);
                books.add(book);
            }
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }

        return books;
    }



    /**
     * Searches for books by title and returns a list of matching books.
     *
     * @param searchFor The title to search for.
     * @return A list of books with titles that match the search string.
     * @throws BooksDbException If there is an error searching for the books.
     */

    @Override
    public List<Book> searchBookByTitle(String searchFor/*, SearchMode mode*/ ) throws BooksDbException {

        List<Book> books = new ArrayList<>();

        try  {

            MongoDatabase database = mongoClient.getDatabase("Library");
            MongoCollection<Document> collection = database.getCollection("Books");
            Pattern regexPattern = Pattern.compile(searchFor, Pattern.CASE_INSENSITIVE);
            Document query = new Document("title", regexPattern);
            FindIterable<Document> documents = collection.find(query);

            for (Document document : documents) {
                String isbn = document.getString("isbn");
                String title = document.getString("title");
                String author = document.getString("author");
                String published = document.getString("published");
                String genre = document.getString("genre");
                String grade = document.getString("grade");
                Book book = new Book(isbn, title, author, published, genre, grade);
                books.add(book);
            }
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
        return books;
    }

    /**
     * Searches for books by ISBN and returns a list of matching books.
     *
     * @param searchFor The ISBN to search for.
     * @return A list of books with ISBNs that match the search string.
     * @throws BooksDbException If there is an error searching for the books.
     */
    @Override
    public List<Book> searchBookByISBN(String searchFor/*, SearchMode mode*/ ) throws BooksDbException {

        List<Book> books = new ArrayList<>();

        try  {

            MongoDatabase database = mongoClient.getDatabase("Library");

            MongoCollection<Document> collection = database.getCollection("Books");

            Pattern regexPattern = Pattern.compile(searchFor, Pattern.CASE_INSENSITIVE);

            Document query = new Document("isbn", regexPattern);

            FindIterable<Document> documents = collection.find(query);

            for (Document document : documents) {
                String isbn = document.getString("isbn");
                String title = document.getString("title");
                String author = document.getString("author");
                String published = document.getString("published");
                String genre = document.getString("genre");
                String grade = document.getString("grade");

                Book book = new Book(isbn, title, author, published, genre, grade);
                books.add(book);
            }
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
        return books;
    }


    /**
     * Searches for books by author and returns a list of matching books.
     *
     * @param searchFor The author's name to search for.
     * @return A list of books with authors that match the search string.
     * @throws BooksDbException If there is an error searching for the books.
     */
    @Override
    public List<Book> searchBookByAuthor(String searchFor/*, SearchMode mode*/ ) throws BooksDbException {

        List<Book> books = new ArrayList<>();

        try  {

            MongoDatabase database = mongoClient.getDatabase("Library");

            MongoCollection<Document> collection = database.getCollection("Books");

            Pattern regexPattern = Pattern.compile(searchFor, Pattern.CASE_INSENSITIVE);

            Document query = new Document("author", regexPattern);

            FindIterable<Document> documents = collection.find(query);

            for (Document document : documents) {
                String isbn = document.getString("isbn");
                String title = document.getString("title");
                String author = document.getString("author");
                String published = document.getString("published");
                String genre = document.getString("genre");
                String grade = document.getString("grade");

                Book book = new Book(isbn, title, author, published, genre, grade);
                books.add(book);
            }
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
        return books;
    }



    /**
     * Executes the specified SQL statement against the database.
     *
     * @param statement The SQL statement to execute.
     * @throws SQLException If an error occurs during database interaction.
     */
    public static void executeStatement(String statement) throws SQLException {
      /*
        //  System.out.println("current statement to execute: " +statement);
        try (Statement stmt = getConnection().createStatement()) {
            // Execute the SQL statement
            stmt.executeUpdate(statement);
            //  System.out.println("executed a statement");
        }catch (SQLException e){
            System.err.println(e.getMessage());
        }
        */
    }

    /**
     * Updates the grade for a book with the specified title.
     *
     * @param grade The new grade for the book.
     * @param title The title of the book to update.
     */
    @Override
    public void updateGrade(/*int grade*/String grade, String title)throws BooksDbException {
        //TODO:if-sats för att kolla grade mellan 1-5? Eller begränsing i mongo-databasen?

            try{
            MongoDatabase database = mongoClient.getDatabase("Library");
            MongoCollection<Document> collection = database.getCollection("Books");

            Document query = new Document("title", title);
            Document update = new Document("$set", new Document("grade", grade));

         Document updatedDoc = collection.findOneAndUpdate(query, update);
            System.out.println("updated the document: " + updatedDoc);
            }catch(MongoException e){
            throw new BooksDbException(e.toString()); //TODO:rätt?
            }

    }

    /**
     * Adds a new book to the database with the specified ISBN, title, genre, author's full name, publication date, and grade.
     *
     * @param isbn The ISBN of the book to be added.
     * @param title The title of the book to be added.
     * @param genre The genre of the book to be added.
     * @param fullName The full name of the author of the book to be added.
     * @param publish The date of publication of the book to be added.
     * @throws SQLException If an error occurs during database interaction.
     */
    @Override
    public void addBook(String isbn, String title, String fullName, String publish, String genre, String grade) throws RuntimeException
    {
        //String testGrade = grade;

        try {
            //TODO: Gör mongoClient och collection till globala variabler?
            MongoDatabase database = mongoClient.getDatabase("Library");
            MongoCollection<Document> collection = database.getCollection("Books");

            Document document = new Document("isbn", isbn)
                            .append("title", title)
                            .append("author", fullName)
                            .append("published", publish)
                            .append("genre", genre)
                            .append("grade", grade);

                    //  .append("stuff", Arrays.asList("bajen", "Hammarby") //Array

            collection.insertOne(document);
            ObjectId id = document.getObjectId("_id"); // id
            System.out.println("Added new book with id:" + id);

        } catch (RuntimeException e) {
            System.err.println("Error: " + e.getMessage());
        }


    }

    /**
     * Deletes a book from the database with the specified title.
     *
     * @param title The title of the book to be deleted.
     * @throws SQLException If an error occurs during database interaction.
     */
    @Override
    public void deleteBook(String title) throws BooksDbException {

        try {

            MongoDatabase database = mongoClient.getDatabase("Library");
            MongoCollection<Document> collection = database.getCollection("Books");
            Document document = collection.findOneAndDelete(eq("title", title));


            ObjectId id = document.getObjectId("_id"); // id
            String isbn = document.getString("isbn");
            String deletedTitle = document.getString("title");
            String author = document.getString("author");
            System.out.println("Deleted document with id:" + id
                    +"\n ISBN: " + isbn
                    +"\n Title: " + deletedTitle
                    +"\n Author: " + author);

        } catch (RuntimeException e) {
           throw new BooksDbException(e.getMessage());
        }

    }

    /**används för att kolla om en author existerar i T_book
     * används av metoden addBookToDB
     *
     *
     * */
    public static boolean authorExists(String author){
/*
        String query = "SELECT COUNT(*) FROM T_author WHERE fullName ='" + author + "'";
        Connection con = getConnection();
        try (Statement stmt = con.createStatement()) {
            // Execute the SQL statement
            ResultSet rs = stmt.executeQuery(query);
            rs.next();
            int count = rs.getInt(1);
            if (count > 0){
                return true;
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }*/
        return false;
    }

}
