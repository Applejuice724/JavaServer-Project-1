/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server_project.GUI;

import Server_project.ApplicationStateManager;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;



public final class TopBar extends ApplicationStateManager implements ActionListener  {
    // Private Variables  
    private boolean errorFree = true;
    private javax.swing.JButton CreateServer;
    public  javax.swing.JButton ExitButton;        
    private javax.swing.JButton Cancel;
    private javax.swing.JButton ResetButton;
    private javax.swing.JLabel Version;
    private javax.swing.JLabel Error;
    javax.swing.GroupLayout layout;
    
    public TopBar()
    {
        InitComponents();        
    }
    
    public void setVersion(double InputVersion)
    {
        String VersionConversion = String.valueOf(InputVersion);
        Version.setText("Current Version: " + VersionConversion);
        Version.setBorder(BorderFactory.createLineBorder(Color.black));
    }
    
        
    @SuppressWarnings("unchecked")
    public void InitComponents() 
    {       
       ExitButton = new javax.swing.JButton("Exit");                           
       CreateServer = new javax.swing.JButton("Create Server");   
       Cancel = new javax.swing.JButton("Back to Main Menu");   
       Cancel.setVisible(false);
       ResetButton = new javax.swing.JButton("Reset");
       Version = new javax.swing.JLabel("0.00");
       Error = new javax.swing.JLabel("Errror"); Error.setVisible(false);
       ExitButton.addActionListener(ApplicationStateManager.actionPerformed);       
       layout = new javax.swing.GroupLayout(this);
       this.setLayout(layout);
       
       layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)     
                    .addGroup(layout.createSequentialGroup()
                            .addGap(1)                                                                                     
                            .addComponent(Version , javax.swing.GroupLayout.DEFAULT_SIZE, 800, Short.MAX_VALUE)
                            .addComponent(Error)
                            .addGap(1200)                                                         
                            .addComponent(CreateServer, javax.swing.GroupLayout.DEFAULT_SIZE, 800, Short.MAX_VALUE)
                            .addComponent(Cancel, javax.swing.GroupLayout.DEFAULT_SIZE, 800, Short.MAX_VALUE)
                            .addGap(1)
                            .addComponent(ResetButton, javax.swing.GroupLayout.DEFAULT_SIZE, 800, Short.MAX_VALUE)                                
                            .addGap(1)                             
                            .addComponent(ExitButton, javax.swing.GroupLayout.DEFAULT_SIZE, 800, Short.MAX_VALUE))               
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Version , javax.swing.GroupLayout.DEFAULT_SIZE, 800, Short.MAX_VALUE) 
                    .addComponent(Error)
                    .addComponent(CreateServer, javax.swing.GroupLayout.DEFAULT_SIZE, 800, Short.MAX_VALUE)          
                    .addComponent(Cancel, javax.swing.GroupLayout.DEFAULT_SIZE, 800, Short.MAX_VALUE)            
                    .addComponent(ExitButton, javax.swing.GroupLayout.DEFAULT_SIZE, 800, Short.MAX_VALUE)            
                    .addComponent(ResetButton, javax.swing.GroupLayout.DEFAULT_SIZE, 800, Short.MAX_VALUE)                
        );
        
        ExitButton.addActionListener(this);
        ResetButton.addActionListener(this); 
        CreateServer.addActionListener(this);
        Cancel.addActionListener(this);
        ResetButton.setPreferredSize(new Dimension(100, 100)); 

        ExitButton.setPreferredSize(new Dimension(100, 100));                
        CreateServer.setPreferredSize(new Dimension(100, 100));
        ResetButton.setPreferredSize(new Dimension(100, 100)); 
    }  
    
    public void sendError(String inputError)
    {
        if (errorFree) 
        {
            Error.setText(inputError + "~ See Error Report for more details");
            Error.setVisible(true);
            errorFree = false;
        }                
        if (!errorFree) 
        {
            Error.setText("MULTIPLE ERRORS FOUND! Read error Report");
        }   
        PushErrorOnLayout();
    }

    @Override
    public void actionPerformed(ActionEvent e)  {
         Object source = e.getSource();
         if (source == ExitButton)       
         {                                 
             super.ProgramRunning(false);
         }         
         if (source == CreateServer)       
         {            
             CreateServer.setVisible(false);
             Cancel.setVisible(true);
             super.setLayerSelect(LayerSet.SERVERCREATE);
         }        
         if (source == ResetButton)      // Resets application
            {                        
                super.RestartApplication();
            }
           if (source == Cancel)
           {                            
               CreateServer.setVisible(true);             
               Cancel.setVisible(false);
               super.setLayerSelect(LayerSelect.SERVERMENU);
           }
    }
    public void PushErrorOnLayout()
    {

         layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)     
                    .addGroup(layout.createSequentialGroup()
                            .addGap(1)                                                                                     
                            .addComponent(Version , javax.swing.GroupLayout.DEFAULT_SIZE, 800, Short.MAX_VALUE)
                            .addGap(10)                                                                                     
                            .addComponent(Error)
                            .addGap(800)                                                                                     
                            .addComponent(CreateServer, javax.swing.GroupLayout.DEFAULT_SIZE, 800, Short.MAX_VALUE)
                            .addComponent(Cancel, javax.swing.GroupLayout.DEFAULT_SIZE, 800, Short.MAX_VALUE)
                            .addGap(1)
                            .addComponent(ResetButton, javax.swing.GroupLayout.DEFAULT_SIZE, 800, Short.MAX_VALUE)                                
                            .addGap(1)                             
                            .addComponent(ExitButton, javax.swing.GroupLayout.DEFAULT_SIZE, 800, Short.MAX_VALUE))               
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Version , javax.swing.GroupLayout.DEFAULT_SIZE, 800, Short.MAX_VALUE) 
                    .addComponent(Error)
                    .addComponent(CreateServer, javax.swing.GroupLayout.DEFAULT_SIZE, 800, Short.MAX_VALUE)          
                    .addComponent(Cancel, javax.swing.GroupLayout.DEFAULT_SIZE, 800, Short.MAX_VALUE)            
                    .addComponent(ExitButton, javax.swing.GroupLayout.DEFAULT_SIZE, 800, Short.MAX_VALUE)            
                    .addComponent(ResetButton, javax.swing.GroupLayout.DEFAULT_SIZE, 800, Short.MAX_VALUE)                
        );
    }        
}
