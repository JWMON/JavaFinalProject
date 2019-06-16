package edu.handong.finale.readers;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.apache.poi.ss.usermodel.DataFormatter;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelReader {
	private ArrayList<ArrayList<Row>> valuesHeaders;
	private ArrayList<Row> values;
	private ArrayList<Row> headers;
	
	public ArrayList<ArrayList<Row>> getData(InputStream is, boolean extraHeader) {
		valuesHeaders = new ArrayList<ArrayList<Row>>();
		values = new ArrayList<Row>();
		headers = new ArrayList<Row>();
		
		valuesHeaders.add(headers);
		valuesHeaders.add(values);
		
		int i;
		
		try (InputStream inp = is) {
		    
		        Workbook wb = WorkbookFactory.create(inp);
		        Sheet sh = wb.getSheetAt(0);
		        Row row;
		        DataFormatter df = new DataFormatter();

		        if(extraHeader) 
		        	i = 2; 
		        else 
		        	i = 1;
		        
		        for(int k = 0; k < i; k++){
		        	row = sh.getRow(k);
		        	if(row != null) headers.add(row);
		        	
		        }
		        
		        while(true) {
		        	row = sh.getRow(i);
		        	if(row == null || df.formatCellValue(row.getCell(0)) == "" && df.formatCellValue(row.getCell(1)) == "") 
		        		break;
		        	values.add(row);
		        	i++;
		        }
		        

		    } catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		
		return valuesHeaders;
	}
}
