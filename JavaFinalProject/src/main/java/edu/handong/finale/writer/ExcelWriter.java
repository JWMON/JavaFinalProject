package edu.handong.finale.writer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;

public class ExcelWriter {

	
	public static void writeAFile(ArrayList<String> lines, String targetFileName) {
		
		PrintWriter outputStream = null;

		
		while (outputStream == null) {
			try
			{
				outputStream = new PrintWriter(new OutputStreamWriter(new FileOutputStream(targetFileName), "EUC-KR"));
			
			for (String line:lines) {
				outputStream.println(line);
			}
			
			outputStream.close();

			}
			catch (FileNotFoundException e)
			{
				File makeDir = new File(targetFileName);
				makeDir.getParentFile().mkdirs();
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		
	}

}
