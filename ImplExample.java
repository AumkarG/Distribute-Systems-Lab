import java.rmi.Remote; 
import java.rmi.RemoteException;  
import java.io.File;  
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;  
import java.io.FileWriter;   
import java.util.Scanner; 
import java.io.FileNotFoundException;  
import java.rmi.registry.Registry; 
import java.rmi.registry.LocateRegistry; 
import java.rmi.RemoteException; 
import java.rmi.server.UnicastRemoteObject; 

interface Student extends Remote {  
   String displayMarks(int id) throws RemoteException;
   void  postDoubt(String doubt) throws RemoteException;
   void uploadAssignment(byte[] buffer,int length,String fname) throws RemoteException;
   void copy_write(String message,String fname,boolean append) throws RemoteException;
   void copy_assgt(byte[] buffer,int length,String fname) throws RemoteException;
   //public boolean sendData(String filename, byte[] data, int len) throws RemoteException;

//   void copy_write(String message,String fname) throws RemoteException;


} 

interface Teacher extends Remote {  
   void grade_assignment() throws RemoteException;
   void view_assignment() throws RemoteException;
   void post_announcement(String message) throws RemoteException;
   void enter_marks(String message) throws RemoteException;
   String get_marks(int id) throws RemoteException;
   void copy_write(String message,String fname,boolean append) throws RemoteException;
} 

public class ImplExample implements Student, Teacher{  

   int server_no;
   String db;
   public int serves[]={1,2,3};

   public ImplExample(int server_no)
   {
      this.server_no=server_no;
      this.db="DB"+server_no;
      System.out.println("Server number "+this.server_no+" "+"Connected to database :"+this.db);
   }





   public String displayMarks(int id) {  
      try 
      {
         File myObj = new File(db+"//"+"marks"+id+".txt");
         Scanner myReader = new Scanner(myObj);
         String data = myReader.nextLine();
         System.out.println("Marks of student "+id+" accessed");
         myReader.close();
         return data;
      } 
      catch (FileNotFoundException e) {
         System.out.println("Bad request");
      }
      return "Not found";

   }





   public void postDoubt(String doubt) 
   {   

      try 
      {
         FileWriter myWriter = new FileWriter(db+"//doubt_forum.txt",true);
         myWriter.write(doubt);
         myWriter.write("\n");   
         myWriter.close();
         Registry registry = LocateRegistry.getRegistry(null);     

         System.out.println("Successfully wrote to the file.");
         for(int i=1;i<4;i++)
         {
            if(i!=server_no)
            {
               try{
               Student stub =(Student) registry.lookup("Server"+i+"S");
               stub.copy_write(doubt,"doubt_forum",true); 
               }
               catch(Exception e){

               }
            }
         }
      } 
      catch (Exception e) 
      {
         System.out.println("An error occurred.");
         e.printStackTrace();
      }
   }  










   public void uploadAssignment(byte[] buffer,int length,String fname) {  
      FileOutputStream outstream = null;

       try
       {
            File outfile =new File(db+"//"+fname);
            outstream = new FileOutputStream(outfile);
            outstream.write(buffer, 0,length);
            outstream.close();
            System.out.println("Assignment uploaded");
            Registry registry = LocateRegistry.getRegistry(null);     

            for(int i=1;i<4;i++)
            {
               if(i!=server_no)
               {
                  try{
                  Student stub =(Student) registry.lookup("Server"+i+"S");
                  stub.copy_assgt(buffer,length,fname); 
                  }
                  catch(Exception e){

                  }
               }
            }
      }
      catch(IOException ioe)
      {
            ioe.printStackTrace();
      }


   }  





    public void grade_assignment() {  
      System.out.println("This is an example RMI potat");  
   }



     public void view_assignment() {  
      System.out.println("This is an example RMI potat");  
   }



     public void post_announcement(String message) {  
      System.out.println("Announcement = "+message);  
   }  



     public void enter_marks(String message) {  

      try 
      {
         FileWriter myWriter = new FileWriter(db+"//marks1.txt");
         myWriter.write(message);
         myWriter.close();
         Registry registry = LocateRegistry.getRegistry(null);     

         System.out.println("Successfully wrote to the file.");
         for(int i=1;i<4;i++)
         {
            if(i!=server_no)
            {
               try{
               Teacher stub =(Teacher) registry.lookup("Server"+i+"S");
               stub.copy_write(message,"marks1",true); 
               }
               catch(Exception e){

               }
            }
         }
      } 
      catch (Exception e) 
      {
         System.out.println("An error occurred.");
         e.printStackTrace();
      }
   } 
    public void  copy_write(String message, String fname, boolean append) {  

      try 
      {
         FileWriter myWriter = new FileWriter(db+"//"+fname+".txt",append); 

         myWriter.write(message);
         if(append)
            myWriter.write("\n");  
         myWriter.close();
         System.out.println("Database updated to maintain consistency.");
      } 
      catch (IOException e) 
      {
         System.out.println("An error occurred.");
         e.printStackTrace();
      }
   }  
    public void  copy_assgt(byte[] buffer,int length,String fname) {  
      FileOutputStream outstream = null;

      try 
      {
         File outfile =new File(db+"//"+fname);
         outstream = new FileOutputStream(outfile);
         outstream.write(buffer, 0,length);
         outstream.close();
         System.out.println("Assignment copied to maintain consistency");
      } 
      catch (IOException e) 
      {
         System.out.println("An error occurred.");
         e.printStackTrace();
      }
   }  
     public String get_marks(int id) {  
      try 
      {
         File myObj = new File(db+"//"+"marks"+id+".txt");
         Scanner myReader = new Scanner(myObj);
         String data = myReader.nextLine();
         System.out.println("Marks of student "+id+" accessed");
         myReader.close();
         return data;
      } 
      catch (FileNotFoundException e) {
         System.out.println("Bad request");
      }
      return "Not found";
   }  

}