/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server_project.UserInformation.FileManager.projectSystem.Security;
import Server_project.UserInformation.FileManager.projectSystem.File_Manager;
import java.io.IOException;
import java.io.Serializable;
import org.apache.commons.codec.digest.DigestUtils;
/**
 *
 * @author wamp
 */
public class fileEncrypt {
    private final static char[] hexArray = "0123456789ABCDEF".toCharArray();
    private static DigestUtils Digest;    
    private static File_Manager fileManage = new File_Manager();

    // data[0] = FirstName, data[1] = LastName, data[2] = StaffID, data[3] = userName
    public void createClientDataWithEncyrp(String filePath, String data[]) throws IOException
    {
        String path_w = filePath.substring(0, filePath.length() - 5)+ "ClientData";
        String filePath_W = path_w+"/"+data[3]+".ser";
        if (!fileManage.DoesExist(path_w)) fileManage.createFolder(path_w);
        if (!fileManage.DoesExist(filePath_W)) fileManage.createEmptyFile(filePath_W);
//        System.out.println("de path " +path_w);                
//        System.out.println("de file " +filePath_W);
        String data_w[] = data;
        for (int i = 0; i < data.length; i++)
        {
            data_w[i]=writeEncryptedData(data[i]);
        }
        fileManage.setClientDate(filePath_W, data_w); 
    }
    public String[] ReadClientDataWithEncryption(String filePath )
    {
        return fileManage.GetClientData(filePath);
    }

    private String writeEncryptedData(String data)    
    {
        try{
            byte[] utf8 = data.getBytes("UTF-8");
            byte[] test = Digest.sha(Digest.sha(utf8)); 
            return "*" + bytesToHex(test).toUpperCase();            
        }catch(Exception e)        
        {                     
            System.out.println(":> ENCRYPTION Error:>");                                                 
            return "Error";            
        }
    }
    private static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for ( int j = 0; j < bytes.length; j++ ) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }    
        return new String(hexChars);
    }    
}
