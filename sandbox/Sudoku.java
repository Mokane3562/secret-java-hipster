

public class Sudoku {
	private String[][] x;
	private final int SIZE = 9;

	public Sudoku(String s) {
		int k = 0;
		x = new String[SIZE][SIZE];
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				x[i][j] = s.substring(k, k + 1);
				k++;
			}
		}
	}

	public boolean colIsLatin(int j) {
		boolean[] found = new boolean[SIZE];
		for (int i = 0; i < SIZE; i++) {
			// Numbers run from 1 to 9, Array indexes 0 to 8, so subtract 1
			int k = Integer.parseInt(x[i][j]) - 1;

			found[k] = true; // found[0] = true means 1 is present, 
								// found[1] = true means 2 is present,... 
		}
		boolean result = true; // Assume row is latin...
		for (int i = 0; i < SIZE; i++) {
			result = result && found[i]; //... unless we detect a missing symbol
		}
		return result;
	}

	public boolean colsAreLatin() {
		boolean result = true; // Assume cols are latin
		for (int j = 0; j < SIZE; j++) {
			result = result && colIsLatin(j); // Make sure each row is latin
		}
		return result;
	}

	public boolean goodSubsquare(int i, int j) {
		boolean[] found = new boolean[SIZE];
		// We have a 3 x 3 arrangement of subsquares
		// Multiplying each subscript by 3 converts to the original array subscripts
		for (int p = i * 3, rowEnd = p + 3; p < rowEnd; p++) {
			for (int q = j * 3, colEnd = q + 3; q < colEnd; q++) {
				// Numbers run from 1 to 9, Array indexes 0 to 8, so subtract 1
				int k = Integer.parseInt(x[p][q]) - 1;

				found[k] = true; // found[0] = true means 1 is present, 
									// found[1] = true means 2 is present,... 
			}
		}
		boolean result = true; // Assume subsquare has every symbol...
		for (int p = 0; p < SIZE; p++) {
			result = result && found[p]; //... unless we detect a missing symbol
		}
		return result;
	}

	public boolean goodSubsquares() {
		boolean result = true; // Assume subsquares are latin
		for (int i = 0; i < SIZE / 3; i++) {
			for (int j = 0; j < SIZE / 3; j++) {
				result = result && goodSubsquare(i, j); // Make sure each subsquare is latin
			}
		}
		return result;
	}

	public boolean isValidSudoku() {
		return rowsAreLatin() && colsAreLatin() && goodSubsquares();
	}

	public boolean rowIsLatin(int i) {
		boolean[] found = new boolean[SIZE];
		for (int j = 0; j < SIZE; j++) {
			// Numbers run from 1 to 9, Array indexes 0 to 8, so subtract 1
			int k = Integer.parseInt(x[i][j]) - 1;

			found[k] = true; // found[0] = true means 1 is present, 
								// found[1] = true means 2 is present,... 
		}

		boolean result = true; // Assume row is latin...
		for (int j = 0; j < SIZE; j++) {
			result = result && found[j]; //... unless we detect a missing symbol
		}
		return result;
	}

	public boolean rowsAreLatin() {
		boolean result = true; // Assume rows are latin
		for (int i = 0; i < SIZE; i++) {
			result = result && rowIsLatin(i); // Make sure each row is latin
		}
		return result;
	}

	@Override
	public String toString() {
		String temp = "";
		for (int i = 0; i < SIZE; i++) {
			if ((i == 3) || (i == 6)) {
				temp = temp + "=================\n";
			}
			for (int j = 0; j < SIZE; j++) {
				if ((j == 3) || (j == 6)) {
					temp = temp + " || ";
				}
				temp = temp + x[i][j];
			}
			temp = temp + "\n";
		}
		return temp;
	}
}
