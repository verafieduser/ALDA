package se.su.dsv.MyAldaList;

import java.util.Arrays;

public class ClosestPoints {

	/**
	 *  
	 * @param points 	:	an unsorted array of points, that are comparable on the x-axis
	 * @return			:	the pair of points that are the closest together in param points. 
	 */
	public static Point[] findClosestPairOfPoints(Point[] points) {
		Arrays.sort(points); // sorterar alla värdena efter compareTo i Point, som jämför x-värden.
		return findClosestPair(points, 0, points.length); //den rekursiva metoden
	}

	/**
	 * 
	 * @param points	:	an array of points that must be sorted on the x axis 
	 * @param from		:	start value that this particular instance of recursion starts on. 
	 * 							should be 0 if first iteration.
	 * @param to		:	end value that this particular instance of recursion ends on. 
	 * 							should be points.length if first iteration.
	 * @return 			:	pair of points that constitute the closest pair of the input array "points".
	 */
	private static Point[] findClosestPair(Point[] points, int from, int to) {
		Point[] result = null;
		double minDistanceSoFar;

		if (to - from < 10) { 	//om storleken på nuvarande iterations andel av arrayen 
								//är under 10 så används "brute-force"-lösning.
								//baserat på lite testning verkade det inte göra så stor skillnad om 
								//den är 5, 10, 20, e.t.c. - så länge den inte blir alltför stor. 
			return findSmallestBruteForce(points, from, to);

		} else {	//annars delas nuvarande iterations andel av arrayen upp i två halvor...
			int middle = (to + from) / 2;
			Point[] resultLeft = findClosestPair(points, from, middle);
			Point[] resultRight = findClosestPair(points, middle + 1, to);

			double distanceLeft = resultLeft[0].distanceTo(resultLeft[1]);
			double distanceRight = resultRight[0].distanceTo(resultRight[1]);

			if (distanceLeft < distanceRight) {
				result = resultLeft;
				minDistanceSoFar = distanceLeft;
			} else {
				result = resultRight;
				minDistanceSoFar = distanceRight;
			}

			int[] middleCutoffs = findMiddleCutOffs(points, minDistanceSoFar, middle, from, to);

			
			if (middleCutoffs[0] >= 0 && middleCutoffs[1] >= 0) { 	//if cutoffs are 0 or below that means 
																	//that there were no relevant middle connections
				//I tried using brute force here instead of findClosestPair, but both took around the same amount of time
				//given 1k iterations over the long test - went w/ closest pair because it was more aesthetically pleasing
				Point[] resultThree = findClosestPair(points, middleCutoffs[0], middleCutoffs[1]);
				double distanceMiddle = resultThree[0].distanceTo(resultThree[1]);

				return distanceMiddle < minDistanceSoFar ? resultThree : result;
			} else {
				return result;
			}
		}
	}

	/**
	 * Brute force method (O(N^2) worst case) for finding the smallest pair of points in a pre-sorted array. 
	 * @param points	:	an array of points
	 * @param from 		:	the start index of the range of elements in points
	 * @param to 		:	the end index of the range of elements in points
	 * @return
	 */
	private static Point[] findSmallestBruteForce(Point[] points, int from, int to) {
		Point[] result = null;
		double distance;
		double minDistanceSoFar = Double.MAX_VALUE;

		for (int i = from; i < to; i++) {
			for (int j = i + 1; j < to; j++) {
				distance = points[i].distanceTo(points[j]);
				if (distance < minDistanceSoFar) {
					result = new Point[2];
					result[0] = points[i];
					result[1] = points[j];
					minDistanceSoFar = distance;
				}
			}
		}
		return result;
	}

	/**
	 * Method for finding the range for the middle segment 
	 * @param points
	 * @param minDistance
	 * @param middle
	 * @param from
	 * @param to
	 * @return
	 */
	private static int[] findMiddleCutOffs(Point[] points, double minDistance, int middle, int from, int to) {

		int upperBound = -1; 
		int lowerBound = -1;
		//upper bound
		for(int i = middle+1; i < to; i++){
			if(points[i].xDistance(points[middle]) >= minDistance){
				upperBound = i;
				break;
			}
		}

		for(int i = middle-1; i > from; i--){
			if(points[i].xDistance(points[middle]) >= minDistance){
				lowerBound = i;
				break;
			}
		}

		return new int[] { lowerBound, upperBound };
	}

}
