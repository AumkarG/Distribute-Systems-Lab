import java.rmi.registry.Registry; 
import java.rmi.registry.LocateRegistry; 
import java.rmi.RemoteException; 
import java.rmi.server.UnicastRemoteObject; 

public class Server1 extends ImplExample { 
   public Server1(int server_no) {
      super(server_no);
   } 
   public static void main(String args[]) { 
      try { 
         ImplExample obj = new ImplExample(1); 
         ImplExample ob1 = new ImplExample(1);
    
      
         Student stub = (Student) UnicastRemoteObject.exportObject(obj, 0);  
         Teacher stub1=(Teacher) UnicastRemoteObject.exportObject(ob1,1);


         Registry registry = LocateRegistry.getRegistry(); 

         registry.rebind("Server1S", stub);  
         registry.rebind("Server1T",stub1);
         System.err.println("Server 1 ready"); 
      } catch (Exception e) { 
         System.err.println("Server exception: " + e.toString()); 
         e.printStackTrace(); 
      } 
   } 
} 