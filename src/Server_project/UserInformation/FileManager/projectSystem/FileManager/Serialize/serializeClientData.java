/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server_project.UserInformation.FileManager.projectSystem.FileManager.Serialize;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

// data[0] = FirstName, data[1] = LastName, data[2] = StaffID, data[3] = userName
public class serializeClientData implements Serializable
{
    private static String firstName_W;
    private static String LastName_W;
    private static String staffIdNumber_W;
    private static String UserName_W;
    
    private static void Serialize(String filePath_W)
    {             
//        System.out.println("foo "+firstName_W);
//        System.out.println("foo "+LastName_W);
//        System.out.println("foo "+staffIdNumber_W);
//        System.out.println("foo "+UserName_W);
        
        serializeClientData data = new serializeClientData();
        FileOutputStream fos = null; 
        ObjectOutputStream out = null; try 
        {
            fos = new FileOutputStream(filePath_W); 
            out = new ObjectOutputStream(fos); 
            out.writeObject(data); 
            out.close(); 
        } 
        catch(IOException ex) {
            ex.printStackTrace(); 
            System.out.println("No no no");
        } 
    }
    
    public void setClientData(String filePath, String data[])
    {
        for (int i =0; i < 4; i++)
        {
            switch(i)
            {                
                case 0:
                    firstName_W = data[i] ;
                    break;                          
                case 1:
                    LastName_W = data[i];
                    break; 
                case 2:
                    staffIdNumber_W = data[i];
                    break;
                case 3:
                    UserName_W= data[i];
                    break;
            }
        }
        Serialize(filePath);
    }  
    public String getFirstName(){return firstName_W;}        
    public String getSecondName(){return LastName_W;}
    public String getStaffID(){return staffIdNumber_W;}
    public String getUserName(){return UserName_W;}


        
}
