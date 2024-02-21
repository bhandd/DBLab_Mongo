module dblab.dblab_mongo {

    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.mongodb.driver.sync.client;
    requires org.mongodb.driver.core;
    requires org.mongodb.bson;
    requires slf4j.api;


    opens dblab.dblab_mongo to javafx.fxml;
    exports dblab.dblab_mongo;
}