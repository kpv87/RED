package red;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import oracle.jdbc.OraclePreparedStatement;
import oracle.jdbc.driver.OracleResultSet;
import red.exporttype.ExportType;
import red.exporttype.FileInLocal;

public class Main {
	private static OracleTable run_sql(final String sql)
			throws Exception {
		OraclePreparedStatement psSql;
		psSql = (OraclePreparedStatement) OracleConnection.con.prepareStatement(
				sql, ResultSet.TYPE_FORWARD_ONLY,// TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_READ_ONLY);
		/*
		 * for(int i=0;i<Utils.unescapeXml(sql).length();i+=40)
		 * if(i+2>=Utils.unescapeXml(sql).length())
		 * LOG.sysout(Utils.unescapeXml(
		 * sql).substring(i,Utils.unescapeXml(sql).length())); else
		 * LOG.sysout(Utils.unescapeXml(sql).substring(i,i+40));
		 */

		OracleTable table = null;
		if (psSql.execute()) {
			table = new OracleTable((OracleResultSet) psSql.getResultSet());
		}
		return table;
	}
	
	private static OutputStream getStream(ExportType exportType, String paramToChange) {
		exportType.changeState(paramToChange);
		OutputStream out = exportType.export();
		return out;
	}
	
	public static void run_local(String select, boolean printColumn, int maxRowInFile, String javaDateFormat, String pathToExport, ExportType exportType) throws Exception {
		OracleTable table = run_sql(select);
		int columnCount = table.getColumnCount();
		int startRow = 0;
		int fileNum = 1;
		OutputStream out;
		String path = pathToExport;
		   
        SXSSFWorkbook wb = new SXSSFWorkbook(10000); // keep 100 rows in memory, exceeding rows will be flushed to disk
        Sheet sheet = wb.createSheet("Sheet");  
        ExcelFormatParser excelFormatParser;
		excelFormatParser = new ExcelFormatParser(javaDateFormat);

		if (printColumn) {
        	Row row = sheet.createRow(startRow);
        	for (int c = 0; c < columnCount; c++) {
        		
        		Cell cell = row.createCell(c);
        		excelFormatParser.setValueAt(table.getColumnName(c), cell);  
            }
        	startRow = 1;
        } 
        int rowNum = startRow;
        while (table.getRows().next()) {
        	Row row = sheet.createRow(rowNum);
        	rowNum ++;
        	
        	for (int c = 0; c < columnCount; c++) {  
                Cell cell = row.createCell(c);  
                excelFormatParser.setValueAt(table.getRows().getObject(c+1), cell);  
            }  
        	
        	if (rowNum % maxRowInFile == 0) {
        		rowNum = startRow; 
        		out = getStream(exportType, path);

    	        wb.write(out);
    	        out.close();
    	        path = pathToExport + "_["+fileNum+"]";
        		fileNum ++;
        		wb = new SXSSFWorkbook(10000); // keep 100 rows in memory, exceeding rows will be flushed to disk
    	        sheet = wb.createSheet("Sheet");
        	}
		}
        out = getStream(exportType, path);
        wb.write(out);
        out.close();
	}

	public static void main(String[] args) throws Exception {
//		OracleTable table = run_sql("select /*+ parallel(8) */  * from da.Abonents_Daily ad where rownum < 300");
		String filial_name = "FE";
		String month = "201506";
		String[] table = {//"sale_book_sales"
						 "sale_book_sales_r_pos",
						   "sale_book_sales_r_neg",
						   "sale_book_sales_nr_pos",
						   "sale_book_sales_nr_neg",
						   "sale_book_advances_nr",
						   "sale_book_advances_r"
						   
						   /*"buy_book_r",
						   "buy_book_nr"*/};
		String[] table_descr = {//"all"
				   "�������_���������_�������������",
				   "�������_���������_�������������",
				   "�������_�����������_�������������",
				   "�������_�����������_�������������",
				   "������_�����������",
				   "������_���������"
				   
				   /*"�����_�������_���������",
				   "�����_�������_�����������"*/};
		long start = System.currentTimeMillis();;
		
		for (int i=0;i<table.length;i++) {
			System.out.println(table[i]);
		
//		TestXLSXFile(table);
		run_local(/*"select * from sale_book_sales@Linkdb.megafonsib.local"*/"select * from "+table[i]+"",
				true,
				900000,
				"dd.MM.yyyy",
				"C:\\temp\\salebook\\"+filial_name+"_"+table_descr[i]+"_"+month,
				new FileInLocal());
		}
	    long end = System.currentTimeMillis();;
	    System.out.println("Execution time:");
	    System.out.println((end - start)/1000 + " sek");
	    System.out.println((end - start)/1000/60 + " min");
	}
	
/*	private static void TestXLSXFile(OracleTable table) throws SQLException, Exception {
		 	String excelFileName = "C:\\temp\\Test";//name of excel file
		 	String fileFormat = ".xlsx";
		 	String sheetName = "Sheet1";//name of sheet
		 	String dateFormat = "dd.MM.yyyy";
		 	int fileMaxRow = 500000;
		 	int columnCount = table.getColumnCount();
		 	int fileNum = 1;
	   
	        SXSSFWorkbook wb = new SXSSFWorkbook(1000); // keep 100 rows in memory, exceeding rows will be flushed to disk
	        Sheet sheet = wb.createSheet(sheetName);  
	        ExcelFormatParser excelFormatParser = new ExcelFormatParser(dateFormat);
	        
	        
	        int rowNum = 0;
	        while (table.getRows().next()) {
	        	Row row = sheet.createRow(rowNum);
	        	rowNum ++;
	        	
	        	for (int c = 0; c < columnCount; c++) {  
	                Cell cell = row.createCell(c);  
	                excelFormatParser.setValueAt(table.getRows().getObject(c+1), cell);  
	            }  
	        	
	        	if (rowNum % fileMaxRow == 0) {
	        		rowNum = 0;
	        		FileOutputStream out = new FileOutputStream(excelFileName+fileFormat);
	    	        wb.write(out);
	    	        out.close();
	    	        
	        		excelFileName = excelFileName + fileNum;
	        		fileNum ++;
	        		wb = new SXSSFWorkbook(1000); // keep 100 rows in memory, exceeding rows will be flushed to disk
	    	        sheet = wb.createSheet(sheetName);
	        	}
			}
	        FileOutputStream out = new FileOutputStream(excelFileName+fileFormat);
	        wb.write(out);
	        out.close();
	}*/
}
