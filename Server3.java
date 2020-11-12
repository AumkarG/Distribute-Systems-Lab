import java.rmi.registry.Registry; 
import java.rmi.registry.LocateRegistry; 
import java.rmi.RemoteException; 
import java.rmi.server.UnicastRemoteObject; 

public class Server3 extends ImplExample { 
   public Server3(int s) {
      super(s);
   } 
   public static void main(String args[]) { 
      try { 
         ImplExample obj = new ImplExample(3); 
         ImplExample ob1 = new ImplExample(3);
    
     
         Student stub = (Student) UnicastRemoteObject.exportObject(obj, 4);  
         Teacher stub1=(Teacher) UnicastRemoteObject.exportObject(ob1,4);
         Registry registry = LocateRegistry.getRegistry(); 

         registry.rebind("Server3S", stub);  
         registry.rebind("Server3T",stub1);
         System.err.println("Server 3 ready"); 
      } catch (Exception e) { 
         System.err.println("Server exception: " + e.toString()); 
         e.printStackTrace(); 
      } 
   } 
} 