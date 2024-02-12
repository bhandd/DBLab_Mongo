package dblab.dblab_mongo.model.Dialog;
import java.util.Optional;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


/**
 * A simplified example of a form, using JavaFX Dialog and DialogPane, 
 * see AddBookDialog. The dialog is type parameterized for FooBook. 
 * The result is printed on the console, but 
 * 
 * @author Anders Lindström, anderslm@kth.se
 */
public class DialogExampleMain extends Application {

    @Override
    public void start(Stage primaryStage) {

        AddBookDialog dialog = new AddBookDialog();

        Button btn = new Button();
        btn.setText("Add a FooBook");
        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Optional<FooBook> result = dialog.showAndWait();
                if (result.isPresent()) {
                    FooBook book = result.get();
                    System.out.println("Result: " + book.toString());
                } else {
                    System.out.println("Canceled");
                }
            }
        });

        StackPane root = new StackPane();
        root.getChildren().add(btn);

        Scene scene = new Scene(root, 300, 250);

        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
