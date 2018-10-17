// BinarySearchTree class
//
// CONSTRUCTION: with no initializer
//
// ******************PUBLIC OPERATIONS*********************
// void insert( x )       --> Insert x
// void remove( x )       --> Remove x
// boolean contains( x )  --> Return true if x is present
// Comparable findMin( )  --> Return smallest item
// Comparable findMax( )  --> Return largest item
// boolean isEmpty( )     --> Return true if empty; else false
// void makeEmpty( )      --> Remove all items
// void printTree( )      --> Print tree in sorted order
// ******************ERRORS********************************
// Throws UnderflowException as appropriate

/**
 * Implements an unbalanced binary search tree. Note that all "matching" is
 * based on the compareTo method.
 * 
 * @author Mark Allen Weiss
 */
public class BinarySearchTree<AnyType extends Comparable<? super AnyType>> {

	/** The tree root. */
	private BinaryNode<AnyType> root;

	/**
	 * Construct the tree.
	 */
	public BinarySearchTree() {
		root = null;
	}

	/**
	 * Insert into the tree; duplicates are ignored.
	 * 
	 * @param x the item to insert.
	 */
	public void insert(AnyType x) {
		root = insert(x, root);
	}

	/**
	 * Remove from the tree. Nothing is done if x is not found.
	 * 
	 * @param x the item to remove.
	 */
	public void remove(AnyType x) {
		root = remove(x, root);
	}

	/**
	 * Find the smallest item in the tree.
	 * 
	 * @return smallest item or null if empty.
	 */
	public AnyType findMin() {
		if (isEmpty())
			throw new UnderflowException();
		return findMin(root).element;
	}

	/**
	 * Find the largest item in the tree.
	 * 
	 * @return the largest item of null if empty.
	 */
	public AnyType findMax() {
		if (isEmpty())
			throw new UnderflowException();
		return findMax(root).element;
	}

	/**
	 * Find an item in the tree.
	 * 
	 * @param x the item to search for.
	 * @return true if not found.
	 */
	public boolean contains(AnyType x) {
		return contains(x, root);
	}

	/**
	 * Make the tree logically empty.
	 */
	public void makeEmpty() {
		root = null;
	}

	/**
	 * Test if the tree is logically empty.
	 * 
	 * @return true if empty, false otherwise.
	 */
	public boolean isEmpty() {
		return root == null;
	}

	/**
	 * Print the tree contents in sorted order.
	 */
	public void printTree() {
		if (isEmpty())
			System.out.println("Empty tree");
		else
			printTree(root);
	}

	/**
	 * Count the nodes in the tree.
	 */
	public int nodeCount() {
		if (isEmpty())
			return 0;
		else
			return nodeCount(root);
	}

	/**
	 * Test if the tree is full.
	 * 
	 * @return true if full, false otherwise.
	 */
	public boolean isFull() {
		return isFull(root);
	}

	/**
	 * Compare if the structure of two trees are match
	 * 
	 * @param tree the BinarySearchTree to compare with
	 * @return ture if they are match
	 */
	public boolean compareStructure(BinarySearchTree<AnyType> tree) {
		return compareStructure(this.root, tree.root);
	}

	/**
	 * Compares if the current tree is identical to another tree.
	 * 
	 * @param tree the BinarySearchTree to compare with
	 * @return ture if they are identical
	 */
	public boolean equals(BinarySearchTree<AnyType> tree) {
		return equals(this.root, tree.root);
	}

	/**
	 * Creates and returns a new tree that is a copy of the original tree.
	 * 
	 * @return
	 */
	public BinarySearchTree<AnyType> copy() {
		BinarySearchTree<AnyType> newTree = new BinarySearchTree<>();
		newTree.root = copy(root);
		return newTree;
	}

	/**
	 * Creates and returns a new tree that is a mirror image of the original tree.
	 * 
	 * @return
	 */
	public BinarySearchTree<AnyType> mirror() {
		BinarySearchTree<AnyType> newTree = this.copy();
		mirror(newTree.root);
		return newTree;
	}

	/**
	 * Returns true if the tree is a mirror of the passed tree.
	 * 
	 * @param tree
	 * @return
	 */
	public boolean isMirror(BinarySearchTree<AnyType> tree) {
		BinarySearchTree<AnyType> newTree = tree.mirror();
		return this.equals(newTree);
	}

	/**
	 * Performs a single rotation on the node having the passed value.
	 * 
	 * @param x
	 */
	public void rotateRight(AnyType x) {
		if (this.contains(x)) {
			root = rotateRight(x, root);
		}
	}

	/**
	 * Performs a single rotation on the node having the passed value.
	 * 
	 * @param x
	 */
	public void rotateLeft(AnyType x) {
		if (this.contains(x)) {
			root = rotateLeft(x, root);
		}
	}

	/**
	 * performs a level-by-level printing of the tree.
	 * 
	 */
	public void printLevels() {
		if (isEmpty())
			System.out.println("Empty tree");
		else {
			int h = height(root);
			for (int i = 0; i <= h; i++) {
				printLevels(root, i);
				System.out.println();
			}
		}
	}

	/**
	 * Internal method to insert into a subtree.
	 * 
	 * @param x the item to insert.
	 * @param t the node that roots the subtree.
	 * @return the new root of the subtree.
	 */
	private BinaryNode<AnyType> insert(AnyType x, BinaryNode<AnyType> t) {
		if (t == null)
			return new BinaryNode<>(x, null, null);

		int compareResult = x.compareTo(t.element);

		if (compareResult < 0) {
			t.left = insert(x, t.left);
		} else if (compareResult > 0) {
			t.right = insert(x, t.right);
		} else
			; // Duplicate; do nothing
		return t;
	}

	/**
	 * Internal method to remove from a subtree.
	 * 
	 * @param x the item to remove.
	 * @param t the node that roots the subtree.
	 * @return the new root of the subtree.
	 */
	private BinaryNode<AnyType> remove(AnyType x, BinaryNode<AnyType> t) {
		if (t == null)
			return t; // Item not found; do nothing

		int compareResult = x.compareTo(t.element);

		if (compareResult < 0) {
			t.left = remove(x, t.left);
		} else if (compareResult > 0) {
			t.right = remove(x, t.right);
		} else if (t.left != null && t.right != null) // Two children
		{
			t.element = findMin(t.right).element;
			t.right = remove(t.element, t.right);
		} else
			t = (t.left != null) ? t.left : t.right;
		return t;
	}

	/**
	 * Internal method to find the smallest item in a subtree.
	 * 
	 * @param t the node that roots the subtree.
	 * @return node containing the smallest item.
	 */
	private BinaryNode<AnyType> findMin(BinaryNode<AnyType> t) {
		if (t == null)
			return null;
		else if (t.left == null)
			return t;
		return findMin(t.left);
	}

	/**
	 * Internal method to find the largest item in a subtree.
	 * 
	 * @param t the node that roots the subtree.
	 * @return node containing the largest item.
	 */
	private BinaryNode<AnyType> findMax(BinaryNode<AnyType> t) {
		if (t != null)
			while (t.right != null)
				t = t.right;

		return t;
	}

	/**
	 * Internal method to find an item in a subtree.
	 * 
	 * @param x is item to search for.
	 * @param t the node that roots the subtree.
	 * @return node containing the matched item.
	 */
	private boolean contains(AnyType x, BinaryNode<AnyType> t) {
		if (t == null)
			return false;

		int compareResult = x.compareTo(t.element);

		if (compareResult < 0)
			return contains(x, t.left);
		else if (compareResult > 0)
			return contains(x, t.right);
		else
			return true; // Match
	}

	/**
	 * Internal method to print a subtree in sorted order.
	 * 
	 * @param t the node that roots the subtree.
	 */
	private void printTree(BinaryNode<AnyType> t) {
		if (t != null) {
			printTree(t.left);
			System.out.println(t.element);
			printTree(t.right);
		}
	}

	/**
	 * Internal method to compute height of a subtree.
	 * 
	 * @param t the node that roots the subtree.
	 */
	private int height(BinaryNode<AnyType> t) {
		if (t == null)
			return -1;
		else
			return 1 + Math.max(height(t.left), height(t.right));
	}

	/**
	 * Internal method to count the nodes in a subtree.
	 * 
	 * @param t the node that roots the subtree.
	 */
	private int nodeCount(BinaryNode<AnyType> t) {
		int count = 0;
		if (t != null) {
			count += nodeCount(t.left);
			count++;
			count += nodeCount(t.right);
		}
		return count;
	}

	/**
	 * Internal method to check if a subtree is full.
	 * 
	 * @param t the node that roots the subtree.
	 */
	private boolean isFull(BinaryNode<AnyType> t) {
		if (t.left != null && t.right != null) {
			return isFull(t.left) && isFull(t.right);
		} else if (t.left == null && t.right == null)
			return true;
		return false;
	}

	/**
	 * Internal method to check if two subtrees are the same in structure.
	 * 
	 * @param t1 the node that roots the subtree1.
	 * @param t2 the node that roots the subtree2.
	 * @return true if they match
	 */
	private boolean compareStructure(BinaryNode<AnyType> t1, BinaryNode<AnyType> t2) {
		if (t1 == null && t2 == null)
			return true;
		if (t1 == null || t2 == null)
			return false;
		return compareStructure(t1.left, t2.left) && compareStructure(t1.right, t2.right);
	}

	/**
	 * Internal method to check if two subtrees are identical.
	 * 
	 * @param t1 the node that roots the subtree1.
	 * @param t2 the node that roots the subtree2.
	 * @return true if they are identical
	 */
	private boolean equals(BinaryNode<AnyType> t1, BinaryNode<AnyType> t2) {
		if (t1 == null && t2 == null)
			return true;
		if (t1 == null || t2 == null)
			return false;
		int compareResult = t1.element.compareTo(t2.element);
		if (compareResult != 0)
			return false;
		return equals(t1.left, t2.left) && equals(t1.right, t2.right);
	}

	/**
	 * Internal method to copy nodes from old tree to new tree.
	 * 
	 * @param t the node that roots the old subtree.
	 * @return new Tree
	 */
	private BinaryNode<AnyType> copy(BinaryNode<AnyType> t) {
		
		if(t == null)
			return null;
		
		BinaryNode<AnyType> newNode = new BinaryNode<>(t.element);
		newNode.left = copy(t.left);
		newNode.right = copy(t.right);
		
		return newNode;
	}

	/**
	 * Internal method to create a mirror nodes from old tree to new tree.
	 * 
	 * @param t the node that roots the subtree.
	 * @return
	 */
	private BinaryNode<AnyType> mirror(BinaryNode<AnyType> t) {
		if (t == null)
			return t;
		BinaryNode<AnyType> oldLeft = mirror(t.left);
		BinaryNode<AnyType> oldRight = mirror(t.right);
		t.left = oldRight;
		t.right = oldLeft;
		return t;
	}

	/**
	 * Internal method to rotate right once.
	 * 
	 * @param t old root of a subtree
	 * @return new root of a subtree
	 */
	private BinaryNode<AnyType> rotateRight(AnyType x, BinaryNode<AnyType> t) {
		if (t == null)
			return null;
		int compareResult = x.compareTo(t.element);
		if (compareResult < 0)
			t.left = rotateRight(x, t.left);
		else if (compareResult > 0)
			t.right = rotateRight(x, t.right);
		else if (t.left == null)
			return t; // nothing to rotate
		else {
			BinaryNode<AnyType> temp = t.left;
			t.left = temp.right;
			temp.right = t;
			return temp;
		}
		return t;
	}

	/**
	 * Internal method to rotate left once.
	 * 
	 * @param t old root of a subtree
	 * @return new root of a subtree
	 */
	private BinaryNode<AnyType> rotateLeft(AnyType x, BinaryNode<AnyType> t) {
		if (t == null)
			return null;
		int compareResult = x.compareTo(t.element);
		if (compareResult < 0)
			t.left = rotateLeft(x, t.left);
		else if (compareResult > 0)
			t.right = rotateLeft(x, t.right);
		else if (t.right == null)
			return t; // nothing to rotate
		else {
			BinaryNode<AnyType> temp = t.right;
			t.right = temp.left;
			temp.left = t;
			return temp;
		}
		return t;
	}

	/**
	 * Internal method to print a subtree level-by-level.
	 * 
	 * @param t the node that roots the subtree.
	 */
	private void printLevels(BinaryNode<AnyType> t, int depth) {
		if (t == null) {
			return;
		}
		if (depth == 0) {
			System.out.print(t.element + " ");
		} else if (depth > 0) {
			printLevels(t.left, depth - 1);
			printLevels(t.right, depth - 1);
		}
	}

	// Basic node stored in unbalanced binary search trees
	private static class BinaryNode<AnyType> {

		AnyType element; // The data in the node
		BinaryNode<AnyType> left; // Left child
		BinaryNode<AnyType> right; // Right child

		// Constructors
		BinaryNode(AnyType theElement) {
			this(theElement, null, null);
		}

		BinaryNode(AnyType theElement, BinaryNode<AnyType> lt, BinaryNode<AnyType> rt) {
			element = theElement;
			left = lt;
			right = rt;
		}
	}

	// Test program
	public static void main(String[] args) {

		BinarySearchTree<Integer> tree1 = new BinarySearchTree<>();
		tree1.insert(5);
		tree1.insert(3);
		tree1.insert(8);
		tree1.insert(1);
		tree1.insert(4);

		BinarySearchTree<Integer> tree2 = new BinarySearchTree<>();
		tree2.insert(10);
		tree2.insert(5);
		tree2.insert(15);
		tree2.insert(2);
		tree2.insert(7);

		BinarySearchTree<Integer> tree3 = new BinarySearchTree<>();
		tree3.insert(6);
		tree3.insert(2);
		tree3.insert(8);
		tree3.insert(1);
		tree3.insert(5);
		tree3.insert(3);
		tree3.insert(4);

		// nodeCount
		System.out.println("nodeCount");
		System.out.println("nodeCount tree1: " + tree1.nodeCount());
		System.out.println("nodeCount tree2: " + tree2.nodeCount());
		System.out.println("nodeCount tree3: " + tree3.nodeCount());

		// isFull
		System.out.println("isFull");
		System.out.println("isFull tree1: " + tree1.isFull());
		System.out.println("isFull tree2: " + tree2.isFull());
		System.out.println("isFull tree3: " + tree3.isFull());

		// compareStructure
		System.out.println("compareStructure");
		System.out.println("compareStructure tree1 and tree2: " + tree1.compareStructure(tree2));
		System.out.println("compareStructure tree1 and tree3: " + tree1.compareStructure(tree3));

		// equals
		System.out.println("equals");
		System.out.println("tree1 and tree2 equals: " + tree1.equals(tree2));
		System.out.println("tree1 and tree3 equals: " + tree1.equals(tree3));

		// copy
		System.out.println("copy");
		BinarySearchTree<Integer> tree1copy = tree1.copy();
		System.out.println("tree1copy and tree1 compareStructure: " + tree1.compareStructure(tree1copy));
		System.out.println("tree1copy and tree1 equals: " + tree1.equals(tree1copy));

		// mirror
		System.out.println("mirror");
		BinarySearchTree<Integer> tree4 = new BinarySearchTree<>();
		tree4.insert(100);
		tree4.insert(50);
		tree4.insert(150);
		tree4.insert(40);
		tree4.insert(45);
		BinarySearchTree<Integer> tree4Mirror = tree4.mirror();
		BinarySearchTree<Integer> tree4Mirror2 = tree4Mirror.mirror();
		System.out.println("mirror: " + tree4Mirror2.equals(tree4));

		// isMirror
		System.out.println("isMirror");
		boolean r1 = tree4Mirror.isMirror(tree4);
		boolean r2 = tree4.isMirror(tree4Mirror);
		System.out.println("isMirror tree4 and tree4Mirror: " + r1);
		System.out.println("isMirror tree4 and tree4Mirror: " + r2);

		//rotate, printLevel
		System.out.println("rotateRight");
		tree4.rotateRight(100);
		tree4.printLevels();
		System.out.println("rotateLeft");
		tree4.rotateLeft(50);
		tree4.printLevels();
	}
}
