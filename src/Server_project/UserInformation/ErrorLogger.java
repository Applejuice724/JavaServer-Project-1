/*
    Error definitions at the bottom
 */
package Server_project.UserInformation;

import Server_project.ApplicationStateManager;
import Server_project.UserInformation.FileManager.projectSystem.File_Manager;

public class ErrorLogger {
    ApplicationStateManager App = ApplicationStateManager.getInstance();
    public enum ERRORLIST {ERR1, ERR2, ERR3, ERR42};
    private static File_Manager ManageFiles=new File_Manager();
    private final String MainErrorLog = "ErrorReport.txt";
    public void LogKnownError(String filePath, ERRORLIST Error)
    {
        //  Takes the current path of the index for the server, Takes off the 
        //  "Index" Part and replaces it with ERROR.log to get to the area where
        //  The log file is to be stored
        String Path_w = filePath.substring(0, filePath.length() -5)+"Error.log";
        if (ManageFiles.DoesExist(Path_w)) appendErrorToFile(Path_w, Error);
        if (!ManageFiles.DoesExist(Path_w))createFile(Path_w, Error); 
        consolidateErrorReport(Path_w, Error);
        App.sendErrorToScreen(DefineError(Error));
    } 
    public void LogKnownError(String filePath, ERRORLIST Error, String exceptionDetails)
    {
        //  Takes the current path of the index for the server, Takes off the 
        //  "Index" Part and replaces it with ERROR.log to get to the area where
        //  The log file is to be stored
        String Path_w = filePath.substring(0, filePath.length() -5)+"Error.log";
        if (ManageFiles.DoesExist(Path_w)) appendErrorToFile(Path_w, Error, exceptionDetails);
        if (!ManageFiles.DoesExist(Path_w))createFile(Path_w, Error, exceptionDetails);                
    }  
    // Append to ErrorFile WITHOUT extra details
    private void appendErrorToFile(String filePath, ERRORLIST Error)
    {    
        ManageFiles.appendToErrorFile(filePath, DefineError(Error));
    }
    // Append to ErrorFile WITH extra details
    private void appendErrorToFile(String filePath, ERRORLIST Error, String exceptionDetails)
    {        
        ManageFiles.appendToErrorFile(filePath, DefineError(Error)+ " " + exceptionDetails);
    }       
    // Create Error File WITHOUT details
    private void createFile(String filePath, ERRORLIST Error)
    {
        ManageFiles.createErrorFile(filePath, DefineError(Error));
    }
    // Create ErrorFile WITH extra details
    private void createFile(String filePath, ERRORLIST Error, String exceptionDetails)
    {
        ManageFiles.createErrorFile(filePath, DefineError(Error) + " " + exceptionDetails);
    }  
    private void consolidateErrorReport( String filePath, ERRORLIST Error)
    {
        if (ManageFiles.DoesExist(MainErrorLog)) appendErrorToFile(MainErrorLog , Error, "~Eror Logged to: " + filePath);
        if (!ManageFiles.DoesExist(MainErrorLog))createFile(MainErrorLog , Error, "~Eror Logged to: " + filePath);
    }
    private void checkErrorPathExists()
    {
        for (int i =0 ; i < readErrorFile().length(); i++)
        {
        }
    }        
    private String readErrorFile()
    {
        return ManageFiles.ReadFile(MainErrorLog);
    }
    private String DefineError(ERRORLIST inputError)
    {        
        switch(inputError)
        {
            case ERR1: return "MYSQL CRITICAL ERROR: No MYSQL database found";                   
            case ERR2: return "MYSQL CRITICAL ERROR: Table could not be found";
            default:
                break;                                
        }                             
        return "ERROR 42: Undefined Error";
    }
}




/* 
~MYSQL ERRORS: 1-20
    ERR1:   
        - MYSQL has failed, unable to connect to database
    ERR2
        - MYSQL could not find Table
    ERR3
        - Placeholder Error







ERROR 42:
    - Just like life, this error is unkown or just undefined
*/ 
