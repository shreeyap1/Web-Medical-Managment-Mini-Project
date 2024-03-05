import java.util.ArrayList;
import java.util.Scanner;

import Doctor.Doctor;
import Patient.Patient;
import Sql.Sql;
import Treatment.Treatment;

import java.sql.*;


public class App {
    public static void main(String[] args) throws Exception {
        Connection conn=Sql.connect();
        Scanner sc = new Scanner(System.in);

        boolean flag=true;
        while(flag==true){
            System.out.println("enter the user you want to select");
            System.out.println(" 1.PATIENT \n 2.DOCTOR \n 3.Exit");
            int c = sc.nextInt();

            
            if(c==1){

                System.out.println("You Have Entered Patient side");
                System.out.println("1.already registered or 2.new regeistration");

                // String ma=sc.nextLine();
                int m = sc.nextInt();
                switch (m) {
                    case 2:

                    System.out.println("enter patient details:");
                    System.out.println("enter patient name:");
                    String s=sc.nextLine();
                    while(s.equals("")){
                        System.out.println("Enter the valid name ");
                        s=sc.nextLine();
                    }
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
                     Patient p=new Patient(s,s1,s2);
                        p.new_reg();
                        
                    break;
                    case 1:
                    System.out.println("Please Enter your patient Id");
            
                    int pid =sc.nextInt();
                     PreparedStatement ps=conn.prepareStatement("select pid from patient where pid =?");
                             ps.setInt(1, pid);
                             ResultSet psres=ps.executeQuery();
                     if(!psres.next()){
                         System.out.println("No user existing");
                         
                     }else{
               
                        System.out.println("1.do you want to see new doctor \n 2.do you want to see all reports");
                        Integer a_reg=sc.nextInt();
                        Patient k=Patient.getp(pid);
                         switch(a_reg){
                            
                             case 1:
                                k.updated_reg();
                                break;
                             case 2:
                                k.getAreport(pid);      
                                break;              
         
         
                         }
                     }
                
                    }
                } 
                else if(c==2){
                    
                    //doctor
                    System.out.println("You Have Entered Doctor side");
                    System.out.println("1.New doctor\n2.Aldready registered");
                    
                    int m1 = sc.nextInt();
                    switch(m1){
                        case 1:
                        System.out.println("enter doctor name");
                        String d_name=sc.nextLine();
                        while(d_name.equals("")){
                            d_name=sc.next();
                        }
                        System.out.println("your specialization");
                        String spec= sc.nextLine();
                        while(spec.equals("")){
                            spec=sc.next();
                        }
                        System.out.println("enter your year of experince");
                        String y=sc.nextLine();
                        while(y.equals("")){
                            y=sc.next();
                        }
                        Integer ye=Integer.parseInt(y);
                        Doctor d =new Doctor(d_name,spec,ye);
                        d.new_doc();
                        break;
                        
                        case 2:
                        System.out.println("enter your doctor id");
                        int did = sc.nextInt();
                        Doctor dy=Doctor.get(did);
                        dy.reg_doc();
                        break;
            
                        
                    }
                    // Scanner sc = new Scanner(System.in);
                    
                    
                }
                else if(c==3){
                    System.out.println("Exiting!!");
                    flag=false;

                }
                else
                {
                    System.out.println("wrong input");
                    flag=false;
                }
                
  }

}
public static boolean checkSpec(String s2, ArrayList<String> arr) {

    for(int i=0;i<arr.size();i++)
    {
        if(s2.equals(arr.get(i))){
            return true;
        }
    }

    return false;
}
}