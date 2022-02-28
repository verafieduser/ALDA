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

	@Test
	void testAll(){
		for(int i = 0; i < 5; i++){
            long startTimeOne = System.currentTimeMillis();
			test(1000);

			long endTimeOne = System.currentTimeMillis();
			long timeOne = endTimeOne - startTimeOne;
			System.out.println("Step " + i + " took: " + timeOne);
		}
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

		assertEquals(y[0].distanceTo(y[1]), x[0].distanceTo(x[1]), 0.1);
	}

	/**
	 * Detta test försöker kontrollera att implementationen är korrekt ordo-mässigt
	 * genom att testa med väldigt många punkter. Max-tiden är lite av en gissning
	 * och kan komma att förändras.
	 */
	@Test
	void largeScaleTimeTest() {
		Point[] points = randomPoints(MAX_POINTS);
		assertTimeoutPreemptively(Duration.ofSeconds(1), () -> {
			ClosestPoints.findClosestPairOfPoints(points);
		});
	}

}
