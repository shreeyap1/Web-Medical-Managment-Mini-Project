package Treatment;

import java.sql.*;
import java.util.*;
import Sql.Sql;
public class Treatment {

    public static void doTreatment(int p,int d)throws Exception{
        System.out.println("pid: "+p+" did :"+d);
        Scanner sc=new Scanner(System.in);
        Connection conn= Sql.connect();
        
            Statement statement = conn.createStatement();  
            PreparedStatement stq1= conn.prepareStatement("select problem from  treatment where pid=? and medicine is null");
            stq1.setInt(1, p);  
            ResultSet res1= stq1.executeQuery();
            ResultSet resultSet = statement.executeQuery("select pid,pname from patient where  pid="+p);
            //System.out.println("doc raaa");
            // if(!resultSet.next()){
            //     System.out.println("empty");
            // }
            
            while(resultSet.next())
            {
               // System.out.println("doc raaa INSIDE");
            System.out.println("patient id: "+resultSet.getString(1)+
            " \npatient name: "+resultSet.getString(2));
            }  
            while(res1.next())
            {
               // System.out.println("doc raaa INSIDE");
              //  System.out.println("pooobey");
            System.out.println("patient problem "+res1.getString(1));
            } 
                 


        System.out.println("enter medicine:");
        String med=sc.nextLine();
        PreparedStatement smt=conn.prepareStatement("update treatment set medicine=? where pid=? and did=? and medicine is null");
                    
                    smt.setString(1,med);
                    smt.setInt(2, p);
                    smt.setInt(3,d);
                    smt.executeUpdate();
                    smt.close();          
     




    }
}
