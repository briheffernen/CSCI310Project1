package CSCI310;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue; 

public class FlightMap {
	private HashMap<String, ArrayList<CostPair>> map = new HashMap<>(); 
	private String origin; 
	private 	HashMap<String, CostPair> predecessors = new HashMap<>(); 

	
	void addOrigin(String first) {
		this.origin = first; 
		
	}
	
	void addEdge(String line) {
		String[] parts = line.split(" "); 
		
		String from = parts[0]; 
		String to = parts[1]; 
		String costString = parts[2]; 
		Integer cost = Integer.valueOf(costString); 
		
		CostPair currentPair = new CostPair(to, cost); 
		CostPair parent = new CostPair(from, cost); 
		
		CostPair parentExists = predecessors.get(to); 
		
		
		if (parentExists == null && !to.equals(origin)) {
			predecessors.put(to, parent); 	
		}
		
		//System.out.println("Set predecessor of " + to + " to " + from + " with cost " + cost);
		
		//System.out.println("Read in " + from + " " + to + " $" + cost);
		
		ArrayList<CostPair> result = map.get(from); 
		
		if (result != null) {
			//System.out.print("Adding to array list for " + from + " - ");
			//currentPair.print(); 
			result.add(currentPair); 
			map.put(from, result); 
		} else {
			//System.out.println("Adding a new array list");
			result = new ArrayList<CostPair>(); 
			result.add(currentPair); 
			map.put(from, result); 
		}
		
//		System.out.print(from + " ");
//		currentPair.print();	
	}
	
	public void printFlightMap() {
		
		System.out.println("Destination\tFlight Route From P\tTotal Cost");
		
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
				System.out.print(current + "\t");
				
				printParents(current, predecessors,0); 
			
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
	} 
	
	public void printParents(String start, HashMap<String,CostPair> parents, int cost) {
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
		System.out.println(toPrint + " $" + totalCost);
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
	
	public void print() {
		System.out.println(destination + " $" + cost);
	}
}
