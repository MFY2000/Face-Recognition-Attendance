package FRA;

import java.sql.*;
import java.time.LocalDate;

public class DbConn {

    public Connection connection;
    public Statement statement;
    public ResultSet result;
    public static DbConn handler = null;



    private static final String DATABASE_URL = "jdbc:mysql://localhost/Library_db?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    private static final String DATABASE_USERNAME = "root";
    private static final String DATABASE_PASSWORD = "";
    private static final String INSERT_QUERY_BOOK = "INSERT INTO `book_collection` (`id`, `sno`, `name`, `isbn`, `auther`, `insertion_date`) VALUES (NULL,?,?, ?, ?, ?)";

    public static String UserId;

    public DbConn() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
            statement = connection.createStatement();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public ResultSet execQuery(String query){
        try{
            statement = connection.createStatement();
            result = statement.executeQuery(query);
        }
        catch(SQLException e){
            printSQLException(e);
            return null;
        }
        finally {
        }
        return result;
    }

    //get Instance

    public static DbConn getInstance(){
        if(handler == null){
            handler = new DbConn();
        }
        return handler;
    }


    //print the error
    public static void printSQLException(SQLException ex) {
        for (Throwable e: ex) {
            if (e instanceof SQLException) {
                e.printStackTrace(System.err);
                System.err.println("SQLState: " + ((SQLException) e).getSQLState());
                System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
                System.err.println("Message: " + e.getMessage());
                Throwable t = ex.getCause();
                while (t != null) {
                    System.out.println("Cause: " + t);
                    t = t.getCause();
                }
            }
        }
    }
}
