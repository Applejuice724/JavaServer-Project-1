
package Server_project;

import Server_project.UserInformation.FileManager.File_Manager;
import java.nio.file.Files;
import java.nio.file.Paths;




public class Application 
{
    private static String IndexConfig;                   
    String RawContentVariables[];
    ApplicationStateManager event;   
    private double Version;
    private int ServerCount;
    private String ServerPaths[];
    
    
    // CONSTRUCTORS
    public Application(){}  
    
    // No size specified
    public Application(String title, ApplicationStateManager.LayerSet InitLayout)          
    {
        event = new ApplicationStateManager();        
        int size = 800;                       // Set size
        event.Init(title, size, InitLayout);  // Sets Initial Values
    }    
    // FULLSCREEN application
    public Application(String title, int width , int height, ApplicationStateManager.LayerSet InitLayout ) 
    {
        event = new ApplicationStateManager();         
        event.Init(title,  width, height, InitLayout);  
    }     
    // Non-Fullscreen
    public Application(String title, int size, ApplicationStateManager.LayerSet InitLayout)// Basic details and Generation here
    {
        event = new ApplicationStateManager();        
        event.Init(title, size, InitLayout); 
    }
        
    //FUNCTIONS
    public void run()                   // This is the location of the main Loop
    {         
        event.SetConfiguration(Version, ServerPaths, ServerCount);
        
        
        while (event.running)                 // Application loop
        {
            event.update();
        } 
                   
        System.out.println("Exiting....");       
        System.gc();        
        System.exit(0); 
    }
    
    // PreAppConfig Functions:
    //      * Detects if there's a Index File, if not it creates one
    //      * If there is a Index File but it's null it sets the default values
    
    
    public void PreAppConfig(Double InputVersion) 
    {                   
        File_Manager Config = new File_Manager(); 
        Version = Config.GetVersion();                      
        System.out.println("Version = " + Version);
        if (Version != InputVersion)
        {
            System.out.println("ERROR: Version Mismatch!");               
        }
        ServerCount = Config.GetServerCount();
        
        System.out.println("Server Count = " + ServerCount);       
        ServerPaths= Config.GetServerPaths(ServerCount);
        
        for (int i = 0; i <= ServerCount - 1 ; i++){                  
            System.out.println("ServerPath for " + i + " = " + ServerPaths[i]);              
        }        
        
        if (!Files.isDirectory(Paths.get("ServerFiles"))) 
        {
            Config.createFolder("ServerFiles");
            
        } else System.out.println("Server Directory Found!");                        
        System.out.println("App Initialised: Running.. \n ________________________________ \n" );
    }
}
