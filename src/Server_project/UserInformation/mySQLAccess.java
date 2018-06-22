
package Server_project.UserInformation;
import Server_project.UserInformation.ErrorLogger;
import static Server_project.UserInformation.ErrorLogger.ERRORLIST.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class mySQLAccess {
    private static mySQLAccess instance = null;
    private static ErrorLogger ErrorLog = new ErrorLogger();     
    public String DataBase = "JavaServerDatabase"; 
    public String admintable = "adminusers";
    private String SQLUsername = "Admin";
    private String SQLPassword = "Jeremy56789!";    
    private Connection connect = null;
    private Statement stmt = null; 
    private ResultSet rs = null;
    private String DefaultDatabase = "jdbc:mysql://localhost/JavaServerDatabase?";


    public static mySQLAccess getInstance() 
    {
        if(instance == null)
            instance = new mySQLAccess();        
        return instance;
    }   
    public void connect(String InputDatabase, String inputUsername, String inputPassword) // Make new connection
    {
        try
        {                            
           connect = DriverManager.getConnection("jdbc:mysql://localhost/" + InputDatabase+ "?" +
                                   "user="+inputUsername+"&password=" + inputPassword);
           closeSQL();
        }catch(Exception e){} finally {closeSQL();}   
    }
    public void connect() // Connect with default settings
    {
        try
        {                            
           connect = DriverManager.getConnection(DefaultDatabase+
                                   "user="+SQLUsername+"&password=" + SQLPassword);
           closeSQL();
        }catch(Exception e){} finally {closeSQL();}                
    }
    public void StartDefaultDatabase() // Connect with default settings
    {
        try
        {                            
           connect = DriverManager.getConnection(DefaultDatabase+
                                   "user="+SQLUsername+"&password=" + SQLPassword);
           closeSQL();
        }catch(Exception e)
        {
            createDefaultDataBase();
        } finally {closeSQL();}                
    }
    private void createDefaultDataBase()
    {
        try {
            connect= DriverManager.getConnection("jdbc:mysql://localhost/?" +        
                    "user=root&password=");             
            Statement s= connect.createStatement();        
            int Result=s.executeUpdate("CREATE DATABASE "+DataBase);
        } catch (SQLException ex) {
            Logger.getLogger(mySQLAccess.class.getName()).log(Level.SEVERE, null, ex);
        }                
    }    
    public boolean CheckTable(String table, String serverName) // Checks if the table exists
    {
        try { 
//            System.out.println("SQL Checking table:> " + RemoveIllegalCharecters(table));
            if (connect == null)connect = DriverManager.getConnection(DefaultDatabase +
                                   "user="+SQLUsername+"&password=" + SQLPassword);   
            stmt = connect.createStatement();   
            rs = stmt.executeQuery("SELECT * FROM `"+ RemoveIllegalCharecters(table) +"`");                        
            if (stmt.execute("SELECT * FROM `"+ RemoveIllegalCharecters(table) +"`")) {
                rs = stmt.getResultSet();
            }                            
            return true;
        }catch(Exception e){                        
            ErrorLog.LogKnownError(serverName, ERR1, ":"+DataBase);
            if(RemoveIllegalCharecters(table).equals(admintable)) return false;
            if(createServerTable(RemoveIllegalCharecters(table), serverName)) return true;
            else return false;            
        } finally
        {     
            closeSQL();       
        }       
    }
                
    public boolean createServerTable(String inputTable, String serverName) //returns false if fail
    {
        try{        
            if (connect == null) connect = DriverManager.getConnection
                 (DefaultDatabase+ "user="+SQLUsername+"&password=" + SQLPassword);                                        
            stmt = connect.createStatement();    
            stmt.executeUpdate(                       
                    "CREATE TABLE "  + inputTable + "(" 
                     + "  id int not null primary key auto_increment," 
                     + "  f_name varchar(50),"
                     + "  l_name varchar(50),"
                     + "  email varchar(150),"
                     + "  username varchar(25),"
                     + "  password varchar (75));");

//            System.out.println("SQL Table Created:> " + inputTable);
            return true;
        }catch(Exception e){
//            System.out.println("SQL: Table Creation Error:> "+e);
            ErrorLog.LogKnownError(serverName, ERR2);
            return false;
        } 
        finally {closeSQL();}   
    }
    public boolean CreateAdminTable(String inputTable, String serverName)
    {
        try{        
            if (connect == null) connect = DriverManager.getConnection
                 (DefaultDatabase+ "user="+SQLUsername+"&password=" + SQLPassword);                                        
            stmt = connect.createStatement();    
            stmt.executeUpdate(                       
                    "CREATE TABLE "  + inputTable + "(" 
                     + "  id int not null primary key auto_increment," 
                     + "  f_name varchar(50),"
                     + "  l_name varchar(50),"
                     + "  username varchar(25),"                            
                     + "  email varchar(150)"
                     + "  );");
//            System.out.println("SQL AdminTable Created:> " + inputTable);
            return true;
        }catch(Exception e){
//            System.out.println("SQL: Table Creation Error:> "+e);
            ErrorLog.LogKnownError(serverName, ERR2);
            return false;
        } 
        finally {closeSQL();}   
        
    }
    
    
    public boolean CheckCredentuals(String InputServerName, String Username, String Password)
    {
        try {                                            
//            System.out.println("SQL Checking:> " + Username + " PAssword " + Password);
            if (connect == null)connect = DriverManager.getConnection(DefaultDatabase +
                                   "user="+SQLUsername+"&password=" + SQLPassword);
            stmt = connect.createStatement();    
            rs = stmt.executeQuery("SELECT * FROM `"+ RemoveIllegalCharecters(InputServerName) +"`");      
            while (rs.next())
            {                                          
                System.out.println(rs.getString(5));

                if (Username.equals(rs.getString(5))) 
                {
//                    System.out.println("SQL:> Username Found" );
//                    System.out.println("SQL:> Comparing Password " + rs.getString(6) + " to " + Password);                    
                    if (Password.equals( rs.getString(6))) {
//                        System.out.println("SQL:> PAssword Authenticated");                                                
                        return true;                        
                    }                                
                    else return false;
                }
            }  
        } catch(Exception e)
        {
            System.out.println("Fail " + e);
        }finally
        {     
            closeSQL();       
        }            
        return false;
    }    
    public boolean checkUserExists(String InputServerName, String Username)
    {
         try {                    
            if (connect == null)connect = DriverManager.getConnection(DefaultDatabase +
                                   "user="+SQLUsername+"&password=" + SQLPassword);
            stmt = connect.createStatement();    
            rs = stmt.executeQuery("SELECT * FROM `"+ RemoveIllegalCharecters(InputServerName) +"`"); 
            if (stmt.execute("SELECT * FROM `"+ RemoveIllegalCharecters(InputServerName) +"`")) {
                rs = stmt.getResultSet();
            }          
            while (rs.next())
            {   
                if (Username.equals(rs.getString("username"))) return true;
            }  
        } catch(Exception e)
        {
//            System.out.println("Fail" + e);
        }finally
        {     
            closeSQL();       
        } 
//         System.out.println("User: "+ Username + " Not found in: " + InputServerName);
        return false;
    }
    public String[] getUserInformaiton(String inputTable, String Username) 
    {
        System.out.println("Table Searched : "+ RemoveIllegalCharecters(inputTable));
        System.out.println("User Searched : "+ Username);

        String userData[] = new String[6];
        try { 
             connect = DriverManager.getConnection(DefaultDatabase +
                                       "user="+SQLUsername+"&password=" + SQLPassword);
                stmt = connect.createStatement();
                rs = stmt.executeQuery("SELECT * FROM `"+ RemoveIllegalCharecters(inputTable)+"`"); 
                rs = stmt.getResultSet();
                while(rs.next())
                {
                    if (rs.getString("username").equals(Username))
                    {
                        userData[1]= rs.getString("username");   // Username
                        userData[2]= rs.getString(2);     // FirstName
                        userData[3]= rs.getString(3);     // LastName
                        userData[4]= rs.getString(1);         // StaffID
                    }
                }
//                for (int i = 0; i < 5; i++)
//                {
//                    System.out.println("info: "+i+" "+userData[i]);
//                }
        } catch(Exception e)
        {
            System.out.println("Could not connect to adminServer");                        
            System.out.println(e);
        }finally
        {            
            closeSQL();       
            return userData;            
        }  
    }
    public String getProfile(String userName)
    {
        try { 
                   
            connect = DriverManager.getConnection(DefaultDatabase +
                                   "user="+SQLUsername+"&password=" + SQLPassword);
            stmt = connect.createStatement();    
                rs = stmt.executeQuery("SELECT * FROM `"+ admintable+"`");                       
            rs = stmt.getResultSet();
            while (rs.next())
            {   
                if (userName.equals(rs.getString(4))) return "ADMIN";
//                System.out.println("TEST: "+ rs.getString(4));
            }  
        } catch(Exception e)
        {
            System.out.println("Could not connect to adminServer TO get profile");
            System.out.println(e);

        }finally
        {     
            closeSQL();       
        }                        
        return "USER";
    }
    public boolean AddUser(String inputTable, String SQLSTatement) //returns false if fail
    {
//        System.out.println("SQL STATEMENT:> " + "insert into "+ RemoveIllegalCharecters(inputTable) +" "
//                    + SQLSTatement);
        try{        
            if (connect == null) connect = DriverManager.getConnection
                 (DefaultDatabase+ "user="+SQLUsername+"&password=" + SQLPassword);                                        
            stmt = connect.createStatement();    
            stmt.executeUpdate("insert into "+ RemoveIllegalCharecters(inputTable) +" "
                    + SQLSTatement);
            System.out.println("SQL User Created:> " + inputTable);
            return true;
        }catch(Exception e){
            System.out.println("SQL: User Creation Error:> "+e);
        } 
        finally {closeSQL();}                      
        return false;
    }
    
    
    
    
    private void closeSQL()
    {            
        if (rs != null) {                
            try {                        
                rs.close();                        
            } catch (SQLException sqlEx) { } // ignore                    
            rs = null;                
        }            
        if (stmt != null) {                
            try {                        
                stmt.close();                        
            } catch (SQLException sqlEx) { } // ignore                    
            stmt = null;                
        }         
    }    
    private String RemoveIllegalCharecters(String inputVariable)
    {
        return inputVariable.replaceAll("\"", "").replaceAll("!", "").replaceAll(" ", "").toLowerCase();
    }      
    
}
