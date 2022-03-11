/**
 * @author Vera Nygren, klny8594
 */
package se.su.dsv.MyAldaList.ProjectTwo;

import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import org.junit.jupiter.api.*;

public class SLLightTests {

	private SLLight sl;
	private Time defaultTime = new Time("10:10:10");

	@BeforeEach
	public void setUp() {
		sl = new SLLight();
		sl.initializeData(false, false);
	}

	@Test
	public void findNeighbouringNodeAStarDepartureAfterTime() {
		Station from = sl.findNode("Fridhemsplan T-bana");
		Station to = sl.findNode("S:t eriksplan T-bana");
		List<Edge> edges = sl.findPath(from, to, defaultTime, false, true);
		assertEquals(1, edges.size());
		Edge edge = edges.get(0);
		assertEquals("metro", edge.getType());
		// it leaves after the time specified:
		assertTrue(defaultTime.compareTo(edge.getFrom().getTime()) < 0);

	}

	@Test
	public void findNeighbouringNodeAStarArrivalBeforeTime() {
		Station from = sl.findNode("Fridhemsplan T-bana");
		Station to = sl.findNode("S:t eriksplan T-bana");
		List<Edge> edges = sl.findPath(from, to, defaultTime, true, true);
		assertEquals(1, edges.size());
		Edge edge = edges.get(0);
		assertEquals("metro", edge.getType());
		// it leaves after the time specified:
		assertTrue(defaultTime.compareTo(edge.getFrom().getTime()) > 0);
	}

	@Test
	public void findPathOnSameLineMinShiftDepartureAfterTime() {
		Station from = sl.findNode("Ropsten t-bana");
		Station to = sl.findNode("Brevik");
		List<Edge> edges = sl.findPath(from, to, defaultTime, false, false);
		assertEquals(9, edges.size());
		Edge edge = edges.get(0);
		assertEquals("tramway", edge.getType());
		assertTrue(defaultTime.compareTo(edge.getFrom().getTime()) < 0);
	}

	@Test
	public void findPathOnSameLineMinShiftArrivalBeforeTime() {
		Station from = sl.findNode("Ropsten t-bana");
		Station to = sl.findNode("Brevik");
		List<Edge> edges = sl.findPath(from, to, defaultTime, true, false);
		System.out.println(edges);
		assertEquals(9, edges.size());
		Edge edge = edges.get(0);
		assertEquals("tramway", edge.getType());
		assertTrue(defaultTime.compareTo(edge.getFrom().getTime()) > 0);
	}

}
