import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

// todo: make square that shows where you are pointing

public class GraphicsHandler extends JPanel {

	Body b;
	public ArrayList<Point> array = new ArrayList<Point>();
	public ArrayList<Point> bigArray = new ArrayList<Point>();
	public int maxIterations = 500;
	BufferedImage img;
	
	public GraphicsHandler (Body b) {
		this.b = b;
	}
	
	public void findMandelbrot(double minX, double maxX, double minY, double maxY) {
		
		double incrementX = (Math.abs(minX - maxX))/(double)b.WIDTH;
		double incrementY = (Math.abs(minY - maxY))/(double)b.HEIGHT;
		
		double xFactor = (double)b.WIDTH/(Math.abs(minX - maxX));
		double yFactor = (double)b.HEIGHT/(Math.abs(minY - maxY));
		
		for (double x = minX; x <= maxX; x += incrementX) {
			for (double y = minY; y <= maxY; y+= incrementY) {
				double a = x;
				double b = y;
				double n = 0;
				while (n < maxIterations) {
					
					double aa = a*a - b*b;
					double bb = 2 * a * b;
					
					a = aa + x;
					b = bb + y;
					
					if (Math.abs(a + b) > 16) {
						break;
					}
					
					n++;
				}
				
				Point p = new Point();
				
				p.x = (int)((Math.abs(minX) + x)*xFactor);
				if (minX > 0 && maxX > 0) {
					p.x = (int)((x - minX)*xFactor);
				}
				p.y = (int)((Math.abs(minY) + y)*yFactor);
				if (minY > 0 && maxY > 0) {
					p.y = (int)((y - minY)*yFactor);
				}
				p.n = n;
				
				array.add(p);
				
			}
		}
		
	}
	
	public void createImage() {
		
		img = new BufferedImage(b.WIDTH, b.HEIGHT, BufferedImage.TYPE_INT_RGB);
		
		for (int i = 0; i < array.size(); i++) {
			Point p = array.get(i);
			
			if (b.colorMode == 0 || b.colorMode == 1) {
				if (b.colorMode == 0) {
					img.setRGB(p.x, p.y, (int)(255 - (p.n/maxIterations)*255) << 16 | (int)(255 - (p.n/maxIterations)*255) << 8 | (int)(255 - (p.n/maxIterations)*255));
				} else {
					img.setRGB(p.x, p.y, (int)((p.n/maxIterations)*255) << 16 | (int)((p.n/maxIterations)*255) << 8 | (int)((p.n/maxIterations)*255));
				}
			} else {
				
				float hue = findHue(p.n);
				int saturation = 1;
				int brightness = p.n == maxIterations? 0 : 1;
				
				Color pointColor = Color.getHSBColor(hue, saturation, brightness);
				
				img.setRGB(p.x, p.y, pointColor.getRGB());
			}
			
		}
	}
	
	public void paint(Graphics g) {
			
			g.drawImage(img, 0, 0, this);
			g.drawString(String.valueOf(b.zoomFactor) + "x    Zoom", 10, 10);
			
			// draws button to save image
			g.setColor(Color.gray);
			g.fillRect(b.WIDTH - 125, 5, 100, 30);
			g.setColor(Color.white);
			g.drawString("Save as Image", b.WIDTH - 115, 25);
			
			// draw button to reset zoom
			g.setColor(Color.gray);
			g.fillRect(b.WIDTH - 125, 45, 100, 30);
			g.setColor(Color.white);
			g.drawString("Reset Zoom", b.WIDTH - 110, 65);
			
			// draw colour button
			g.setColor(Color.gray);
			g.fillRect(b.WIDTH - 125, 85, 100, 30);
			g.setColor(Color.white);
			g.drawString("Toggle Colour", b.WIDTH - 116, 105);
	}
	
	public void saveMandelbrot(double minX, double maxX, double minY, double maxY) {
		
		double incrementX = (Math.abs(minX - maxX))/4000.0;
		double incrementY = (Math.abs(minY - maxY))/4000.0;
		
		double xFactor = 4000.0/(Math.abs(minX - maxX));
		double yFactor = 4000.0/(Math.abs(minY - maxY));
		
		for (double x = minX; x <= maxX; x += incrementX) {
			for (double y = minY; y <= maxY; y+= incrementY) {
				double a = x;
				double b = y;
				double n = 0;
				while (n < maxIterations) {
					
					double aa = a*a - b*b;
					double bb = 2 * a * b;
					
					a = aa + x;
					b = bb + y;
					
					if (Math.abs(a + b) > 16) {
						break;
					}
					
					n++;
				}
				
				Point p = new Point();
				
				p.x = (int)((Math.abs(minX) + x)*xFactor);
				if (minX > 0 && maxX > 0) {
					p.x = (int)((x - minX)*xFactor);
				}
				p.y = (int)((Math.abs(minY) + y)*yFactor);
				if (minY > 0 && maxY > 0) {
					p.y = (int)((y - minY)*yFactor);
				}
				p.n = n;
				
				bigArray.add(p);
				
			}
		}
		
		BufferedImage img = new BufferedImage(4000, 4000, BufferedImage.TYPE_INT_RGB);
		File f = null;
		
		for (int i = 0; i < bigArray.size(); i++) {
			Point p = bigArray.get(i);

			if (b.colorMode == 0 || b.colorMode == 1) {
				if (b.colorMode == 0) {
					img.setRGB(p.x, p.y, (int)(255 - (p.n/maxIterations)*255) << 16 | (int)(255 - (p.n/maxIterations)*255) << 8 | (int)(255 - (p.n/maxIterations)*255));
				} else {
					img.setRGB(p.x, p.y, (int)((p.n/maxIterations)*255) << 16 | (int)((p.n/maxIterations)*255) << 8 | (int)((p.n/maxIterations)*255));
				}
			} else {
				
				float hue = findHue(p.n);
				int saturation = 1;
				int brightness = p.n == maxIterations? 0 : 1;

				Color pointColor = Color.getHSBColor(hue, saturation, brightness);
				
				img.setRGB(p.x, p.y, pointColor.getRGB());
			}
			
		}
		
		String fileName = "C:\\Users\\cyrus\\Pictures\\Mandelbrot\\Mandelbrot" + fileReader() + ".png";
		
		try {
			f = new File(fileName);
			ImageIO.write(img, "png", f);
		} catch (IOException e) {
			System.out.println("Error: " + e);
		}
		
		incrementUID();
	}
	
	public float findHue(double n) {
		
		float hue = 0;
		
		switch(b.colorMode) {
		case 2: hue = (float) (1 - n / maxIterations);
		break;
		case 3: hue = (float) (n / maxIterations);
		break;
		case 4: hue = Math.abs((float)(0.25 - n / maxIterations));
		break;
		case 5: hue = Math.abs((float)(0.5 - n / maxIterations));
		break;
		case 6: hue = Math.abs((float)(0.75 - n / maxIterations));
		break;
		}
		
		return hue;
		
	}
	
	public String fileReader() {
		try {
			@SuppressWarnings("resource")
			BufferedReader in = new BufferedReader(new FileReader("C:\\Users\\cyrus\\Pictures\\Mandelbrot\\UID.txt"));
			return in.readLine();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "";
	}
	
	public void incrementUID() {
		String oldUID = fileReader();
		String newUID = "" + (Integer.parseInt(oldUID) + 1);
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter("C:\\Users\\cyrus\\Pictures\\Mandelbrot\\UID.txt"));
			out.write(newUID);
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
