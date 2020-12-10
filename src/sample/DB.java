package sample;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class DB {
    public static void main(String args[]) throws Exception{
            //Registering the Driver
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            //Getting the connection
            String mysqlUrl = "jdbc:mysql://localhost/sampleDB";
            Connection con = DriverManager.getConnection(mysqlUrl, "root", "password");
            System.out.println("Connection established......");
            PreparedStatement pstmt = con.prepareStatement("INSERT INTO MyTable VALUES(?,?)");
            pstmt.setString(1, "sample image");
            //Inserting Blob type
            InputStream in = new FileInputStream("E:\\images\\cat.jpg");
            pstmt.setBlob(2, in);
            //Executing the statement
            pstmt.execute();
            System.out.println("Record inserted......");
        }

}
