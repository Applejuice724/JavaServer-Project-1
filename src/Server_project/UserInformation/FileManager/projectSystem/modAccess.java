/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server_project.UserInformation.FileManager.projectSystem;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.border.LineBorder;
import org.apache.commons.codec.digest.DigestUtils;


public class modAccess {
    private static JFrame MainWindow;
    private static JPanel searchResultPanel;
    private static JPanel addUserWindow;
    private static javax.swing.SpringLayout Layout_W;
    public static void createFrame()
    {
        EventQueue.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                MainWindow = new JFrame("Mod Tools v1.0");
                MainWindow.setPreferredSize(new Dimension(800,800));
                MainWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);                                                          
                setInterface();
                MainWindow.add(mainQueryInputPanel);
                MainWindow.add(addUserWindow);
                MainWindow.pack();
                MainWindow.setLocationByPlatform(true);
                MainWindow.setVisible(true);
                MainWindow.setResizable(false);
                searchInput.requestFocus();
            }
        });
    }          
    private static ActionListener actionlisten  = new ActionListener() 
    {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource().equals(SubmitUserInformation))
            {
//    private static JTextField UsernameInputFeild;
//    private static JTextField FirstNameInputFeild;
//    private static JTextField lastNameInputFeild;
//    private static JTextField EmailInputFeild;    
//    private static JPasswordField PasswordInputFeild;   
                String SQLAddUserMessage = 
                        ("values ('', '"
                        +FirstNameInputFeild.getText()+"', '"
                        +lastNameInputFeild.getText()+"', '"
                        +EmailInputFeild.getText()+"',  '"
                        +UsernameInputFeild.getText()+"', '"
                        +GetPassword()+"'");
                System.out.println(SQLAddUserMessage);
            }
            
        }
    };
    private static DigestUtils Digest;    
    private final static char[] hexArray = "0123456789ABCDEF".toCharArray();        
    private static String GetPassword()
    {
//        try{
//            byte[] utf8 = String.valueOf(PasswordInputFeild.getPassword()).getBytes("UTF-8");
//            byte[] test = Digest.sha(Digest.sha(utf8));
//            return "*" + bytesToHex(test).toUpperCase();
//        }
//        catch(Exception e)
//        {             
//            System.out.println(":> ENCRYPTION Error:>");                                     
//            return "Error";
//        }  
        return null;
    }
    // User GUI Setup Functions
    private static void setInterface()
    {
        Layout_W = new javax.swing.SpringLayout();
        MainWindow.setLayout(Layout_W);
        mainQueryInputPanel = new JPanel();                
        addUserWindow = new JPanel();        
        setInputPanelInterface(); 
        setAddUserInterface();                             
    } 
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // This funciton sets up the user panel for searching        
    private static JPanel inputpanel;
    private static JPanel mainQueryInputPanel;
    private static JTextField searchInput;
    private static JButton searchButton;
    private static JLabel SearchLabel;
    private static javax.swing.SpringLayout ResultPanelLayout;    
    private static void setInputPanelInterface()
    {
        // VV The Main Panel, holding Results, *Query Items (*Such as Search bar)                        
        searchInput = new JTextField(20);        
        searchButton = new JButton("Search!");
        SearchLabel = new JLabel("Enter Username! (Work In Progress)");
        inputpanel = new JPanel();
        inputpanel.setPreferredSize(new Dimension(350,750));
        inputpanel.setBorder(new LineBorder(Color.black, 2));
        inputpanel.add(searchInput);        
        inputpanel.add(searchButton);
        inputpanel.add(SearchLabel);                                     
        ResultPanelLayout = new javax.swing.SpringLayout();
        ResultPanelLayout.putConstraint(SpringLayout.WEST, searchInput, 15
                , SpringLayout.WEST, mainQueryInputPanel);  // Search Input              
        ResultPanelLayout.putConstraint(SpringLayout.NORTH, searchInput, 43
                , SpringLayout.SOUTH, mainQueryInputPanel);                   
        ResultPanelLayout.putConstraint(SpringLayout.WEST, searchButton, 252
                , SpringLayout.WEST, mainQueryInputPanel);  // Search Button       
        ResultPanelLayout.putConstraint(SpringLayout.NORTH, searchButton, 40
                , SpringLayout.SOUTH, mainQueryInputPanel); 
        ResultPanelLayout.putConstraint(SpringLayout.WEST, SearchLabel, 23
                , SpringLayout.WEST, mainQueryInputPanel);  // Search Label
        ResultPanelLayout.putConstraint(SpringLayout.NORTH, SearchLabel, 18
                , SpringLayout.SOUTH, mainQueryInputPanel);  
        // VV Inside Panel storing results 
        searchResultPanel = new JPanel();
        searchResultPanel.setPreferredSize(new Dimension(320,660));  
        searchResultPanel.setBorder(new LineBorder(Color.black, 1));
        ResultPanelLayout.putConstraint(SpringLayout.WEST, searchResultPanel, 10
                , SpringLayout.WEST, inputpanel);        
        ResultPanelLayout.putConstraint(SpringLayout.SOUTH, searchResultPanel, -10
                , SpringLayout.SOUTH, inputpanel);
        inputpanel.setLayout(ResultPanelLayout);
        inputpanel.add(searchResultPanel);
        mainQueryInputPanel.add(inputpanel);                
        Layout_W.putConstraint(SpringLayout.WEST, mainQueryInputPanel, 10
                , SpringLayout.WEST, MainWindow);        
        Layout_W.putConstraint(SpringLayout.SOUTH, mainQueryInputPanel, -40
                , SpringLayout.SOUTH, MainWindow);  
    }        
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // This function sets up the user panel for adding Users
    private static JPanel usernameInputPanel;
    private static JPanel firstNameInputPanel;
    private static JPanel lastNameInputPanel;    
    private static JPanel EmailInputPanel;    
    private static JPanel passwordInputPanel;   
    private static JPanel submitInputPanel;   
    private static javax.swing.SpringLayout addUserInterfaceLayout;
    private static JTextField UsernameInputFeild;
    private static JTextField FirstNameInputFeild;
    private static JTextField lastNameInputFeild;
    private static JTextField EmailInputFeild;    
    private static JPasswordField PasswordInputFeild;   
    private static JButton SubmitUserInformation;

    private static void setAddUserInterface()
    {
        addUserInterfaceLayout = new javax.swing.SpringLayout();
        addUserWindow.setPreferredSize(new Dimension(400,235));  
        addUserWindow.setBorder(new LineBorder(Color.black, 2));
        addUserWindow.setLayout(addUserInterfaceLayout);   
        Layout_W.putConstraint(SpringLayout.EAST, addUserWindow, -25
                , SpringLayout.EAST, MainWindow);        
        Layout_W.putConstraint(SpringLayout.SOUTH, addUserWindow, -45
                , SpringLayout.SOUTH, MainWindow);         
        // VV Username Panel
        JLabel UsernameInputLabel = new JLabel("USERNAME  ");
        UsernameInputFeild = new JTextField(20);
        usernameInputPanel = new JPanel();
        usernameInputPanel.setPreferredSize(new Dimension(320,30));  
        usernameInputPanel.setBorder(new LineBorder(Color.black, 1));                        
        addUserInterfaceLayout.putConstraint(SpringLayout.WEST, usernameInputPanel, 10
                , SpringLayout.WEST, addUserWindow);  // Search Label
        addUserInterfaceLayout.putConstraint(SpringLayout.NORTH, usernameInputPanel, 10
                , SpringLayout.NORTH, addUserWindow);  
        usernameInputPanel.add(UsernameInputLabel);
        usernameInputPanel.add(UsernameInputFeild); 
        // VV FirstName                
        FirstNameInputFeild = new JTextField(20);
        firstNameInputPanel = new JPanel();
        firstNameInputPanel.setPreferredSize(new Dimension(320,30));  
        firstNameInputPanel.setBorder(new LineBorder(Color.black, 1));                        
        addUserInterfaceLayout.putConstraint(SpringLayout.WEST, firstNameInputPanel, 10
                , SpringLayout.WEST, addUserWindow);  // Search Label
        addUserInterfaceLayout.putConstraint(SpringLayout.NORTH, firstNameInputPanel, 42
                , SpringLayout.NORTH, addUserWindow);  
        firstNameInputPanel.add(new JLabel("FIRSTNAME  "));
        firstNameInputPanel.add(FirstNameInputFeild); 
        // vv SecondName
        lastNameInputFeild = new JTextField(20);
        lastNameInputPanel = new JPanel();
        lastNameInputPanel.setPreferredSize(new Dimension(320,30));  
        lastNameInputPanel.setBorder(new LineBorder(Color.black, 1));        
        addUserInterfaceLayout.putConstraint(SpringLayout.WEST, lastNameInputPanel, 10
                , SpringLayout.WEST, addUserWindow);  // Search Label
        addUserInterfaceLayout.putConstraint(SpringLayout.NORTH, lastNameInputPanel, 74
                , SpringLayout.NORTH, addUserWindow);            
        lastNameInputPanel.add(new JLabel("LASTNAME  "));
        lastNameInputPanel.add(lastNameInputFeild);  
        // vv Email                
        EmailInputFeild = new JTextField(20);
        EmailInputPanel = new JPanel();
        EmailInputPanel.setPreferredSize(new Dimension(320,30));  
        EmailInputPanel.setBorder(new LineBorder(Color.black, 1));        
        addUserInterfaceLayout.putConstraint(SpringLayout.WEST, EmailInputPanel, 10
                , SpringLayout.WEST, addUserWindow);  // Search Label
        addUserInterfaceLayout.putConstraint(SpringLayout.NORTH, EmailInputPanel, 106
                , SpringLayout.NORTH, addUserWindow);            
        EmailInputPanel.add(new JLabel("EMAIL           "));
        EmailInputPanel.add(EmailInputFeild);  
        // vv Password
        PasswordInputFeild = new JPasswordField(20);
        passwordInputPanel = new JPanel();
        passwordInputPanel.setPreferredSize(new Dimension(320,30));  
        passwordInputPanel.setBorder(new LineBorder(Color.black, 1));        
        addUserInterfaceLayout.putConstraint(SpringLayout.WEST, passwordInputPanel, 10
                , SpringLayout.WEST, addUserWindow);  // Search Label
        addUserInterfaceLayout.putConstraint(SpringLayout.NORTH, passwordInputPanel, 138
                , SpringLayout.NORTH, addUserWindow);            
        passwordInputPanel.add(new JLabel("PASSWORD"));
        passwordInputPanel.add(PasswordInputFeild); 
        // VV Submit                 
        SubmitUserInformation = new JButton("SUBMIT");
        submitInputPanel = new JPanel();
        submitInputPanel.setPreferredSize(new Dimension(375,40));  
        submitInputPanel.setBorder(new LineBorder(Color.black, 1));        
        addUserInterfaceLayout.putConstraint(SpringLayout.WEST, submitInputPanel, 10
                , SpringLayout.WEST, addUserWindow);  // Search Label
        addUserInterfaceLayout.putConstraint(SpringLayout.NORTH, submitInputPanel, 180
                , SpringLayout.NORTH, addUserWindow);            
        submitInputPanel.add(new JLabel("SUBMIT USER ADD REQUEST:"));
        submitInputPanel.add(SubmitUserInformation);           
        SubmitUserInformation.addActionListener(actionlisten);
        // Final Add       
        addUserWindow.add(usernameInputPanel);         
        addUserWindow.add(firstNameInputPanel);          
        addUserWindow.add(lastNameInputPanel);                 
        addUserWindow.add(EmailInputPanel); 
        addUserWindow.add(passwordInputPanel);                         
        addUserWindow.add(submitInputPanel);    
    }
}
