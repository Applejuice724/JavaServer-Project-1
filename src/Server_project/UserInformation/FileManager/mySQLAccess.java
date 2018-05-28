
package Server_project.UserInformation.FileManager;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class mySQLAccess {
    private static mySQLAccess instance = null;
    private String DataBase = "projectjavaserver";    
    private String SQLUsername = "Admin";
    private String SQLPassword = "Jeremy56789!";    
    private Connection connect = null;
    private Statement stmt = null; 
    private ResultSet rs = null;
    private String DefaultDatabase = "jdbc:mysql://localhost/projectjavaserver?";


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
    
    public boolean CheckTable(String table) // Checks if the table exists
    {
        try { 
            System.out.println("SQL Checking table:> " + RemoveIllegalCharecters(table));
            if (connect == null)connect = DriverManager.getConnection(DefaultDatabase +
                                   "user="+SQLUsername+"&password=" + SQLPassword);   
            stmt = connect.createStatement();   
            rs = stmt.executeQuery("SELECT * FROM `"+ RemoveIllegalCharecters(table) +"`");                        
            if (stmt.execute("SELECT * FROM `"+ RemoveIllegalCharecters(table) +"`")) {
                rs = stmt.getResultSet();
            }                            
            return true;
        }catch(Exception e){
            System.out.println("SQL:> " + e);
            if(createServerTable(RemoveIllegalCharecters(table))) return true;
            else return false;            
        } finally
        {     
            closeSQL();       
        }       
    }
    
            
    public boolean createServerTable(String inputTable) //returns false if fail
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

            System.out.println("SQL Table Created:> " + inputTable);
            return true;
        }catch(Exception e){System.out.println(e);return false;} 
        finally {closeSQL();}   
    }
    
    
    public boolean CheckCredentuals(String InputServerName, String Username, String Password)
    {
        try { 
                   
                        
            System.out.println("SQL Checking:> " + Username + " PAssword " + Password);
            if (connect == null)connect = DriverManager.getConnection(DefaultDatabase +
                                   "user="+SQLUsername+"&password=" + SQLPassword);
            stmt = connect.createStatement();    
            rs = stmt.executeQuery("SELECT * FROM `"+ RemoveIllegalCharecters(InputServerName) +"`"); 
            if (stmt.execute("SELECT * FROM `"+ RemoveIllegalCharecters(InputServerName) +"`")) {
                rs = stmt.getResultSet();
            }          
            while (rs.next())
            {                                          
                System.out.println(rs.getString(5));

                if (Username.equals(rs.getString(5))) 
                {
                    System.out.println("SQL:> Username Found" );
                    System.out.println("SQL:> Comparing Password " + rs.getString(6) + " to " + Password);                    
                    if (Password.equals( rs.getString(6))) {
                        System.out.println("SQL:> PAssword Authenticated");
                        
                        
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
                if (Username == rs.getString(5)) return true;
            }  
        } catch(Exception e)
        {
            System.out.println("Fail" + e);
        }finally
        {     
            closeSQL();       
        }                
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
