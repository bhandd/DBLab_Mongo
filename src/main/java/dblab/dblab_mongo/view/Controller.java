package dblab.dblab_mongo.view;

//import se.kth.anderslm.booksdb.model.Book;
//import se.kth.anderslm.booksdb.model.BooksDbInterface;
//import se.kth.anderslm.booksdb.model.SearchMode;


import dblab.dblab_mongo.model.entityClasses.Book;
import dblab.dblab_mongo.model.entityClasses.Genre;
import dblab.dblab_mongo.model.exceptions.BooksDbException;
import dblab.dblab_mongo.model.*;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static javafx.scene.control.Alert.AlertType.*;

//TODO: check interaction between controller and BooksDB so that it goes trough the interface
//TODO: Doublecheck that there are no SQL-queries in the controller
/**
 * The controller is responsible for handling user requests and update the view
 * (and in some cases the model).
 *
 * @author anderslm@kth.se
 */
public class Controller  {
    private BooksPaneView booksView; // view
    private BooksDbInterface booksDb; // model

    public Controller(BooksDbInterface booksDb, BooksPaneView booksView) {
        this.booksDb = booksDb;
        this.booksView = booksView;
    }


    //Ta bort joins där de inte behövds
    /**
     * Handles a search operation based on the specified search criteria and mode.
     *
     * @param searchFor The search string or criteria.
     * @param mode      The search mode indicating whether to search by Title, ISBN, or Author.
     */
    protected void onSearchSelected(String searchFor, SearchMode mode) {

        try {
            if (searchFor != null && searchFor.length() > 1) {
                List<Book> result = new ArrayList<>();
                switch (mode) {
                    case Title:
                        result = booksDb.searchBookByTitle(searchFor);
                        break;
                    case ISBN:
                        result = booksDb.searchBookByISBN(searchFor);
                        break;
                    case Author:
                        result =  booksDb.searchBookByAuthor(searchFor);
                        break;
                    default:
                        result = new ArrayList<>();
                }
                if (result == null || result.isEmpty()) {
                    booksView.showAlertAndWait(
                            "No results found.", INFORMATION);
                } else {
                    booksView.displayBooks(result);
                }
            } else {
                booksView.showAlertAndWait(
                        "Enter a search string!", WARNING);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage()+ "onSearchSelected");
            booksView.showAlertAndWait("Database error.", ERROR);
        }
    }

    /**
     * Event handler for connecting to the database.
     * <p>
     * Attempts to establish a connection to the database using the {@link BooksDb} instance.
     * </p>
     *
     * @param actionEvent The event triggered by the connect action.
     * @throws RuntimeException If an exception occurs during the database connection process.
     */
    public EventHandler<ActionEvent> connectHandler = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent actionEvent) {
            try {
               // booksDb.connect();
                booksDb.startConnection();
            } catch (BooksDbException e) {
                BooksPaneView.showAlertAndWait(e.getMessage(), ERROR);
            }
        }
    };

    /**
     * Event handler for displaying books from the database.
     * <p>
     * Retrieves books from the database using a specific SQL query and displays them using the BooksView.
     * </p>
     *
     * @param actionEvent The event triggered by the show books action.
     * @throws RuntimeException If an SQL exception occurs during the process of querying and displaying books.
     */
    public EventHandler<ActionEvent> showBooksInDB = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent actionEvent) {

            List<Book> books = new ArrayList<>();
            try {
            //  books = booksDb.getBookList();
                booksView.displayBooks(booksDb.getBookList());
            } catch (BooksDbException e) {
                BooksPaneView.showAlertAndWait(e.getMessage(), ERROR);
            }
        }
    };

    /**
     * Event handler for adding a book to the database.
     * <p>
     * Displays a dialog prompting the user to enter details for a new book, and then adds the book to the database.
     * The database operation is performed in a separate thread to prevent blocking the UI.
     * </p>
     *
     * @param actionEvent The event triggered by the add book action.
     */
    public EventHandler<ActionEvent> addBookDB = new EventHandler<ActionEvent>() {
        Alert alert = new Alert(CONFIRMATION);

        String isbn = null;
        String title = null;
        String author = null;
        String published = null;
        String genre = null;
        String grade = null;

        private TextField titleField = new TextField();
        private TextField isbnField = new TextField();
        private TextField authorField = new TextField();
        private TextField publishedField = new TextField();
        private TextField gradeField = new TextField();
        private ComboBox<Genre> genreComboBox = new ComboBox<>();

        @Override
        public void handle(ActionEvent actionEvent) {
            alert.setTitle("Add book");
            alert.setResizable(false);

            GridPane grid = new GridPane();
            grid.setAlignment(Pos.CENTER);
            grid.setHgap(5);
            grid.setVgap(5);
            grid.setPadding(new Insets(10, 10, 10, 10));
            grid.add(new Label("Isbn for book "), 1, 1);
            grid.add(isbnField, 2, 1);
            grid.add(new Label("Enter title of book "), 1, 2);
            grid.add(titleField, 2, 2);
            grid.add(new Label("Author that wrote book "), 1, 3);
            grid.add(authorField, 2, 3);
            grid.add(new Label("Add publish date "), 1, 4);
            grid.add(publishedField, 2, 4);
            grid.add(new Label("Chose genre to book "), 1, 5);
            grid.add(genreComboBox, 2, 5);
            grid.add(new Label("Set grade to book"), 1, 6);
            grid.add(gradeField, 2, 6);

            genreComboBox.getItems().addAll(Genre.values());

            alert.getDialogPane().setContent(grid);

            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get().getButtonData() == ButtonBar.ButtonData.CANCEL_CLOSE) {
                // Reset all fields if user cancels
                isbnField.setText("");
                titleField.setText("");
                authorField.setText("");
                publishedField.setText("");
                gradeField.setText("");
                genreComboBox.setValue(null);
            } else {
                isbn = isbnField.getText();
                title = titleField.getText();
                author = authorField.getText();
                published = publishedField.getText();
                genre = genreComboBox.getValue() != null ? genreComboBox.getValue().toString() : null;
                grade = gradeField.getText();

                if (isbn.isEmpty() || title.isEmpty() || published.isEmpty() || genre == null || grade.isEmpty()) {
                    Alert validationAlert = new Alert(Alert.AlertType.ERROR);
                    validationAlert.setTitle("Error");
                    validationAlert.setHeaderText(null);
                    validationAlert.setContentText("Please fill in all fields");
                    validationAlert.showAndWait();
                    return;
                }
                // Add book to the database
                new Thread(() -> {
                    try {
                        // Database code to add a book
                        booksDb.addBook(isbn, title, author, published, genre, grade);
                        Platform.runLater(() -> {
                            isbnField.setText("");
                            titleField.setText("");
                            authorField.setText("");
                            publishedField.setText("");
                            gradeField.setText("");
                            genreComboBox.setItems(FXCollections.observableArrayList());
                        });
                    } catch (Exception e) {
                        System.out.println("An error occurred in adding the book: " + e.getMessage());
                    }
                }).start();
            }
        }
    };



    /**
     * Event handler for updating the grade of a book in the database.
     * <p>
     * Displays a dialog prompting the user to enter the title of the book and the new grade.
     * The database operation to update the grade is performed in a separate thread to prevent blocking the UI.
     * </p>
     *
     * @param actionEvent The event triggered by the update book action.
     */
    public EventHandler<ActionEvent> updateBookDB = new EventHandler<ActionEvent>() {
        // String gradeValue = "2";
        Alert alert = new Alert(CONFIRMATION);
        String gradeValue = null;
        String title = "mattebok";

        private TextField titleField = new TextField();
        private TextField gradeField = new TextField();

        @Override
        public void handle(ActionEvent actionEvent) {
            alert.setTitle("Set new grade");
            alert.setResizable(false);

            GridPane grid = new GridPane();
            grid.setAlignment(Pos.CENTER);
            grid.setHgap(5);
            grid.setVgap(5);
            grid.setPadding(new Insets(10, 10, 10, 10));
            grid.add(new Label("Title for book "), 1, 1);
            grid.add(titleField, 2, 1);
            grid.add(new Label("New grade "), 1, 2);
            grid.add(gradeField, 2, 2);

            alert.getDialogPane().setContent(grid);

            Optional<ButtonType> result =  alert.showAndWait();
            if (result.isPresent() && result.get().getButtonData() == ButtonBar.ButtonData.CANCEL_CLOSE) {
                // Reset all fields if user cancels
                titleField.setText("");
                gradeField.setText("");
             //   gradeComboBox.setValue(null);
            }else {

                title = titleField.getText();
                gradeValue = gradeField.getText();

                if(gradeValue.isEmpty()) {
                    Alert validationAlert = new Alert(Alert.AlertType.ERROR);
                    validationAlert.setTitle("Error");
                    validationAlert.setHeaderText(null);
                    validationAlert.setContentText("Please fill in all fields");
                    validationAlert.showAndWait();
                    return;
                }
                new Thread(() -> {
                    try {
                        booksDb.updateGrade(/*Integer.parseInt(gradeValue)*/gradeValue, String.valueOf(title));
                        Platform.runLater(() -> {
                            titleField.setText("");
                            gradeField.setText("");
                        });
                    } catch (BooksDbException e) {
                        BooksPaneView.showAlertAndWait(e.getMessage(), ERROR);//TODO: anropa för övriga exceptions?
                    }
                }).start();
            }
        }
    };




    /**
     * Event handler for deleting a book from the database.
     * <p>
     * Displays a dialog prompting the user to enter the title of the book to be deleted.
     * The database operation to delete the book is performed in a separate thread to prevent blocking the UI.
     * </p>
     *
     * @param actionEvent The event triggered by the delete book action.
     */
    public EventHandler<ActionEvent> deleteBookDB = new EventHandler<ActionEvent>() {
        private TextField titleField = new TextField();

        private Alert alert = new Alert(CONFIRMATION);

        private String title = null;

        @Override
        public void handle(ActionEvent actionEvent) {
            alert.setTitle("Delete");
            alert.setResizable(false);

            GridPane grid = new GridPane();
            grid.setAlignment(Pos.CENTER);
            grid.setHgap(5);
            grid.setVgap(5);
            grid.setPadding(new Insets(10, 10, 10, 10));
            grid.add(new Label("Title for book "), 1, 1);
            grid.add(titleField, 2, 1);

            alert.getDialogPane().setContent(grid);
            Optional<ButtonType> result =  alert.showAndWait();
            if (result.isPresent() && result.get().getButtonData() == ButtonBar.ButtonData.CANCEL_CLOSE) {
                // Reset all fields if user cancels
                titleField.setText("");
            }else{
                title = titleField.getText();
                new Thread(() -> {
                    try {
                        booksDb.deleteBook(title);
                    } catch (BooksDbException e) {
                        System.err.println(e.getMessage());
                    }
                    Platform.runLater(() -> {
                        titleField.setText("");
                    });
                }).start();
            }
        }
    };

    /**
     * Event handler for searching books by title.
     * <p>
     * Displays a dialog prompting the user to enter the title of the book to be searched.
     * The search operation is performed in a separate thread to prevent blocking the UI.
     * </p>
     *
     * @param actionEvent The event triggered by the title search action.
     */
    public EventHandler<ActionEvent> TitleSearch = new EventHandler<ActionEvent>() {
        private TextField titleField = new TextField();

        private Alert alert = new Alert(CONFIRMATION);

        private String title = null;

        @Override
        public void handle(ActionEvent actionEvent) {
            alert.setTitle("Search");
            alert.setResizable(false);

            GridPane grid = new GridPane();
            grid.setAlignment(Pos.CENTER);
            grid.setHgap(5);
            grid.setVgap(5);
            grid.setPadding(new Insets(10, 10, 10, 10));
            grid.add(new Label("Title for book "), 1, 1);
            grid.add(titleField, 2, 1);

            alert.getDialogPane().setContent(grid);
            Optional<ButtonType> result =  alert.showAndWait();
            if (result.isPresent() && result.get().getButtonData() == ButtonBar.ButtonData.CANCEL_CLOSE) {
                // Reset all fields if user cancels
                titleField.setText("");
            }else{
                title = titleField.getText();
                onSearchSelected(title, SearchMode.Title);
                titleField.setText("");
            }

        }
    };

    /**
     * Event handler for searching books by ISBN.
     * <p>
     * Displays a dialog prompting the user to enter the ISBN of the book to be searched.
     * The search operation is performed in a separate thread to prevent blocking the UI.
     * </p>
     *
     * @param actionEvent The event triggered by the ISBN search action.
     */
    public EventHandler<ActionEvent> ISBNSearch = new EventHandler<ActionEvent>() {
        private TextField ISBNField = new TextField();

        private Alert alert = new Alert(CONFIRMATION);

        private String ISBN = null;

        @Override
        public void handle(ActionEvent actionEvent) {
            alert.setTitle("Search");
            alert.setResizable(false);

            GridPane grid = new GridPane();
            grid.setAlignment(Pos.CENTER);
            grid.setHgap(5);
            grid.setVgap(5);
            grid.setPadding(new Insets(10, 10, 10, 10));
            grid.add(new Label("Title for book "), 1, 1);
            grid.add(ISBNField, 2, 1);

            alert.getDialogPane().setContent(grid);
            Optional<ButtonType> result =  alert.showAndWait();
            if (result.isPresent() && result.get().getButtonData() == ButtonBar.ButtonData.CANCEL_CLOSE) {
                // Reset all fields if user cancels
                ISBNField.setText("");
            }else{
                ISBN = ISBNField.getText();
                onSearchSelected(ISBN, SearchMode.ISBN);
                ISBNField.setText("");
            }

        }
    };

    /**
     * Event handler for searching books by author.
     * <p>
     * Displays a dialog prompting the user to enter the author's name to perform a search.
     * The search operation is performed in a separate thread to prevent blocking the UI.
     * </p>
     *
     * @param actionEvent The event triggered by the author search action.
     */
    public EventHandler<ActionEvent> AuthorSearch = new EventHandler<ActionEvent>() {
        private TextField authorField = new TextField();

        private Alert alert = new Alert(CONFIRMATION);

        private String Author = null;

        @Override
        public void handle(ActionEvent actionEvent) {
            alert.setTitle("Search");
            alert.setResizable(false);

            GridPane grid = new GridPane();
            grid.setAlignment(Pos.CENTER);
            grid.setHgap(5);
            grid.setVgap(5);
            grid.setPadding(new Insets(10, 10, 10, 10));
            grid.add(new Label("Title for book "), 1, 1);
            grid.add(authorField, 2, 1);

            alert.getDialogPane().setContent(grid);
            Optional<ButtonType> result =  alert.showAndWait();
            if (result.isPresent() && result.get().getButtonData() == ButtonBar.ButtonData.CANCEL_CLOSE) {
                // Reset all fields if user cancels
                authorField.setText("");
            }else{
                Author = authorField.getText();
                onSearchSelected(Author, SearchMode.Author);
                authorField.setText("");
            }

        }
    };

    /**
     * Event handler for ending the database connection.
     * <p>
     * This handler is responsible for disconnecting from the database.
     * It catches potential exceptions such as SQLException and BooksDbException,
     * then wraps them in a RuntimeException for simplified error handling.
     * </p>
     *
     * @param actionEvent The event triggered by the end connection action.
     */
    public EventHandler<ActionEvent> endConnectHandler = new EventHandler<ActionEvent>() {


        @Override
        public void handle(ActionEvent actionEvent) {
            try {
                booksDb.EndConnection();
            } catch (BooksDbException e) {
                BooksPaneView.showAlertAndWait(e.getMessage(), ERROR);
            }
        }
    };

    /**
     * Initializes the search view based on the provided search field and search mode.
     * <p>
     * This method extracts the search string from the given {@code searchField} and the
     * search mode from the provided {@code searchModeBox}. It then invokes the
     * {@link #onSearchSelected(String, SearchMode)} method to perform the search based on
     * the extracted information.
     * </p>
     *
     * @param searchField   The TextField containing the search string.
     * @param searchModeBox The ComboBox containing the search mode.
     */
    public void initSearchView (TextField searchField, ComboBox<SearchMode> searchModeBox) {
            String searchFor = searchField.getText();
            SearchMode mode = SearchMode.valueOf(String.valueOf(searchModeBox.getValue()));
            onSearchSelected(searchFor, mode);
        }
}
