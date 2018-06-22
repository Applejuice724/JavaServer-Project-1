/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server_project.UserInformation;
import Server_project.UserInformation.CreateServer.genServer;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;


public class CreateServer {        
    Thread ServerThreads[];
    boolean flagStartup = true;
    int CurrentServerSlot = 0;
    mySQLAccess SQL = mySQLAccess.getInstance();
    
    public CreateServer()
    {                                    
        ServerThreads = new Thread[100];
        SQL.StartDefaultDatabase();
        if (!SQL.CheckTable(SQL.admintable, SQL.DataBase)) SQL.CreateAdminTable(SQL.admintable, SQL.DataBase);
        System.out.println("Server Manager init");
    }
    public void ServerGenerationMass(String InputData[][], int ServerCount){ 
        System.out.println("*** Generating Servers ");        
        for (int i = 0; i < ServerCount; i++)     
        {
            if (checkConflictID(i))
            {
                flagStartup = false;
                System.out.println("*** Error: Server ID Conflict Detected! ");
                break;                
            }                        
            try{
                String TableServerUsers= RemoveIllegalCharecters(InputData[i][2])+ "users";
                String AdminName = RemoveIllegalCharecters(InputData[i][2])+"ADMIN";
//                System.out.println("*** Generated with Server Name " + InputData[i][2] + ":" +
//                        " Path: "+ InputData[i][1] + " With port: "+ InputData[i][0]);                
                if(SQL.CheckTable(TableServerUsers, InputData[i][1]))
//                    System.out.println("SQL Success:> Table for server "+ InputData[i][2]+ " Verified")
                    ;                               
                else System.out.println("SQL Failure:> Table for server "+ InputData[i][2]+ " not verified");                
                if(!SQL.checkUserExists(TableServerUsers, AdminName))               // Checks if default Admin Exists
                    SQL.AddUser(TableServerUsers, GenerateAdminSQLstatement(i, AdminName));       
                if(!SQL.checkUserExists(SQL.admintable, AdminName))               // Checks if default Admin has rights                
                    SQL.AddUser(SQL.admintable, GrantAdminPrivilige(i, AdminName));                 
                CurrentServerSlot = i;                                
                ServerThreads[i] = new genServer(InputData[i][0], InputData[i][1], InputData[i][2]);
                ServerThreads[i].start();
            }
            catch(Exception e)
            {                            
                System.out.println("*** Network Error: Server Input out of bounds!");
            }
        }                
        if (!flagStartup)
        {            
        } 
        System.out.println("*** End of server Gen! ");
    }  
    
        
    private boolean checkConflictID(int id)
    {    
        if (ServerThreads[id] == null) return false;
        return true;
    }
    
    final class genServer extends Thread
    {
        String ServerName;
        int ServerPort;
        String ServerFilePath;
        
        public genServer(String port, String Path, String inputServerName)
        {
            ServerName = inputServerName;
            ServerPort = Integer.parseInt(port);
            ServerFilePath = Path;
        }        
        @Override
        public void run() 
        {
          try
        {
          // Create a server socket
          ServerSocket serverSocket = new ServerSocket(ServerPort);
          // To number a client
          int clientNo = 1;

          while (true)
          {
            Socket connectToClient = serverSocket.accept();                  
            // Find the client's host name, and IP address
            System.out.println("Port Number:> "+ connectToClient.getLocalPort() + " Server Port:> " + ServerPort);
            InetAddress clientInetAddress =
              connectToClient.getInetAddress();            
            System.out.println(":> Host Connected" + clientNo + " FROM IP " + clientInetAddress.getHostName() + " Connected to: " + ServerName);
            // Create a new thread for the connection
            HandleAClient thread = new HandleAClient(connectToClient, ServerFilePath, ServerName, clientInetAddress.getHostName());
            // Start the new thread
            thread.start();
            // Increment clientNo
            clientNo++;
          }
        }
        catch(IOException ex)
        {
          System.err.println(ex);
        }
        }
    }
    
    private String GenerateAdminSQLstatement(int InputNumber, String Adminname)
    {
        return "(f_name, l_name ,email ,username,password) values( '"
                            +"ADMIN"+InputNumber+"', '"
                            +"ADMIN"+InputNumber+"', '"
                            +"ADMINISTRATOR"+InputNumber+"@email.com"+"',  '"    // Email
                            +Adminname+"', "                                     // UserName
                            +"password('ABC123'));";        
    }
    private String GrantAdminPrivilige(int InputNumber, String Adminname)
    {
        return "(f_name, l_name ,username, email) values( '"
                            +"ADMIN"+InputNumber+"', '"
                            +"ADMIN"+InputNumber+"', '"
                            +Adminname+"', '"                                     // UserName                                            
                            +"ADMINISTRATOR"+InputNumber+"@email.com');";        
    }
        private String RemoveIllegalCharecters(String inputVariable)
    {
        return inputVariable.replaceAll("\"", "").replaceAll("!", "").replaceAll(" ", "").toLowerCase();
    }      
}

