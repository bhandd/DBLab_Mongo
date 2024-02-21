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

import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


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


//        //Creating a MongoDB client
//       MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
//        //Connecting to the database
//        MongoDatabase database = mongoClient.getDatabase("Library");
//        //Creating a collection object
//        MongoCollection<Document> collection = database.getCollection("Author");
//        //Retrieving the documents
//        FindIterable<Document> iterDoc = collection.find();
//        Iterator it = iterDoc.iterator();
//        while (it.hasNext()) {
//            System.out.println(it.next());
//
//        }

        Logger logger =  LoggerFactory.getLogger("MyApp");
       logger.error("Logging an Error");
        MongoClient mongoClient;
        MongoDatabase mongoDb;
        mongoClient = MongoClients.create("mongodb://localhost:27017");

            mongoDb = mongoClient.getDatabase("Library");
            System.out.println(mongoDb);

                MongoCollection<Document> collection = mongoDb.getCollection("Author");
           // System.out.println(collection);
             //   Document query = new Document("name", "kalle");
              //  Document result = collection.find().first();
               Document  result = collection.find;
            System.out.println(result);
                if(result !=null){
                    String name = result.getString("name");
                    System.out.println(name);
                }
        result = collection.find;
        System.out.println(result);
        if(result !=null){
            String name = result.getString("name");
            System.out.println(name);
        }


        //Skriva till db
//        Document document = new Document("name", "Reine")
//                .append("contact", new Document("phone", "4852")
//                        .append("email", "reineb@kth.se"))
//                .append("shoe_size", 43);
//        MongoCollection<Document> collection=;
//        collection.insertOne(document);
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