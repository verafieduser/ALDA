package se.su.dsv.MyAldaList.ProjectTwo;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.*;


/**
 * @author vera nygren
 */
public class SLlightTests {

	private SLlight sl = new SLlight();
	private Time defaultTime = new Time("10:10:10");


    @BeforeAll
	public void setUp() {
		sl.initializeData(false);
	}

	@Test 
	public void findNeighbouringNodeAStarDepartureAfterTime() {
		SL_Stop from = sl.findNode("Fridhemsplan T-bana");
		SL_Stop to = sl.findNode("S:t eriksplan T-bana");
		List<Edge> edges = sl.findPath(from, to, defaultTime, false, true);
		assertEquals(1, edges.size());
		Edge edge = edges.get(0);
		assertEquals("metro", edge.getType());
		//it leaves after the time specified:
		assertTrue(defaultTime.compareTo(edge.getFrom().getDepartureTime()) < 0);

	}

	@Test 
	public void findNeighbouringNodeAStarArrivalBeforeTime() {
		SL_Stop from = sl.findNode("Fridhemsplan T-bana");
		SL_Stop to = sl.findNode("S:t eriksplan T-bana");
		List<Edge> edges = sl.findPath(from, to, defaultTime, true, true);
		assertEquals(1, edges.size());
		Edge edge = edges.get(0);
		assertEquals("metro", edge.getType());
		//it leaves after the time specified:
		assertTrue(defaultTime.compareTo(edge.getFrom().getDepartureTime()) > 0);
	}

	public void findPathOnSameLineMinShiftDepartureAfterTime() {
		SL_Stop from = sl.findNode("Ropsten t-bana");
		SL_Stop to = sl.findNode("Brevik");
		List<Edge> edges = sl.findPath(from, to, defaultTime, false, false);
		assertEquals(10, edges.size());
		Edge edge = edges.get(0);
		assertEquals("tramway", edge.getType());
		assertTrue(defaultTime.compareTo(edge.getFrom().getDepartureTime()) < 0);
	}

	public void findPathOnSameLineMinShiftArrivalBeforeTime() {
		SL_Stop from = sl.findNode("Ropsten t-bana");
		SL_Stop to = sl.findNode("Brevik");
		List<Edge> edges = sl.findPath(from, to, defaultTime, true, false);
		assertEquals(10, edges.size());
		Edge edge = edges.get(0);
		assertEquals("tramway", edge.getType());
		assertTrue(defaultTime.compareTo(edge.getFrom().getDepartureTime()) > 0);
	}


}
