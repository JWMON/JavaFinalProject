package edu.handong.finale.readers;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;

import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipFile;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;

import edu.handong.finale.writer.ExcelWriter;
import edu.handong.finale.writer.Lines;
import edu.handong.finale.utils.InvalidFileNameException;

public class ZipReader{
	private ArrayList<ArrayList<Lines>> list;
	private ArrayList<Lines> sumList;
	private ArrayList<Lines> tableList;
	private ArrayList<String> invalidFiles;
	
	private String fileName = "";
	private String fn = "";
	
	private boolean headerSum = false;
	private boolean headerTable1 = false;
	private boolean headerTable2 = false;
	private boolean errorCSV = false;

	public ArrayList<ArrayList<Lines>> readFileInZip(String path) {
		list = new ArrayList<ArrayList<Lines>>();
		sumList = new ArrayList<Lines>();
		tableList = new ArrayList<Lines>();
		invalidFiles = new ArrayList<String>();
		
		list.add(sumList);
		list.add(tableList);
		
		ZipFile zipFile;
		
		try {
			File dir = new File(path);
			File[] fileList = dir.listFiles();
			for(File file : fileList) {
				if(file.isFile()) {
					fn = file.getPath();
					fileName = file.getName().substring(0, file.getName().lastIndexOf("."));
					zipFile = new ZipFile(fn, "EUC-KR");
					Enumeration<? extends ZipArchiveEntry> entries = zipFile.getEntries();
					DataFormatter df = new DataFormatter();
				    while(entries.hasMoreElements()){
				    	ZipArchiveEntry entry = entries.nextElement();
				        InputStream stream = zipFile.getInputStream(entry);
				        
				        
				        try {
				        	ExcelReader myReader = new ExcelReader();
					        if(entry.getName().contains("(요약문)")) { 
					        	for(ArrayList<Row> valuesHeaders:myReader.getData(stream, false)) { 
					        		for(Row value : valuesHeaders) {
					        			if(headerSum == true && df.formatCellValue(value.getCell(0)).trim().equals("제목")) 
					        				continue;
					        			else if(headerSum == false && df.formatCellValue(value.getCell(0)).trim().equals("제목"))
					        				headerSum = true;	
					        			sumList.add(new Lines(value, fileName));
					        			
					        		}
					        	}
					        	
					        }
					        else if(entry.getName().contains("(표.그림)")) {
					        	for(ArrayList<Row> valuesHeaders:myReader.getData(stream, false)) { 
					        		for(Row value : valuesHeaders) {
					        			if(headerTable1 == false && df.formatCellValue(value.getCell(0)).trim().contains("찾은 자료 내에 있는 그림이나 표의 자료내")) 
					        				headerTable1 = true;
					        			else if(headerTable1 == true && df.formatCellValue(value.getCell(0)).trim().contains("찾은 자료 내에 있는 그림이나 표의 자료내"))
					        				continue;
					        			if(headerTable2 == false && df.formatCellValue(value.getCell(0)).trim().contains("반드시 요약문 양식에 입력한")) 
					        				headerTable2 = true;
					        			else if(headerTable2 == true && df.formatCellValue(value.getCell(0)).trim().contains("반드시 요약문 양식에 입력한"))
					        				continue;
					        			tableList.add(new Lines(value, fileName, 6));
					        			
					        		}
					        	}
					        }
					        
					        else {
					        	System.out.println(entry.getName());
					        	throw new InvalidFileNameException(entry.getName(), invalidFiles);
					        }
				        } catch(InvalidFileNameException e) {
							System.out.println(e.getMessage());
							errorCSV = true;
					    	e.printStackTrace();
					    }
				    }
				}
			}
		}

		 catch (IOException e) {
			e.printStackTrace();
		}
		if(errorCSV) ExcelWriter.writeAFile(invalidFiles, "error.csv");
		return list;
		}
	}

	


