package src;

import java.awt.image.BufferedImage;
import basic_processes.*;

/**
* The ImageProcess is an abstract class that holds basic structure for an image processing process.
*
* @author  Anuj Gajjar
* @version 1.0
* @since   Summer 2019
*/
public abstract class ImageProcess {
	/**
	 * The input image
	 */
	public BufferedImage input;
	
	/**
	 * The output image
	 */
	public BufferedImage output;
	
	/**
	 * The width and height
	 */
	public int w, h;
	
	/**
	 * The data storing the RGB data
	 */
	public int[][] r, g, b;
	
	/**
	 * Constructor for ImageProcess class.
	 * 
	 * @return void
	 */
	public ImageProcess() {
		input = null;
		output = null;
		w = 0;
		h = 0;
		r = null;
		g = null;
		b = null;
	}
	
	/**
	 * Constructor for ImageProcess class that takes an input BufferedImage.
	 * 
	 * @param in The input BufferedImage
	 * @return void
	 */
	public ImageProcess(BufferedImage in) {
		setFields(in);
		Operation();
	}
	
	/**
	 * Part of the constructor that sets the fields of the ImageProcess class.
	 * 
	 * @param in The input BufferedImage
	 */
	public void setFields(BufferedImage in) {
		input = in;
		w = input.getWidth();
		h = input.getHeight();
		output = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
		ImageRead read = new ImageRead(input);
		r = read.getRed();
		g = read.getGreen();
		b = read.getBlue();
	}
	
	/**
	 * The operation that is to be conducted.
	 * 
	 * @return abstract
	 */
	public abstract BufferedImage Operation();
	
	/**
	 * Conducts the operation specified.
	 * 
	 * @param in The input image
	 * @return The output BufferedImage
	 */
	public BufferedImage Operation(BufferedImage in) {
		if(input == null)
			setFields(in);
		
		output = Operation();
		return output;
	}
	
	/**
	 * Returns the output BufferedImage.
	 * 
	 * @return the output BufferedImage
	 */
	public BufferedImage getOutput() {
		return output;
	}
}
