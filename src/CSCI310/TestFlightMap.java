package CSCI310;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

class TestFlightMap {

	@Test
	void testAddEdge() {
		FlightMap testMap = new FlightMap(); 
		testMap.addOrigin("A");
		
		String testLine = "A B 300";
		testMap.addEdge(testLine);
		
		ArrayList<CostPair> result = testMap.getFlightMap().get("A");
		String compare = result.get(0).getDestination(); 
		
		assertEquals(compare, "B"); 
	}
	
//	@Test
//	void testPrintFlightMap() {
//		
//	}
//	
//	@Test
//	void testPrintPath() {
//		
//	}
//	
//	@Test
//	void testAddOrigin() {
//		
//	}
//			
//	@Test
//	void testSetOutput() {
//		
//	}
}
