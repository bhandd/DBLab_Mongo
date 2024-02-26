package dblab.dblab_mongo.view;

import dblab.dblab_mongo.model.BooksDb;
import dblab.dblab_mongo.model.SearchMode;
import dblab.dblab_mongo.model.entityClasses.Book;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.sql.Date;
import java.util.List;
//import se.kth.anderslm.booksdb.model.Book;
//import se.kth.anderslm.booksdb.model.BooksDbMockImpl;
//import se.kth.anderslm.booksdb.model.SearchMode;


/**
 * The main pane for the view, extending VBox and including the menus. An
 * internal BorderPane holds the TableView for books and a search utility.
 *
 * @author anderslm@kth.se
 */
public class BooksPaneView extends VBox {

    private TableView<Book> booksTable;
    private ObservableList<Book> booksInTable; // the data backing the table view

    private ComboBox<SearchMode> searchModeBox;
    private TextField searchField;
    private Button searchButton;

    private MenuBar menuBar;

    private List<Book> books;

    public BooksPaneView(BooksDb booksDb) {
        final Controller controller = new Controller(booksDb, this);
        this.init(controller);
    }

    /**
     * Display a new set of books, e.g. from a database select, in the
     * booksTable table view.
     *
     *
     */
    public void displayBooks(List<Book> books) {
        booksInTable.clear();
        booksInTable.addAll(books);
    }
    
    /**
     * Notify user on input error or exceptions.
     * 
     * @param msg the message
     * @param type types: INFORMATION, WARNING et c.
     */
    protected void showAlertAndWait(String msg, Alert.AlertType type) {
        // types: INFORMATION, WARNING et c.
        Alert alert = new Alert(type, msg);
        alert.showAndWait();
    }


    //TODO: kolla vilka metoder som går att flytta till model
    private void init(Controller controller) {

        booksInTable = FXCollections.observableArrayList();

        // init views and event handlers
        initBooksTable();
        initSearchView(controller);
        initMenus(controller);

        FlowPane bottomPane = new FlowPane();
        bottomPane.setHgap(10);
        bottomPane.setPadding(new Insets(10, 10, 10, 10));
        bottomPane.getChildren().addAll(searchModeBox, searchField, searchButton);

        BorderPane mainPane = new BorderPane();
        mainPane.setCenter(booksTable);
        mainPane.setBottom(bottomPane);
        mainPane.setPadding(new Insets(10, 10, 10, 10));

        this.getChildren().addAll(menuBar, mainPane);
        VBox.setVgrow(mainPane, Priority.ALWAYS);
    }

    private void initBooksTable() {
        booksTable = new TableView<>();
        booksTable.setEditable(false); // don't allow user updates (yet)
        booksTable.setPlaceholder(new Label("No rows to display"));

        // define columns
        TableColumn<Book, Integer> idCol = new TableColumn<>("Book ID"); //TODO:behövs?
        TableColumn<Book, String> isbnCol = new TableColumn<>("ISBN");
        TableColumn<Book, String> titleCol = new TableColumn<>("Title");
        //   TableColumn<Book, List<Author>> authorCol = new TableColumn<>("Author"); //more than 1 author
        TableColumn<Book, String> authorCol = new TableColumn<>("Author"); //one author
        TableColumn<Book, Date> publishedCol = new TableColumn<>("Published");
        TableColumn<Book, String> genreCol = new TableColumn<>("Genre");
        TableColumn<Book, Integer> gradeCol = new TableColumn<>("Grade");
        //  TableColumn<Book, Integer> pagesCol = new TableColumn<>("Pages");
        //  TableColumn<Book, String> languageCol = new TableColumn<>("Language");

        //TODO: add idCol, authorCol and gradeCol,,publishedCol , languageCol, genreCol(pages col can probably be omitted
        booksTable.getColumns().addAll(idCol, isbnCol, authorCol, titleCol, publishedCol, genreCol, gradeCol );
        // give title column some extra space
        titleCol.prefWidthProperty().bind(booksTable.widthProperty().multiply(0.5));

        // define how to fill data for each cell,
        // get values from Book properties
       // idCol.setCellValueFactory(new PropertyValueFactory<>("_id")); //TODO:Behövs?
        isbnCol.setCellValueFactory(new PropertyValueFactory<>("isbn"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        //  authorCol.setCellValueFactory(new PropertyValueFactory<>("authors")); //more than 1 author
        authorCol.setCellValueFactory(new PropertyValueFactory<>("author")); //one author
        publishedCol.setCellValueFactory(new PropertyValueFactory<>("published"));
        genreCol.setCellValueFactory(new PropertyValueFactory<>("genre"));

        gradeCol.setCellValueFactory(new PropertyValueFactory<>("grade"));
        // associate the table view with the data
        booksTable.setItems(booksInTable);
    }

    private void initSearchView(Controller controller) {
        searchField = new TextField();
        searchField.setPromptText("Search for...");
        searchModeBox = new ComboBox<>();
        searchModeBox.getItems().addAll(SearchMode.values());
        searchModeBox.setValue(SearchMode.Title);
        searchButton = new Button("Search");

        // event handling (dispatch to controller)
        searchButton.setOnAction(Event ->  controller.initSearchView(searchField,searchModeBox));
    }


    private void initMenus(Controller controller) {

        Menu fileMenu = new Menu("File");
        MenuItem exitItem = new MenuItem("Exit");
        MenuItem connectItem = new MenuItem("Connect to Db");
        connectItem.addEventHandler(ActionEvent.ACTION, controller.connectHandler);
        MenuItem disconnectItem = new MenuItem("Disconnect");
        disconnectItem.addEventHandler(ActionEvent.ACTION, controller.endConnectHandler);
        MenuItem addBoks = new MenuItem("Get books");
        addBoks.addEventHandler(ActionEvent.ACTION, controller.showBooksInDB);
        fileMenu.getItems().addAll(exitItem, connectItem, disconnectItem, addBoks);

        Menu searchMenu = new Menu("Search");
        MenuItem titleItem = new MenuItem("Title");
        titleItem.addEventHandler(ActionEvent.ACTION, controller.TitleSearch);
        MenuItem isbnItem = new MenuItem("ISBN");
        isbnItem.addEventHandler(ActionEvent.ACTION, controller.ISBNSearch);
        MenuItem authorItem = new MenuItem("Author");
        authorItem.addEventHandler(ActionEvent.ACTION, controller.AuthorSearch);
        searchMenu.getItems().addAll(titleItem, isbnItem, authorItem);

        Menu manageMenu = new Menu("Manage");
        MenuItem addItem = new MenuItem("Add");
        addItem.addEventHandler(ActionEvent.ACTION, controller.addBookDB);
        MenuItem removeItem = new MenuItem("Remove");
        removeItem.addEventHandler(ActionEvent.ACTION, controller.deleteBookDB);
        MenuItem updateItem = new MenuItem("Update");
        updateItem.addEventHandler(ActionEvent.ACTION, controller.updateBookDB);
        manageMenu.getItems().addAll(addItem, removeItem, updateItem);
        menuBar = new MenuBar();
        menuBar.getMenus().addAll(fileMenu, searchMenu, manageMenu);
    }


}
