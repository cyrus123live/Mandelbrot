import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Body {
	
	public JFrame f;
	public JPanel mousePanel;
	public JPanel graphicsPanel; 
	public final int WIDTH = 1000;
	public final int HEIGHT = 1000;
	double minX = -2;
	double maxX = 1;
	double minY = -1.5;
	double maxY = 1.5;
	public int zooms = 0;
	public double zoomFactor = 1;
	public int colorMode = 0;
	private int numColorModes = 7;

	public Body () {
		
		// mouseListener
		mousePanel = new JPanel();
	    MouseHandler Handler = new MouseHandler(this);
	    mousePanel.addMouseListener(Handler);
	    
	    // graphicsHandlerThing
	    graphicsPanel = new GraphicsHandler(this);
	    
	    // Main JFrame
	    f = new JFrame ("Mandelbrot Zooming Tool");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	    // Tie it Together with a bow
		f.getContentPane().add(graphicsPanel);
	    f.setSize(WIDTH, HEIGHT);
	    f.setLocation(-7, 0);
	    f.setVisible(true);
	    f.add(mousePanel);
	    
	    runMandelbrot();
	    
	}
	
	public void runMandelbrot() {
		
		((GraphicsHandler) graphicsPanel).findMandelbrot(minX, maxX, minY, maxY);
		((GraphicsHandler) graphicsPanel).createImage();
		graphicsPanel.repaint();
		
	}
	
	public void mouseClicked(int x, int y) {
		
		// if save button pressed
		if (x <= 995 && x >= 875 && y <= 35 && y >= 5) {
			
			try {
				
				((GraphicsHandler)graphicsPanel).saveMandelbrot(minX, maxX, minY, maxY);
				JOptionPane.showMessageDialog(null, "Image saved");
				
			} catch (Exception e){
				JOptionPane.showMessageDialog(null, "Image was unable to be saved");
			}
			
		// reset zoom
		} else if (x <= 995 && x >= 875 && y <= 75 && y >= 45) {
			
			minX = -2;
			maxX = 1;
			minY = -1.5;
			maxY = 1.5;
			zooms = 0;
			zoomFactor = 1;
			
			((GraphicsHandler) graphicsPanel).findMandelbrot(minX, maxX, minY, maxY);
			((GraphicsHandler) graphicsPanel).createImage();
			graphicsPanel.repaint();
			
		// toggle colour mode
		} else if (x <= 995 && x >= 875 && y <= 105 && y >= 85) {
			
			colorMode++;
			if (colorMode >= numColorModes) {
				colorMode = 0;
			}
			
			//((GraphicsHandler) graphicsPanel).findMandelbrot(minX, maxX, minY, maxY);
			((GraphicsHandler) graphicsPanel).createImage();
			graphicsPanel.repaint();
		
		// zoom in
		} else {
			
			zooms++;
			
			double xFactor = (double)WIDTH/(Math.abs(minX - maxX));
			double yFactor = (double)HEIGHT/(Math.abs(minY - maxY));
			
			double xClicked = x / xFactor + minX;
			double yClicked = y / yFactor + minY;
			
			double distance = Math.pow(0.25, zooms);
			
			minX = xClicked - distance;
			maxX = xClicked + distance;
			minY = yClicked - distance;
			maxY = yClicked + distance;
			
			zoomFactor = 3 / (Math.abs(minX - maxX));
			
			((GraphicsHandler) graphicsPanel).findMandelbrot(minX, maxX, minY, maxY);
			((GraphicsHandler) graphicsPanel).createImage();
			graphicsPanel.repaint();
			
		}
		
	}
	
	public void pan(int x, int y) {
		
		double xFactor = (double)WIDTH/(Math.abs(minX - maxX));
		double yFactor = (double)HEIGHT/(Math.abs(minY - maxY));
		
		double xClicked = x / xFactor + minX;
		double yClicked = y / yFactor + minY;
		
		// find center of current screen and compare to current center, in complex number system
		
		double differenceX = xClicked - (minX+maxX)/2;
		double differenceY = yClicked - (minY+maxY)/2;
		
		// add difference to both min and max coordinates
		minX = minX + differenceX;
		maxX = maxX + differenceX;
		minY = minY + differenceY;
		maxY = maxY + differenceY;
		
		((GraphicsHandler) graphicsPanel).findMandelbrot(minX, maxX, minY, maxY);
		((GraphicsHandler) graphicsPanel).createImage();
		graphicsPanel.repaint();
		
	}
	
}
