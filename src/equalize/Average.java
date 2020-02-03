package equalize;

import java.awt.Color;
import java.awt.image.BufferedImage;

import src.ImageProcess;

/**
* The Average class takes an average of two inputted images and outputs the average.
*
* @author  Anuj Gajjar
* @version 1.0
* @since   Summer 2019
*/
public class Average extends ImageProcess {
	// Store the first image
	private BufferedImage img1;
	
	// Store the second image
	private BufferedImage img2;
	
	// Store the output image
	private BufferedImage finl;
	
	// Store the width and height
	private int w;
	private int h;
	
	/**
	 * Construct an instance of the Average class.
	 */
	public Average() {}
	
	/**
	 * Construct an instance of the Average class with two inputted images, and compute 
	 * the pixel-wise average of the two images.
	 * 
	 * @param i the first input BufferedImage
	 * @param j the second input BufferedImage
	 * @return void
	 */
	public Average(BufferedImage i, BufferedImage j) {
		img1 = i;
		img2 = j;
		w = i.getWidth();
		h = i.getHeight();
		
		finl = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
		Operation();
	}
	
	/**
	 * Conduct the pixel-wise average operation on two inputted BufferedImages;
	 * 
	 * @param img1 the first input BufferedImage
	 * @param img2 the second input BufferedImage
	 * @return the output BufferedImage
	 */
	public BufferedImage Operation(BufferedImage img1, BufferedImage img2) {
		for(int i = 0; i < w; i++) {
			for(int j = 0; j < h; j++) {
				Color color1 = new Color(img1.getRGB(i, j));
				Color color2 = new Color(img2.getRGB(i, j));
			
				int r = (color1.getRed() + color2.getRed())/2;
				int g = (color1.getGreen() + color2.getGreen())/2;
				int b = (color1.getBlue() + color2.getBlue())/2;
				
				Color avgcolor = new Color(r, g, b);
				
				finl.setRGB(i, j, avgcolor.getRGB());
			}
		}
		
		return finl;
	}
	
	/**
	 * Conduct the pixel-wise average operation on the two inputted BufferedImages;
	 * 
	 * @return the output BufferedImage
	 */
	public BufferedImage Operation() {
		
		
		for(int i = 0; i < w; i++) {
			for(int j = 0; j < h; j++) {
				Color color1 = new Color(img1.getRGB(i, j));
				Color color2 = new Color(img2.getRGB(i, j));
			
				int r = (color1.getRed() + color2.getRed())/2;
				int g = (color1.getGreen() + color2.getGreen())/2;
				int b = (color1.getBlue() + color2.getBlue())/2;
								
				Color avgcolor = new Color(r, g, b);
				
				finl.setRGB(i, j, avgcolor.getRGB());
			}
		}
		
		return finl;
	}
	
	/**
	 * Returns the pixel-wise average image of the two inputted images.
	 * 
	 * @return the output BufferedImage
	 */
	public BufferedImage getOutput() {
		return finl;
	}
	
	
	
}
