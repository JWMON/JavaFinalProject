package edu.handong.finale.writer;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Cell;

public class Lines {
	private String fileName;
	private Cell title;
	private Cell sum;
	private Cell key;
	private Cell date;
	private Cell dataa;
	private Cell source;
	private Cell copyright;
	
	public String getFileName() {
		return fileName;
	}
	public Cell getTitle() {
		return title;
	}
	public Cell getSum() {
		return sum;
	}
	public Cell getKey() {
		return key;
	}
	public Cell getDate() {
		return date;
	}
	public Cell getDataa() {
		return dataa;
	}
	public Cell getSource() {
		return source;
	}
	public Cell getCopyright() {
		return copyright;
	}

	public Lines(Row line, String fileName) {
		this.fileName = fileName; 
		title = line.getCell(0);
		sum = line.getCell(1);
		key = line.getCell(2);
		date = line.getCell(3);
		dataa = line.getCell(4);
		source = line.getCell(5);
		copyright = line.getCell(6);
		
	}
	
	public Lines(Row line, String fileName, int column) {
		this.fileName = fileName; 
		title = line.getCell(0);
		sum = line.getCell(1);
		key = line.getCell(2);
		date = line.getCell(3);
		dataa = line.getCell(4);
		
	}
}
