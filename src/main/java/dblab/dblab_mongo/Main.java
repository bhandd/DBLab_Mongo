package dblab.dblab_mongo;

import com.mongodb.ClientSessionOptions;
import com.mongodb.connection.ClusterDescription;
import dblab.dblab_mongo.model.BooksDb;
import dblab.dblab_mongo.model.BooksDbInterface;
import dblab.dblab_mongo.view.BooksPaneView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import com.mongodb.client.*;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.List;


//import dbgui.lab1examplegui.model.BooksDbMockImpl;
//import dbgui.lab1examplegui.view.BooksPane;
//import se.kth.anderslm.booksdb.model.BooksDbMockImpl;
//import se.kth.anderslm.booksdb.view.BooksPane;

/**
 * Application start up.
 *
 * @author anderslm@kth.se
 */
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        BooksDbInterface booksDbInterface;
        BooksDb booksDb = new BooksDb(); // model
        // Don't forget to connect to the db, somewhere...
        try {
            booksDb.connect();
            BooksPaneView root = new BooksPaneView(booksDb);

            Scene scene = new Scene(root, 800, 600);

            primaryStage.setTitle("Books Database Client");
            // add an exit handler to the stage (X) ?
            primaryStage.setOnCloseRequest(event -> {
                try {
                    booksDb.disconnect();
                } catch (Exception e) {
                }
            });
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void stop() throws Exception {
        BooksDb booksDb = new BooksDb();
        booksDb.disconnect();
        super.stop();
    }
    public static void main(String[] args) {
        MongoClient mongoClient = new MongoClient() {
            @Override
            public MongoDatabase getDatabase(String s) {
                return null;
            }

            @Override
            public ClientSession startSession() {
                return null;
            }

            @Override
            public ClientSession startSession(ClientSessionOptions clientSessionOptions) {
                return null;
            }

            @Override
            public void close() {

            }

            @Override
            public MongoIterable<String> listDatabaseNames() {
                return null;
            }

            @Override
            public MongoIterable<String> listDatabaseNames(ClientSession clientSession) {
                return null;
            }

            @Override
            public ListDatabasesIterable<Document> listDatabases() {
                return null;
            }

            @Override
            public ListDatabasesIterable<Document> listDatabases(ClientSession clientSession) {
                return null;
            }

            @Override
            public <TResult> ListDatabasesIterable<TResult> listDatabases(Class<TResult> aClass) {
                return null;
            }

            @Override
            public <TResult> ListDatabasesIterable<TResult> listDatabases(ClientSession clientSession, Class<TResult> aClass) {
                return null;
            }

            @Override
            public ChangeStreamIterable<Document> watch() {
                return null;
            }

            @Override
            public <TResult> ChangeStreamIterable<TResult> watch(Class<TResult> aClass) {
                return null;
            }

            @Override
            public ChangeStreamIterable<Document> watch(List<? extends Bson> list) {
                return null;
            }

            @Override
            public <TResult> ChangeStreamIterable<TResult> watch(List<? extends Bson> list, Class<TResult> aClass) {
                return null;
            }

            @Override
            public ChangeStreamIterable<Document> watch(ClientSession clientSession) {
                return null;
            }

            @Override
            public <TResult> ChangeStreamIterable<TResult> watch(ClientSession clientSession, Class<TResult> aClass) {
                return null;
            }

            @Override
            public ChangeStreamIterable<Document> watch(ClientSession clientSession, List<? extends Bson> list) {
                return null;
            }

            @Override
            public <TResult> ChangeStreamIterable<TResult> watch(ClientSession clientSession, List<? extends Bson> list, Class<TResult> aClass) {
                return null;
            }

            @Override
            public ClusterDescription getClusterDescription() {
                return null;
            }
        }

//        launch(args);
//        Author author1 = new Author(1, "kalle balle");
//        Author author2 = new Author(2, "gurra murra");
//        Author author3 = new Author(2, "gert Stjhärt");
//        ArrayList<Author> authors = new ArrayList<>();
//        authors.add(author1);
//        authors.add(author2);
//        authors.add(author3);
//       // System.out.println(author.getFullName());
//       // System.out.println(author.toString());
//       // testBook testbook = new testBook(2,"255555","Drabant", author1, "ASDF", 3);
//      //  author.getFullName();
//        Date date = new Date(2000,01,01);
//        Book testbook = new Book(2,"123123", "asdf",date ,"SCHOOL",5);
//        testbook.addAuthor(authors);
//       // testbook.printAuthors();
//        //System.out.println(testbook.getAuthorsNames(authors));
//        System.out.println(testbook.toString());
//        System.out.println("färdig");

    }
}