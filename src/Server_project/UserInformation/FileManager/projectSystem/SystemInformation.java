/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server_project.UserInformation.FileManager.projectSystem;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

/**
 *
 * @author wamp
 */
public class SystemInformation {
    public InetAddress getHostIp()
    {
        try
        {
            return InetAddress.getLocalHost();
        }
        catch(Exception e){}
        return null;
    }
    
    public Enumeration InetNetworkInterface()
    {         
        try
        {
            return NetworkInterface.getNetworkInterfaces();
        }
        catch(Exception e){}
        return null;       
    }   
    public long getRunTimeMemory()
    {
        return Runtime.getRuntime().freeMemory();
    }            
}
