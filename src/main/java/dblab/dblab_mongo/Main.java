package dblab.dblab_mongo;

import dblab.dblab_mongo.model.BooksDb;
import dblab.dblab_mongo.model.BooksDbInterface;
import dblab.dblab_mongo.view.BooksPaneView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import com.mongodb.client.*;
import org.bson.Document;

import static com.mongodb.client.model.Filters.eq;


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

/**ett sätt
**/
        //Creating a MongoDB client
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
/**ett annat sätt*/
//        Logger logger =  LoggerFactory.getLogger("MyApp");
//       logger.error("Logging an Error");
        MongoClient mongoClient;
        MongoDatabase mongoDb;
        mongoClient = MongoClients.create("mongodb://localhost:27017");

            mongoDb = mongoClient.getDatabase("Library");


                MongoCollection<Document> collection = mongoDb.getCollection("Books");
           // System.out.println(collection);
             //   Document query = new Document("name", "kalle");
              //  Document result = collection.find().first();

        //sök första collection
               Document  result = collection.find(eq("author" , "Tom Sten")).first();
            System.out.println(result); //printa hela dokumentet

                //spara varje fält i variabel och printa, använd för att lägga in i author
                if(result !=null){
                    String isbn = result.getString("isbn");
                   String title = result.getString("title");
                   String author = result.getString("author");
                    String published = result.getString("published");
                    String genre = result.getString("genre");
                    System.out.println(isbn);
                    System.out.println(title);
                    System.out.println(author);
                    System.out.println(published);
                    System.out.println(genre);

                  //      System.out.println(number);
                }

                //Ta bort
          //      collection.findOneAndDelete(eq("author" , "Kalle Anka"));

                //sök på en bok
        FindIterable<Document> find = collection.find(eq("title", "detektivmysteriet"));
        System.out.println("Documents: " + find);
        for (MongoCursor<Document> cursor = find.iterator(); cursor.hasNext();) {
            Document doc = cursor.next();
            System.out.println(doc.get("title"));
        }

//
//        System.out.println(result);
//        if(result !=null){
//            String name = result.getString("name");
//            System.out.println(name);
//        }


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