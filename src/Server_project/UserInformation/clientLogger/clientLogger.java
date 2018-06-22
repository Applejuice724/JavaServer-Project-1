/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server_project.UserInformation.clientLogger;

import Server_project.UserInformation.FileManager.projectSystem.File_Manager;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
public class clientLogger {
    private static ClientData clientDataLog;   
    private static File_Manager man = new File_Manager();
    private static String Path_w;
    private static String LogTextPath;
    private static String Username_w;
    private static String LastLogon;
    private static  String[] content;
    
    // 0 = Username, 1 = LastLogin, 2= ClientIP Last Entry = recent History    
    public clientLogger(String clientPath, String Username) throws ClassNotFoundException            
    {
        Path_w = clientPath+"/Activity.ser";
        LogTextPath=clientPath+"/ActivityLog.txt";
        Username_w = Username;
        if (!man.DoesExist(clientPath))man.createFolder(clientPath);
        if (!man.DoesExist(clientPath)) 
        {
            clientDataLog = new ClientData(Path_w, Username);
            serializeClientData();
            writeSucceededLogin();
        }
        DeserializeClientData();
        content=clientDataLog.getlog();
    }  
    public void refresh()
    {               
        serializeClientData();
    }
    public String getUsername()
    {
        try
        {
             return LastLogon = content[0];        
        }catch(Exception e)
        {return null;}
    }    
    public String getLastLogin()
    {
        try{        
            LastLogon = content[1];              
            writeSucceededLogin();            
            return LastLogon;            
        }
        catch(Exception e){return null;}
    }  
    public void updateClientIP(String IP)
    {
        clientDataLog.setClientIP(IP);
        System.out.println("IP CLIENT LOGGED: " + IP);
        serializeClientData();
        printlog();
    }
    public void setLastLogout()
    {                
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
        Date date = new Date();   
        clientDataLog.setLastLogout(formatter.format(date));
        serializeClientData();
        printlog();
    }
    
    private void writeSucceededLogin()
    {         
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
        Date date = new Date(); 
        clientDataLog.setLastLogin(formatter.format(date));  
        serializeClientData(); 
        printlog();
    }
    public void writeActivity(String activity)
    {
        clientDataLog.setNewActivity(activity);  
        System.out.println("First created log: " + clientDataLog.getlog(0));
        printlog();
    }
    public void printlog()
    {    
        String contentPrint = "";          
        try{
            DeserializeClientData();
            content = clientDataLog.getlog();
            for (int i = 0; i < content.length; i++)        
            {
                switch (i)
                {
                    case 0:     // Set the Username!   
                        contentPrint=contentPrint+"USERNAME: "+"\t \t"+content[i]+System.lineSeparator();                                            
                        break;
                    case 1:     // set the last Login date!
                        contentPrint=contentPrint+"LAST LOGGED: "+"\t \t"+content[i]+System.lineSeparator();                                            
                        break;
                    case 2:  
                        contentPrint=contentPrint+"LOGGED OUT: "+"\t \t"+content[i]+System.lineSeparator();                                            
                        break;
                    case 3:
                        contentPrint=contentPrint+"SESHION TIME: "+"\t \t"+content[i]+System.lineSeparator();                                            
                        break;
                    case 4:                                                
                        contentPrint=contentPrint+"CLIENT IP: "+"\t \t"+content[i]+System.lineSeparator();                                            

                    default:
                        if (i == content.length-1) 
                            contentPrint+=content[i]+System.lineSeparator();                                                
                        break;                        
                }
            }            
        }catch(Exception e){}             
        File_Manager man = new File_Manager();
        man.OverwriteFile(LogTextPath, contentPrint);
        serializeClientData();
    }    
    private void serializeClientData()
    {
        FileOutputStream fos = null; 
        ObjectOutputStream out = null;
        try 
        {
            fos = new FileOutputStream(Path_w); 
            out = new ObjectOutputStream(fos); 
            out.writeObject(clientDataLog); 
            out.close(); 
        } 
        catch(IOException ex) {
            ex.printStackTrace(); 
            System.out.println("No no no this no good");
        }         
        content=clientDataLog.getlog();        
    }
    private void DeserializeClientData() throws ClassNotFoundException{
        ClientData clientDataLog = null;    
        FileInputStream fis = null; 
        ObjectInputStream in = null; 
        try { 
            fis = new FileInputStream(Path_w); 
            in = new ObjectInputStream(fis); 
            clientDataLog = (ClientData)in.readObject(); 
            in.close(); 
            content = clientDataLog.getlog();
        } 
        catch(IOException ex)         
        {                    
            ex.printStackTrace();             
        } 
    }
}