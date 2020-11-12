import java.rmi.registry.LocateRegistry; 
import java.rmi.registry.Registry;  
import java.util.Scanner;  
import java.util.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class ClientStudent {  
   private ClientStudent() 
   {

   }  
   public static void main(String[] args) 
   {  
      try 
      {  
            Registry registry = LocateRegistry.getRegistry(null);     
            Lb_interface stub =(Lb_interface) registry.lookup("LoadBalancer");
            boolean execute=true;
            Scanner sc = new Scanner(System.in);

            while(execute==true)
            {
               System.out.println("Enter the number corresponding to your choice");
               System.out.println("1. Display marks");
               System.out.println("2. Post doubt");
               System.out.println("3. Upload assignment");
               System.out.println("4. Exit");


               int choice = sc.nextInt();
               int id;

               switch(choice)
               {
                  case 1:
                     System.out.println("Enter student ID");
                     id = sc.nextInt();
                     String marks=stub.displayMarks(id);
                     System.out.println(marks); 
                     break;
                  case 2:
                     System.out.println("Enter student ID");
                     id = sc.nextInt();
                     sc.nextLine();
                     System.out.println("Enter doubt");
                     String message = sc.nextLine();
                     stub.postDoubt(message);
                     System.out.println("Doubt posted");
                     break;
                  case 3:
                  FileInputStream instream = null;
                  System.out.println("Enter file path");
                  String path;
                  path=sc.nextLine();
                  path=sc.nextLine();
                  String[] words=path.split("\\\\");
                  System.out.println(words);
                  String name=words[words.length-1];
                  System.out.println(name);
                  try{
                        File infile =new File(path);
               
                        instream = new FileInputStream(infile);
               
                        byte[] buffer = new byte[10485760];
                        int length=instream.read(buffer);
                        stub.uploadAssignment(buffer,length,name);
                        instream.close();
                        System.out.println("File copied");
                     }
                     catch(IOException ioe)
                     {
                        ioe.printStackTrace();
                     }
                     break;
                   case 4:
                     System.out.println("Exiting...");
                     execute=false;
                     break;
                  default:
                     System.out.println("Please re-enter a valid input");
                     break;
               }
            }
      }
      catch (Exception e) 
      {
            System.err.println("Client exception: " + e.toString()); 
            e.printStackTrace(); 
      } 
   } 
}