/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server_project.UserInformation;

import Server_project.UserInformation.FileManager.mySQLAccess;
import Server_project.UserInformation.FileManager.projectSystem.Security.fileEncrypt;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
/**
 *
 * @author Butthole
 */
       
class HandleAClient extends Thread      
{
    private final String ACK = "ACK";
    private final String DENYUSERINVALID = "DENY1";
    private final String DENYPASSINVALID = "DENY2";
    private fileEncrypt encryptFile;
    private boolean Authenticated = false;
    BufferedReader isFromClient;
    PrintWriter osToClient;
    private final String ServerPath;
    private String RawTextFromClient;
    private Socket connectToClient;            // A connected socket   
    private String UserName;
    private String ServerSQLName;
    private String Password;
    private mySQLAccess SQL = mySQLAccess.getInstance(); 
    
    private final UserDataHandler handleUserData = new UserDataHandler();
    public HandleAClient(Socket socket, String ServerFilePath, String inputServerName)        /**Construct a thread*/    
    { 
        ServerSQLName = inputServerName + "Users";
        connectToClient = socket;
        ServerPath = ServerFilePath;
        encryptFile = new fileEncrypt();
    }    
    @Override    
    public void run()    
    {        
        try        
        {        
            // Create data input and output streams            
            isFromClient = new BufferedReader(            
                    new InputStreamReader(connectToClient.getInputStream()));              
            osToClient = new PrintWriter(            
                    connectToClient.getOutputStream(), true);
            RawTextFromClient = "";
            setUpConnection();  // This is the credentual Check Stage               
            while (Authenticated)  // If creds Statisfied, run network Communicaitons          
            {
                RawTextFromClient = isFromClient.readLine().replaceAll("//s", "");                 
                
            }            
        } catch(IOException ex)         
        {                    
            System.err.println(ex);            
        }        
        System.out.print("User has logged off");
    }    
    private void setUpConnection()
    {
        try{
            while(!Authenticated)
            {                                                     
               RawTextFromClient = isFromClient.readLine().replaceAll("//s", "");                                                        
               if (RawTextFromClient.equals("NETWORKSTART") && !Authenticated)                                     
               {                               
                   sendMessage(ACK);                              
                   getCredentials();                              
               }
            }             
        }catch(IOException e){}    
    }
    private void getCredentials()    
    {          
        String ClientUsername;
        while(true){        
            try{                                            

                RawTextFromClient = isFromClient.readLine().replaceAll("//s", "");   
                ClientUsername = RawTextFromClient;  
                sendMessage(ACK);                         

                if (ClientUsername != null)                
                {                                   
                    RawTextFromClient = isFromClient.readLine().replaceAll("//s", "");   
                    if (Authenticate(ClientUsername, RawTextFromClient))                    
                    {                    
                        Authenticated = true;                        
                        UserName = ClientUsername;                        
                        Password = RawTextFromClient;                        
                        handleUserData.CheckUserSetup(UserName, ServerPath);                
                        fileEncrypt saveClientData = new fileEncrypt();
                        saveClientData.createClientDataWithEncyrp(ServerPath, getUserdata());                                                
                        sendMessage(ACK); 
                    }                    
                    else                    
                    {                                                
                        sendMessage(DENYPASSINVALID);                                                  
                        break;                       
                    }                    
//                    System.out.println(RawTextFromClient);                        
                }                                       
            }catch(IOException e){
//                System.out.println("Error!: " + e); 
                break;                                    
            }     
        }
    } 
    private boolean Authenticate(String inputName, String inputPassword)
    {            
//        System.out.println("Authentication for " + inputName + " Password " + inputPassword);              
        if(SQL.CheckCredentuals(ServerSQLName, inputName, inputPassword)) return true;
        else return false;
    }    
    void sendMessage(String Message)
    {
//        System.out.println(":> Sending " + Message);    
        RawTextFromClient = null;
        osToClient.flush();                       
        osToClient.println(Message);        
    }      
    String[] getUserdata()
    {
        String data[] = new String[4];
        data[0]= ("First Name").replaceAll("\\s", ""); // Gets the name of the User
        data[1]= ("Last Name").replaceAll("\\s", "");  // Gets the Last Name of User
        data[2]= ("Id Number").replaceAll("\\s", "");  // Gets ID of User
        data[3]= UserName;
        return data;
    }
}
