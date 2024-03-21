package dblab.dblab_mongo;

import dblab.dblab_mongo.model.BooksDb;
import dblab.dblab_mongo.model.BooksDbInterface;
import dblab.dblab_mongo.model.exceptions.BooksDbException;
import dblab.dblab_mongo.view.BooksPaneView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javax.swing.*;
import com.mongodb.client.*;
import org.bson.Document;

import java.lang.reflect.InvocationTargetException;

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
          //  booksDb.connect();
            BooksPaneView root = new BooksPaneView(booksDb);
            Scene scene = new Scene(root, 800, 600);
            primaryStage.setTitle("Books Database Client");
            // add an exit handler to the stage (X) ?
            primaryStage.setOnCloseRequest(event -> {
                try {
                    booksDb.disconnect();
                } catch (BooksDbException e){
                    System.out.println(e.getMessage());
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
//        BooksDb booksDb = new BooksDb();
//
//            booksDb.disconnect();

        super.stop();
    }
    public static void main(String[] args) {
        launch(args);
    }
}