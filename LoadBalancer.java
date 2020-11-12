import java.rmi.registry.Registry; 
import java.rmi.registry.LocateRegistry; 
import java.rmi.RemoteException; 
import java.rmi.server.UnicastRemoteObject; 
import java.rmi.Remote; 
import java.util.*; 


// Creating Remote interface for our application 
interface Lb_interface extends Remote {  
   void grade_assignment() throws RemoteException;
   void view_assignment() throws RemoteException;
   void post_announcement(String annouce) throws RemoteException;
   boolean enter_marks(int resource_key, String message) throws RemoteException;
   String get_marks(int id) throws RemoteException;
   String  displayMarks(int id) throws RemoteException;
   void  postDoubt(String message) throws RemoteException;
   void uploadAssignment(byte[] buffer,int length,String fname) throws RemoteException;
   boolean acquire(int resource_key) throws RemoteException;
   boolean release(int resource_key) throws RemoteException;
   
}

public class LoadBalancer implements Lb_interface { 
   public int counter=0;
   public int queue[]=new int[100];
   public HashMap<Integer, Boolean> mutex_locks_marks = new HashMap<Integer, Boolean>();
   public HashMap<Integer, Integer> readers_count_marks = new HashMap<Integer, Integer>();
   public boolean mutex_locks_announce=false;
   public int readers_count_announce=0;
   public String studentServerList[]={"Server1S","Server2S","Server3S"};
   public String teacherServerList[]={"Server1T","Server2T","Server3T"};
   public int server_weights[]={5,3,2};
   public int server_Counters[]={0,0,0};

   public LoadBalancer() {
       mutex_locks_marks.put(1,false);
       mutex_locks_marks.put(2,false);
       mutex_locks_marks.put(3,false);
       mutex_locks_marks.put(4,false);
       readers_count_marks.put(1,0);
       readers_count_marks.put(2,0);
       readers_count_marks.put(3,0);
       readers_count_marks.put(4,0);

   } 

   public static void main(String args[]) { 
      try
      { 
         LoadBalancer lb = new LoadBalancer();         
         Lb_interface stub = (Lb_interface) UnicastRemoteObject.exportObject(lb, 0);  
         Registry registry = LocateRegistry.getRegistry(); 
         registry.rebind("LoadBalancer", stub);  
         System.err.println("Load balancer running ..."); 
         System.out.println("The servers working are :");
         for(int i=0;i<3;i++)
         {System.out.println(lb.studentServerList[i]);
         }
      } catch (Exception e) { 
         System.err.println("Server exception: " + e.toString()); 
         e.printStackTrace(); 
      } 
   } 
    
   public String displayMarks(int id) {  
        try
        {

            Registry registry = LocateRegistry.getRegistry(null);     
            String server=studentServerList[counter];
            System.out.println("Server allocated is "+server.charAt(6));
            counter+=1;
            counter%=3;
            Student stub =(Student) registry.lookup(server);
            return stub.displayMarks(id);
        }
        catch(Exception e)
        {
            System.out.println("Error");
            System.out.println(e);
        }
        return "Not found";
   }
   public void postDoubt(String message) {    
        try
        {
            Registry registry = LocateRegistry.getRegistry(null);     
            String server=studentServerList[counter];
            System.out.println("Server allocated is "+server.charAt(6));
            counter+=1;
            counter%=3;
            System.out.println(message);
            Student stub =(Student) registry.lookup(server);
            stub.postDoubt(message);
        }
        catch(Exception e)
        {
            System.out.println("Error");
            System.out.println(e);
        }
   }  
   public void uploadAssignment(byte[] buffer,int length,String fname) 
   {  
        try
        {
            Registry registry = LocateRegistry.getRegistry(null);     
            String server=studentServerList[counter];
            System.out.println("Server allocated is "+server.charAt(6));
            counter+=1;
            counter%=3;
            Student stub =(Student) registry.lookup(server);

            stub.uploadAssignment(buffer,length,fname);
        }
        catch(Exception e)
        {
            System.out.println("ERROR");
            System.out.println(e);
        }
   }  
    public void grade_assignment() { 
        try
        {
            Registry registry = LocateRegistry.getRegistry(null);     
            String server=teacherServerList[counter];
            System.out.println("Server allocated is "+server.charAt(6));
            System.out.println("Request :  Upload ");
            counter+=1;
            counter%=3;
            Teacher stub =(Teacher) registry.lookup(server);
            stub.grade_assignment();
           
        }
        catch(Exception e)
        {
        }
   }  
     public void view_assignment() {  
        try
        {
            Registry registry = LocateRegistry.getRegistry(null);     
            String server=teacherServerList[counter];
            System.out.println("Server allocated is "+server.charAt(6));
            System.out.println("Request :  View Assignment ");
            counter+=1;
            counter%=3;
            Teacher stub =(Teacher) registry.lookup(server);
            stub.view_assignment();
           
        }
        catch(Exception e)
        {
        }
   }  
     public void post_announcement(String announce) {  
        try
        {
            Registry registry = LocateRegistry.getRegistry(null);     
            String server=teacherServerList[counter];
            System.out.println("Server allocated is "+server.charAt(6));
            System.out.println("Request :  Announcment ");

            counter+=1;
            counter%=3;
            Teacher stub =(Teacher) registry.lookup(server);
            stub.post_announcement(announce);
           
        }
        catch(Exception e)
        {
        }
   }  
     public boolean enter_marks(int resource_key,String message) {  
        try
        {
         
                Registry registry = LocateRegistry.getRegistry(null);     
                String server=studentServerList[counter];
                System.out.println("Server allocated is "+server.charAt(6));
                counter+=1;
                counter%=3;
                Teacher stub =(Teacher) registry.lookup(server);              
                stub.enter_marks(message);
                return true;
        
        }
        catch(Exception e)
        {
            return false;
        }
    }   

     public String get_marks(int id) 
     {  
        try
        {

            Registry registry = LocateRegistry.getRegistry(null);     
            String server=teacherServerList[counter];
            System.out.println("Server allocated is "+server.charAt(6));
            counter+=1;
            counter%=3;
            Teacher stub =(Teacher) registry.lookup(server);
            return stub.get_marks(id);
        }
        catch(Exception e)
        {
            System.out.println("Error");
            System.out.println(e);
        }
        return "Not found";
    }  
    public boolean acquire(int resource_key)
    {
       if(mutex_locks_marks.get(resource_key))
       {
           // queue[queue.length]=queue.length;
            System.out.println("Locked");
            return false;

       }
        else
        {
            mutex_locks_marks.replace(resource_key,true); 
            System.out.println("granted");
            System.out.println(mutex_locks_marks);
            return true;
        }
    }
    public boolean release(int resource_key)
    {
        mutex_locks_marks.replace(resource_key,false);
        System.out.println("Released");
        return true;
    }

} 