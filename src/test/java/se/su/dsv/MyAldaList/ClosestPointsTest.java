package se.su.dsv.MyAldaList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;

import java.time.Duration;
import java.util.Random;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class ClosestPointsTest {

	private static final int MAX_COORD = 1000;
	private static final int MAX_POINTS = 1000000;
	private static final Random RND = new Random();

	private Point[] bruteForceSolution(Point[] points) {
		Point[] closest = { points[0], points[1] };
		double closestDistance = closest[0].distanceTo(closest[1]);

		for (Point p1 : points) {
			for (Point p2 : points) {
				if (p1 != p2) {
					double distance = p1.distanceTo(p2);
					if (distance < closestDistance) {
						closest[0] = p1;
						closest[1] = p2;
						closestDistance = distance;
					}
				}
			}
		}

		return closest;
	}

	private Point[] randomPoints(int number) {
		Point[] points = new Point[number];
		for (int i = 0; i < number; i++) {
			points[i] = new Point(RND.nextInt(MAX_COORD), RND.nextInt(MAX_COORD));
		}
		return points;
	}


	@ParameterizedTest
	@ValueSource(ints = { 10, 20, 50, 100 })
	void test(int number) {
		Point[] points = randomPoints(number);

		Point[] x = ClosestPoints.findClosestPairOfPoints(points);
		Point[] y = bruteForceSolution(points);
		
		double xDistance = x[0].distanceTo(x[1]);
		double yDistance = y[0].distanceTo(y[1]);

		if(xDistance != yDistance){
			Point y1 =null, y2 =null;
			int index = 0;
			int index1 =0;
			int index2 =0 ;
			for(Point point : points){
				if(y[0] == point){
					y1=point;
					index1 = index;
				} else if(y[1] == point){
					y2=point;
					index2 = index;
				}
				index++;
			}
			System.out.println("Points: " + y1.toString() + " on index " + index1 + " and " + y2.toString() + " on index " + index2 + " length was " + points.length);
		}
		//System.out.println(y[0].distanceTo(y[1]));
		assertEquals(y[0].distanceTo(y[1]), x[0].distanceTo(x[1]), 0.1);
	}

	@Test
	void testAll() {
		for(int i = 10; i < 10000; i++){
			System.out.println(i);
			test(10);
			test(20);
			test(50);
			test(100);
		}
	}


	/**
	 * Detta test försöker kontrollera att implementationen är korrekt ordo-mässigt
	 * genom att testa med väldigt många punkter. Max-tiden är lite av en gissning
	 * och kan komma att förändras.
	 */
	@Test
	void largeScaleTimeTest() {
		Point[] points = randomPoints(MAX_POINTS);
		assertTimeoutPreemptively(Duration.ofSeconds(2), () -> {
			ClosestPoints.findClosestPairOfPoints(points);
		});
	}

}
