package net.robinjam.mosaic;

import java.awt.Color;
import java.awt.image.BufferedImage;

/**
 * A node in a quadtree consisting of a colour and an array of child nodes (if the node is not a leaf). The colour is determined by the colours of the children.
 * 
 * @author robinjam
 */
public class QuadtreeNode {
	
	/**
	 * The children of this node. Index 0 = top-left, 1 = top-right, 2 = bottom-left, 3 = bottom-right.
	 */
	public final QuadtreeNode[] children;
	
	/**
	 * The RGB value of the colour of this node.
	 */
	public final int colour;
	
	/**
	 * Creates a new leaf node with the given colour.
	 * 
	 * @param colour The RGB value for the colour of the node.
	 */
	private QuadtreeNode(int colour) {
		this.colour = colour;
		this.children = null;
	}
	
	/**
	 * Creates a new non-leaf node with the given children. The colour of this node is determined by its children.
	 * 
	 * @param children The children under this node.
	 */
	private QuadtreeNode(QuadtreeNode[] children) {
		this.children = children;
		this.colour = averageColour();
	}
	
	/**
	 * @return true if the node is a leaf (i.e. it has no children), false otherwise.
	 */
	public boolean isLeaf() {
		return children == null;
	}
	
	/**
	 * Creates a new quadtree based on the given image and returns its root node.
	 * 
	 * @param image The image from which the colours of the leaf nodes should be taken. This image must be square, and its edge sizes must be a power of two.
	 * @return The root node of the newly created quadtree.
	 */
	public static QuadtreeNode createQuadtree(BufferedImage image) {
		int size = image.getWidth();
		
		// Create the leaf nodes
		QuadtreeNode[][] nodes = new QuadtreeNode[size][size];
		for (int x = 0; x < size; x++) {
			for (int y = 0; y < size; y++) {
				nodes[x][y] = new QuadtreeNode(image.getRGB(x, y));
			}
		}
		
		// Move upwards to the root node
		while (nodes.length > 1) {
			QuadtreeNode[][] children = nodes;
			nodes = new QuadtreeNode[nodes.length / 2][nodes[0].length / 2];
			
			for (int x = 0; x < nodes.length; x++) {
				for (int y = 0; y < nodes[0].length; y++) {
					int a = x * 2, b = y * 2;
					nodes[x][y] = new QuadtreeNode(new QuadtreeNode[] { children[a][b], children[a + 1][b], children[a][b + 1], children[a + 1][b + 1] });
				}
			}
		}
		
		// Return the root node
		return nodes[0][0];
	}
	
	/**
	 * Calculates the average HSB colour for this node's children, and returns the RGB value of the result.
	 * 
	 * @return The RGB value of the average colour.
	 */
	private int averageColour() {
		float averageH = 0, averageS = 0, averageB = 0;
		for (QuadtreeNode node : children) {
			float[] rgb = new Color(node.colour).getRGBColorComponents(null);
			float[] hsb = Color.RGBtoHSB((int) (rgb[0] * 255), (int) (rgb[1] * 255), (int) (rgb[2] * 255), null);
			averageH += hsb[0];
			averageS += hsb[1];
			averageB += hsb[2];
		}
		return Color.getHSBColor(averageH / children.length, averageS / children.length, averageB / children.length).getRGB();
	}
	
}
