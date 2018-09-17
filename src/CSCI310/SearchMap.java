package CSCI310;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class SearchMap {
	
	public static void main(String[] args) {
		
		FileReader fr = null; 
		String inputFile = args[0]; 
		String outputFile = args[1]; 
		String first = ""; 
		FlightMap map = new FlightMap(); 
		
		try {
			// Read in input file 
			fr = new FileReader(inputFile); 
			BufferedReader buffer = new BufferedReader(fr); 
			first = buffer.readLine(); 
			
			// Add origin and output file to the flight map 
			map.addOrigin(first);
			map.setOutput(outputFile);
			
			// Read line by line and add edges
			String current = buffer.readLine(); 

			while (current != null) {
				map.addEdge(current);
				current = buffer.readLine(); 
			}
						
			buffer.close();
			
			// Print flight map to output file 
			map.printFlightMap(); 
						
		} catch (FileNotFoundException fnfe){
			System.out.println("The file could not be found");
		} catch (IOException ioe) {
			System.out.println("IOException"); 
		}
		
	}
	
}