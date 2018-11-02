import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MyHashTable<AnyType> {

	private static final int DEFAULT_TABLE_SIZE = 101; // 109617

	private HashEntry<AnyType>[] array; // The array of elements
	private int occupied; // The number of occupied cells
	private int theSize; // Current size
	private int maxLength; // maxLength of a word

	public MyHashTable() {
		this(DEFAULT_TABLE_SIZE);
	}

	public MyHashTable(int size) {
		allocateArray(size);
		doClear();
	}

	public MyHashTable(String dictionaryFile) {
		this(DEFAULT_TABLE_SIZE);
		loadDictionary(dictionaryFile);
	}

	/**
	 * Insert into the hash table. If the item is already present, do nothing.
	 * 
	 * @param x the item to insert.
	 */
	public boolean insert(AnyType x) {
		return insert(x, false);
	}

	/**
	 * Internal method to insert into the hash table.
	 * 
	 * @param x      the item to insert.
	 * @param prefix true if the item is a prefix
	 */
	private boolean insert(AnyType x, boolean prefix) {
		// Insert x as active
		int currentPos = findPos(x);
		if (isActive(currentPos))
			return false;

		if (array[currentPos] == null)
			++occupied;
		array[currentPos] = new HashEntry<>(x, true, prefix);
		theSize++;

		if (occupied > array.length / 2)
			rehash();

		return true;
	}

	/**
	 * Internal method to update a item status.
	 * 
	 * @param x      the item to update.
	 * @param prefix true if the item is a prefix
	 * @return
	 */
	private boolean update(AnyType x, boolean prefix) {
		int currentPos = findPos(x);
		if (!isActive(currentPos) || array[currentPos] == null)
			return insert(x, prefix);

		array[currentPos].isPrefix = prefix;

		return true;
	}

	/**
	 * Expand the hash table.
	 */
	private void rehash() {
		HashEntry<AnyType>[] oldArray = array;

		// Create a new double-sized, empty table
		allocateArray(2 * oldArray.length);
		occupied = 0;
		theSize = 0;

		// Copy table over
		for (HashEntry<AnyType> entry : oldArray)
			if (entry != null)
				insert(entry.element);
	}

	/**
	 * Method that performs linear probing resolution.
	 * 
	 * @param x the item to search for.
	 * @return the position where the search terminates.
	 */
	private int findPos(AnyType x) {
		int offset = 1;
		int currentPos = myhash(x);

		while (array[currentPos] != null && !array[currentPos].element.equals(x)) {
			currentPos += offset; // Compute ith probe
			if (currentPos >= array.length)
				currentPos -= array.length;
		}

		return currentPos;
	}

	/**
	 * Remove from the hash table.
	 * 
	 * @param x the item to remove.
	 * @return true if item removed
	 */
	public boolean remove(AnyType x) {
		int currentPos = findPos(x);
		if (isActive(currentPos)) {
			array[currentPos].isActive = false;
			theSize--;
			return true;
		} else
			return false;
	}

	/**
	 * Get current size.
	 * 
	 * @return the size.
	 */
	public int size() {
		return theSize;
	}

	/**
	 * Get length of internal table.
	 * 
	 * @return the size.
	 */
	public int capacity() {
		return array.length;
	}

	/**
	 * Get maxLength of a word
	 * 
	 * @return maxLength
	 */
	public int maxLength() {
		return maxLength;
	}

	/**
	 * Check if a item exist.
	 * 
	 * @param x the item to search for.
	 * @return true if exist.
	 */
	public boolean contains(AnyType x) {
		int currentPos = findPos(x);
		return isActive(currentPos);
	}

	/**
	 * Find an item in the hash table.
	 * 
	 * @param x the item to search for.
	 * @return the matching item.
	 */
	public String findword(String x) {
		StringBuilder sb = new StringBuilder();
		StringBuilder result = new StringBuilder();
		String reversed = sb.append(x).reverse().toString();

		int currentPos1 = findPos((AnyType) x);
		int currentPos2 = findPos((AnyType) reversed);

		// find 1
		if (isActive(currentPos1) ^ isActive(currentPos2)) {
			if (isActive(currentPos1)) {
				result.append((String) array[currentPos1].element);
			} else {
				result.append((String) array[currentPos2].element);
			}
			return result.toString();
		}
		// find 2
		if (isActive(currentPos1) && isActive(currentPos2)) {
			result.append((String) array[currentPos1].element).append("\n");
			result.append((String) array[currentPos2].element);
			return result.toString();
		}

		return "";
	}

	/**
	 * Return true if currentPos exists and is active.
	 * 
	 * @param currentPos the result of a call to findPos.
	 * @return true if currentPos is active.
	 */
	private boolean isActive(int currentPos) {
		return array[currentPos] != null && array[currentPos].isActive;
	}

	private boolean isPrefix(int currentPos) {
		return array[currentPos] != null && array[currentPos].isPrefix;
	}

	/**
	 * Make the hash table logically empty.
	 */
	public void makeEmpty() {
		doClear();
	}

	private void doClear() {
		occupied = 0;
		for (int i = 0; i < array.length; i++)
			array[i] = null;
	}

	private int myhash(AnyType x) {
		int hashVal = x.hashCode();

		hashVal %= array.length;
		if (hashVal < 0)
			hashVal += array.length;

		return hashVal;
	}

	private static class HashEntry<AnyType> {
		public AnyType element; // the element
		public boolean isActive; // false if marked deleted
		public boolean isPrefix; // false if is a word

		public HashEntry(AnyType e) {
			this(e, true, false);
		}

		public HashEntry(AnyType element, boolean isActive, boolean isprefix) {
			this.element = element;
			this.isActive = isActive;
			this.isPrefix = isprefix;
		}
	}

	/**
	 * Internal method to allocate array.
	 * 
	 * @param arraySize the size of the array.
	 */
	private void allocateArray(int arraySize) {
		array = new HashEntry[nextPrime(arraySize)];
	}

	/**
	 * Internal method to find a prime number at least as large as n.
	 * 
	 * @param n the starting number (must be positive).
	 * @return a prime number larger than or equal to n.
	 */
	private static int nextPrime(int n) {
		if (n % 2 == 0)
			n++;

		for (; !isPrime(n); n += 2)
			;

		return n;
	}

	/**
	 * Internal method to test if a number is prime. Not an efficient algorithm.
	 * 
	 * @param n the number to test.
	 * @return the result of the test.
	 */
	private static boolean isPrime(int n) {
		if (n == 2 || n == 3)
			return true;

		if (n == 1 || n % 2 == 0)
			return false;

		for (int i = 3; i * i <= n; i += 2)
			if (n % i == 0)
				return false;

		return true;
	}

	/**
	 * Load external dictionary file into MyHashTable
	 * 
	 * @param fileName Path and file name of dictionary
	 */

	/**
	 * Load external dictionary file into MyHashTable
	 * 
	 * @param fileName Path and file name of dictionary
	 * @param prifix   True if create prefix while loading words
	 */
	private void loadDictionary(String fileName) {
		List<String> dict = Collections.emptyList();
		maxLength = 0;
		try {
			dict = Files.readAllLines(Paths.get(fileName), StandardCharsets.UTF_8);
		} catch (NoSuchFileException e) {
			System.out.println("Dictionary file not found.");
		} catch (IOException e) {
			e.printStackTrace();
		}

		for (String line : dict) {
			insert((AnyType) line);
			if (line.length() > maxLength)
				maxLength = line.length();
		}
	}

	public void loadDictionary(String fileName, boolean prifix) {
		ArrayList<String> dict = new ArrayList<String>();

		try {
			BufferedReader in = new BufferedReader(new FileReader(fileName));
			StringBuilder sb = new StringBuilder();
			int c;
			while ((c = in.read()) != -1) {
				char ch = (char) c;
				if (ch != '\n') {
					sb.append(ch);
					if (sb.length() > 1) {
						insert((AnyType) sb.toString(), true);
					}
				} else {
					update((AnyType) sb.toString(), false);
					sb = new StringBuilder();
				}
			}
		} catch (IOException e) {
			System.err.println("A file error occurred: " + fileName);
			System.exit(1);
		}
	}

	public static void main(String[] args) {
		MyHashTable<String> a = new MyHashTable<>();
		a.loadDictionary("src/testDic.txt", true);
		System.out.println("done");
	}
}
