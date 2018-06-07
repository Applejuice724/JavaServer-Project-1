/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server_project.UserInformation.FileManager.projectSystem.FileManager;

import Server_project.UserInformation.FileManager.projectSystem.FileManager.Serialize.deserializeClientData;
import Server_project.UserInformation.FileManager.projectSystem.File_Manager;
import Server_project.UserInformation.FileManager.projectSystem.File_Manager;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

public class ReadFile extends File_Manager {         
    Boolean FatelError;    
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
    
    public String ReadFile(Path InputFile) 
    {        
        String Content = "";
        try (InputStream in = Files.newInputStream(InputFile);    
                BufferedReader reader =      
                        new BufferedReader(new InputStreamReader(in))) {    
            
            String Line = null;    
            while ((Line = reader.readLine()) != null) { 
                Content = Content + Line;
            }

            return Content;

        } catch (IOException x) {   
//            System.err.println(x);
//            System.out.println("Error, file Not readable: " + x);            
//            Content = "File Not readAble";
            return Content;
        }                                
    }
    
    public String ReadFile(String InputFile) 
    {        
        Path Path_w = FileSystems.getDefault().getPath(InputFile);   
        String Content = "";
        try (InputStream in = Files.newInputStream(Path_w);    
                BufferedReader reader =      
                        new BufferedReader(new InputStreamReader(in))) {                
            String Line = null;    
            while ((Line = reader.readLine()) != null) { 
                Content+= Line + System.lineSeparator();
            }
            return Content;
        } catch (IOException x) {   
//            System.err.println(x);
//            System.out.println("Error, file Not readable: " + x);            
//            Content = "File Not readAble";
            return Content;
        }                                
    }         
            
    public Boolean DoesExist(Path Filename)
    {                
        try (InputStream in = Files.newInputStream(Filename);    
                BufferedReader reader =      
                        new BufferedReader(new InputStreamReader(in))) 
        {                
            String line = null;    
            while ((line = reader.readLine()) != null) {       
            }
            return true;
        } catch (IOException x) {                     
            return false;
        }                                
    }    
    
    public Boolean DoesExist(String Filename)
    {        
        Path Path_w = FileSystems.getDefault().getPath(Filename);   
        try (InputStream in = Files.newInputStream(Path_w);    
                BufferedReader reader =      
                        new BufferedReader(new InputStreamReader(in))) 
        {                
            String line = null;    
            while ((line = reader.readLine()) != null) {       
            }
            return true;
        } catch (IOException x) {                       
            return false;
        }                                
    }
    public int getAmountOfLines(String filePath)
    {        
        int lines = 0;        
        try{
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            while (reader.readLine() != null) lines++;
            reader.close();
        }catch(Exception e){}
        return lines;
    }        
    public String readLinesToArray(String[] inputString, int lineAmount)
    {
        try{
            
            FileInputStream fstream = new FileInputStream("textfile.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));     
                for (int i = 0; i < lineAmount; i++)
                {
                }
        }catch(Exception e){}
        return null;
    }
    public String[] deserialiizeClientData(String Path)
    {                
        deserializeClientData test1 = new deserializeClientData();
        return test1.getUserData(Path);
    }    
}

