package com.rp25.main;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import com.rp25.tools.HelperMethods;

public class GivenData {
	public static ArrayList<Point> read(String fileName) {
		ArrayList<Point> output = new ArrayList<>();
		BufferedReader reader = null;
		
		try {
			reader =  new BufferedReader(new FileReader(fileName));
		}
		catch (FileNotFoundException e){
			System.err.println("File: " + fileName + " was not found!");
		}
		
		String line = "";
		
		try {
			while((line = reader.readLine()) != null) {
				String[] components = HelperMethods.split(line, ",", 0);//line.split(",");
				int x = Integer.parseInt(components[0]);
				int y = Integer.parseInt(components[1]);
				
				output.add(new Point(x, y));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return output;
	}
}
