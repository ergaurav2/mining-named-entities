package com.ire.main;

import com.ire.indextool.IndexingTools;
import com.ire.sax.SAXParserService;
/**
 * class for the main exection of the index creation
 * @author gaurav
 *
 */
public class MainTest {

public static void main(String[] args) {
	//String inputfilepath = args[0];
	//String inputfilepath = "evaluate.xml";//args[0];
	String inputfilepath = "evaluate.xml";
	long time1 = System.currentTimeMillis();
	//System.out.println("Input file path "+inputfilepath);
	//IndexingTools.indexoutputfolder = args[1];
	IndexingTools.indexoutputfolder = "index";
	SAXParserService.runParser(inputfilepath);
	long time2 = System.currentTimeMillis();
	System.out.println("Difference"+(time2-time1)/1000+"seconds");
}
}
