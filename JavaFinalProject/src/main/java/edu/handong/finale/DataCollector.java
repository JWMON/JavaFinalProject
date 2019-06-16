package edu.handong.finale;

import edu.handong.finale.readers.ZipReader;
import edu.handong.finale.writer.ExcelWriter;
import edu.handong.finale.writer.Lines;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

import java.util.ArrayList;

import org.apache.poi.ss.usermodel.DataFormatter;

public class DataCollector {
	
	private ArrayList<ArrayList<Lines>> lines;
	private ArrayList<String> outputSum;
	private ArrayList<String> outputTable;
	
	private String inputPath = "";
	private String outputPathSum = "";
	private String outputPathTable = "";
	
	private DataFormatter df;
	
	private boolean help;
	
	public void run(String args[]) {
		
		Options options = createOptions();
				
		if(parseOptions(options, args)) {
			if(help) {
				printHelp(options);
				return;
			}
		ZipReader zipReader = new ZipReader();
		lines = zipReader.readFileInZip(inputPath);
		df = new DataFormatter();
		
		outputSum = new ArrayList<String>();
		outputTable = new ArrayList<String>();
		
		sumThread st = new sumThread();
		tableThread tt = new tableThread();
		st.start();
		tt.start();
		try {
			st.join();
			tt.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
			
		ExcelWriter.writeAFile(outputSum, outputPathSum);
		ExcelWriter.writeAFile(outputTable, outputPathTable);
	}
	}
	
	private boolean parseOptions(Options options, String[] args) {
		CommandLineParser parser = new DefaultParser();
		
		try {
			CommandLine cmd = parser.parse(options, args);
			
			inputPath = cmd.getOptionValue("i");
			outputPathSum = cmd.getOptionValue("o1");
			outputPathTable = cmd.getOptionValue("o2");
			help = cmd.hasOption("h");
			
		}
		
		catch(Exception e) {
			printHelp(options);
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	private Options createOptions() {
		Options options = new Options();
		
		options.addOption(Option.builder("i").longOpt("input")
			.desc("Set an input file path")
			.hasArg()
			.argName("Input path")
			.required()
			.build());
			
		options.addOption(Option.builder("o1").longOpt("output1")
			.desc("Set an output file path for summaries")
			.hasArg()
			.argName("Ouput path for summaries")
			.required()
			.build());
		
		options.addOption(Option.builder("o2").longOpt("output2")
			.desc("Set an output file path for images and tables")
			.hasArg()
			.argName("Ouput path for images and tables")
			.required()
			.build());
		
		options.addOption(Option.builder("h").longOpt("help")
			.desc("Show a Help page")
			.argName("Help")
			.build());
			
		return options;
		
	}
	
	private void printHelp(Options options) {

		HelpFormatter formatter = new HelpFormatter();
		String header = "Data Collector";
		String footer ="";
		formatter.printHelp("DataCollector", header, options, footer, true);
	}
	
	private class sumThread extends Thread{
		public void run() {
			ArrayList<Lines> sumList = lines.get(0);
			for(Lines line : sumList) {
				String sumText = "\"" + line.getFileName() + "\"" + "," + "\"" + df.formatCellValue(line.getTitle())+ "\"" + "," + "\"" +df.formatCellValue(line.getSum())+ "\"" + "," + "\"" +df.formatCellValue(line.getKey())+ "\"" + "," + "\"" +df.formatCellValue(line.getDate())+ "\"" + "," + "\"" +df.formatCellValue(line.getDataa())+ "\"" + "," + "\"" +df.formatCellValue(line.getSource())+ "\"" + "," + "\"" +df.formatCellValue(line.getCopyright())+ "\"";
	            outputSum.add(sumText);
			}
		}
	}

	private class tableThread extends Thread{
		public void run() {
			ArrayList<Lines> tableList = lines.get(1);
			for(Lines line : tableList) {
				String tableText = "\"" + line.getFileName() + "\"" + "," + "\"" + df.formatCellValue(line.getTitle())+ "\"" + "," + "\"" +df.formatCellValue(line.getSum())+ "\"" + "," + "\"" +df.formatCellValue(line.getKey())+ "\"" + "," + "\"" +df.formatCellValue(line.getDate())+ "\"" + "," + "\"" +df.formatCellValue(line.getDataa())+ "\"" ;
				outputTable.add(tableText);
			}
		}	
	}
}

