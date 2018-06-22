/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server_project.UserInformation.clientLogger;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

/**
 *
 * @author wamp
 */
// 0 = Username, 1 = LastLogin, 2 = Clients Ip, 3 = quote of the day!
public class ClientData implements Serializable {
    private static int recentHistoryAllocationNumber = 0;  // Keeps track on where to replace history if filled
    private static String Path_w;
    private static String Username_w;
    private static String ClientIP;
    private static String LastLogin;
    private static String LastLogout;
    private static String SeshionTime;
    private static String[] recenthistory;
    
    public ClientData(String Path, String Username)
    {        
        if (recenthistory == null) GenerateString();
        Username_w = Username;
        Path_w = Path;
        System.out.println("Original ClientData name: "+Username_w);
    }
    public String[] getlog()
    {            
        if (recenthistory == null) GenerateString();
        String[] log = new String[recenthistory.length];
        log[0] = Username_w;
        log[1] = LastLogin;  
        log[2] = LastLogout;
        log[3]=  SeshionTime;
        log[4] = ClientIP;  
        log[recenthistory.length-1] = printRecenthistory();          
        return log;
    }     
    public void setLastLogin(String inputdate)
    {
        LastLogin=inputdate;
        LastLogout=null;
        SeshionTime=null;
    }
    public void setClientIP(String IP)
    {
        ClientIP=IP;
    }
    public void setLastLogout(String inputdate)
    {
        LastLogout = inputdate;
        Date T1 = null;                
        Date T2 = null;
        try
        {
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
            T1 = formatter.parse(LastLogin);
            T2 = formatter.parse(LastLogout);
            long diff = T2.getTime() - T1.getTime();                        			
            long diffSeconds = diff / 1000 % 60;	
            long diffMinutes = diff / (60 * 1000) % 60;	
            long diffHours = diff / (60 * 60 * 1000) % 24;	
            long diffDays = diff / (24 * 60 * 60 * 1000);			
//            System.out.print(diffDays + " days, ");	
//            System.out.print(diffHours + " hours, ");	
//            System.out.print(diffMinutes + " minutes, ");	
//            System.out.print(diffSeconds + " seconds.");
            SeshionTime = ("DAYS: "+diffDays+" HOURS: " + diffHours + " MINUTES: "+ diffMinutes + " SECONDS: " + diffSeconds);                        
            if (diffDays > 365)SeshionTime+="\t qEE49rDsB0CS9T6pv9rLq32u17PYr7gZRonlDMUy/6Eon7MtnJLUT1nXqzr2qJL7" ;
        }catch(Exception e){}

        
    }
    
    public void setNewActivity(String activity)
    {
        if (recenthistory == null) GenerateString();
        boolean iswritten = false;
        for (int i = 0; i < recenthistory.length; i++)
        {
            System.out.println("User activity at pos: "+i+"= "+ getlog(i));
            if (recenthistory[i] == null) {
                recenthistory[i] = activity;
                iswritten = true;
                break;
            }
        }
        if (!iswritten)
        {
            recenthistory[recentHistoryAllocationNumber] = activity;
            recentHistoryAllocationNumber++;
            if (recentHistoryAllocationNumber == 10) recentHistoryAllocationNumber = 0;
        }
    }
    public String getlog(int i)
    {
        return recenthistory[i];
    }
    
    private String printRecenthistory()
    {
        String content = System.lineSeparator()+"USER ACTIVITY: ";
        for (int i = 0; i < recenthistory.length;i++)
        {
            if(recenthistory[i] != null)content+= System.lineSeparator()+recenthistory[i];
        }
        return content;
    } 
    private void GenerateString()
    {
        recenthistory = new String[10];
    }
}
