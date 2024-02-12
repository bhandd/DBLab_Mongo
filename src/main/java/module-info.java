module dblab.dblab_mongo {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens dblab.dblab_mongo to javafx.fxml;
    exports dblab.dblab_mongo;
}