import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

/**
 *
 * 
 * 
 * 
 *
 */
public class WordPuzzle {
	private int row;
	private int col;
	private char[][] grid = {};
	private Set<String> result = new HashSet<>();

	public WordPuzzle(int row, int col) {
		this.row = row;
		this.col = col;
		grid = new char[row][col];
		init();
	}

	public WordPuzzle(int row, int col, boolean usePrefix) {
		this.row = row;
		this.col = col;
		grid = new char[row][col];
		init();
	}

	/**
	 * Fill word puzzle with random characters
	 * 
	 */
	private void init() {
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < col; j++) {
				Random rand = new Random();
				grid[i][j] = (char) (rand.nextInt(26) + 'a');
			}
		}
	}

	/**
	 * solve the word puzzle
	 * 
	 * @param dictionary to look up
	 * @param usePrefix  use prefix algorithm to solve the puzzle
	 */
	public void solvePuzzle(MyHashTable<String> dictionary, boolean usePrefix) {
		if (usePrefix == true) {
			solvePuzzleWithPrefix(dictionary);
		} else {
			solvePuzzle(dictionary);
		}
	}

	/**
	 * Internal method to solve the puzzle with prefix
	 * 
	 * @param dictionary
	 */
	private void solvePuzzleWithPrefix(MyHashTable<String> dictionary) {

		// starting point
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < col; j++) {
				// scan rightward
				StringBuilder rightward = new StringBuilder();
				for (int k = j; k < col && k - j < dictionary.maxLength(); k++) {
					rightward.append(grid[i][k]);
					if (rightward.length() > 1) {
						String chars = rightward.toString();
						if (dictionary.contains(chars)) {
							if (dictionary.isPrefix(chars)) {
								continue;
							} else {
								addResult(dictionary.findWord(chars));
							}
						} else {
							break;
						}
					}
				}

				// scan leftward
				StringBuilder leftward = new StringBuilder();
				for (int k = j; k >= 0 && j - k < dictionary.maxLength(); k--) {
					leftward.append(grid[i][k]);
					if (leftward.length() > 1) {
						String chars = leftward.toString();
						if (dictionary.contains(chars)) {
							if (dictionary.isPrefix(chars)) {
								continue;
							} else {
								addResult(dictionary.findWord(chars));
							}
						} else {
							break;
						}
					}
				}

				// scan downward
				StringBuilder downward = new StringBuilder();
				for (int k = i; k < row && k - i < dictionary.maxLength(); k++) {
					downward.append(grid[k][j]);
					if (downward.length() > 1) {
						String chars = downward.toString();
						if (dictionary.contains(chars)) {
							if (dictionary.isPrefix(chars)) {
								continue;
							} else {
								addResult(dictionary.findWord(chars));
							}
						} else {
							break;
						}
					}
				}

				// scan upward
				StringBuilder upward = new StringBuilder();
				for (int k = i; k >= 0 && i - k < dictionary.maxLength(); k--) {
					upward.append(grid[k][j]);
					if (upward.length() > 1) {
						String chars = upward.toString();
						if (dictionary.contains(chars)) {
							if (dictionary.isPrefix(chars)) {
								continue;
							} else {
								addResult(dictionary.findWord(chars));
							}
						} else {
							break;
						}
					}
				}

				// scan up-right
				if (i > 0 && j < col - 1) {
					StringBuilder diagonalUR = new StringBuilder();
					int count = 0;
					for (int k = i, l = j; k >= 0 && l < col && count < dictionary.maxLength(); k--, l++) {
						diagonalUR.append(grid[k][l]);
						count++;
						if (diagonalUR.length() > 1) {
							String chars = diagonalUR.toString();
							if (dictionary.contains(chars)) {
								if (dictionary.isPrefix(chars)) {
									continue;
								} else {
									addResult(dictionary.findWord(chars));
								}
							} else {
								break;
							}
						}
					}
				}

				// scan down-right
				if (i < row - 1 && j < col - 1) {
					StringBuilder diagonalDR = new StringBuilder();
					int count = 0;
					for (int k = i, l = j; k < row && l <= col - 1 && count < dictionary.maxLength(); k++, l++) {
						diagonalDR.append(grid[k][l]);
						count++;
						if (diagonalDR.length() > 1) {
							String chars = diagonalDR.toString();
							if (dictionary.contains(chars)) {
								if (dictionary.isPrefix(chars)) {
									continue;
								} else {
									addResult(dictionary.findWord(chars));
								}
							} else {
								break;
							}
						}
					}
				}

				// scan up-left
				if (i > 0 && j > 0) {
					StringBuilder diagonalUL = new StringBuilder();
					int count = 0;
					for (int k = i, l = j; k >= 0 && l >= 0 && count < dictionary.maxLength(); k--, l--) {
						diagonalUL.append(grid[k][l]);
						count++;
						if (diagonalUL.length() > 1) {
							String chars = diagonalUL.toString();
							if (dictionary.contains(chars)) {
								if (dictionary.isPrefix(chars)) {
									continue;
								} else {
									addResult(dictionary.findWord(chars));
								}
							} else {
								break;
							}
						}
					}
				}

				// scan down-left
				if (i < row - 1 && j > 0) {
					StringBuilder diagonalDL = new StringBuilder();
					int count = 0;
					for (int k = i, l = j; k < row && l >= 0 && count < dictionary.maxLength(); k++, l--) {
						diagonalDL.append(grid[k][l]);
						count++;
						if (diagonalDL.length() > 1) {
							String chars = diagonalDL.toString();
							if (dictionary.contains(chars)) {
								if (dictionary.isPrefix(chars)) {
									continue;
								} else {
									addResult(dictionary.findWord(chars));
								}
							} else {
								break;
							}
						}
					}
				}

			}
		}
	}

	/**
	 * Internal method to solve the puzzle normally
	 * 
	 * @param dictionary
	 */
	private void solvePuzzle(MyHashTable<String> dictionary) {

		for (int i = 0; i < row; i++) {
			for (int j = 0; j < col; j++) {
				// scan horizontally
				StringBuilder horizontal = new StringBuilder();
				for (int k = j; k < col && k - j < dictionary.maxLength(); k++) {
					horizontal.append(grid[i][k]);
					if (horizontal.length() > 1) {
						addResult(dictionary.findWordwithReverse(horizontal.toString()));
					}
				}

				// scan vertically
				StringBuilder vertical = new StringBuilder();
				for (int k = i; k < row && k - i < dictionary.maxLength(); k++) {
					vertical.append(grid[k][j]);
					if (vertical.length() > 1) {
						addResult(dictionary.findWordwithReverse(vertical.toString()));
					}
				}

				// scan up-right diagonal
				if (i > 0 && j < col - 1) {
					StringBuilder diagonalUR = new StringBuilder();
					int count = 0;
					for (int k = i, l = j; k >= 0 && l < col && count < dictionary.maxLength(); k--, l++) {
						diagonalUR.append(grid[k][l]);
						count++;
						if (diagonalUR.length() > 1) {
							addResult(dictionary.findWordwithReverse(diagonalUR.toString()));
						}
					}
				}

				// scan down-right diagonal
				if (i < row - 1 && j < col - 1) {
					StringBuilder diagonalDR = new StringBuilder();
					int count = 0;
					for (int k = i, l = j; k < row && l <= col - 1 && count < dictionary.maxLength(); k++, l++) {
						diagonalDR.append(grid[k][l]);
						count++;
						if (diagonalDR.length() > 1) {
							addResult(dictionary.findWordwithReverse(diagonalDR.toString()));
						}
					}
				}
			}
		}

	}

	/**
	 * Add found word in a result Set
	 * 
	 * @param wordfound words found in the dictionary
	 */
	private void addResult(String wordfound) {
		String[] words = wordfound.split("\n");
		for (String word : words) {
			if (word.length() > 1) {
				result.add(word);
			}
		}
	}

	/**
	 * prompt
	 * 
	 * @param prompt
	 * @return
	 */
	public static int input(String prompt) {
		int i = 0;
		Scanner sc = new Scanner(System.in);
		do {
			System.out.print(prompt);
			if (sc.hasNextInt()) {
				i = sc.nextInt();
			} else {
				System.out.println("please enter a number");
				sc.next();
			}
		} while (i <= 0);
		return i;
	}

	/**
	 * print puzzle in the console
	 */
	public void printWordPuzzle() {
		System.out.println("Rows:" + row + ", Columns:" + col);
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < col; j++) {
				System.out.print(grid[i][j] + " ");
			}
			System.out.println();
		}
	}

	/**
	 * print the resut set
	 */
	public void printResult() {
		System.out.println("\nResult: " + getResultCount() + " words.");

		Iterator<String> it = result.iterator();
		System.out.print("[");
		int count = 0;
		if (it.hasNext()) {
			System.out.print(it.next());
			while (it.hasNext()) {
				System.out.print(" ," + it.next());
				count++;
				if (count == col) {
					System.out.println();
					count = 0;
				}
			}
		}
		System.out.println("]");
		System.out.println();
	}

	public Set<String> getResult() {
		return result;
	}

	public int getResultCount() {
		return result.size();
	}

	public static void main(String[] args) {
		int row = 0;
		int col = 0;
		boolean usePrefix = false;
		boolean input = true;
		Scanner sc = new Scanner(System.in);

		while (row <= 0) {
			System.out.print("please input number of rows:");
			if (sc.hasNextInt()) {
				row = sc.nextInt();
			} else {
				System.out.println("please enter a number greater than 0.");
				sc.next();
			}
		}

		while (col <= 0) {
			System.out.print("please input number of columns:");
			if (sc.hasNextInt()) {
				col = sc.nextInt();
			} else {
				System.out.println("please enter a number greater than 0.");
				sc.next();
			}
		}

		while (input) {
			System.out.print("Do you want to use prefix when solving puzzle?(Y/N)");
			if (sc.hasNextLine()) {
				sc.nextLine();
				String yn = sc.nextLine();
				if (yn.equals("Y") || yn.equals("y") || yn.equals("yes")) {
					usePrefix = true;
					input = false;
				} else {
					usePrefix = false;
					input = false;
				}
			}
		}

		sc.close();

		long startTime = System.currentTimeMillis();
		System.out.print("Loading dictionary...");
		MyHashTable<String> dictionary = new MyHashTable<>("src/dictionary.txt", usePrefix);
		System.out.println("done");

		System.out.print("Generating a " + row + "x" + col + " word puzzle ");
		WordPuzzle wordPuzzle = new WordPuzzle(row, col);
		System.out.println("done");
		System.out.println("Rows:" + row + ", Columns:" + col);
		System.out.println();

//		wordPuzzle.printWordPuzzle();
		if (usePrefix) {
			System.out.print("Solving puzzle with prefix enhancement...");
		} else {
			System.out.print("Solving puzzle...");
		}
		wordPuzzle.solvePuzzle(dictionary, usePrefix);
		System.out.println("done");
//		wordPuzzle.printResult();

		long endTime = System.currentTimeMillis();
		System.out.println("Elapsed time: " + (endTime - startTime));
	}
}
