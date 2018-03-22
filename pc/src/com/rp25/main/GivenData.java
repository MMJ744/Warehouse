package com.rp25.main;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.rp25.tools.HelperMethods;

public class GivenData {
	
	private static final Logger logger = Logger.getLogger(GivenData.class);
	
	public static ArrayList<Point> read(String fileName) {
		ArrayList<Point> output = new ArrayList<>();
		BufferedReader reader = null;
		
		try {
			reader =  new BufferedReader(new FileReader(fileName));
			String line = "";
			while((line = reader.readLine()) != null) {
				String[] components = HelperMethods.split(line, ",", 0);//line.split(",");
				int x = Integer.parseInt(components[0]);
				int y = Integer.parseInt(components[1]);
				
				output.add(new Point(x, y));
			}
		}
		catch (FileNotFoundException e){
			logger.info("File: " + fileName + " was not found!");
		} catch (NumberFormatException e) {
			logger.info("File: " + fileName + " has the wrong format!");
		} catch (IOException e) {
			logger.info("IOException: " + e.getMessage());
		}
		
		return output;
	}
}
