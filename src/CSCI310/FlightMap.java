package CSCI310;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public class FlightMap {
	
	private HashMap<String, ArrayList<CostPair>> map = new HashMap<>(); 
	private String origin; 
	private String outputFile; 
	private 	HashMap<String, CostPair> predecessors = new HashMap<>(); 

	/**
	 * This sets the origin of the flight map 
	 * @param first the origin city 
	 */
	void addOrigin(String first) {
		// Sets origin of flight map 	
		this.origin = first; 
	}
	
	/**
	 * This sets the output filename for the flight map to eventually be printed to
	 * @param filename the name of the output file 
	 */
	void setOutput(String filename) {
		// Sets the output file name
		this.outputFile = filename; 
	}
	
	/**
	 * This function adds edges to the graph using the origin, destination, and cost 
	 * @param line is the current line from the input file to be parsed and added to map 
	 */
	void addEdge(String line) {
		// Reads through line and adds an edge to the flight map
		
		String[] parts = line.split(" "); 
		
		// Split up the 
		String from = parts[0]; 
		String to = parts[1]; 
		String costString = parts[2]; 
		Integer cost = Integer.valueOf(costString); 
		
		CostPair currentPair = new CostPair(to, cost); 
		CostPair parent = new CostPair(from, cost); 
		
		// Check if predecessor exists for current node
		CostPair parentExists = predecessors.get(to); 
		
		// Set predecessor by adding to predecessors HashMap 
		if (parentExists == null && !to.equals(origin)) {
			predecessors.put(to, parent); 	
		}
		
		// Get the children of the current node 
		ArrayList<CostPair> result = map.get(from); 
		
		if (result != null) {
			// Add edge to already existing node  
			result.add(currentPair); 
			map.put(from, result); 
			
		} else {
			// Add a new node and edge
			result = new ArrayList<CostPair>(); 
			result.add(currentPair); 
			map.put(from, result); 
			
		}
	}
	
	/**
	 * This function prints the flight map to an output file
	 */
	public void printFlightMap() {
		// Output flight hash map to designated file  
		String output = ""; 
		
		output += "Destination           ";
		output += "Flight Route From P   ";
		output += "Total Cost\n";
		
		// Set up for BFS 
		HashMap<String, Boolean> visited = new HashMap<>(); 
		LinkedList<String> queue = new LinkedList<String>(); 
		String current = ""; 
		
		// Add the original node to the queue 
		queue.add(origin); 
		visited.put(origin, true); 
		
		while (!queue.isEmpty()) {
			// Execute BFS 
			current = queue.pop(); 
			visited.put(current, true);
			
			if (!current.equals(origin)) {
				output += current + "                     ";
				
				output += printParents(current, predecessors,0) + "\n"; 
			
			}
			
			ArrayList<CostPair> toAdd = map.get(current); 
			
			for (int i = 0; toAdd != null && i < toAdd.size(); i++) {
				CostPair currPair = toAdd.get(i); 
				String dest = currPair.getDestination(); 
				
				Boolean seen = visited.get(dest); 
				
				if (seen == null) {
					visited.put(dest,true);
					queue.add(dest); 	
				}
			}

		}
		
		try {
			FileWriter fileWriter = new FileWriter(outputFile);
			PrintWriter printWriter = new PrintWriter(fileWriter); 
			printWriter.print(output);
			printWriter.close();
			fileWriter.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	} 
	
	/**
	 * This function iterates through the parents of a node and prints the path and its cost 
	 * @param start the origin node of the path to be printed
	 * @param parents hash map of the predecessors for each node until origin
	 * @param cost the original cost to be added to throughout the path
	 * @return returns a string with the path from the destination node to the origin
	 */
	public String printParents(String start, HashMap<String,CostPair> parents, int cost) {
		// Prints the path from the origin to the destination 
		
		int totalCost = cost; 
		int pathSize = 0; 
		String toPrint = ""; 
		
		CostPair before = parents.get(start); 
				
		while (before != null) {
			// Iterate through the nodes from the start node to the origin  
			pathSize++; 
			
			int addCost = before.getCost(); 
		
			String parent = before.getDestination(); 

			totalCost += addCost; 
			toPrint = parent + "," + toPrint; 
			
			before = parents.get(parent); 
		}
		
		toPrint += start; 
		
		for (int i = 0; i < 23 - pathSize*2; i++) {
			toPrint += " "; 
		}
		
		return toPrint + "$" + totalCost; 
	}
}

class CostPair {
	private String destination; 
	private Integer cost; 
	
	/**
	 * This function sets the destination and cost for a given node 
	 * @param d the destination node
	 * @param c the cost from origin to destination
	 */
	CostPair(String d, Integer c) {
		this.destination = d; 
		this.cost = c; 
	}
	
	/**
	 * This function returns the destination attributed to the cost pair
	 * @return the destination
	 */
	public String getDestination() {
		return this.destination;
	}
	
	/**
	 * This function returns the cost attributed to the cost pair 
	 * @return the cost 
	 */
	public Integer getCost() {
		return this.cost; 
	}
	
}
