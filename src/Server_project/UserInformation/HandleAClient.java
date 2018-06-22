/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server_project.UserInformation;

import Server_project.UserInformation.FileManager.projectSystem.Security.fileEncrypt;
import Server_project.UserInformation.clientLogger.clientLogger;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.nio.file.Files;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Butthole
 */
       
class HandleAClient extends Thread      
{
    private mySQLAccess SQL = mySQLAccess.getInstance(); 
    private boolean Authenticated = false;
    private boolean Process = false;
    public boolean running = true;
    private final String ACK = "ACK";
    private final String DENYUSERINVALID = "DENY1";
    private final String DENYPASSINVALID = "DENY2";
    private final String FILECOULDNOTBECREATED = "FILEERROR1";
    private final String DENYSECURITYCLEAR = "DENYSECURITYCLEAR";
    private final String USERINFOREQUEST = "USERINFOREQUEST";
    private final String FILETRANREQ =     "FILETRANREQ";
    private final String ADDUSRREQ =     "ADDUSERREQ";
    private fileEncrypt encryptFile;
    private BufferedReader isFromClient;
    private PrintWriter osToClient;      
    private final String ServerPath;           // Path to the IndexFile
    private String clientFilePath;             // Path to the Clients file when  Authenticated
    private String RawTextFromClient;          // String from Client
    private Socket connectToClient;            // A connected socket  
    //      Client Informaiton
    private String ClientIp;
    private String Profile;    
    private String UserName;
    private String FirstName;
    private String LastName;
    private String StaffID;
    private String LastLoginData;
    private String ServerSQLName;
    private String Password;   
    private static final UserDataHandler handleUserData = new UserDataHandler();
    private static clientLogger ClientActivityLog; 
    public HandleAClient(Socket socket, String ServerFilePath, String inputServerName, String clientIP)        /**Construct a thread*/    
    { 
        ServerSQLName = inputServerName + "Users";
        connectToClient = socket;
        ServerPath = ServerFilePath;
        encryptFile = new fileEncrypt();
        ClientIp = clientIP;
    }    
    @Override    
    public void run()    
    {   
        while(running){
            try        
            {        
                // Create data input and output streams            
                isFromClient = new BufferedReader(            
                        new InputStreamReader(connectToClient.getInputStream()));              
                osToClient = new PrintWriter(            
                        connectToClient.getOutputStream(), true);
                RawTextFromClient = "";
                setUpConnection();      // This is the credentual Check Stage  
            }  
            catch(IOException ex){                    
                System.err.println(ex);            
            } 
            while(!connectToClient.equals(connectToClient.isClosed()) && running)
            {
                while (Authenticated && running)   // If creds Statisfied, run network Communicaitons          
                {
                try {                          
                    RawTextFromClient = isFromClient.readLine().replaceAll("//s", "");

                    switch (RawTextFromClient)
                    {
                        case USERINFOREQUEST: 
                             sendMessage(Profile);
                             sendMessage(UserName);
                             sendMessage(FirstName);
                             sendMessage(LastName);
                             sendMessage(StaffID);
                             sendMessage(LastLoginData);
                            break;                        
                        case FILETRANREQ:                              
    //                        System.out.println("Receiving File");
                            File SavedFile;
                            try { 
                                if (handleUserData.IfClientFileExists(clientFilePath)){                                                                  
                                    sendMessage(ACK);                                                                                    
                                    RawTextFromClient = isFromClient.readLine().replaceAll("//s", "");                            
                                    String FileName = RawTextFromClient;                            
                                    sendMessage(ACK);                                                    
                                    SavedFile = new File(clientFilePath+"\\"+FileName);                                                            
                                    // *****************                                                                                 
                                    InputStream inp = connectToClient.getInputStream();                            
                                    ObjectInputStream ois = new ObjectInputStream(inp);                            
                                    byte[] content = null;                            
                                    content = (byte[]) ois.readObject();                                                            
                                    Files.write(SavedFile.toPath(), content);                            
                                    isFromClient = new BufferedReader(                                                                                                          
                                            new InputStreamReader(connectToClient.getInputStream()));                                          
                                    osToClient = new PrintWriter(   
                                                connectToClient.getOutputStream(), true);              
                                    if (handleUserData.IfClientFileExists(SavedFile.toString()))sendMessage(ACK);                              
                                    else {sendMessage(FILECOULDNOTBECREATED); 
                                    break;}
                                    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");	
                                    Date date = new Date();
                                    System.out.println("*** File from network saved!");
                                    ClientActivityLog.writeActivity("FILE-UPLOADED: " + SavedFile +" UPLOADED-AT: " + dateFormat.format(date));                                                     
                                    break;                        
                                }
                            }
                            catch (IOException ex) {sendMessage(FILECOULDNOTBECREATED);} 
                            catch (ClassNotFoundException ex) {sendMessage(FILECOULDNOTBECREATED);}  
                            break;

                        case ADDUSRREQ:
                            try{                

                                sendMessage(ACK);                 
                                RawTextFromClient = isFromClient.readLine().replaceAll("//s", "");  
                                if (!SQL.getProfile(UserName).equals("ADMIN")) 
                                {
                                    sendMessage(DENYSECURITYCLEAR);
                                    break;
                                }
                                sendMessage(ACK);                                                                                                
                                String SQLCommand = isFromClient.readLine();
                                System.out.println(SQLCommand);
                                sendMessage(ACK);
                                SQL.AddUser(ServerSQLName, SQLCommand);
                            }catch(Exception e){}

                            break;
                    } } catch (IOException ex) {
                        System.out.print("User has logged off");                   
                        ClientActivityLog.setLastLogout();
                        break;
                    }                  
                }
            }
        }
        System.out.println("User has logged off");  
        return;
    }    
    private void setUpConnection()
    {
        try{
            while(!Authenticated && running)
            {                     
               RawTextFromClient = null;
               RawTextFromClient = isFromClient.readLine().replaceAll("//s", "");    
               System.out.println("*** Authenticaiton message received: "+RawTextFromClient);
               if (RawTextFromClient.equals("NETWORKSTART") && !Authenticated)                                     
               {                               
                   sendMessage(ACK);                              
                   getCredentials(); 
                   if(!running) break;
               }
            }             
        }catch(IOException e){} 
        return;
    }
    private void getCredentials()    
    {          
        String ClientUsername;
        while(running){        
            try{         
                RawTextFromClient =null;
                RawTextFromClient = isFromClient.readLine().replaceAll("//s", "");   
                ClientUsername = RawTextFromClient;  
               System.out.println("*** Username received: "+RawTextFromClient);
                sendMessage(ACK);                         
                if (ClientUsername != null)                
                {                                   
                    RawTextFromClient = isFromClient.readLine().replaceAll("//s", "");   
                    if (Authenticate(ClientUsername, RawTextFromClient))                    
                    {                    
                        Authenticated = true;                        
                        UserName = ClientUsername;                        
                        Password = RawTextFromClient;            
                        System.out.println("*** Password received: "+RawTextFromClient);
                        String path_w = ServerPath.substring(0, ServerPath.length() - 5);       
                        clientFilePath = path_w+"ClientData/"+UserName;
                        handleUserData.CheckUserSetup(UserName, ServerPath);                
                        fileEncrypt saveClientData = new fileEncrypt();
                        saveClientData.createClientDataWithEncyrp(ServerPath, getUserdata()); 
                        PopulateUserData(ClientUsername);
                        ClientActivityLog = new clientLogger(clientFilePath, UserName);
                        ClientActivityLog.updateClientIP(ClientIp);
                        ClientActivityLog.printlog();
                        LastLoginData = ClientActivityLog.getLastLogin();
                        sendMessage(ACK); 
                        break;
                    }                    
                    else                    
                    {                                                
                        sendMessage(DENYPASSINVALID); 
                        running = false;
                        break;                       
                    }                    
//                    System.out.println(RawTextFromClient);                        
                }                                       
            }catch(IOException e){
                System.out.println("Error!: " + e); 
                break;                                    
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(HandleAClient.class.getName()).log(Level.SEVERE, null, ex);
            }  
//            System.out.println("ClientFile: "+clientFilePath);
        }                    
//        System.out.println("Authentication failed!");                        
    } 
    private boolean Authenticate(String inputName, String inputPassword)
    {            
//        System.out.println("Authentication for " + inputName + " Password " + inputPassword);              
        if(SQL.CheckCredentuals(ServerSQLName, inputName, inputPassword)) return true;
        else return false;
    }    
    void sendMessage(String Message)
    {
        System.out.println(":> Sending " + Message);    
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
    private void PopulateUserData(String Username)
    {
        try{
            String[] data_W = SQL.getUserInformaiton(ServerSQLName, Username);
            Profile=    SQL.getProfile(data_W[1]); 
            UserName=   data_W[1];
            FirstName=  data_W[2];
            LastName=   data_W[3];
            StaffID=    data_W[4];
        }catch(Exception e){System.out.println(e);}
    }
}
