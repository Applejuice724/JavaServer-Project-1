/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server_project.UserInformation.FileManager.projectSystem.FileManager;

import Server_project.UserInformation.FileManager.projectSystem.File_Manager;
import Server_project.UserInformation.FileManager.projectSystem.File_Manager;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author 101580150
 */
public class AddConfiguration extends File_Manager {
        protected static  final String serverIndex = "Index";

    public void CreateServer(String ServerData[], String ServerPath, int CurrentServerCount)
    {
        // 0 = Name | 1 = Ip  | 2 = port | 3 = Description
        String FilePath = ("ServerFiles/"+ ServerPath);
        createFolder(FilePath);          
        GenerateServerFile(FilePath, ServerData);
        addToPathToIndex(ServerPath, CurrentServerCount); // Handles addding to config
    }
    
    private void GenerateServerFile(String FilePath, String ServerVariables[])
    {
         try {
                /* Unpacking Server Information */
                String Path_w = FilePath+"/Index";
                String Name = ServerVariables[0];
                String ip = ServerVariables[1];
                String port = ServerVariables[2];
                String Description = ServerVariables[3];
             
             
                
               // Assume default encoding.
               FileWriter fileWriter =
                   new FileWriter(Path_w);

               // Always wrap FileWriter in BufferedWriter.
               BufferedWriter bufferedWriter =
                   new BufferedWriter(fileWriter);

               // Note that write() does not automatically
               // append a newline character.
               bufferedWriter.write("#Name: " + Name + "#");
               bufferedWriter.newLine();               
               bufferedWriter.write("#IP: " + ip + " #");
               bufferedWriter.newLine();
               bufferedWriter.write("#Port: " + port + "#");
               bufferedWriter.newLine();               
               bufferedWriter.write("#Description: " + Description + "#");

               // Always close files.
               bufferedWriter.close();
           }
           catch(IOException ex) {
               System.out.println(
                   "Error writing to file '"
                   + serverIndex + "'");
               // Or we could just do this:
               // ex.printStackTrace();
           }
    }
    
    
    private void addToPathToIndex(String ServerPath, int CurrentServerCount)                        
    {            
        String IndexRaw = super.ReadFile(serverIndex);// Get Config       
        IndexRaw= incrementServerCount(IndexRaw, CurrentServerCount);// Replace  Current Server Count
        String tempField = "";
        int HashCount = 0;                
        int CurrentVariableField = 0;  
        int LastField = 4;
        String Temp = "";                    
        for (int i = 0; i < IndexRaw.length(); i++)
        {             
            Temp = IndexRaw.substring(i, i + 1);
            if (Temp.equals("#") && HashCount < 5)
            {
                HashCount++;
            }
            if (Temp.equals("#") && HashCount == 5)
            {
                tempField = Temp+Temp;
                LastField = i;
                HashCount++;
            }
            if (HashCount == 6)
            {
                break;
            }                           
        }
        String FirstSection = IndexRaw.substring(0, LastField);
        String LastSelection = IndexRaw.substring(LastField, IndexRaw.length());               
        String newContent = (IndexRaw.substring(0, LastField) +"\"ServerFiles/" + ServerPath + "/Index\"" + IndexRaw.substring(LastField, IndexRaw.length()));
        
        System.out.println("New Index:> " + FirstSection +"\"ServerFiles/" + ServerPath + "/Index\"" + LastSelection);  
        try {                         
                
               // Assume default encoding.
               FileWriter fileWriter =
                   new FileWriter(serverIndex);

               // Always wrap FileWriter in BufferedWriter.
               BufferedWriter bufferedWriter =
                   new BufferedWriter(fileWriter);

               // Note that write() does not automatically
               // append a newline character.
               bufferedWriter.write(newContent);
               // Always close files.
               bufferedWriter.close();
           }
           catch(IOException ex) {
               System.out.println(
                   "Error writing to file '"
                   + serverIndex + "'");
               // Or we could just do this:
               // ex.printStackTrace();
           }
    }
    
    
        
private String incrementServerCount(String InputNewContent, int currentServerCount)
    {
        String OldContent ="";
        int FirstIndex = 1;
        boolean SecondField = false;
        boolean LoopEnd = false;
        int count = 1;
        
        while (!LoopEnd)        
        {
             if (count >= 99999) LoopEnd = true;
             switch(InputNewContent.substring(count, count + 1))
             {
                 case "#":
                     if (SecondField==false) SecondField=true;
                     if (SecondField==true) {
                         FirstIndex=count+1;
                         LoopEnd=true;
                     }
                     break;
                 case ":":
                     OldContent ="";
                     break;                                          
             }                                  
             OldContent = OldContent+InputNewContent.substring(count, count + 1);                     

             count++;
        }                              
        int Secondindex = 1;
        SecondField = true;
        LoopEnd = false;
        while (!LoopEnd)        
        {
             if (count >= 99999) LoopEnd = true;
             switch(InputNewContent.substring(count, count + 1))
             {
                 case "#":
                     if (SecondField==false) SecondField=true;
                     if (SecondField==true) {
                         Secondindex=count+1;
                         LoopEnd=true;
                     }
                     break;
                 case ":":
                     OldContent ="";
                     break;                                          
             }                                  
             OldContent = OldContent+InputNewContent.substring(count, count + 1);                     

             count++;
        }      
        OldContent = InputNewContent;                  
        int Servercount = currentServerCount+=1;
        String FirstHalf= OldContent.substring(0, FirstIndex);
        String Version = "Server_Amount: " + Servercount + "#";
        String SecondHalf= OldContent.substring(Secondindex, InputNewContent.length());
        String Newcontent = FirstHalf+Version+SecondHalf;        
        return Newcontent;
    }            
}
