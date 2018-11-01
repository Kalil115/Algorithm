import java.util.Random;
import java.util.Scanner;

/**
 * 1. matrix of charactor 2. dictionary
 * 
 * 
 * 3. prefix 4. extended word
 * 
 * input int of rows and collum filled with random charactor
 * 
 * 
 * 
 *
 */
public class WordPuzzle {
	private int row;
	private int col;
	private char[][] matrix = {};

	public WordPuzzle(int row, int col) {
		this.row = row;
		this.col = col;
		matrix = new char[row][col];
		init();
	}

	private void init() {
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < col; j++) {
				Random rand = new Random();
				matrix[i][j] = (char) (rand.nextInt(26) + 'a');
			}
		}
	}

	public void printWordPuzzle() {
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < col; j++) {
				System.out.print(matrix[i][j]);
			}
			System.out.println();
		}
	}

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		int row = 0;
		int col = 0;

		do {
			System.out.print("please input number of rows:");
			if (sc.hasNextInt()) {
				row = sc.nextInt();
			} else {
				System.out.println("please enter a number");
				sc.next();
			}
		} while (row <= 0);

		do {
			System.out.print("please input number of columns:");
			if (sc.hasNextInt()) {
				col = sc.nextInt();
			} else {
				System.out.println("please enter a number");
				sc.next();
			}
		} while (col <= 0);
		
		sc.close();

		System.out.println("Rows:" + row + ", Columns:" + col);
		WordPuzzle wordPuzzle = new WordPuzzle(row, col);

		wordPuzzle.printWordPuzzle();
	}
}
