/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server_project.GUI.SERVERCREATE;

import Server_project.ApplicationStateManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ServerCreationGuI extends ApplicationStateManager implements ActionListener{
    
    private javax.swing.JPanel Panel;
    private javax.swing.GroupLayout ServerCreateLayout;          
    private javax.swing.JLabel PortLBL;
    private javax.swing.JLabel ServerFolderLBL;
    private javax.swing.JLabel ServerNameLBL;
    private javax.swing.JLabel DescriptionLBL;
    private javax.swing.JTextField ServerName;
    private javax.swing.JTextField ServerPort;
    private javax.swing.JTextField ServerFolder;
    private javax.swing.JTextField Description;
    private javax.swing.JButton CreateServer;
    
    public void initServerCreate()
    {

        ServerName = new javax.swing.JTextField("MyServer");
        ServerName.setToolTipText("Server Name");
        ServerPort = new javax.swing.JTextField("8000");
        ServerFolder = new javax.swing.JTextField("dum2");
        CreateServer = new javax.swing.JButton("Create Server");
        PortLBL = new javax.swing.JLabel("Enter Port");
        ServerFolderLBL = new javax.swing.JLabel("Enter Server Folder");
        ServerNameLBL = new javax.swing.JLabel("Enter Server Name");
        ServerCreateLayout = new javax.swing.GroupLayout(this);
        DescriptionLBL = new javax.swing.JLabel("Description");
        Description = new javax.swing.JTextField("Stock decrip");
        this.setLayout(ServerCreateLayout);                
        ServerCreateLayout.setHorizontalGroup(
            ServerCreateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ServerCreateLayout.createSequentialGroup()                                
                .addComponent(Description)
                .addComponent(ServerName)                    
                .addComponent(ServerFolder)
                .addComponent(ServerPort)
                .addComponent(CreateServer)
)
            .addGroup(ServerCreateLayout.createSequentialGroup()                                     
                    .addGap(200)                                  
                .addComponent(PortLBL)                                                   
                    .addGap(380)
                .addComponent(ServerNameLBL)                                                    
                    .addGap(330)  
                .addComponent(ServerFolderLBL)
                    .addGap(380)
                .addComponent(DescriptionLBL)                    
                )
        );
        ServerCreateLayout.setVerticalGroup(
            ServerCreateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ServerCreateLayout.createSequentialGroup()
                .addGroup(ServerCreateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(DescriptionLBL)
                    .addComponent(ServerNameLBL)
                    .addComponent(PortLBL)
                    .addComponent(ServerFolderLBL))
                .addGroup(ServerCreateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(Description)
                    .addComponent(ServerFolder)
                    .addComponent(ServerName, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(ServerPort)
                    .addComponent(CreateServer)
                    )));     
        CreateServer.addActionListener(this);
        this.setVisible(true);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
         Object source = e.getSource();
         if (source == CreateServer)       
         {                            
             String[] InputVariables = new String[4];
             InputVariables[0] = ("\"" +ServerName.getText() + "\"");
             InputVariables[1] = "127.0.0.1";                
             InputVariables[2] = ServerPort.getText();              
             InputVariables[3] = ("\"" +Description.getText() + "\"");                             
             String InputServer = (ServerFolder.getText());             
             
             System.out.println(":> Creating Server! " + InputVariables[0]);
             System.out.println(":> At Ip: " + InputVariables[1]);
             System.out.println(":> Port " + InputVariables[2]);
             System.out.println(":> Description: " + InputVariables[3]);
             System.out.println(":> Server file: " + InputServer);                                       
             super.createServer(InputVariables, InputServer);    
             super.RestartApplication();
         }  
    }
    
    
        
    
    
    
    
}
