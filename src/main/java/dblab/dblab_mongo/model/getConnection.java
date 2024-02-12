package dblab.dblab_mongo.model;//package dblab1.dblab1_jdbc.model;
//
//import dblab1.dblab1_jdbc.model.entityClasses.Author;
//import dblab1.dblab1_jdbc.model.entityClasses.Book;
//
//import java.sql.*;
//import java.util.ArrayList;
//import java.util.List;
//
//public class getConnection {
//
//    private static Connection con = null;
//    public static Connection StartConnection() throws Exception {
//        String user = ("app_user");//args[0]; // user name
//        String pwd = ("spion");//args[1]; // password
//        System.out.println(user + ", *********");
//        String database = "Library"; // the name of the specific database
//        String server
//                = "jdbc:mysql://localhost:3306/" + database
//                + "?UseClientEnc=UTF8";
//        try (Connection checkConnect = DriverManager.getConnection(server, user, pwd)){
//            con = DriverManager.getConnection(server, user, pwd);
//            //Class.forName("com.mysql.cj.jdbc.Driver");
//            System.out.println("Connected!");
//            return con;
//            //executeQuery(con, "SELECT * FROM T_book");
//        }  catch (SQLException e) {
//            System.err.println("Connection failed. Error message: " + e.getMessage());
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    public static void EndConnection() throws SQLException {
//        con.close();
//        System.out.println("Connection closed.");
//    }
//
//
//    public static Connection getConnection() {
//        return con;
//    }
//}
