package Sql;
import java.sql.*;

 public class Sql{
    static final   String url="jdbc:mysql://localhost:3306/javapro";
    static final   String username="root";
    static final   String password="root";
    public static Connection connect(){

    
        Connection conn=null;
        try{
             conn = DriverManager.getConnection(url,username,password); 
        
            
        }
        catch(Exception e){
            System.out.println(e);
        }
         if(conn!=null){
            System.out.println("Welcome to WEB MEDICAL MANAGEMENT");
        }
        else{
            System.out.println("no");
        }       
        return conn;
    
    }
    public static void main(String[] args) {
        
    }

}