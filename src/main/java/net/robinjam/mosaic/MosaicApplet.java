package net.robinjam.mosaic;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JApplet;

/**
 * The main applet class, containing the root element of the quadtree.
 * 
 * @author robinjam
 */
public class MosaicApplet extends JApplet {

	private static final long serialVersionUID = -9187375037458025560L;
	
	public MosaicApplet() throws IOException {
		BufferedImage image = ImageIO.read(QuadtreePanel.class.getResource("/flo.jpg"));
		QuadtreeNode root = QuadtreeNode.createQuadtree(image);
		add(new QuadtreePanel(root));
	}
	
}
