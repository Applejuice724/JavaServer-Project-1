/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server_project.UserInformation;

import Server_project.UserInformation.FileManager.projectSystem.File_Manager;

/**
 *
 * @author Butthole
 */
public class UserDataHandler {
    mySQLAccess SQLAcess = mySQLAccess.getInstance();
    File_Manager ManageUserData = new File_Manager();
    public boolean CheckUserSetup(String Username, String filePath)
    {
        String path_w = filePath.substring(0, filePath.length() - 5);
        if (ManageUserData.DoesExist(path_w + "Index"))
        {
//            System.out.println("Results:> Userpath:"+path_w+" valid");
        }
        else
        {
            System.out.println("Error: User File Path cannot be determined");
        }        
        return false;
    }
    public boolean IfClientFileExists(String inputClientHome)
    {
        try{        
            if (!ManageUserData.FileExist(inputClientHome)) 
            {
                ManageUserData.createFolder(inputClientHome);
                System.out.println("Directory " + inputClientHome + " Created!");
            }
            else{System.out.print("Directory Exists!");}
            return true;
        }catch(Exception eppi){                
            System.out.println("File Creaiton Error " + eppi );
        }                
        return false;
    }
    
    
    
    
}
