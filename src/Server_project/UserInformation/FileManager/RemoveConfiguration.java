/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server_project.UserInformation.FileManager;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author Butthole
 */
public class RemoveConfiguration extends File_Manager {  
    
    public void RemovePathFromConfig(int InputServerPathIndex[], int currentServerCount)
    {
        System.out.println("Deleting Path in config:>" + InputServerPathIndex[0]);
        System.out.println("Deleting Path in config:>" + InputServerPathIndex[1]);
        
        String oldContent= ReadFile(serverIndex);
        String FirstHalf= oldContent.substring(0, InputServerPathIndex[0]);
        String Second= oldContent.substring(InputServerPathIndex[1], oldContent.length());
        String newContent = FirstHalf+Second; 
        newContent = DecrementServerCount(newContent, currentServerCount);
        replaceConfig(newContent);
        
            // Debugging Code
//        System.out.println("First Half config:>" + FirstHalf);                
//        System.out.println("Second Half config:>" + Second);                
//        System.out.println("Old Config:>" + oldContent);
//        System.out.println("New Config:>" + newContent);
   
    }
    
    public void replaceConfig(String InputConfig)
    {
        try {
                
               // Assume default encoding.
               FileWriter fileWriter =
                   new FileWriter(serverIndex);

               // Always wrap FileWriter in BufferedWriter.
               BufferedWriter bufferedWriter =
                   new BufferedWriter(fileWriter);

               // Note that write() does not automatically
               // append a newline character.
               bufferedWriter.write(InputConfig);

               // Always close files.
               bufferedWriter.close();
           }
           catch(IOException ex) {
               System.out.println(
                   "Error writing to file '"
                   + serverIndex + "'");
           }
        
    }
    
    private String DecrementServerCount(String InputNewContent, int currentServerCount)
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
        int Servercount = currentServerCount-1;
        String FirstHalf= OldContent.substring(0, FirstIndex);
        String Version = "Server_Amount: " + Servercount + "#";
        String SecondHalf= OldContent.substring(Secondindex, InputNewContent.length());
        String Newcontent = FirstHalf+Version+SecondHalf;        
        System.out.println("Content:>" + Newcontent);      
        return Newcontent;
    }
}
