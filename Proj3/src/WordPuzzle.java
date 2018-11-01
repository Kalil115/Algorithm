import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

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
	private Set<String> result = new HashSet<>();

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
				System.out.print(matrix[i][j]+" ");
			}
			System.out.println();
		}
	}

	public void solvePuzzle() {
		List<Character> word = new ArrayList<>();

	}
	
	static int input(String prompt) {
		System.out.print(prompt);
		Scanner sc = new Scanner(System.in);
		int i = 0;
		if (sc.hasNextInt()) {
			i = sc.nextInt();
		}else {
			System.out.println("please enter a number");
			sc.next();
		}
		sc.close();
		return i;
	}
	
	public void addResult(String wordfound) {
		String[] words = wordfound.split("\n");
        for(String word : words) {
        	result.add(word);
        }
    }
	
	public Set<String> getResult() {
		return result;
	}
	
	public int getResultCount() {
		return result.size();
	}

	public static void main(String[] args) {

		MyHashTable<String> wordlist = new MyHashTable<>();
		System.out.println(wordlist.size());

		int row = 10;
		int col = 10;

//		do {
//			row = input("please input number of rows:");
//		} while (row <= 0);
//
//		do {
//		col = input("please input number of columns:");
//		} while (col <= 0);


		System.out.println("Rows:" + row + ", Columns:" + col);

		long startTime = System.currentTimeMillis();
		WordPuzzle wordPuzzle = new WordPuzzle(row, col);

		wordPuzzle.printWordPuzzle();

		long endTime = System.currentTimeMillis();

		
		wordPuzzle.addResult(wordlist.findword("no"));
		
		System.out.println("\nResult: " + wordPuzzle.getResultCount());
		System.out.println(wordPuzzle.getResult());

		System.out.println("Elapsed time: " + (endTime - startTime));
	}
}
