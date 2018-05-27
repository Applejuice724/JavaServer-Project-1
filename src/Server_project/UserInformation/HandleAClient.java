/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server_project.UserInformation;

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
    private boolean Authenticated = false;
    BufferedReader isFromClient;
    PrintWriter osToClient;
    private final String ServerPath;
    private String RawTextFromClient;
    private Socket connectToClient;            // A connected socket   
    private String UserName;
    private String Password;
    
    private final UserDataHandler handleUserData = new UserDataHandler();
    public HandleAClient(Socket socket, String ServerFilePath)        /**Construct a thread*/    
    { 
        connectToClient = socket;
        ServerPath = ServerFilePath;
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
    }    
    private void setUpConnection()
    {
        try{
            while(true)
            {                                                     
               RawTextFromClient = isFromClient.readLine().replaceAll("//s", "");                                                        
               if (RawTextFromClient.equals("NETWORKSTART") && !Authenticated)                                     
               {                               
                   sendMessage(ACK);                              
                   getCredentials();                              
               }
               while(Authenticated)
               {                                  
                   System.out.println(":> Listening for client");                       
                   RawTextFromClient = isFromClient.readLine().replaceAll("//s", "");  
                   switch(RawTextFromClient)
                   {
                   }
               }
            }             
        }catch(IOException e){}    
    }
    private void getCredentials()    
    {          
        String ClientUsername;
        while(!Authenticated){        
            try{              
                RawTextFromClient = isFromClient.readLine().replaceAll("//s", "");                 
                if (onWhiteList(RawTextFromClient))                 
                {                
                    ClientUsername = RawTextFromClient;
                    sendMessage(ACK);                                                                                                        
                    RawTextFromClient = isFromClient.readLine().replaceAll("//s", ""); 
                    if (ClientUsername != null)
                    {                                       
                        if (PasswordCorrect(ClientUsername, RawTextFromClient))
                        {
                            Authenticated = true;
                            UserName = ClientUsername;
                            Password = RawTextFromClient;
                            handleUserData.CheckUserSetup(UserName, ServerPath);
                        }
                        else
                        {                            
                            sendMessage(DENYPASSINVALID); 
                            break;
                        }
                        System.out.println(RawTextFromClient);    
                    }
                } else // If not on whiteList
                {
                    sendMessage(DENYUSERINVALID); 
                    break;
                }                                        
            }catch(IOException e){}     
        }
    }
    private boolean onWhiteList(String InputName) // To do, Make whiteList
    {
        String foo = "Jane";
        if (InputName.equals(foo)) {                        
            System.out.println(InputName + " is on the whitelist");
            return true;
        }
        return false;
    }    
    private boolean PasswordCorrect(String inputName, String inputPassword)
    {            
        System.out.println("Authentication for " + inputName + " Password " + inputPassword);
        String foo = "Jane";
        String fooPass = "Doe";
        if (inputName.equals(foo) && inputPassword.equals(fooPass))        
        {              
            System.out.println("User Authenticated!");
            osToClient.println(ACK);   
            return true;
        }        
        System.out.println("Authentication: Failed");
        return false;
    }    
    void sendMessage(String Message)
    {
//        System.out.println(":> Sending " + Message);    
        RawTextFromClient = null;
        osToClient.flush();                       
        osToClient.println(Message);        
    }      
}
