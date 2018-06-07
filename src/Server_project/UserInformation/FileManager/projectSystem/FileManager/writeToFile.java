/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server_project.UserInformation.FileManager.projectSystem.FileManager;

import Server_project.UserInformation.FileManager.projectSystem.FileManager.Serialize.deserializeClientData;
import Server_project.UserInformation.FileManager.projectSystem.FileManager.Serialize.serializeClientData;
import Server_project.UserInformation.FileManager.projectSystem.File_Manager;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
public class writeToFile extends ReadFile implements Serializable {
    Calendar cal = Calendar.getInstance();
    SimpleDateFormat sdf = new SimpleDateFormat(" 'Year:'yyyy 'Month:'MM 'ON' EEEE 'AT' HH:mm:ss z ");

    public void CreateFile(String filePath)
    {
        System.out.println("Creating File: " + filePath);              
        File file = new File(filePath);
        try{
        file.createNewFile();
        }catch(Exception e){}
    }
    public void OverwriteErrorLog(String filePath, String data)
    {        
        try {
            PrintWriter writer = new PrintWriter(filePath, "UTF-8");
            writer.println("Error Log:");             
            writer.println("Error Instance: " + sdf.format(cal.getTime())+"DETAILS:");
            writer.println("\t\t ~"+data);
            writer.close();
        }catch(IOException ex){}
    }
    
    public void AppendErrorLog(String filePath, String oldData, String newData)
    {                
        try {
            PrintWriter writer = new PrintWriter(filePath, "UTF-8");
            writer.println(oldData);
            writer.println("Error Instance: " + sdf.format(cal.getTime())+"DETAILS:");
            writer.println("\t\t ~"+newData);
            writer.close();                        
        }catch(IOException ex){}
    }  
    public void SerializeClientData(String filePath, String data[]) throws FileNotFoundException, IOException
    {            
        // data[0] = FirstName, data[1] = LastName, data[2] = StaffID
        serializeClientData client = new serializeClientData();
        client.setClientData(filePath, data);                           
    }
}
