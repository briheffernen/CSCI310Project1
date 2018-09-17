package CSCI310;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

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

}

