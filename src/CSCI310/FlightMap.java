package CSCI310;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue; 

public class FlightMap {
	
	private HashMap<String, ArrayList<CostPair>> map = new HashMap<>(); 
	private String origin; 
	private String outputFile; 
	private 	HashMap<String, CostPair> predecessors = new HashMap<>(); 

	
	void addOrigin(String first) {
		// Sets origin of flight map 
		
		this.origin = first; 
	}
	
	void setOutput(String filename) {
		// Sets the output file name
		
		this.outputFile = filename; 
	}
	
	void addEdge(String line) {
		// Reads through line and adds an edge to the flight map
		
		String[] parts = line.split(" "); 
		
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
	
	public void printFlightMap() {
		
		// Output flight hash map to designated file  
		String output = ""; 
		
		output += "Destination\tFlight Route From P\tTotal Cost\n";
		
		HashMap<String, Boolean> visited = new HashMap<>(); 
		LinkedList<String> queue = new LinkedList<String>(); 
		String current = ""; 
		
		queue.add(origin); 
		visited.put(origin, true); 
		
		while (!queue.isEmpty()) {
			//System.out.println("in the queue thing");
			current = queue.pop(); 
			visited.put(current, true);
			
			if (!current.equals(origin)) {
				output += current + "\t";
				
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
	
	public String printParents(String start, HashMap<String,CostPair> parents, int cost) {
		int totalCost = cost; 
		String toPrint = ""; 
		
		CostPair before = parents.get(start); 
		
		//System.out.println("found parent " + parent + " of child " + start);
		
		while (before != null) {
			int addCost = before.getCost(); 
			String parent = before.getDestination(); 

			totalCost += addCost; 
			toPrint = parent + toPrint; 
			
			before = parents.get(parent); 
			//printParents(toPrint, parents, totalCost); 
		}
		
		toPrint += start; 
		return toPrint + " $" + totalCost; 
	}
}

class CostPair {
	private String destination; 
	private Integer cost; 
	
	CostPair(String d, Integer c) {
		this.destination = d; 
		this.cost = c; 
	}
		
	public String getDestination() {
		return this.destination;
	}
	
	public Integer getCost() {
		return this.cost; 
	}
	
}
