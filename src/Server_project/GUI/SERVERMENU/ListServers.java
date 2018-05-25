/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server_project.GUI.SERVERMENU;
import Server_project.ApplicationStateManager;
import Server_project.UserInformation.FileManager.File_Manager;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author 101580150
 */
public class ListServers extends ApplicationStateManager implements ActionListener {
    // ----------------     Class Functions --------------------
    
    File_Manager ServerControl;
    
    // ----------------     End Class Functions --------------------    
    boolean AliveServers; 
    public boolean updateRequired = false;
    public boolean FilledTables = false;
    private javax.swing.JButton ButtomGroup[];     
    private javax.swing.JTable Table;
    private javax.swing.JScrollPane scrollPane;
    private javax.swing.SpringLayout ServerListLayout; 
    private String ServerPath_w[] = null;
    private String ColumnNames[] = 
        {
            "ID",
            "Name",
            "IP",                        
            "Port",            
            "Options",
        };
    private String Data[][];
    private String ServerCreateData[][]; // Used to send data to the state manager for creation of the actual servers
    private int serverCount_w;

    public void UpdateServer()
    {
        super.createServerMass(ServerCreateData, serverCount_w);           
        updateRequired = false;
    }
    
    private String[][] GetServerData()
    {
        return Data;
    }
    
    public void initList(String ServerPaths[], int ServerCount) // Called ONCE during program setup
    {
        ButtomGroup = new javax.swing.JButton[ServerCount];
        ServerCreateData = new String[ServerCount][3];
        File_Manager ServerControl = new File_Manager();
        if (ServerCount !=0) AliveServers = true;        
        if (AliveServers)
        {
            serverCount_w = ServerCount;
            ServerPath_w = ServerPaths;
            Data = new String[ServerCount][5];          
//            ~~~~~~~~~~~~~~~~~~~   Debugging Code  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~            
//            System.out.println("Server Count " + " = " + serverCount_w);                      
//            for (int i = 0; i <= serverCount_w - 1 ; i++)
//            {                  
//                System.out.println("ServerPath for " + i + " = " + ServerPath_w[i]);              
//            }  
//            ~~~~~~~~~~~~~~~~~~~   Debugging Code  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        

            for (int r=0; r <= ServerPath_w.length -1; r++) { 
                System.out.println("Filling table: " + r); 
                for (int c=0; c <= ColumnNames.length -1; c++) {
                    boolean Exist = ServerControl.DoesExist(ServerPath_w[r]);                   
                    if (!Exist)                 
                    {         
                        // If it does not exist, remove file form startup
                        ServerCreateData[r][1] = "Error:6820"; 
                        serverCount_w--;
                        ServerControl.DestroyStartupIndexPath(ServerPath_w[r]);                        
                        break;                    
                    }
                    if (c == 5) break;
                    switch (c)
                    {
                        case 0:  // Server ID
                            Data[r][0]= String.valueOf(r); // ID for each Server                      
                            break;
                        case 4:                                               
                            Data[r][4]= "Button_ID_" + Data[r][0]; // ID for each Server 
                            javax.swing.JButton Button = new javax.swing.JButton(Data[r][4]);
                            Button.addActionListener(this);
                            add(Button);
                            ButtomGroup[r] = Button;
                            System.out.println("Button ID = " + Data[r][4]); 
                            break;                                                    
                        case 3: // Server Port and serverPath ASsignment
                            Data[r][3] = ServerControl.GetServerValue(c-1, ServerPath_w[r]);                              
                            ServerCreateData[r][0] = Data[r][3].replaceAll("\\s+", "");
                            ServerCreateData[r][1] =  ServerPath_w[r].replaceAll("\\s+", "");                                                        
                            break;
                            
                        case 1:
                            Data[r][1] = ServerControl.GetServerValue(c-1, ServerPath_w[r]);                                                 
                            ServerCreateData[r][2] = Data[r][1];
                            break;

                        default:                        
                            Data[r][c] = ServerControl.GetServerValue(c-1, ServerPath_w[r]);                                                 
                            break;                                                
                    }                                                            
                }                
            }                     
            ServerListLayout = new javax.swing.SpringLayout();          
            this.setLayout(ServerListLayout);
            Table = new javax.swing.JTable(Data, ColumnNames);
            scrollPane = new javax.swing.JScrollPane(Table);
            Table.setFillsViewportHeight(true);                               
            setLayout();        
            this.setLayout(ServerListLayout);   
            this.add(scrollPane);
            this.setVisible(true);               
            updateRequired = true;              
            FilledTables= true;
        }        
    }       
    
    public void DrawTable()
    {

    }
    

    @Override
    public void actionPerformed(ActionEvent ae) {
        String Temp = ae.getActionCommand();
        System.out.println("Action = " + Temp);     
        
        
        
        
        throw new UnsupportedOperationException("Not supported yet");
    }
     
    
    void setLayout()
    {
        this.setLayout(ServerListLayout);
        scrollPane.setPreferredSize(new Dimension(1000, 500));
        ServerListLayout.putConstraint                      
                    (javax.swing.SpringLayout.EAST, scrollPane, 0
                            , javax.swing.SpringLayout.EAST, this);
                        
        ServerListLayout.putConstraint
                    (javax.swing.SpringLayout.NORTH, scrollPane, 0 
                            , javax.swing.SpringLayout.NORTH, this);                
    }
}
