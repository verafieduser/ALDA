package se.su.dsv.MyAldaList;

public class DoubleHashingProbingHashTable<T> extends ProbingHashTable<T> {

	/*
	 * Denna metod ska skrivas klart. Den ska använda bokens förslag på andra
	 * hashalgoritm: f(i) = i * hash2(x), där hash2(x) = R - (x mod R) och R är
	 * ett primtal mindre än tabellstorleken.
	 */
	@Override
	protected int findPos(T x) {
		int position = myhash(x);
		while(continueProbing(position, x)){
			position += hash2(x);

			if (position >= capacity()){
				position -= capacity();
			}

		}
		return position;
	}
	
	private int hash2(T x){
		int r = smallerPrimeThanCapacity();
		return r - (myhash(x) % r);
	}

	/*
	 * Denna metod ger ett primtal mindre än tabellens storlek. Detta primtal ska
	 * användas i metoden ovan.
	 */
	protected int smallerPrimeThanCapacity() {
		int n = capacity() - 2;
		while (!isPrime(n)) {
			n -= 2;
		}
		return n;
	}

}
