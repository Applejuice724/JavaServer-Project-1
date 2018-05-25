/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server_project.UserInformation.FileManager;


import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 *
 * @author Butthole
 */
public class File_Manager {
    private String ConfigVariables[];
    protected static  final String serverIndex = "Index";
    private ReadConfiguration Readconfig;
    private RemoveConfiguration Removeconfig;  
    private AddConfiguration AddConfig;
    Boolean IsNull;
    
    public File_Manager()
    {
    }
    
    public void createnewServer(String ServerData[], String ServerPath)
    {

        setFunctions();        

        AddConfig.CreateServer(ServerData, ServerPath, GetServerCount());        
    }
        
    public void DestroyStartupIndexPath(String InputServerPath)
    {
        setFunctions();        
        System.out.println("Deleting Path in config:>" + InputServerPath);
        int TempIndex[] = Readconfig.getPathIndexFromConfig(InputServerPath);
        Removeconfig.RemovePathFromConfig(TempIndex, GetServerCount());
    }
   
    
    public void setStartupConfig(String InputVariables[])
    {
        setFunctions();        
        ConfigVariables = InputVariables;
    }

    
    public String GetServerValue(int value, String serverPath)
    {           
        setFunctions();
        String Value = Readconfig.getServerValue(value, serverPath);
        return Value;        
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
            System.err.println(x);                        
            return false;
        }                                
    }
    public void createFolder(String path)
    {
        File  f = new File(path);
        f.mkdir();        
        System.out.println("Server Folder created in: " + path);              
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
            System.err.println(x);                        
            return false;
        }                                
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
            System.err.println(x);
            System.out.println("Error, file Not readable: " + x);            
            Content = "File Not readAble";
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
                Content = Content + Line;
            }
            return Content;
        } catch (IOException x) {   
            System.err.println(x);
            System.out.println("Error, file Not readable: " + x);            
            Content = "File Not readAble";
            return Content;
        }                                
    }     
    public double GetVersion()          
    {          
        setFunctions();
        double Version;           
        String InputVersion = Readconfig.getConfigValue(0);                 
        Version = Double.parseDouble(InputVersion);                        
        return Version;        
    }

    public int GetServerCount()
    {       
        setFunctions();
        int count = -1;  
        String StringValue = Readconfig.getConfigValue(1);    
        count = Integer.parseInt(StringValue);    
        return count;            
    }     
    public String[] GetServerPaths(int ServerCount)       
    {     
        setFunctions();        
        String[] Path = Readconfig.getConfigPath();                                             
        return Path;
    }  
    
    private void setFunctions()
    {
        Readconfig = new ReadConfiguration();
        Removeconfig = new RemoveConfiguration(); 
        AddConfig = new AddConfiguration();
    }        
}
