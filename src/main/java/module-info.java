module dblab.dblab_mongo {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.mongodb.driver.sync.client;
    requires org.mongodb.driver.core;
    requires org.mongodb.bson;
    requires slf4j.api;

    // Open the package containing the Book class to the JavaFX modules
    opens dblab.dblab_mongo.model.entityClasses to javafx.base, javafx.fxml;

    // Export your main package
    exports dblab.dblab_mongo;
}
