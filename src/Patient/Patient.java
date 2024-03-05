package Patient;
import java.sql.*;
import java.util.*;

import Doctor.Doctor;
import Sql.Sql;
public  class Patient {
    
   public static Connection conn=Sql.connect();
    static int c = 0;

   static Scanner sc= new Scanner(System.in);
    String pname;
    String pproblem ;
    String pspeacialistrequired ;
    int pid;
    public Patient(String p,String pr,String s)
    {
        pname=p;
        pproblem=pr;
        pspeacialistrequired=s;
        System.out.println("wvdg");
    }
    public Patient(int id,String s)
    {
        pid=id;
        pname=s;
    }

    public static Patient getp(int pid)throws Exception{
        Connection conn = Sql.connect();
         PreparedStatement statement = conn.prepareStatement("SELECT * FROM patient WHERE pid = ?");
                        statement.setInt(1, pid);
                        ResultSet result = statement.executeQuery();
                        Patient x=null;
                        if (result.next()) {
                            String pname = result.getString("pname");
                            x=new Patient(pid,pname);
                        }
                        return x;
                        
    }
   public void new_reg() throws Exception{
            System.out.println(pname+" "+pproblem+" "+pspeacialistrequired);
                     
            PreparedStatement smt=conn.prepareStatement("insert into patient (pname) values(?)");
            smt.setString(1,pname);
            
            smt.executeUpdate();
            smt.close();          

            PreparedStatement statement = conn.prepareStatement("select * from doctor where  spec=?;");
            
            statement.setString(1,pspeacialistrequired);
                
            ResultSet resultSet1 = statement.executeQuery();


            
            if(!resultSet1.next()){
                  System.out.println("no doctors avialable");
                  //break;
            }                                                  
            else{
                PreparedStatement stat = conn.prepareStatement("select * from doctor where spec=?;");
                // stat.setBoolean(1,true);
                stat.setString(1,pspeacialistrequired);
                ResultSet resultSet = statement.executeQuery();

                 while(resultSet.next()){
                System.out.println("Doctor Id = "+resultSet.getString(1)+" "+"Name = "+resultSet.getString(2)+" "+"Specialization = "+resultSet.getString(3)+" "
                +"Available = "+resultSet.getString(4)+" "+"Rating = "+resultSet.getString(5)+" "+"Years_Of_Experince = "+resultSet.getString(6)+" ");
                }   
                System.out.println("Enter the Doctor Id from the available doctor : ");
                String choice = sc.nextLine();
                int ch=Integer.parseInt(choice);
                PreparedStatement sta = conn.prepareStatement("select did from doctor where available=true and did=?");
                sta.setInt(1, ch);
                ResultSet r = sta.executeQuery();
                if (!r.next()) {
                    System.out.println("Doctor Busy");
                }
               
                else{
                
                    System.out.println("Your Id(pid)= ");
                    String i="";
                    ResultSet rsa= statement.executeQuery("select pid from patient ORDER BY pid DESC LIMIT 1;");
                    while(rsa.next()){
                    i= (rsa.getString(1));
                    System.out.println(i);
                    }
                    int ci=Integer.parseInt(i);  
                    PreparedStatement st=conn.prepareStatement("insert into Treatment (pid,did,problem,specialist) values(?,?,?,?)");
                    st.setInt(1, ci);
                    st.setInt(2, ch);
                    st.setString(3,pproblem);
                    st.setString(4, pspeacialistrequired);
                    st.executeUpdate();
                
                    
                    
                    
                    PreparedStatement sm=conn.prepareStatement("update doctor set available=? where did=?");                    
                        sm.setBoolean(1,false);
                        sm.setInt(2,ch);
                    
                        sm.executeUpdate();
                        sm.close();      


                } 
            }    
   }
   
    public  void updated_reg( ) throws Exception{

        System.out.println("enter problem:");
            String s1=sc.nextLine();
             while(s1.equals("")){
                System.out.println("Enter the Correct Problem ");
                s1=sc.nextLine();
            }
            System.out.println("specialists at our hospital :-");
            Statement stq=conn.createStatement(); 
            ResultSet speci=stq.executeQuery("select DISTINCT  spec from doctor ");
            ArrayList<String> arr = new ArrayList<String>();
            while(speci.next()){

                arr.add(speci.getString(1));
                System.out.println((speci.getString(1)));
            }  

            System.out.println("enter specialist required:");
            String s2=sc.nextLine();
            while(!checkSpec(s2,arr)){
                System.out.println("Enter the valid specialist ");
                s2=sc.nextLine();
            }                
            PreparedStatement statement = conn.prepareStatement("select * from doctor where  spec=?;");
            
            statement.setString(1,s2);
                
            ResultSet resultSet1 = statement.executeQuery();


            
            if(!resultSet1.next()){
                  System.out.println("no doctors avialable");
                  //break;
            }                                                  
            else{
                PreparedStatement stat = conn.prepareStatement("select * from doctor where spec=?;");
                // stat.setBoolean(1,true);
                stat.setString(1,s2);
                ResultSet resultSet = statement.executeQuery();

                 while(resultSet.next()){
                System.out.println("Doctor Id = "+resultSet.getString(1)+" "+"Name = "+resultSet.getString(2)+" "+"Specialization = "+resultSet.getString(3)+" "
                +"Available = "+resultSet.getString(4)+" "+"Rating = "+resultSet.getString(5)+" "+"Years_Of_Experince = "+resultSet.getString(6)+" ");
                }   
                System.out.println("Enter the Doctor Id from the available doctor : ");
                String choice = sc.nextLine();
                int ch=Integer.parseInt(choice);
                PreparedStatement sta = conn.prepareStatement("select did from doctor where available=true and did=?");
                sta.setInt(1, ch);
                ResultSet r = sta.executeQuery();
                if (!r.next()) {
                    System.out.println("Doctor Busy");
                }
               
                else{
                
                    System.out.println("Your Id(pid)= "+pid);
                    // String i="";
                    // ResultSet rsa= statement.executeQuery("select pid from patient ORDER BY pid DESC LIMIT 1;");
                    // while(rsa.next()){
                    // i= (rsa.getString(1));
                    // System.out.println(i);
                    // }
                    // int ci=Integer.parseInt(i);  
                    PreparedStatement st=conn.prepareStatement("insert into Treatment (pid,did,problem,specialist) values(?,?,?,?)");
                    st.setInt(1, pid);
                    st.setInt(2, ch);
                    st.setString(3,s1 );
                    st.setString(4, s2);
                    st.executeUpdate();
                
                    
                    
                    
                    PreparedStatement sm=conn.prepareStatement("update doctor set available=? where did=?");                    
                        sm.setBoolean(1,false);
                        sm.setInt(2,ch);
                    
                        sm.executeUpdate();
                        sm.close();      


                } 
            }    
    }

    public  void  getAreport(int pid)throws Exception{
                    System.out.println("Medicine for your problem is");
            float rating=0;
             Statement st=conn.createStatement();
             ResultSet r=st.executeQuery("select medicine,did from treatment where pid="+pid+";");
              Statement sam=conn.createStatement();
             ResultSet re=sam.executeQuery("select medicine,did from treatment where pid="+pid+";");
            String med="";
            int did=0;
                          Statement sam1=conn.createStatement();
             ResultSet re1=sam1.executeQuery("select medicine,did from treatment where pid="+pid+";");
        
             while(r.next()){
                med= (r.getString(1));
                did= r.getInt(2);                            
                }
            
            if(!re.next()){
                System.out.println("No data found........kindly  register your details");
            }
            else if(med==null){
                System.out.println("prescibing .....");
            }
            else{
              //  System.out.println(med);
                           while(re1.next()){
                            
              System.out.println( "Medicine = "+(re1.getString(1)));
              System.out.println("Prescribed by d_id :"+re1.getInt(2));
                }

                System.out.println("Enter rating in 10");
                rating= sc.nextFloat();
            }
            
            if(rating>10){
                System.out.println("Please enter in between 0 and 10");
                float rating2=sc.nextFloat();
                rating =rating2;
            }
            PreparedStatement set_rating=conn.prepareStatement("update doctor set rating=(rating+?)/2 where did=?");
            set_rating.setFloat(1, rating);
            set_rating.setInt(2, did);
          //  ((Connection) set_rating).commit();
          set_rating.executeUpdate();
            set_rating.close();
    }

    
    private static boolean checkSpec(String s2, ArrayList<String> arr) {

        for(int i=0;i<arr.size();i++)
        {
            if(s2.equals(arr.get(i))){
                return true;
            }
        }

        return false;
}


}
