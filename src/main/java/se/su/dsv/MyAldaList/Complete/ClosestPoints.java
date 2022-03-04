// package se.su.dsv.MyAldaList.Complete;

// import java.util.Arrays;

// public class ClosestPoints {

// 	/**
// 	 *  
// 	 * @param points 	:	an unsorted array of points 
// 	 * @return			:	the pair of points that are the closest together in param points. 
// 	 */
// 	public static Point[] findClosestPairOfPoints(Point[] points) {
// 		Arrays.sort(points); // sorterar alla värdena efter compareTo i Point, som jämför x-värden.
// 		return findClosestPair(points, 0, points.length); //den rekursiva metoden
// 	}

// 	/**
// 	 * 
// 	 * @param points	:	an array of points that must be sorted on the x axis 
// 	 * @param from		:	start value that this particular instance of recursion starts on. 
// 	 * 							should be 0 if first iteration.
// 	 * @param to		:	end value that this particular instance of recursion ends on. 
// 	 * 							should be points.length if first iteration.
// 	 * @return
// 	 */
// 	private static Point[] findClosestPair(Point[] points, int from, int to) {
// 		Point[] result = null;
// 		double minDistanceSoFar;

// 		if (to - from < 10) { 	//om storleken på nuvarande iterations andel av arrayen 
// 								//är under 20 så används "brute-force"-lösning.
// 			return findSmallestBruteForce(points, from, to);

// 		} else {	//annars delas nuvarande iterations andel av arrayen upp i två halvor...
// 			int middle = (to + from) / 2;
// 			Point[] resultLeft = findClosestPair(points, from, middle);
// 			Point[] resultRight = findClosestPair(points, middle + 1, to);

// 			double distanceLeft = resultLeft[0].distanceTo(resultLeft[1]);
// 			double distanceRight = resultRight[0].distanceTo(resultRight[1]);

// 			if (distanceLeft < distanceRight) {
// 				result = resultLeft;
// 				minDistanceSoFar = distanceLeft;
// 			} else {
// 				result = resultRight;
// 				minDistanceSoFar = distanceRight;
// 			}

// 			int[] middleCutoffs = findMiddleCutOffs(points, minDistanceSoFar, middle, from, to);

			
// 			if (middleCutoffs[0] >= 0 && middleCutoffs[1] >= 0) { 	//if cutoffs are 0 or below that means 
// 																	//that there were no relevant middle connections
// 				Point[] resultThree = findSmallestBruteForce(points, middleCutoffs[0], middleCutoffs[1]);
// 				double distanceMiddle = resultThree[0].distanceTo(resultThree[1]);

// 				return distanceMiddle < minDistanceSoFar ? resultThree : result;
// 			} else {
// 				return result;
// 			}
// 		}
// 	}

// 	private static Point[] findSmallestBruteForce(Point[] points, int from, int to) {
// 		Point[] result = null;
// 		double distance;
// 		double minDistanceSoFar = Double.MAX_VALUE;

// 		for (int i = from; i < to; i++) {
// 			for (int j = i + 1; j < to; j++) {
// 				distance = points[i].distanceTo(points[j]);
// 				if (distance < minDistanceSoFar) {
// 					result = new Point[2];
// 					result[0] = points[i];
// 					result[1] = points[j];
// 					minDistanceSoFar = distance;
// 				}
// 			}
// 		}
// 		return result;
// 	}

// 	private static int[] findMiddleCutOffs(Point[] points, double minDistance, int middle, int from, int to) {

// 		int upperBound = -1; 
// 		int lowerBound = -1;
// 		//upper bound
// 		for(int i = middle+1; i < to; i++){
// 			if(points[i].xDistance(points[middle]) >= minDistance){
// 				upperBound = i;
// 				break;
// 			}
// 		}

// 		for(int i = middle-1; i > from; i--){
// 			if(points[i].xDistance(points[middle]) >= minDistance){
// 				lowerBound = i;
// 				break;
// 			}
// 		}

// 		return new int[] { lowerBound, upperBound };
// 	}

// }
