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
			fr = new FileReader(inputFile); 
			BufferedReader buffer = new BufferedReader(fr); 
			first = buffer.readLine(); 
			System.out.println("Origin City: " + first);
			
			map.addOrigin(first);
			map.setOutput(outputFile);
			
			String current = buffer.readLine(); 

			while (current != null) {
				map.addEdge(current);
				
				current = buffer.readLine(); 
			}
						
			buffer.close();
			
			map.printFlightMap(); 
						
		} catch (FileNotFoundException fnfe){
			System.out.println("The file could not be found");
		} catch (IOException ioe) {
			System.out.println("IOException"); 
		}
		
	}
	
}