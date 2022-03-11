/**
 * @author Vera Nygren, klny8594
 */
package se.su.dsv.MyAldaList.ProjectTwo;

import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import org.junit.jupiter.api.*;

public class SLlightTests {

	private SLlight sl;
	private Time defaultTime = new Time("10:10:10");

	@BeforeEach
	public void setUp() {
		sl = new SLlight();
		sl.initializeData(false);
	}

	@Test
	public void findNeighbouringNodeAStarDepartureAfterTime() {
		SLStop from = sl.findNode("Fridhemsplan T-bana");
		SLStop to = sl.findNode("S:t eriksplan T-bana");
		List<Edge> edges = sl.findPath(from, to, defaultTime, false, true);
		assertEquals(1, edges.size());
		Edge edge = edges.get(0);
		assertEquals("metro", edge.getType());
		// it leaves after the time specified:
		assertTrue(defaultTime.compareTo(edge.getFrom().getDepartureTime()) < 0);

	}

	@Test
	public void findNeighbouringNodeAStarArrivalBeforeTime() {
		SLStop from = sl.findNode("Fridhemsplan T-bana");
		SLStop to = sl.findNode("S:t eriksplan T-bana");
		List<Edge> edges = sl.findPath(from, to, defaultTime, true, true);
		assertEquals(1, edges.size());
		Edge edge = edges.get(0);
		assertEquals("metro", edge.getType());
		// it leaves after the time specified:
		assertTrue(defaultTime.compareTo(edge.getFrom().getDepartureTime()) > 0);
	}

	@Test
	public void findPathOnSameLineMinShiftDepartureAfterTime() {
		SLStop from = sl.findNode("Ropsten t-bana");
		SLStop to = sl.findNode("Brevik");
		List<Edge> edges = sl.findPath(from, to, defaultTime, false, false);
		assertEquals(9, edges.size());
		Edge edge = edges.get(0);
		assertEquals("tramway", edge.getType());
		assertTrue(defaultTime.compareTo(edge.getFrom().getDepartureTime()) < 0);
	}

	@Test
	public void findPathOnSameLineMinShiftArrivalBeforeTime() {
		SLStop from = sl.findNode("Ropsten t-bana");
		SLStop to = sl.findNode("Brevik");
		List<Edge> edges = sl.findPath(from, to, defaultTime, true, false);
		System.out.println(edges);
		assertEquals(9, edges.size());
		Edge edge = edges.get(0);
		assertEquals("tramway", edge.getType());
		assertTrue(defaultTime.compareTo(edge.getFrom().getDepartureTime()) > 0);
	}

}
