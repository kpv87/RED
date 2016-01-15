package red;

import java.sql.Connection;
import java.sql.DriverManager;
import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;
import java.sql.ResultSet;
import oracle.jdbc.OraclePreparedStatement;
  
public class Constant
{
  static String getConstant(String name) 
  {
         String result="";
         try
         {
               Connection con = OracleConnection.con;
               OraclePreparedStatement psSql =(OraclePreparedStatement)con.prepareStatement("select t.value from da.red_constant t where t.name=?",
                                                                        ResultSet.TYPE_FORWARD_ONLY,
                                                                        ResultSet.CONCUR_READ_ONLY);
               psSql.setString(1,name);
               if(psSql.execute())
               {
                      psSql.getResultSet().next();
                      result=psSql.getResultSet().getString("value");                                                  
               } 
               psSql.close();
//               con.close();
         }
         catch(Exception e)
         {
    //    	 System.err.println(e);
             // throw new Exception ("Ошибка в получении константы из wixo_constant");           
         }                                                                                                                           
         return(result);
  }
  
  static String TYPE_STRING=getConstant("TYPE_STRING");
  static String TYPE_BOOLEAN=getConstant("TYPE_BOOLEAN");
  static String TYPE_ARRAY=getConstant("TYPE_ARRAY");
  static String TYPE_DATE=getConstant("TYPE_DATE");
  static String DESC_ARRAY=getConstant("DESC_ARRAY");
  static String FIELD_PARAM_NAME=getConstant("FIELD_PARAM_NAME");
  static String FIELD_PARAM_TYPE=getConstant("FIELD_PARAM_TYPE");
  static String FIELD_PARAM_VALUE=getConstant("FIELD_PARAM_VALUE");
  static String GROUP_COLUMN=getConstant("GROUP_COLUMN");
  static String GROUP_NAME=getConstant("GROUP_NAME");
  static String GROUP_PAGE=getConstant("GROUP_PAGE");
  static String SQL_ADD_RESULT=getConstant("SQL_ADD_RESULT");
  static String SQL_PARAMS_CURSOR=getConstant("SQL_PARAMS_CURSOR");
  /*static String SQL_START_LOG=getConstant("SQL_START_LOG");
  static String SQL_END_LOG=getConstant("SQL_END_LOG");
  static String SQL_SYSOUT=getConstant("SQL_SYSOUT");*/
  
  static String    SMTP_HOST=getConstant("SMTP_HOST");
  static String    SMTP_PORT=getConstant("SMTP_PORT");
  static String    EXTENSION_TXT    = getConstant("EXTENSION_TXT");
  static String    EXTENSION_XLS    = getConstant("EXTENSION_XLS");
  static String    EXTENSION_ZIP    = getConstant("EXTENSION_ZIP");
  static String    EXTENSION_CSV    = getConstant("EXTENSION_CSV");
  static String    EXTENSION_HTML   = getConstant("EXTENSION_HTML");
  static String    FILE_DELIMETR    = getConstant("FILE_DELIMETR");
  
  static String    MSG_NORESULT =getConstant("MSG_NORESULT");
  /*static String TYPE_STRING="string";
  static String TYPE_BOOLEAN="boolean";
  static String TYPE_ARRAY="array";
  static String TYPE_DATE="date";
  static String DESC_ARRAY="WA.WIXO_ARRAY_VARCHAR";
  static String FIELD_PARAM_NAME="PARAM_NAME";
  static String FIELD_PARAM_TYPE="PARAM_TYPE";
  static String FIELD_PARAM_VALUE="PARAM_VALUE";
  static String GROUP_COLUMN="GROUP_COLUMN";
  static String GROUP_NAME="$GROUP_NAME";
  static String GROUP_PAGE="$GROUP_PAGE";*/
  //static String SQL_ADD_RESULT="{ call wa.WIXO.add_result(?, ?, ?) }";
  static String SQL_START_LOG="{ ? = call da.da_log.start_log(?, ?, ?) }";
  static String SQL_END_LOG="{call da.da_log.end_log(?) }";
  static String SQL_SYSOUT="{call dbms_output.put_line(?) }";
  
  /*static String    SMTP_HOST="nskmail.megafonsib.local";
  static String    SMTP_PORT="25";
  static String    EXTENSION_TXT    = "txt";
  static String    EXTENSION_XLS    = "xls";
  static String    EXTENSION_ZIP    = "zip";
  static String    FILE_DELIMETR    = "#";*/
  
  //static String    MSG_NORESULT ="Запрос не вернул данные";
}