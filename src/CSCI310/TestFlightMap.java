package CSCI310;

import static org.junit.Assert.*;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;


public class TestFlightMap {

	@Test
	public void testAddEdge() {
		FlightMap testMap = new FlightMap(); 
		testMap.addOrigin("A");
		
		String testLine = "A B 300";
		testMap.addEdge(testLine);
		
		ArrayList<CostPair> result = testMap.getFlightMap().get("A");
		String compare = result.get(0).getDestination(); 
		
		assertEquals(compare, "B"); 
	}
	
	@Test 
	public void testPrintParents() {
		FlightMap testMap = new FlightMap(); 
		testMap.addOrigin("A");
		
		String addOne = "A B 200"; 
		String addTwo = "B C 100"; 
		
		testMap.addEdge(addOne);
		testMap.addEdge(addTwo);
		
		String compare = "A,B,C                   $300";
		HashMap<String, CostPair> paths = testMap.getParents(); 
		
		String result = testMap.printParents("C",paths, 0);
		
		assertEquals(compare, result); 
	}
	
	@Test
	public void testAddOrigin() {
		FlightMap testMap = new FlightMap(); 
		testMap.addOrigin("A");
		
		String compare = "A"; 
		String result = testMap.getOrigin(); 
		
		assertEquals(compare, result); 
	}
	
	@Test
	public void testSetOutput() {
		FlightMap testMap = new FlightMap(); 
		testMap.setOutput("Output.txt");
		
		String compare = "Output.txt"; 
		String result = testMap.getOutput();
		
		assertEquals(compare, result); 
		
	}
	
	@Test 
	public void testGetParents() {
		FlightMap testMap = new FlightMap(); 
		testMap.addOrigin("A"); 
		String addOne = "A B 200"; 
		String addTwo = "B C 100"; 
		
		testMap.addEdge(addOne);
		testMap.addEdge(addTwo);
		
		HashMap<String, CostPair> compare = new HashMap<String, CostPair>(); 
		CostPair one = new CostPair("B",200); 
		CostPair two = new CostPair("C",100); 
		
		compare.put("A", one);
		compare.put("B", two); 
		
		HashMap<String, CostPair> result = testMap.getParents(); 
		
		assertEquals(compare.size(), result.size());
		assertTrue(result.containsKey("B") && result.get("B") != null);
		assertTrue(result.containsKey("C") && result.get("C") != null);

	}
	
	@Test
	public void testGetFlightMap() {
		FlightMap testMap = new FlightMap(); 
		testMap.addOrigin("A"); 
		String addOne = "A B 200"; 
		String addTwo = "B C 100"; 
		
		CostPair one = new CostPair("B",200); 
		
		testMap.addEdge(addOne);

		HashMap<String, ArrayList<CostPair>> compare = new HashMap<>(); 	
		ArrayList<CostPair> addToCompare = new ArrayList<CostPair>(); 
		addToCompare.add(one); 
		compare.put("A", addToCompare); 
		
		HashMap<String, ArrayList<CostPair>> result = testMap.getFlightMap(); 
		
		assertEquals(compare.size(), result.size());
		assertTrue(result.containsKey("A") && result.get("A") != null);
		
	}
	
	@Test 
	public void testGetDestination() {
		CostPair testPair = new CostPair("B",200); 
		String compare = "B"; 
		String result = testPair.getDestination(); 
		
		assertEquals(compare, result); 
	}
	
	@Test
	public void testGetCost() {
		CostPair testPair = new CostPair("B",200); 
		Integer compare = 200;  
		Integer result = testPair.getCost(); 
		
		assertEquals(compare, result); 
	}

}

