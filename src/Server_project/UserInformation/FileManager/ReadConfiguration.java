/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server_project.UserInformation.FileManager;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;

public class ReadConfiguration extends File_Manager {         
    Boolean FatelError;
    private static  final String serverIndex = "Index";
    String RawContentVariables[] = getConfigVariables();
    
    
    public String getServerValue(int value, String serverPath)
    {
        String Raw[] = GetServerVariables(serverPath);
        String Content = Raw[value];
        return Content;        
    }    

   private String[] GetServerVariables(String InputFile)
    {        
        String RawData = GetServerConfiguration(InputFile);                     
        
        String Variables[] = new String[4];        
        String CurrentVariable = "";
        int HashCount = 0;
        int CurrentVariableField = 0;                                
        
        for (int i = 0; i <= RawData.length() - 1 ; i++)        
        {  
            String Temp = RawData.substring(i, i + 1);
            if (i == RawData.length()-1)
            {
                Variables[CurrentVariableField] = CurrentVariable;                           
                CurrentVariableField+=1;                
                CurrentVariable= "";
            }                           
            if (CurrentVariableField == 4) {                
                return Variables;                
            }
            switch(Temp)
            {
                case "#":                
                    switch (HashCount)
                    {
                        case 0:
                            HashCount++;  
                            break;
                            
                        case 1:
                            HashCount = 0;
                            CurrentVariable = PruneVariables(CurrentVariable);
                            Variables[CurrentVariableField] = CurrentVariable;
                            CurrentVariableField+=1;
                            CurrentVariable= "";
                            break;  
                    }                    
                    break;                    
                default:
                    CurrentVariable = CurrentVariable + Temp;
                    break;
            }                                                
        }       
        return Variables;
    }
                      
 private String GetServerConfiguration(String InputFile) 
    {        
        String Content = "Index File Deos Not Exist";
        Boolean FileExist = DoesExist(InputFile);
        if (!FileExist)
        {         
            Content = "Null";
            return Content;           
        }
        else {
            Content = ReadFile(InputFile);     
        }
        return Content;          
    }    
    private String PruneVariables( String InputVariable)
    {
                int StartOfValue = 0;                                  
                String TempValue = "";         
                for (int i = 0; i <= InputVariable.length() - 1 ; i++)  
                {                            
                    String Temp = InputVariable.substring(i, i + 1);                        
                    switch (Temp)               
                    {            
                        case ":":                            
                            TempValue = "";                                          
                            break;

                        default:
                            TempValue = TempValue+Temp;
                            break;

                    }                                            
                }                     
                return TempValue;                   
    }
    
    
    // Config Variables
    public int[] getPathIndexFromConfig(String InputServerPath)
    {
        String index = ReadFile(serverIndex);
        int Indexs[] = new int[2];                                
        int FirstPos = index.indexOf(InputServerPath)-1;
        int SecondPos = (FirstPos + 1) + InputServerPath.length() + 1;
        
//        String FoundString = index.substring(FirstPos, SecondPos);
//        System.out.println("Path:>" + InputServerPath);
//        System.out.println("Found String position:>" + FoundString);                         
//        System.out.println("First position:>" + FirstPos);  
//        System.out.println("Second position:>" + SecondPos);  

        Indexs[0] = FirstPos;
        Indexs[1] = SecondPos;
        return Indexs;
    }
        
    public String getConfigValue(String InputFile) 
    {        
        Path Path_w = FileSystems.getDefault().getPath(InputFile); 
        String Content = "Index File Deos Not Exist";
        Boolean FileExist = DoesExist(Path_w);
        if (!FileExist)
        {         

            System.out.println("File Exist = " + FileExist + ": Creating File..");                                
            try {
                
               // Assume default encoding.
               FileWriter fileWriter =
                   new FileWriter(serverIndex);

               // Always wrap FileWriter in BufferedWriter.
               BufferedWriter bufferedWriter =
                   new BufferedWriter(fileWriter);

               // Note that write() does not automatically
               // append a newline character.
               bufferedWriter.write("#Version: 1.01");
               bufferedWriter.newLine();               
               bufferedWriter.write("#Server_Amount: 0#");
               bufferedWriter.newLine();
               bufferedWriter.write("#Server_Paths:");
               bufferedWriter.newLine();               
               bufferedWriter.write("#ServerPathEnd#");

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

            Content = ReadFile(Path_w);    
            System.out.println("Default index Created! ");                                            
            return Content;                                    
        }
        if (FileExist)
        {
            Content = ReadFile(Path_w);                   
            return Content;
        }        
        return Content;
    }
    
    public String[] getConfigVariables()
    {        
        String RawData = getConfigValue(serverIndex);
        String Variables[] = new String[3];        
        String CurrentVariable = "";
        int HashCount = 0;
        int CurrentVariableField = 0;                                
        
        for (int i = 0; i <= RawData.length() - 1 ; i++)        
        {  
            String Temp = RawData.substring(i, i + 1);
                           
            if (CurrentVariableField == 4) return Variables;

            switch(Temp)
            {
                case "#":                
                    switch (HashCount)
                    {
                        case 0:
                            HashCount++;                            
                            break;
                            
                        case 1:
                            HashCount = 0;
                            Variables[CurrentVariableField] = CurrentVariable;
                            CurrentVariableField+=1;
                            CurrentVariable= "";
                            break;  
                    }                    
                    break;                    
                default:
                    CurrentVariable = CurrentVariable + Temp;
                    break;
            }                                                
        }                                    
        return Variables;
    }
    
    public String getConfigValue(int Index)
    {
        String InputString = RawContentVariables[Index];        
        int StartOfValue = 0;                                  
        String TempValue = "";         
        for (int i = 0; i <= InputString.length() - 1 ; i++)  
        {                            
            String Temp = InputString.substring(i, i + 1);
            switch(StartOfValue)
            {
                case 0:
                    switch (Temp)
                    {
                        case ":":
                            StartOfValue = 1;                             
                            break;
                    }                    
                    break;
                          
                case 1:
                    TempValue = TempValue + Temp;
                    break;                                
            }            
        }                     
        TempValue = TempValue.replaceAll("\\s", "");
        return TempValue;        
    }
    
    public String[] getConfigPath()
    {
        String serverpath[] = new String[GetServerCount()];
        String InputString = RawContentVariables[2]; 
        int StartOfValue = 0;  
        int CurrentVariableField = 0;        
        String TempValue = "";
        for (int i = 0; i <= InputString.length() - 1 ; i++)  
                {                            
                    String Temp = InputString.substring(i, i + 1);
                    switch(StartOfValue)
                    {
                        case 0:
                            switch (Temp)
                            {
                                case "\"":
                                    StartOfValue = 1;                             
                                    break;
                            }                    
                            break;

                        case 1:
                            if (Temp.equals("\""))
                            {
                                StartOfValue = 0;
                                serverpath[CurrentVariableField] = TempValue;
                                CurrentVariableField++;
                                TempValue = "";
                            }
                            else {TempValue = TempValue + Temp;}
                            break;                                
                    }            
                }
        return serverpath;
    } 

}

