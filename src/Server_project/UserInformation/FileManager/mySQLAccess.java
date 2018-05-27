/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server_project.UserInformation.FileManager;

/**
 *
 * @author Butthole
 */
public class mySQLAccess {
    private static mySQLAccess instance = null;
    public static mySQLAccess getInstance() 
    {
        if(instance == null)
            instance = new mySQLAccess();        
        return instance;
    }
    
}
