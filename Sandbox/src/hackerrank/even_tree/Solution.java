package hackerrank.even_tree;

import java.util.*;

public class Solution {

	public static void main(String[] args) {

		Solution solution = new Solution();
		solution.run();

	}

	public void run() {
		Scanner in = new Scanner(System.in);
		int n = in.nextInt();
		int m = in.nextInt();
		Tree tree = new Tree(new Node(1));
		tree.getRoot().setId(1);

		for (int i = 0; i < m; i++) {
			int id = in.nextInt();
			Node node = new Node(id);

			int parent = in.nextInt();
			tree.addNode(node, parent);
		}

		Node currentNode = tree.getRoot();
		int edgesToRemove = getNumberOfEdgesToBeRemoved(currentNode);
		System.out.println(edgesToRemove);
	}

	public int getNumberOfEdgesToBeRemoved(Node currentNode) {
		int number = 0;
		if (!currentNode.hasChildren()) {
			return 0;
		} else {
			for (Node child:currentNode.getChildren()) {
				if (canRemoveEdge(child)) {
					number++;
				}
				number += getNumberOfEdgesToBeRemoved(child);
			}
		}
		return number;
	}

	private boolean canRemoveEdge(Node child) {
		if (getSubTreeSize(child) %2 == 0) {
			return true;
		}
		return false;
	}

	private int getSubTreeSize(Node curentNode) {
		int size = 1;
		size += getNumberChildren(curentNode);
		return size;
	}

	private int getNumberChildren(Node curentNode) {
		int size = 0;
		if (!curentNode.hasChildren())
			return 0;
		else {
			size += curentNode.getChildren().size();
			for (Node child:curentNode.getChildren()) {
				size+=getNumberChildren(child);
			}
		}
		return size;
	}

	public class Tree {

		Node root;

		public Tree(Node root) {
			this.root = root;
		}

		public Node getRoot() {
			return root;
		}

		public void addNode(Node node, int parentID) {
			Node parent = getNode(parentID);
			parent.addChild(node);
		}

		public Node getNode(int childID) {
			Node currentNode = root;
			return findChildNode(currentNode, childID);
		}

		private Node findChildNode (Node parent, int childID) {
			if (parent.getId() == childID) {
				return parent;
			}
			if (!parent.hasChildren()) {
				return null;
			} else {
				for (Node child:parent.getChildren()) {
					Node nodeToFind = findChildNode(child, childID);
					if (nodeToFind != null)
						return nodeToFind;
				}
			}

			return null;
		}

	}

	public class Node {

		int id;
		ArrayList<Node> children = new ArrayList<>();

		public Node(int id) {
			this.id = id;
		}

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public ArrayList<Node> getChildren() {
			return children;
		}

		public void addChild(Node child) {
			this.children.add(child);
		}

		public boolean hasChildren() {
			return children.size() != 0;
		}

		public boolean hasChild(int childID) {
			for (Node child:children) {
				if (child.getId() == childID)
					return true;
			}
			return false;
		}

		public Node getChildById(int childId) {
			for (Node child:children) {
				if (child.getId() == childId) {
					return child;
				}
			}
			return null;
		}

		public String toString() {
			return Integer.toString(id);
		}
	}

}