package prj3;

import java.util.Arrays;

class Node {

	private Node leftNode;
	private Node rightNode;
	private int size;
	private int[] point;
	private Node headAssoc;
	private int xMin;
	private int xMax;
	private int yMin;
	private int yMax;
	private int zMin;
	private int zMax;

	public Node(int[] pointInp) {
		size = 1;
		point = pointInp;
	}

	public Node() {

	}

	public Node getLeftNode() {
		return leftNode;
	}

	public Node getRightNode() {
		return rightNode;
	}

	public int getSize() {
		return size;
	}

	public int[] getPoint() {
		return point;
	}

	public void setAssoc(Node headOfAssoc) {
		headAssoc = headOfAssoc;
	}

	public void setLeftNode(Node theNode) {
		leftNode = theNode;
	}

	public void setRightNode(Node theNode) {
		rightNode = theNode;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public Node getAssoc() {
		return headAssoc;
	}

	public void setXMin(int x) {
		xMin = x;
	}

	public void setXMax(int x) {
		xMax = x;
	}

	public void setYMin(int x) {
		yMin = x;
	}

	public void setYMax(int x) {
		yMax = x;
	}

	public void setZMin(int x) {
		zMin = x;
	}

	public void setZMax(int x) {
		zMax = x;
	}

	public int getXMin() {
		return xMin;
	}

	public int getXMax() {
		return xMax;
	}

	public int getYMin() {
		return yMin;
	}

	public int getYMax() {
		return yMax;
	}

	public int getZMin() {
		return zMin;
	}

	public int getZMax() {
		return zMax;
	}
}

public class RangeTree {
	private Node headNode;
	private int numCounted;

	public RangeTree(int[][] points, int depth) {
		headNode = buildKDTree(points, depth);
		updateSubtreeSizes(headNode);
	}

	/*
	 * public Node buildKDTree(int[][] points, int depth) { if (points.length == 0)
	 * { return null; } if (points.length == 1) { Node node = new Node(points[0]);
	 * return node; } int axis = depth % 2; Node node = new Node(); if (axis == 0) {
	 * Arrays.sort(points, (a, b) -> Integer.compare(a[1], b[1]));
	 * node.setYMin(points[0][1]); node.setYMax(points[points.length - 1][1]);
	 * Arrays.sort(points, (a, b) -> Integer.compare(a[0], b[0]));
	 * node.setXMin(points[0][0]); node.setXMax(points[points.length - 1][0]); }
	 * else { Arrays.sort(points, (a, b) -> Integer.compare(a[0], b[0]));
	 * node.setXMin(points[0][0]); node.setXMax(points[points.length - 1][0]);
	 * Arrays.sort(points, (a, b) -> Integer.compare(a[1], b[1]));
	 * node.setYMin(points[0][1]); node.setYMax(points[points.length - 1][1]); } int
	 * mid = points.length / 2; int[][] leftPoints = new int[mid][3]; int[][]
	 * rightPoints = new int[points.length - mid][3]; for (int i = 0; i < mid; i++)
	 * { leftPoints[i] = points[i]; } for (int i = mid; i < points.length; i++) {
	 * rightPoints[i - mid] = points[i]; } node.setAssoc(buildRangeTree(points));
	 * updateSubtreeSizes(node.getAssoc()); node.setLeftNode(buildKDTree(leftPoints,
	 * depth+1)); node.setRightNode(buildKDTree(rightPoints, depth+1)); return node;
	 * } public Node buildRangeTree(int[][] points) { if (points.length == 0) {
	 * return null; } // sorting on z Arrays.sort(points, (a, b) ->
	 * Integer.compare(a[2], b[2])); int mid = points.length / 2; Node node = new
	 * Node(points[mid]); int[][] leftPoints = new int[mid][3]; int[][] rightPoints
	 * = new int[points.length - mid - 1][3]; for (int i = 0; i < mid; i++) {
	 * leftPoints[i] = points[i]; } for (int i = mid + 1; i < points.length; i++) {
	 * rightPoints[i - mid - 1] = points[i]; }
	 * node.setLeftNode(buildRangeTree(leftPoints));
	 * node.setRightNode(buildRangeTree(rightPoints)); return node; }
	 * 
	 * public int queryKDTree(int xMin, int xMax, int yMin, int yMax, int zMin, int
	 * zMax, Node node, int depth) { if (node == null) { return 0; }
	 * 
	 * if (//node.getLeftNode() == null && node.getRightNode() == null &&
	 * node.getPoint() != null && node.getXMin() >= xMin && node.getXMax() <= xMax
	 * && node.getYMin() >= yMin && node.getYMax() <= yMax) { return
	 * queryRangeTree(zMin, zMax, node); }
	 * 
	 * int axis = depth % 2; int count=0; // leaf node if (node.getPoint() != null
	 * && node.getPoint()[0] >= xMin && node.getPoint()[0] <= xMax &&
	 * node.getPoint()[1] >= yMin && node.getPoint()[1] <= yMax &&
	 * node.getPoint()[2] >= zMin && node.getPoint()[2] <= zMax){ count += 1; } else
	 * { if (node.getRightNode() != null && node.getLeftNode() != null &&
	 * node.getXMin() >= xMin && node.getXMax() <= xMax && node.getYMin() >= yMin &&
	 * node.getYMax() <= yMax) { return queryRangeTree(zMin, zMax, node.getAssoc());
	 * } }
	 * 
	 * // below is correct if (node.getLeftNode() != null &&
	 * node.getLeftNode().getXMax() >= xMin && node.getLeftNode().getXMin() <= xMax
	 * && node.getLeftNode().getYMax() >= yMin && node.getLeftNode().getYMin() <=
	 * yMax) { count += queryKDTree(xMin, xMax, yMin, yMax, zMin, zMax,
	 * node.getLeftNode(), depth+1); } if (node.getRightNode() != null &&
	 * node.getRightNode().getXMax() >= xMin && node.getRightNode().getXMin() <=
	 * xMax && node.getRightNode().getYMax() >= yMin &&
	 * node.getRightNode().getYMin() <= yMax) { count += queryKDTree(xMin, xMax,
	 * yMin, yMax, zMin, zMax, node.getRightNode(), depth+1); } return count; }
	 * 
	 * public int queryRangeTree(int zMin, int zMax, Node node) { if (node == null)
	 * { return 0; } int count = 0; if (node.getPoint()[2] >= zMin &&
	 * node.getPoint()[2] <= zMax) { count +=1; } if (node.getPoint()[2] >= zMin &&
	 * node.getLeftNode() != null) { count += queryRangeTree(zMin, zMax,
	 * node.getLeftNode()); } if (node.getPoint()[2] <= zMax && node.getRightNode()
	 * != null) { count += queryRangeTree(zMin, zMax, node.getRightNode()); } return
	 * count; }
	 */

	public Node buildKDTree(int[][] points, int depth) {
		int[][] Px = new int[points.length][];
		int[][] Py = new int[points.length][];
		int[][] Pz = new int[points.length][];
		for (int i = 0; i < points.length; i++) {
			Px[i] = points[i];
			Py[i] = points[i];
			Pz[i] = points[i];
		}
		Arrays.sort(Px, (a, b) -> Integer.compare(a[0], b[0]));
		Arrays.sort(Py, (a, b) -> Integer.compare(a[1], b[1]));
		Arrays.sort(Pz, (a, b) -> Integer.compare(a[2], b[2]));
		return buildKDTree(Px, Py, Pz, depth);
	}
	
	public Node buildKDTree(int[][] pointsX, int[][] pointsY, int[][] pointsZ, int depth) {
		int[][] Px = pointsX;
		int[][] Py = pointsY;
		int[][] Pz = pointsZ;
		if (Px.length == 0) {
			return null;
		}
		if (Py.length == 0) {
			return null;
		}
		if (Px.length == 1) {
			Node leaf = new Node(Px[0]);
			return leaf;
		}
		if (Py.length == 1) {
			Node leaf = new Node(Py[0]);
			return leaf;
		}
		int[][] PxLeft;
		int[][] PxRight;
		int[][] PyLeft;
		int[][] PyRight;
		int[][] PzLeft;
		int[][] PzRight;
		if (Px.length == 2) {
			PxLeft = new int[1][];
			PxRight = new int[1][];
			PyLeft = new int[1][];
			PyRight = new int[1][];
			PzLeft = new int[1][];
			PzRight = new int[1][];
		}
		else
		{
			PxLeft = new int[(Px.length / 2) + 1][];
			PxRight = new int[Px.length - (Px.length / 2) - 1][];
			PyLeft = new int[(Py.length / 2) + 1][];
			PyRight = new int[Py.length - (Py.length / 2) - 1][];
			PzLeft = new int[(Pz.length / 2) + 1][];
			PzRight = new int[Pz.length - (Pz.length / 2) - 1][];
		}
		int median;
		if (depth % 2 == 0) {
			median = Px[(Px.length - 1) / 2][0];
			for (int i = 0; i < Px.length; i++) {
				if (i < Px.length / 2) {
					PxLeft[i] = Px[i];
				}
				else {
					PxRight[i - Px.length / 2 - 1] = Px[i];
				}
			}
			int iter = 0;
			int iter1 = 0;
			for (int i = 0; i < Py.length; i++) {
				if (Py[i][0] <= median) {
					PyLeft[iter] = Py[i];
					iter++;
				}
				else {
					PyRight[iter1] = Py[i];
					iter1++;
				}
			}
			int iter2 = 0;
			int iter3 = 0;
			for (int i = 0; i < Pz.length; i++) {
				if (Pz[i][0] <= median) {
					PzLeft[iter2] = Pz[i];
					iter2++;
				}
				else {
					PzRight[iter3] = Pz[i];
					iter3++;
				}
			}
			Node headNode = new Node(Px[(Px.length - 1) / 2]);
			headNode.setAssoc(buildRangeTree(Pz));
			headNode.setLeftNode(buildKDTree(PxLeft, PyLeft, PzLeft, depth+1));
			headNode.setRightNode(buildKDTree(PxRight, PyRight, PzRight, depth+1));
			return headNode;
		}
		else {
			median = Py[(Py.length - 1) / 2][1];
			for (int i = 0; i < Py.length; i++) {
				if (i < (Py.length) / 2) {
					PyLeft[i] = Py[i];
				}
				else {
					PyRight[i - Py.length / 2 - 1] = Py[i];
				}
			}
			int iter = 0, iter1 = 0;
			for (int i = 0; i < Px.length; i++) {
				if (Px[i][1] <= median) {
					PxLeft[iter] = Px[i];
					iter++;
				}
				else {
					PxRight[iter1] = Px[i];
					iter1++;
				}
			}
			int iter2 = 0, iter3 = 0;
			for (int i = 0; i < Pz.length; i++) {
				if (Pz[i][1] <= median) {
					PzLeft[iter2] = Pz[i];
					iter2++;
				}
				else {
					PzRight[iter3] = Pz[i];
					iter3++;
				}
			}
			Node headNode = new Node(Py[(Py.length - 1) / 2]);
			headNode.setAssoc(buildRangeTree(Pz));
			headNode.setLeftNode(buildKDTree(PxLeft, PyLeft, PzLeft, depth+1));
			headNode.setRightNode(buildKDTree(PxRight, PyRight, PzRight, depth+1));
			return headNode;
		}
	}
	
	public Node buildRangeTree(int[][] points) {
		if (points.length == 0) {
			return null;
		}
		// sorting on z
		int mid = points.length / 2;
		Node node = new Node(points[mid]);
		int[][] leftPoints = new int[mid][3];
		int[][] rightPoints = new int[points.length - mid - 1][3];
		for (int i = 0; i < mid; i++) {
			leftPoints[i] = points[i];
		}
		for (int i = mid + 1; i < points.length; i++) {
			rightPoints[i - mid - 1] = points[i];
		}
		node.setLeftNode(buildRangeTree(leftPoints));
		node.setRightNode(buildRangeTree(rightPoints));
		return node;
	}
	
	public int queryKDTree(int xMin, int xMax, int yMin, int yMax, int zMin, int zMax, Node node, int depth) {
		if (node == null) {
			return 0;
		}
		int axis = depth % 2;
		int count=0;
		// leaf node
		if (node.getPoint() != null && node.getPoint()[0] >= xMin &&
		  node.getPoint()[0] <= xMax && node.getPoint()[1] >= yMin &&
		  node.getPoint()[1] <= yMax && node.getPoint()[2] >= zMin &&
		  node.getPoint()[2] <= zMax){ 
			  count += 1; 
		}
		else {
			if (node.getRightNode() != null && node.getLeftNode() != null && node.getXMin() >= xMin &&
					node.getXMax() <= xMax && node.getYMin() >= yMin && node.getYMax() <= yMax) {
				return queryRangeTree(zMin, zMax, node.getAssoc());
			}
		}
		// below is correct
		if (node.getLeftNode() != null && node.getLeftNode().getXMax() >= xMin && node.getLeftNode().getXMin() <= xMax &&
				node.getLeftNode().getYMax() >= yMin && node.getLeftNode().getYMin() <= yMax) {
			count += queryKDTree(xMin, xMax, yMin, yMax, zMin, zMax, node.getLeftNode(), depth+1);
		}
		if (node.getRightNode() != null && node.getRightNode().getXMax() >= xMin && node.getRightNode().getXMin() <= xMax &&
				node.getRightNode().getYMax() >= yMin && node.getRightNode().getYMin() <= yMax) {
			count += queryKDTree(xMin, xMax, yMin, yMax, zMin, zMax, node.getRightNode(), depth+1);
		}
		return count;
	}
	
	public int queryRangeTree(int zMin, int zMax, Node node) {
		if (node == null) {
			return 0;
		}
		int count = 0;
		if (node.getPoint()[2] >= zMin && node.getPoint()[2] <= zMax) {
			count +=1;
		}
		if (node.getPoint()[2] >= zMin && node.getLeftNode() != null) {
			count +=  queryRangeTree(zMin, zMax, node.getLeftNode());
		}
		if (node.getPoint()[2] <= zMax && node.getRightNode() != null) {
			count += queryRangeTree(zMin, zMax, node.getRightNode());
		}
		return count;
	}
	public int updateSubtreeSizes(Node node) {
		if (node == null) {
			return 0;
		}
		int leftSize = updateSubtreeSizes(node.getLeftNode());
		int rightSize = updateSubtreeSizes(node.getRightNode());
		node.setSize(leftSize + rightSize + 1);
		return node.getSize();
	}

	public Node getHeadNode() {
		return headNode;
	}
}
