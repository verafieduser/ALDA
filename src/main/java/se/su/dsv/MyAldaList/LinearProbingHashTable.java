package se.su.dsv.MyAldaList;

public class LinearProbingHashTable<T> extends ProbingHashTable<T> {

	/*
	 * Denna metod ska skrivas klart. Den ska använda linjär sondering och hela tiden öka med ett.
	 */
	@Override
	protected int findPos(T x) {
		int position = myhash(x);
		while(continueProbing(position, x)){
			position++;

			if (position >= capacity()){
				position -= capacity();
			}

		}
		return position;
	}

}

// int offset = 1;
// int currentPos = myhash(x);
// while (continueProbing(currentPos, x)) {
// 	currentPos += offset; // Compute ith probe
// 	offset += 2; // Korrekt, men kanske inte helt uppenbart. Se avsnitt 5.4.2.
// 	if (currentPos >= capacity())
// 		currentPos -= capacity();
// }

// return currentPos;