package red;

import java.sql.Connection;
import java.sql.DriverManager;

public class OracleConnection {
	static Connection con = null;
	  
    static {
      try 
      {
    	  DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
			// ������������� ���������� � ��������
			con = DriverManager.getConnection(	"tns",
																"schema_name",
																"password");
          
      } 
      catch (Exception e) 
      {
          e.printStackTrace(System.out);
      }
    }
    
    protected Connection getConnection() {
		return con;
	}
}
