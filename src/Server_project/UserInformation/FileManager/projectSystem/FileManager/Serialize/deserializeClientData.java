/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server_project.UserInformation.FileManager.projectSystem.FileManager.Serialize;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;

// data[0] = FirstName, data[1] = LastName, data[2] = StaffID, data[3] = userName
public class deserializeClientData implements Serializable{
    private static String userVariables[]= new String[4];
    private static void deSerialize(String filePath_W)
    { 
        serializeClientData data = null;    
        FileInputStream fis = null; 
        ObjectInputStream in = null; try { 
            fis = new FileInputStream(filePath_W); 
            in = new ObjectInputStream(fis); 
            data = (serializeClientData)in.readObject(); 
            in.close(); } 
        catch(IOException ex)         
        {                    
            System.out.println("foo");                        
            ex.printStackTrace(); 
        } 
        catch(ClassNotFoundException ex) {
            ex.printStackTrace();         
        }                    
        userVariables[0] = data.getFirstName();                
        userVariables[1] = data.getSecondName();        
        userVariables[2] = data.getStaffID();        
        userVariables[3] = data.getUserName();  
        for (int i = 0 ; i < userVariables.length; i++)
        {
            Eventlog(userVariables[i]);
        }        
    }    
    public String[] getUserData(String filePath) 
    {
        deSerialize(filePath);
        return userVariables;
    }   
    private static void Eventlog(String printError)
    {
//        System.out.println("Deserialize Data:> " + printError);
    }
}
