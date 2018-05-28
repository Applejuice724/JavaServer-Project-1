/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server_project.UserInformation;
import Server_project.UserInformation.CreateServer.genServer;
import Server_project.UserInformation.FileManager.mySQLAccess;
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
                ServerThreads[i] = new genServer(InputData[i][0], InputData[i][1], InputData[i][2]);
                ServerThreads[i].start();
                System.out.println("*** Generated with Server Name " + InputData[i][2] + ":" +
                        " Path: "+ InputData[i][1] + " With port: "+ InputData[i][0]);

                if(SQL.CheckTable(InputData[i][2] + "users"))System.out.println("SQL Success:> Table for server "+ InputData[i][2]+ " Verified");                               
                else System.out.println("SQL Failure:> Table for server "+ InputData[i][2]+ " not verified");
                CurrentServerSlot = i;
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
            InetAddress clientInetAddress =
              connectToClient.getInetAddress();            
            System.out.println(":> Host Connected" + clientNo + " FROM IP " + clientInetAddress.getHostName() + " Connected to: " + ServerName);
            // Create a new thread for the connection
            HandleAClient thread = new HandleAClient(connectToClient, ServerFilePath, ServerName);
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
}

