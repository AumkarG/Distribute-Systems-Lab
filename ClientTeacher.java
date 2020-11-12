import java.rmi.registry.LocateRegistry; 
import java.rmi.registry.Registry;  
import java.util.Scanner;  
import java.util.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class ClientTeacher {  
   private ClientTeacher() 
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
               System.out.println("1. View marks");
               System.out.println("2. Enter marks");
               System.out.println("3. Make announcement");
               System.out.println("4. Grade assignment");
               System.out.println("5. Exit");
               int choice = sc.nextInt();
               int id;
               String s;

               switch(choice)
               {
                  case 1:
                     System.out.println("Enter student ID");
                     id = sc.nextInt();
                     String reply=stub.get_marks(id); 
                     System.out.println(reply);
                     break;
                  case 2:
                     System.out.println("Enter student ID");
                     id = sc.nextInt();
                     sc.nextLine();
                     int counter=0;
                     boolean flag=true;
                     while(stub.acquire(id)==false)
                     {
                         System.out.println("Waiting to access resource...");
                         try 
                         {
                              Thread.sleep(2000);
                           
                         } 
                         catch (Exception e)
                         {
                              e.printStackTrace();
                         }
                         counter+=1;
                         if(counter==5)
                         {
                            flag=false;
                            break;
                         }
                     }
                     if(flag)
                     {
                           System.out.println("Enter marks");
                           String message=sc.nextLine();
                           System.out.println("Please wait "+message);
                           boolean x=stub.enter_marks(id,message);
                           System.out.println(x);
                           stub.release(id);
                     }
                  case 3:
                     System.out.println("");
                     break;
                  case 4:
                     System.out.println("");
                     break;
                   case 5:
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