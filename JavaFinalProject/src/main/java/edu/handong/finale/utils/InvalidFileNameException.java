package edu.handong.finale.utils;

import java.util.ArrayList;

@SuppressWarnings("serial")
public class InvalidFileNameException extends Exception {
	public InvalidFileNameException() {
		super("Invalid file name. The file name should either contain '(요약문)' or '(표.그림)'");
	}
	public InvalidFileNameException(String fileName, ArrayList<String> list){
		super("Invalid file name. The file name should either contain '(요약문)' or '(표.그림)'");
		list.add(fileName);
	}
}
