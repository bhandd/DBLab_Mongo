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

import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

import static com.mongodb.client.model.Filters.eq;
import static java.lang.String.*;

/**
 * A mock implementation of the BooksDBInterface interface to demonstrate how to
 * use it together with the user interface.
 * <p>
 * Your implementation must access a real database.
 *
 * @author anderslm@kth.se
 */
public class BooksDb implements BooksDbInterface {


    private static MongoClient mongoClient = null; //TODO: check if needed here
 //   private static MongoDatabase mongoDb;
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
     * @throws BooksDbException if an error occurs during database interaction.
     */
    public void disconnect() throws BooksDbException {
        try{
            EndConnection();
        }catch (MongoException e){
            throw new BooksDbException(e.getMessage());
        }

    }


    /**
     * Establishes a connection to the database.
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
            System.out.println("Yes");
            return mongoClient;
        }  catch (MongoException e) {
            System.err.println("Connection failed. Error message: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

//TODO: try-catch-block här?
    /**End the connection to the database
     *
     *
     * */
    @Override
    public void EndConnection() throws BooksDbException {
try {
    mongoClient.close();
    System.out.println("Connection closed.");
}catch (Exception e){
    throw new BooksDbException(e.getMessage());
}

    }


/**Fetches an authors name from the DB based on author-id.
 *
 * returns Author with a name if author is found.
 * returns null, if no author is found
 * */

    private Author getAuthorNameById(String id) throws BooksDbException{
        Author author = new Author();
        try  {
            MongoDatabase database = mongoClient.getDatabase("Library");
            MongoCollection<Document> collection = database.getCollection("Author");
            Document query = new Document("id_", id);
            FindIterable<Document> documents = collection.find(query);

            Document document = documents.first();
            if(document != null){
                String authorName = document.getString("name");
                author.setfName(authorName);
                return author;
            }else {
                return null;
            }
        } catch (MongoException e) {
            throw new BooksDbException(e.getMessage());
        }
    }


    /**Fetches an authors name from the DB based on author-id.
     *
     * returns Author with a name if author is found.
     * returns null, if no author is found
     * */

    private String getAuthorIdByName(String id) throws BooksDbException{
        Author author = new Author();
        try  {
            MongoDatabase database = mongoClient.getDatabase("Library");
            MongoCollection<Document> collection = database.getCollection("Author");
            Document query = new Document("name", id);
            FindIterable<Document> documents = collection.find(query);

            Document document = documents.first();
            if(document != null){
                String authorId = document.getString("id");

                return authorId;
            }else {
                return null;
            }
        } catch (MongoException e) {
            throw new BooksDbException(e.getMessage());
        }
    }

    //TODO: check if needed
/** get a connection
 * */
    public static MongoClient getConnection() {
        return mongoClient;
    }


    /**
     * Retrieves a list of all books from the database and returns them as a {@code List<Book>} object.
     *
     * @return A list of all books in the database, or an empty list if there are no books.
     * @throws BooksDbException If there is an error retrieving the books from the database.
     */
    /*@Override
    public List<Book> getBookList() throws BooksDbException{

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
        } catch (MongoException e) {
            throw new BooksDbException(e.getMessage());
        }

        return books;
    }*/

    @Override
    public List<Book> getBookList() throws BooksDbException {
        List<Book> books = new ArrayList<>();

        String connectionString = "mongodb://localhost:27017";

        try {
            MongoDatabase database = mongoClient.getDatabase("Library");
            MongoCollection<Document> collection = database.getCollection("Books");

            FindIterable<Document> documents = collection.find();

            for (Document document : documents) {
                String isbn = document.getString("isbn");
                String title = document.getString("title");

                // Retrieve list of authors as Document objects
                List<Document> authorDocuments = document.getList("authors", Document.class);

                // Map each author document to Author object
                List<Author> authors = new ArrayList<>();
                for (Document authorDoc : authorDocuments) {
                    String authorName = authorDoc.getString("name");
                    System.out.println(authorName);
                  //  LocalDate birthDate = authorDoc.getDate("birthdate"); //gamla sättet
                    String birthDateString = authorDoc.getString("birthdate");
                    LocalDate birthdate = LocalDate.parse(birthDateString);
                    Author author = new Author(authorName, birthdate);
                    authors.add(author);
                }

                String published = document.getString("published");
                String genre = document.getString("genre");
                String grade = document.getString("grade"); // Uncomment if needed

                // Create a new Book object for each retrieved document
                Book book = new Book(isbn, title, authors, published, genre, grade);
                books.add(book);
            }
        } catch (MongoException e) {
            throw new BooksDbException(e.getMessage());
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
                List<Author> authors = document.getList("authors", Author.class);
                String published = document.getString("published");
                String genre = document.getString("genre");
                String grade = document.getString("grade");
                Book book = new Book(isbn, title, authors, published, genre, grade);
                books.add(book);
            }
        } catch (MongoException e) {
            throw new BooksDbException(e.getMessage());
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
                List<Author> authors = document.getList("authors", Author.class);
                String published = document.getString("published");
                String genre = document.getString("genre");
                String grade = document.getString("grade");

                Book book = new Book(isbn, title, authors, published, genre, grade);
                books.add(book);
            }
        } catch (MongoException e) {
           throw new BooksDbException(e.getMessage());
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
                List<Author> authors = document.getList("authors", Author.class);
                String published = document.getString("published");
                String genre = document.getString("genre");
                String grade = document.getString("grade");

                Book book = new Book(isbn, title, authors, published, genre, grade);
                books.add(book);
            }
        } catch (MongoException e) {
            throw new BooksDbException(e.getMessage());
        }
        return books;
    }




    /**
     * Updates the grade for a book with the specified title.
     *
     * @param grade The new grade for the book.
     * @param title The title of the book to update.
     */
    @Override
    public void updateGrade(/*int grade*/String grade, String title)throws BooksDbException {

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
     * @throws BooksDbException If an error occurs during database interaction.
     */
    @Override
    public void addBook(String isbn, String title, String fullName, String publish, String genre, String grade) throws BooksDbException
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

            collection.insertOne(document);
            ObjectId id = document.getObjectId("_id"); // id
            System.out.println("Added new book with id:" + id);

        } catch (RuntimeException e) {
            throw new BooksDbException(e.getMessage());
        }


    }

    /**
     * Deletes a book from the database with the specified title.
     *
     * @param title The title of the book to be deleted.
     * @throws BooksDbException If an error occurs during database interaction.
     */
    @Override
    public void deleteBook(String title) throws BooksDbException {

        try {

            MongoDatabase database = mongoClient.getDatabase("Library");
            MongoCollection<Document> collection = database.getCollection("Books");
            Document document = collection.findOneAndDelete(eq("title", title));


           // ObjectId id = document.getObjectId("_id"); // id
            String isbn = document.getString("isbn");
            String deletedTitle = document.getString("title");
            String author = document.getString("author");
            System.out.println("Deleted document with title:" + title
                    +"\n ISBN: " + isbn
                    +"\n Author: " + author);

        } catch (RuntimeException e) {
           throw new BooksDbException(e.getMessage());
        }

    }



}
