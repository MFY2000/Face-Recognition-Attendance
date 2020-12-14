package sample.DB;

import java.io.*;
import java.sql.*;
import java.util.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DB{
    public Connection connection;
    public Statement statement;
    public ResultSet result;
    // Replace below database url, username and password with your actual database credentials

    private static final String DATABASE_URL = "jdbc:mysql://localhost/fraimage?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    private static final String DATABASE_USERNAME = "root";
    private static final String DATABASE_PASSWORD = "";


    DB(){
        // load and register JDBC driver for MySQL
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
            statement = connection.createStatement();
        } catch (ClassNotFoundException | SQLException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
    }

    public void SaveImage(String name,String Adrress) {
        try {
            File image = new File(Adrress);
            FileInputStream inputStream = new FileInputStream(image);
            try (PreparedStatement preparedStatement = connection.prepareStatement("insert into trn_imgs(img_title, img_data) " + "values(?,?)")) {
                preparedStatement.setString(1, name);
                preparedStatement.setBinaryStream(2,inputStream, (int) (image.length()));

                // Step 3: Execute the query or update query
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                // print SQL exception information
                System.out.println(e);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public FileOutputStream ReturnImage(String id) throws SQLException, Exception {
        File file = new File("C:\\Users\\MF\\Desktop\\arrow.png\\");
        FileOutputStream fos = new FileOutputStream(file);
        byte b[];
        Blob blob;

        PreparedStatement ps = connection.prepareStatement("select * from trn_imgs WHERE img_id = 1");
        ResultSet rs=ps.executeQuery();

        while(rs.next()){
                blob=rs.getBlob("img_data");
                b=blob.getBytes(1,(int)blob.length());
                fos.write(b);

        }

//        ps.close();
//        fos.close();
//        connection.close();
        return fos;
    }


    public void CreateDB_Image() throws SQLException {
        String command  = "CREATE TABLE `fraimage   ` . `trn_imgs`(`img_id` int(10) unsigned NOT NULL auto_increment,`img_title` varchar(45) collate latin1_general_ci NOT NULL,`img_data` blob NOT NULL,PRIMARY KEY  (`img_id`))";
        System.out.println(statement.executeQuery(command));

    }

//    public static void main(String[] args) throws Exception {
//        DB obj = new DB();
//        obj.ReturnImage("C:\\Users\\MF\\Desktop\\arrow.png");
//    }

}


//abstract class varabile{
//    //Database varaible that save data
//    public static String UserId;
//    public static String QuizSelete;
//    public static String QuizTime;
//    public static String QuizNoofAttemt;
//    public static String TotalQuizQuestion;
//    public static boolean QuizStart = false;
//    public ArrayList<String> QuestioAnswer = new ArrayList<String>();
//    public String Answer;
//}
//
//interface Method {
//    void insertRecord(String NoOfQuestionCorrect,String Percentage,String dateTime) throws SQLException;
//    boolean validate(String emailId, String password) throws SQLException;
//    ArrayList<String> displayFeildList();
//    String getOnlyAnswer(int QuestionId);
//    boolean checkPin(String pinEnter,String Feild);
//    void forgetPassword(String id,String Password);
//    boolean QuizAlreadyGiven() throws SQLException;
//    ArrayList<String> getQuizDetails(int QuestionId);
//
//}

//    private static final String INSERT_QUERY = "INSERT INTO `resultofquiz` (`id`,`UserId`, `QuizName`, `TotalQuestion`, `NoOfQuestionCorrect`, `Percentage`, `Time`) VALUES (NULL,?,?, ?, ?, ?, ?)";
//    private static final String SELECT_QUERY_LOGIN = "SELECT * FROM login WHERE email_id = ? and password = ?";

//extends varabile implements Method
//    public void insertRecord(String NoOfQuestionCorrect,String Percentage,String dateTime) throws SQLException {
//
//        // Step 1: Establishing a Connection and
//        // try-with-resource statement will auto close the connection.
//
//        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_QUERY)) {
//            preparedStatement.setString(1, getUserId());
//            preparedStatement.setString(2, getQuizSelete());
//            preparedStatement.setString(3, getQuizNoofAttemt());
//            preparedStatement.setString(4, NoOfQuestionCorrect);
//            preparedStatement.setString(5, Percentage);
//            preparedStatement.setString(6, dateTime);
//
//            // Step 3: Execute the query or update query
//            preparedStatement.executeUpdate();
//        } catch (SQLException e) {
//            // print SQL exception information
//            printSQLException(e);
//        }
//    }
//    //    login system
//    public boolean validate(String emailId, String password) throws SQLException {
//
//        // Step 1: Establishing a Connection and
//        // try-with-resource statement will auto close the connection.
//        try (// Step 2:Create a statement using connection object
//             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_QUERY_LOGIN)) {
//            preparedStatement.setString(1, emailId);
//            preparedStatement.setString(2, password);
//
//            ResultSet resultSet = preparedStatement.executeQuery();
//            if(resultSet.next()){
//                UserId = emailId;
//                return true;
//            }
//
//        } catch (SQLException e) {
//            // print SQL exception information
//            printSQLException(e);
//        }
//        return false;
//    }
//    // fetch data
//    public ArrayList<String> displayFeildList(){
//        List<String> Feild = new ArrayList<String>();
//        try {
//            String query = "select * from subjectlist";
//            result = statement.executeQuery(query);
//
//            while (result.next()){
//                Feild.add(result.getString("Field"));
//            }}
//        catch (Exception e){
//            System.out.println("Error : "+e);
//        }
//        return (ArrayList<String>) Feild;
//    }
//    public boolean checkPin(String pinEnter,String Feild){
//        String query = "SELECT * FROM `subjectlist` WHERE `Field` LIKE "+'"'+Feild+'"'+" ";
//        boolean match = false;
//        try {
//            result = statement.executeQuery(query);
//            result.next();
//            String pin = result.getString("Pincode");
//            if (pin.equals(pinEnter)){
//                match = true;
//                QuizSelete = Feild;
//                QuizTime = result.getString("QuizTime");
//                QuizNoofAttemt =  result.getString("QuizNoofAttemp");
//                TotalQuizQuestion = result.getString("TotalQuestion");
//            }
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        }
//        return match;
//    }
//    public ArrayList<String> getQuizDetails(int QuestionId){
//        String query = "SELECT * FROM "+'`'+ QuizSelete +'`'+" WHERE `Id` LIKE "+'"'+QuestionId+'"'+" ";
//        QuizStart = true;
//        try {
//            result = statement.executeQuery(query);
//            result.next();
//            QuestioAnswer.add(" "+result.getString("Question"));
//            QuestioAnswer.add(" "+result.getString("A"));
//            QuestioAnswer.add(" "+result.getString("B"));
//            QuestioAnswer.add(" "+result.getString("C"));
//            QuestioAnswer.add(" "+result.getString("D"));
//            QuestioAnswer.add(result.getString("Answer"));
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        }
//        return QuestioAnswer;
//
//    }
//    public String getOnlyAnswer(int QuestionId){
//        String query = "SELECT `Answer` FROM "+'`'+ QuizSelete +'`'+" WHERE `Id` LIKE "+'"'+QuestionId+'"'+" ";
//
//        try {
//            result = statement.executeQuery(query);
//            result.next();
//            Answer = result.getString("Answer");
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        }
//        return Answer;
//
//    }
//    public void forgetPassword(String id,String Password){
//        String query = "UPDATE `login` SET `password`= ? WHERE email_id = ?";
//        try {
//            // create the java mysql update preparedstatement
//            PreparedStatement preparedStmt = connection.prepareStatement(query);
//            preparedStmt.setString(1, Password);
//            preparedStmt.setString(2, id);
//
//            // execute the java preparedstatement
//            preparedStmt.executeUpdate();
//
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        }
//    }
//    public boolean QuizAlreadyGiven() throws SQLException {
//        boolean match = false;
//        String query = "SELECT * FROM `resultofquiz` where `UserId` Like '"+getUserId()+"'and `QuizName` LIKE '"+getQuizSelete()+"'";//
//        result = statement.executeQuery(query);
//        if (result.next()){
//            match = true;
//        }
//        return match;
//    }
//
//    //print the error
//    public static void printSQLException(SQLException ex) {
//        for (Throwable e: ex) {
//            if (e instanceof SQLException) {
//                e.printStackTrace(System.err);
//                System.err.println("SQLState: " + ((SQLException) e).getSQLState());
//                System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
//                System.err.println("Message: " + e.getMessage());
//                Throwable t = ex.getCause();
//                while (t != null) {
//                    System.out.println("Cause: " + t);
//                    t = t.getCause();
//                }
//            }
//        }
//    }
//
//    // getter/Setter
//    public static boolean getQuizStart() {
//        return QuizStart;
//    }
//    public static void setUserId() {
//        UserId = null;
//    }
//    public static String getQuizSelete() {
//        return QuizSelete;
//    }
//    public static String getTotalQuizQuestion() {
//        return TotalQuizQuestion;
//    }
//    public static String getUserId() {
//        return UserId;
//    }
//    public static String getQuizNoofAttemt() {
//        return QuizNoofAttemt;
//    }
//    public static String getQuizTime() {
//        return QuizTime;
//    }