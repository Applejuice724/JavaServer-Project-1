
/*
    This class is used to do specify what buttons/layouts are currently being 
    used and to initialize the graphical interface when appropiate



*/
package Server_project;
import Server_project.GUI.SERVERCREATE.ServerCreationGuI;
import Server_project.GUI.SERVERMENU.ListServers;
import Server_project.GUI.TopBar;
import Server_project.UserInformation.CreateServer;
import Server_project.UserInformation.FileManager.File_Manager;
import java.awt.event.ActionListener;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Panel;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.SpringLayout;
import javax.swing.BorderFactory;
import javax.swing.border.Border;


public class ApplicationStateManager extends javax.swing.JPanel{

    public static enum LayerSet {SERVERMENU, SERVERCREATE, NETPROCESS, NONE, TEST};
    public static ActionListener actionPerformed;
    //          _____________PRIVATE FUNCTIONS_________________
    private final SpringLayout MainWinlayout = new SpringLayout();
    private JFrame Panel_w;
    private Container con;    
    // Widget Groups
    private static TopBar topBar;
    private static ListServers ServerList;
    private static CreateServer ServerManager;
    private static ServerCreationGuI ServerCreateScreen;
    private static File_Manager Managefile;
    // Functional Utilities
    public static LayerSet LayerSelect;       // What layout to put on screen  
    private static String ip_w;
    public static boolean running;            
    private int AppHeight;
    private int AppWidth;
    private double Version;
    private int ServerCount = 0;
    

    
    public void Init(String title, int width, int height, LayerSet Layout)       
    { 
        Panel_w  = new JFrame(title);          // Sets the title 
        Panel_w.setPreferredSize( new Dimension( width, height ) );                       
        LayerSelect = Layout;  
        AppHeight = height;
        AppWidth = width;
        PanelInfo();        
    }
     public void Init(String title, int size, LayerSet Layout)       
    { 
        int width = size + size;
        Panel_w  = new JFrame(title);          // Sets the title       
        Panel_w.setPreferredSize(new Dimension(width, size));    // Sets size                           
        LayerSelect = Layout;  
        AppHeight = size;
        AppWidth = width;        
        PanelInfo();   
    }
    //          _____________PUBLIC FUNCTIONS_________________
     protected void ProgramRunning(boolean Isrunning)
     {
         running = Isrunning;
     }     
     // This is used to set the layout
     public void setLayerSelect(LayerSet LayerSelected) 
     {
            LayerSelect = LayerSelected;                                               
     }
     // Sets everything up for processing
     public void SetConfiguration(double InputVersion, String ServerPaths[], int serverCount)
     {
         topBar.setVersion(InputVersion);
         ServerList.initList(ServerPaths, serverCount); 
         ServerCount = serverCount;
     }
     // When creating a single server, usually called on run-time
     protected void createServer(String ServerVariables[], String inputPath) 
     {
         Managefile.createnewServer(ServerVariables, inputPath);
     }
     // When creating a lot of servers at once, usually at start-up
     protected void createServerMass(String InputServerdata[][], int ServerCount)       
     {                         
         if (ServerManager  == null) ServerManager = new CreateServer();  
         ServerManager.ServerGenerationMass(InputServerdata, ServerCount);    
     }
     protected void RestartApplication()
     {       
         try         
         {         
             final String javaBin = System.getProperty("java.home") + File.separator + "bin" + File.separator + "java";                             
             final File currentJar = new File(Client_project.class.getProtectionDomain().getCodeSource().getLocation().toURI());                          
             /* is it a jar file? */             
             if(!currentJar.getName().endsWith(".jar"))             
                 return;                 
             /* Build command: java -jar application.jar */             
             final ArrayList<String> command = new ArrayList<String>();             
             command.add(javaBin);             
             command.add("-jar");             
             command.add(currentJar.getPath());             
             final ProcessBuilder builder = new ProcessBuilder(command);             
             builder.start();             
             System.exit(0);             
         }                     
         catch(IOException exception){           
                    System.exit(0);           
         } catch (URISyntaxException ex) {                     
             Logger.getLogger(ApplicationStateManager.class.getName()).log(Level.SEVERE, null, ex);         
         }          
     }
     
    //  This function checks for updates before it applies them when it is needed
    //  other functionality will be contained in a seperate function, this 
    //  function is ONLY used for updating the widgets/screens
    void update(){ 
      //  LayerSelect = 1;
        switch(LayerSelect)
       {
           case NONE:        
               if (ServerList.updateRequired) {                            
                   ServerList.updateRequired = false;
                   ServerList.UpdateServer();
               }
                break;
                
           case SERVERMENU:  // Log in Screen
               // With the layer selected, we add constraints                          
               System.out.println("* Server List Screen Selected");
               con.removeAll();                // Remove Everything                
               SetConstraints(LayerSet.SERVERMENU);  // Add constraints 
               con.add(topBar);
               con.add(ServerList);
               
               ContainerRefresh();
               if (ServerList.FilledTables == true) LayerSelect = LayerSet.NONE;
               else{ LayerSelect = LayerSet.NONE;}
               break;
               
           case SERVERCREATE:  // Update to Main Menu
               System.out.println("** Server Create Screen Selected");                               
               con.removeAll();                // Remove Everything  
               SetConstraints(LayerSelect);                              
               con.add(topBar);
               con.add(ServerCreateScreen);               
               ContainerRefresh();
               LayerSelect = LayerSet.NONE;
               break;               
               
           case TEST:
               break;
           
           default:      
               break;
       
       }
        
    }
    void SetConstraints(LayerSet ConstraintSelect)
    {
        switch(ConstraintSelect)
       {
            case SERVERMENU:                                                             
                // ################# SYSTEM FUNCTIONS ################                                                                 
                
                MainWinlayout.putConstraint                
                    (SpringLayout.EAST, ServerList, -350
                            , SpringLayout.EAST, con);
                MainWinlayout.putConstraint
                            (SpringLayout.NORTH, ServerList, 200
                                    , SpringLayout.NORTH, con);  
                
                
                MainWinlayout.putConstraint                
                    (SpringLayout.EAST, topBar, 0
                            , SpringLayout.EAST, con);
                MainWinlayout.putConstraint
                            (SpringLayout.NORTH, topBar, 0 
                                    , SpringLayout.NORTH, con);                                             
                

                
                break;
                
            case SERVERCREATE:                                                                                                    
                
                MainWinlayout.putConstraint                
                    (SpringLayout.EAST, topBar, 0
                            , SpringLayout.EAST, con);
                MainWinlayout.putConstraint
                            (SpringLayout.NORTH, topBar, 0 
                                    , SpringLayout.NORTH, con);  
                                                
                MainWinlayout.putConstraint                
                    (SpringLayout.EAST, ServerCreateScreen, -0
                            , SpringLayout.EAST, con);
                MainWinlayout.putConstraint
                            (SpringLayout.NORTH, ServerCreateScreen, 200
                                    , SpringLayout.NORTH, con);  
                                                    
                break;        
        }
    }
    
    void PanelInfo()
    {                         
        Border border = BorderFactory.createLineBorder(Color.WHITE, 25);
        Panel_w.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Panel_w.setExtendedState(JFrame.MAXIMIZED_BOTH); 
        Panel_w.setUndecorated(true);  
        con = new Container();
        con = Panel_w.getContentPane();
        con.setLayout(MainWinlayout);
        running = true;
        // Component Groups
        Managefile          = new File_Manager();
        topBar              = new TopBar();
        ServerList          = new ListServers();
        ServerManager       = new CreateServer();
        ServerCreateScreen  = new ServerCreationGuI();
        ServerCreateScreen.initServerCreate();
        Panel_w.setVisible(true);
        Panel_w.getAlignmentX();
        // WIDGET Sizes
        // SYSTEM WIDGETS
        topBar.setPreferredSize(new Dimension (Panel_w.getWidth(),100));  
        ServerList.setPreferredSize(new Dimension (Panel_w.getWidth(), Panel_w.getHeight())); 
        ServerCreateScreen.setPreferredSize(new Dimension (Panel_w.getWidth(), Panel_w.getHeight())); 
        // Log In Screen                
        // Menu                                
    }    
    void ContainerRefresh()
    {                     
              con.setVisible(false);          // Refresh Screen        
              con.setVisible(true);           // Refresh Screen      
    }    
}