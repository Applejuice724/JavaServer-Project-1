/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server_project.UserInformation;

import Server_project.UserInformation.FileManager.projectSystem.File_Manager;
import Server_project.UserInformation.FileManager.mySQLAccess;

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
        System.out.println("Results:> " + path_w);        
        if (ManageUserData.DoesExist(path_w + "Index"))
        {
            System.out.println("Results:> Userpath, valid");
        }
        else
        {
            System.out.println("Error: User File Path cannot be determined");
        }        
        return false;
    }
    
    
    
    
}
