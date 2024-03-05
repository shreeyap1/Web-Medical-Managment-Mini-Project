package Doctor;

import java.sql.*;
import java.util.Scanner;

import Sql.Sql;
import Treatment.Treatment;

public class Doctor {
    String dname;
    int did;
    String spec;
    Integer yofexp;
    float rating;
    boolean available;
    public Doctor()
    {

    }
    public Doctor(String n,String sp,Integer ex)
    {
        dname=n;
        rating=5;
        available=true;
        spec=sp;
        yofexp=ex;
    }
    public void setAvailable(boolean av)
    {
        available=av;
    }
    public static Doctor get(int did)throws Exception{
        Connection conn = Sql.connect();
         PreparedStatement statement = conn.prepareStatement("SELECT * FROM doctor WHERE did = ?");
                        statement.setInt(1, did);
                        ResultSet result = statement.executeQuery();
                        Doctor doctor=null;
                        if (result.next()) {
                            String dname = result.getString("dname");
                            String spec = result.getString("spec");
                            int yofexp = result.getInt("years_of_experince");
                            float rating = result.getFloat("rating");
                            boolean available = result.getBoolean("available");

                            doctor = new Doctor(dname, spec, yofexp);
                            doctor.did = did;
                            doctor.rating = rating;
                            doctor.available = available;
                            return doctor;
                        }
                        return doctor;
                        
    }
    public  void reg_doc(){
        try (Connection conn = Sql.connect()){
                Scanner sc= new Scanner(System.in);

                    
                    Statement sta=conn.createStatement();  
               
                     Statement sam=conn.createStatement(); 
                     ResultSet res=sam.executeQuery("select did from doctor where did="+did+"");  
                    ResultSet r1=sta.executeQuery("select pid,did from Treatment where medicine IS NULL and did="+did+"");
                    if(!res.next()){
                         System.out.println("wrong id ");
                    } 
                    else if(!r1.next()){
                        System.out.println("no patients avialable enjoy");
                    }
                    else
                    {
                       
            
                        ResultSet r=sta.executeQuery("select pid,did from Treatment where medicine IS NULL and did="+did+"");
                        int arr[]=new int[3];
                        while(r.next())
                        {                 
                            arr[0]=r.getInt(1);
                            arr[1]=r.getInt(2);        
                            Treatment.doTreatment(arr[0],arr[1]);                
                        }
                            PreparedStatement s=conn.prepareStatement("update doctor set available=? where did=?");    
                            setAvailable(true);                
                            s.setBoolean(1,available);
                            s.setInt(2,did);                   
                            s.executeUpdate();
                            s.close(); 

                    }                
                    } catch (Exception e) {
                       System.out.println(e);
                    }
    }
    public void new_doc() throws SQLException{
        Connection conn = Sql.connect();
        PreparedStatement stq_ins= conn.prepareStatement("insert into doctor (dname,spec,available,rating,years_of_experince) values(?,?,?,?,?)");
        stq_ins.setString(1, dname);
        stq_ins.setString(2, spec);
        stq_ins.setBoolean(3,available);
        stq_ins.setFloat(4, rating);
        stq_ins.setInt(5, yofexp);
        stq_ins.executeUpdate();
        System.out.println("Doctor added");
        System.out.println("your id is:");
        PreparedStatement id=conn.prepareStatement("select * from doctor ");
        ResultSet d_id = id.executeQuery("select did from doctor ORDER BY did DESC LIMIT 1;");
        while(d_id.next()){
            System.out.println(d_id.getInt(1));
            did=d_id.getInt(1);
            }
    }
  
}
