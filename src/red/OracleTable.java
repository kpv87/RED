package red;

import oracle.jdbc.OracleResultSet;

public class OracleTable 
{
  private OracleResultSet rs=null;

  public OracleTable(OracleResultSet oracleResultSet)
  {
        this.rs=oracleResultSet;
  }

  public String getColumnName(final int column)throws Exception
  {
    return rs.getMetaData().getColumnName(column + 1);
  }

  public int getColumnCount()throws Exception
	{
		return rs.getMetaData().getColumnCount();
	}

  public int getGroupColumn()throws Exception
	{
		int result=-1;
        for(int i=0;i<getColumnCount();i++)
    {
            if(getColumnName(i).equals(Constant.GROUP_COLUMN))
            {
                  result=i; 
                  break;
            }
    }
    return result;
	}

  public  int getRowCount()throws Exception
	{
		int result = 0;
		rs.last();
		result = rs.getRow();
        rs.beforeFirst();
		return result;
	}

  public OracleResultSet getRows()throws Exception
	{
		return this.rs;
	}

  public void close()throws Exception
	{
		  rs.close();
	}
  
   
}