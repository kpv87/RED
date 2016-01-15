package red;

import java.math.BigDecimal;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.apache.poi.ss.usermodel.Cell;

public class ExcelFormatParser {
	
	String dateFormat;
	
	public ExcelFormatParser(String dateFormat) {
		this.dateFormat = dateFormat;
	}
	
	protected void setValueAt(Object value, Cell cell) throws ParseException {
		if (value != null)
	      {
	        if (value instanceof Boolean)
	        {
	          //cell.setCellType(HSSFCell.CELL_TYPE_BOOLEAN);
	          cell.setCellValue((Boolean) value);
	        }
	        else if (value instanceof Integer)
	        {
	          //cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
	          cell.setCellValue((Integer) value);
	        }
	        else if (value instanceof Double)
	        {
	          //cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
	          cell.setCellValue((Double) value);
	        }
	        else if (value instanceof BigDecimal)
	        {
	          //cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
	          cell.setCellValue(((BigDecimal)value).doubleValue());
	        }
	        else if (value instanceof String)
	        {
	          //cell.setCellType(HSSFCell.CELL_TYPE_STRING);
	          // cell.set
	          // cell.setEncoding(cell.ENCODING_UTF_16);
	          cell.setCellValue((String) value);
	        }
	        else if (value instanceof Date)

	        {
	        	String newstring = new SimpleDateFormat(dateFormat).format(value);
	          cell.setCellValue(((String) newstring));
	        }
	        else
	        {
	          cell.setCellValue("неизвестный тип данных");
	        }
	        
	      }
	}
}
