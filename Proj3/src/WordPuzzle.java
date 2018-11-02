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

	private void init() {
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < col; j++) {
				Random rand = new Random();
				grid[i][j] = (char) (rand.nextInt(26) + 'a');
			}
		}
//		grid = new char[][] { { 'a', 'b', 'c', 'd', 'e' }, { 'o', 'e', 'o', 'o', 'o' }, { 'o', 'o', 'n', 'e', 'o' },
//				{ 'o', 'e', 'e', 'o', 'o' }, { 'o', 'o', 'o', 'o', 'o' }, { 'o', 'o', 'o', 'o', 'o' } };

	}

	public void solvePuzzle(MyHashTable<String> dictionary) {

		for (int i = 0; i < row; i++) {
			for (int j = 0; j < col; j++) {
				// scan horizontally
				StringBuilder horizontal = new StringBuilder();
				for (int k = j; k < col && k - j < dictionary.maxLength(); k++) {
					horizontal.append(grid[i][k]);
					if (horizontal.length() > 1) {
						addResult(dictionary.findword(horizontal.toString()));
					}
				}

				// scan vertically
				StringBuilder vertical = new StringBuilder();
				for (int k = i; k < row && k - i < dictionary.maxLength(); k++) {
					vertical.append(grid[k][j]);
					if (vertical.length() > 1) {
						addResult(dictionary.findword(vertical.toString()));
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
							addResult(dictionary.findword(diagonalUR.toString()));
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
							addResult(dictionary.findword(diagonalDR.toString()));
						}
					}
				}
			}
		}

	}

	private void addResult(String wordfound) {
		String[] words = wordfound.split("\n");
		for (String word : words) {
			if (word.length() > 1) {
				result.add(word);
			}
		}
	}

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

	public void printWordPuzzle() {
		System.out.println("Rows:" + row + ", Columns:" + col);
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < col; j++) {
				System.out.print(grid[i][j] + " ");
			}
			System.out.println();
		}
	}

	public void printResult() {
		System.out.println("\nResult: " + getResultCount());

		Iterator<String> it = result.iterator();
		System.out.print("[");
		int count = 0;
		while (it.hasNext()) {
			System.out.print(it.next() + ", ");
			count++;
			if (count == col) {
				System.out.println();
				count = 0;
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

		int row = input("please input number of rows:");
		int col = input("please input number of columns:");
//		int row = 10;
//		int col = 10;
		boolean usePrefix = false;
		
		long startTime = System.currentTimeMillis();

//		MyHashTable<String> dictionary = new MyHashTable<>("src/dictionary.txt");
//		System.out.println("dictionary size: " + dictionary.size());

		WordPuzzle wordPuzzle = new WordPuzzle(row, col);
		System.out.println();
		wordPuzzle.printWordPuzzle();

//		wordPuzzle.solvePuzzle(dictionary);
		wordPuzzle.printResult();

		long endTime = System.currentTimeMillis();
		System.out.println("Elapsed time: " + (endTime - startTime));
	}
}
