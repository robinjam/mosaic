package net.robinjam.mosaic;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

/**
 * Displays a single quadtree node as a block of colour, and allows the children of this node to be revealed by mousing over it.
 * 
 * @author robinjam
 */
public class QuadtreePanel extends JPanel {
	
	private static final long serialVersionUID = -6176001515501740268L;
	
	private final QuadtreeNode node;
	private final MouseListener mouseListener = new MouseAdapter() {
		
		@Override
		public void mouseEntered(MouseEvent e) {
			if (getComponentCount() == 0 && !node.isLeaf()) {
				for (QuadtreeNode q : node.children) {
					add(new QuadtreePanel(q));
				}
				revalidate();
			}
		}
		
	};
	
	public QuadtreePanel(QuadtreeNode quadrant) {
		this.node = quadrant;
		addMouseListener(mouseListener);
		setLayout(new GridLayout(2, 2));
		setBackground(new Color(quadrant.colour));
	}
	
}
