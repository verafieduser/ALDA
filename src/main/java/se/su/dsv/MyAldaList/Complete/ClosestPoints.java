/**
 * @author Vera Sol Nygren
 * klny8594
 */
package se.su.dsv.MyAldaList.Complete;
import java.util.Arrays;

public class ClosestPoints {

	/**
	 * Method for finding the closest pair of points in the array provided. 
	 * Sorts the array on the x-axis, and then recursively splits the array in two, 
	 * and brute forces the halves when they are less than 4.
	 * Afterwards it creates a middle strip of all points x distance from the middle that is less
	 * than minimum distance found in both halves. It then searches through this middle strip by 
	 * sorting it on the y-axis, and then brute forcing them.
	 * 
	 * I chose to not provide a pre-sorted list for the y-axis that would be split up as the recursion
	 * goes deeper. This is because it would be completely contrary to the solution with the indices,
	 * because you would have to iterate over the y-list to be able to split it on the x-axis as required.
	 * Even though this could improve performance in the case that points are gathered around the centre, it
	 * would make the general performance a lot worse as a cost. I do not think that cost is worth it, 
	 * especially since the data we tested upon was randomly distributed, which gives an incredible low chance
	 * for a list of points concentrated along the middle-points. 
	 * 
	 * However, if I would take this algorithm further, it would be to overload it with a method that takes 
	 * the same parameters, except for a boolean flag extra. This flag would decide if you'd want to use the
	 * anti-clustering algorithm, or this one. That could give the user the power to decide which algorithm to use,
	 * based on the data they are dealing with and what risks they are willing to take given pseudo random data.
	 * 
	 * @param points array of points, unsorted. 
	 * @return the closest pair of points in points array. 
	 */
	public static Point[] findClosestPairOfPoints(Point[] points) {
		Point[] pointsX = points;
		Arrays.sort(pointsX, (p1, p2) -> p1.getX() - p2.getX());
		 
		return findClosestPairOfPoints(pointsX, 0, pointsX.length); 
	}

	/**
	 * Main method in which recursion occurs. 
	 * @param points array of points presorted on the x-axis.
	 * @param from start index of the current recursion. if first time, it is 0.
	 * @param to end index of the current recursion. if first time, it is the length of points.
	 * @return returns the closest pair of points in the array points.
	 */
	private static Point[] findClosestPairOfPoints(Point[] points, int from, int to) {
		Point[] result = null;
		double minDistanceSoFar;

		if (to - from <= 3) { // om storleken på nuvarande iterationens andel av arrayen
								// är under 3 så används "brute-force"-lösning.
			return closestPointBruteForce(points, Double.MAX_VALUE, from, to, false);

		} else {
			int middle = (to + from) / 2;
			Point[] resultLeft = findClosestPairOfPoints(points, from, middle + 1);
			Point[] resultRight = findClosestPairOfPoints(points, middle, to);

			double distanceLeft = resultLeft[0].distanceTo(resultLeft[1]);
			double distanceRight = resultRight[0].distanceTo(resultRight[1]);

			if (distanceLeft < distanceRight) {
				result = resultLeft;
				minDistanceSoFar = distanceLeft;
			} else {
				result = resultRight;
				minDistanceSoFar = distanceRight;
			}

			Point[] middleStrip = createMiddleStrip(points, minDistanceSoFar, from, to);
			Point[] resultThree = closestPointBruteForce(middleStrip, minDistanceSoFar, 0, middleStrip.length, true);

			if (resultThree[1] == null || resultThree[0] == null) {
				return result;
			} else {
				double distanceMiddle = resultThree[0].distanceTo(resultThree[1]);
				return distanceMiddle < minDistanceSoFar ? resultThree : result;				
			}


		}
	}

	/**
	 * Method that creates the middle strip of point pairs which the two recursive calls in the main method cannot consider. 
	 * Iterates from the center, which means the least amount as possible of the array will be iterated over.
	 * @param points a presorted array of points, sorted on the x-axis. 
	 * @param minDistance minimum distance found so far.
	 * @param middle the index of the middle point of the section we are currently looking at
	 * can be calculated by (from+to)/2
	 * @param from array start index for current iteration. 0 if you are currently on middle section.
	 * @param to array end index for current iteration. length of array if you are currently on middle section.
	 * @return returns an array of Points that consist of the middle point+-minDistance of the array Points
	 */
	private static Point[] createMiddleStrip(Point[] points, double minDistance, int from, int to) {

		int middle = (from+to)/2;
		int lowerBound = middle;
		int upperBound = middle;

		for (int i = middle+1; i < to; i++) {
			if (points[i].xDistance(points[middle]) <= minDistance) { 
				upperBound = i;
			} else {
				upperBound +=1;
				break;
			}
		}

		for (int i = middle - 1; i >= from; i--) {
			if (points[i].xDistance(points[middle]) <= minDistance) {
				lowerBound = i;
			} else {
				break;
			}
		}

		int middleLength = upperBound-lowerBound+1;
		if(middleLength > 0){
			Point[] middleStrip = new Point[middleLength];
			for(int i = 0; i < middleLength; i++ ){
				middleStrip[i] = points[lowerBound+i];
			}
			return middleStrip;
		}

		return new Point[0];
	}

	/**
	 * Method directly inspired by implementation on page 453 in Data Structures and Algorithm Analysis in Java, 
	 * by Mark Allen Weiss. Ensures running time is O(n log^2 n).
	 * Made general by the parameter isMiddle, ensuring there is no need for two separate brute force methods, when
	 * they essentially do the same thing. 
	 * @param points an array sorted on the x axis. if isMiddle is true, no pre-sorting is required. 
	 * @param minDistance the minimum distance between two points found so far. 
	 * @return two points which are the two closest pair found in this method. 
	 */
	private static Point[] closestPointBruteForce(Point[] points, double minDistance, int from, int to, boolean isMiddle){
		if(isMiddle){
			Arrays.sort(points, (p1, p2) -> p1.getY() - p2.getY()); 	
		}

		Point[] result = new Point[2];
		for(int i = from; i < to; i++){
			for(int j = i + 1; j < to; j++){
				if(isMiddle && points[i].yDistance(points[j]) > minDistance){
					break;
				} else if(points[i].distanceTo(points[j])<minDistance){
					result[0] = points[i];
					result[1] = points[j];
					minDistance = points[i].distanceTo(points[j]);
				}
			}
		}
		return result;
	}
}
