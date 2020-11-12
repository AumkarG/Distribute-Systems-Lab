import java.rmi.registry.Registry; 
import java.rmi.registry.LocateRegistry; 
import java.rmi.RemoteException; 
import java.rmi.server.UnicastRemoteObject; 

public class Server2 extends ImplExample { 
   public Server2(int s) {
      super(s);
   } 
   public static void main(String args[]) { 
      try { 
         ImplExample obj = new ImplExample(2); 
         ImplExample ob1 = new ImplExample(2);
    
    
         Student stub = (Student) UnicastRemoteObject.exportObject(obj, 2);  
         Teacher stub1= (Teacher) UnicastRemoteObject.exportObject(ob1,3);
         Registry registry = LocateRegistry.getRegistry(); 

         registry.rebind("Server2S", stub);  
         registry.rebind("Server2T",stub1);
         System.err.println("Server 2 ready"); 
      } catch (Exception e) { 
         System.err.println("Server exception: " + e.toString()); 
         e.printStackTrace(); 
      } 
   } 
} 