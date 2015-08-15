import javafx.scene.Parent;

/* SplayTree.java */

/**
 * SplayTree implements a Dictionary as a binary tree that is kept roughly
 * balanced with splay tree operations. Multiple entries with the same key are
 * permitted.
 *
 * DO NOT CHANGE ANY PROTOTYPES IN THIS FILE.
 *
 * @author Deepak Warrier and Jonathan Shewchuk
 **/
public class SplayTree<K extends Comparable<K>, V> {
	
	public static void main(String[] args) {
		SplayTree<Integer, Integer> tree = new SplayTree<Integer, Integer>();
		
	}

	/**
	 * size is the number of items stored in the dictionary. myRoot is the
	 * BinaryTreeNode that serves as root of the tree. If there are no items,
	 * size is zero and root is null.
	 **/
	private int size;
	private TreeNode myRoot;

	/**
	 * Construct an empty splay tree.
	 **/
	public SplayTree() {
		makeEmpty();
	}

	/**
	 * makeEmpty() removes all the entries from the dictionary.
	 */
	public void makeEmpty() {
		size = 0;
		myRoot = null;
	}

	/**
	 * size() returns the number of entries stored in the dictionary.
	 *
	 * @return the number of entries stored in the dictionary.
	 **/
	public int size() {
		return size;
	}

	/**
	 * isEmpty() tests if the dictionary is empty.
	 *
	 * @return true if the dictionary has no entries; false otherwise.
	 **/
	public boolean isEmpty() {
		return size == 0;
	}

	/**
	 * rotateRight() rotates a node up through its parent. It works only if
	 * "node" is a left child of its parent.
	 *
	 *
	 * @param node
	 *            the node to rotate up through its parent.
	 **/
	private void rotateRight(TreeNode node) {
		if (node == null || node.parent == null || node.parent.myLeft != node) {
			throw new IllegalArgumentException("Illegal call to rotateRight().");
		}

		TreeNode exParent = node.parent;
		TreeNode subtreeParent = exParent.parent;

		// Move "node"'s right subtree to its former parent.
		exParent.myLeft = node.myRight;
		if (node.myRight != null) {
			node.myRight.parent = exParent;
		}

		// Make exParent become a child of "node".
		node.myRight = exParent;
		exParent.parent = node;

		// Make "node" become a child of exParent's former parent.
		node.parent = subtreeParent;
		if (subtreeParent == null) {
			myRoot = node;
		} else if (subtreeParent.myRight == exParent) {
			subtreeParent.myRight = node;
		} else {
			subtreeParent.myLeft = node;
		}
	}

	/**
	 * rotateLeft() rotates a node up through its parent. It works only if
	 * "node" is a right child of its parent.
	 *
	 *
	 * @param node
	 *            the node to rotate up through its parent.
	 **/
	private void rotateLeft(TreeNode node) {
		if (node == null || node.parent == null || node.parent.myRight != node) {
			throw new IllegalArgumentException("Illegal call to rotateLeft().");
		}

		TreeNode exParent = node.parent;
		TreeNode subtreeParent = exParent.parent;

		// Move "node"'s left subtree to its former parent.
		exParent.myRight = node.myLeft;
		if (node.myLeft != null) {
			node.myLeft.parent = exParent;
		}

		// Make exParent become a child of "node".
		node.myLeft = exParent;
		exParent.parent = node;

		// Make "node" become a child of exParent's former parent.
		node.parent = subtreeParent;
		if (subtreeParent == null) {
			myRoot = node;
		} else if (subtreeParent.myRight == exParent) {
			subtreeParent.myRight = node;
		} else {
			subtreeParent.myLeft = node;
		}
	}

	/**
	 * zig() rotates "node" up through its parent. (Note that this may entail
	 * either a rotation right or a rotation left.)
	 *
	 * @param node
	 *            the node to splay one step up the tree.
	 **/
	private void zig(TreeNode node) {
		if (node.parent.myLeft == node) {
			rotateRight(node);
		} else {
			rotateLeft(node);
		}
	}

	/**
	 * zigZag() performs a zig-zag operation, thereby splaying "node" two steps
	 * closer to the root of the tree.
	 *
	 * @param node
	 *            the node to splay two steps up the tree.
	 **/
	private void zigZag(TreeNode node) {
		if (node.parent.myRight == node) {
			rotateLeft(node);
			rotateRight(node);
		} else {
			rotateRight(node);
			rotateLeft(node);
		}
	}

	/**
	 * zigZig() performs a zig-zig operation, thereby splaying "node" two steps
	 * closer to the root of the tree. Unlike a zig-zag operation, a zig-zig
	 * operation rotates "node"'s parent up through its grandparent first, then
	 * rotates "node" up through its parent.
	 *
	 * @param node
	 *            the node to splay two steps up the tree.
	 **/
	private void zigZig(TreeNode node) {
		if (node.parent.myLeft == node) {
			rotateRight(node);
			rotateRight(node);
		} else {
			rotateLeft(node);
			rotateLeft(node);
		}
	}

	/**
	 * splayNode() splays "node" to the root of the tree with a sequence of
	 * zig-zag, zig-zig, and zig operations.
	 *
	 * @param node
	 *            the node to splay to the root.
	 **/
	private void splayNode(TreeNode node) {
		while (node != null && node != myRoot) {
			if (node.parent == myRoot) {
				zig(node);
			} else if ((node.parent.myRight == node && node.parent.parent.myLeft == node.parent)
					&& (node.parent.myLeft == node && node.parent.parent.myRight == node.parent)) {
				zigZag(node);
			} else {
				zigZig(node);
			}
		}
	}

	/**
	 * put() constructs and inserts a new (key, value) pair, into the Splay Tree
	 * Multiple entries with the same key (or even the same key and value) can
	 * coexist in the dictionary.
	 *
	 * @param key
	 *            the key by which the entry can be retrieved. Must be of a
	 *            class that implements java.lang.Comparable.
	 * @param value
	 *            an arbitrary object associated with the key.
	 * @return an Entry object referencing the key and value.
	 **/
	public void put(K key, V value) {
		if (myRoot == null) {
			myRoot = new TreeNode(key, value);
		} else {
			putHelper(key, value, myRoot);
		}
		size++;
	}

	/**
	 * insertHelper() recursively does the work of inserting a new Entry object
	 * into the dictionary, including calling splayNode() on the new node.
	 *
	 * @param key
	 *            the key by which the entry can be retrieved.
	 * @param value
	 *            the value that the key is associated with
	 * @param node
	 *            the root of a subtree in which the new entry will be inserted.
	 **/
	private void putHelper(K key, V value, TreeNode node) {
		if (key.compareTo(node.key) <= 0) {
			if (node.myLeft == null) {
				node.myLeft = new TreeNode(key, value, node);
				splayNode(node.myLeft);
			} else {
				putHelper(key, value, node.myLeft);
			}
		} else {
			if (node.myRight == null) {
				node.myRight = new TreeNode(key, value, node);
				splayNode(node.myRight);
			} else {
				putHelper(key, value, node.myRight);
			}
		}
	}

	/**
	 * get() searches for a node with the specified key. If such a node is
	 * found, it returns the value; otherwise, it returns null. If more than one
	 * node has the key, one of them is chosen arbitrarily and returned.
	 *
	 * @param key
	 *            the search key. Must be of a class that implements
	 *            java.lang.Comparable.
	 * @return an Entry referencing the key and an associated value, or null if
	 *         no entry contains the specified key.
	 **/
	public V get(K key) {
		TreeNode node = getHelper(key, myRoot);
		if (node == null) {
			return null;
		} else {
			return node.value;
		}
	}

	/**
	 * Search for a node with the specified key, starting from "node". If a
	 * matching key is found (meaning that key1.compareTo(key2) == 0), return a
	 * node containing that key. Otherwise, return null.
	 *
	 * Be sure this method returns null if node == null.
	 **/

	private TreeNode getHelper(K key, TreeNode node) {
		if (node == null) {
			return null;
		}

		if (key.compareTo(node.key) < 0) { // "key" < "node"'s key
			if (node.myLeft == null) {
				splayNode(node); // Splay the last node visited to the root.
				return null;
			}
			return getHelper(key, node.myLeft);
		} else if (key.compareTo(node.key) > 0) { // "key" > "node"'s key
			if (node.myRight == null) {
				splayNode(node); // Splay the last node visited to the root.
				return null;
			}
			return getHelper(key, node.myRight);
		} else { // "key" == "node"'s key
			splayNode(node); // Splay the found node to the root.
			return node;
		}
	}

	/**
	 * Convert the tree into a string.
	 **/

	public String toString() {
		if (myRoot == null) {
			return "";
		} else {
			return myRoot.toString();
		}
	}
	

	/**
	 * TreeNode represents a node in a binary tree.
	 *
	 **/
	private class TreeNode {

		/**
		 * entry is a (key, value) pair stored in this node. parent is the
		 * parent of this node. myLeft and myRight are the children of this
		 * node.
		 **/
		private K key;
		private V value;
		private TreeNode parent;
		private TreeNode myLeft, myRight;

		/**
		 * Construct a BinaryTreeNode with a specified entry; parent and
		 * children are null.
		 **/
		public TreeNode(K key, V value) {
			this(key, value, null, null, null);
		}

		/**
		 * Construct a TreeNode with a specified key, value and parent; children
		 * are null.
		 **/
		public TreeNode(K key, V value, TreeNode parent) {
			this(key, value, parent, null, null);
		}

		/**
		 * Construct a TreeNode, specifying all five fields.
		 **/
		public TreeNode(K key, V value, TreeNode parent, TreeNode left, TreeNode right) {
			this.key = key;
			this.value = value;
			this.parent = parent;
			myLeft = left;
			myRight = right;
		}

		/**
		 * Express a BinaryTreeNode as a String.
		 *
		 * @return a String representing the BinaryTreeNode.
		 **/
		public String toString() {
			String s = "";

			if (myLeft != null) {
				s = "(" + myLeft.toString() + ")";
			}
			s = s + key.toString() + value.toString();
			if (myRight != null) {
				s = s + "(" + myRight.toString() + ")";
			}
			return s;
		}
	}

}
