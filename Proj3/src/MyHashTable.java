import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MyHashTable<AnyType> {

	private static final int DEFAULT_TABLE_SIZE = 1000; // 109617
	private static final String DICTIONARY_FILE = "src/dictionary.txt";

	private HashEntry<AnyType>[] array; // The array of elements
	private int occupied; // The number of occupied cells
	private int theSize; // Current size

	public MyHashTable() {
		this(DEFAULT_TABLE_SIZE);
	}

	public MyHashTable(int size) {
		allocateArray(size);
		loadDictionary(DICTIONARY_FILE);
	}

	/**
	 * Insert into the hash table. If the item is already present, do nothing.
	 * 
	 * @param x the item to insert.
	 */
	public boolean insert(AnyType x) {
		// Insert x as active
		int currentPos = findPos(x);
		if (isActive(currentPos)) // see if element is duplicated
			return false;

		if (array[currentPos] == null)
			++occupied;
		array[currentPos] = new HashEntry<>(x, true, false);
		theSize++;

		if (occupied > array.length / 2)
			rehash();

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
		public boolean isprefix; // false if is a word

		public HashEntry(AnyType e) {
			this(e, true, false);
		}

		public HashEntry(AnyType element, boolean isActive, boolean isprefix) {
			this.element = element;
			this.isActive = isActive;
			this.isprefix = isprefix;
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
	private void loadDictionary(String fileName) {
		List<String> dic = Collections.emptyList();
		try {
			dic = Files.readAllLines(Paths.get(fileName), StandardCharsets.UTF_8);
		} catch (NoSuchFileException e) {
			System.out.println("Dictionary file not found.");
		} catch (IOException e) {
			e.printStackTrace();
		}

		for (String line : dic) {
			insert((AnyType) line);
		}

	}

}
