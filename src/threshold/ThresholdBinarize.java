package threshold;

import java.awt.Color;
import java.awt.image.BufferedImage;
import src.ImageProcess;

/**
 * Class that conducts a threshold and binarizing operation of an image
 * 
 * @author Anuj Gajjar
 * @version 1.0
 * @since Summer 2019
 */
public class ThresholdBinarize extends ImageProcess {
	
	/**
	 * Constructs an instance of the ThresholdBinarize class. 
	 * Conducts a threshold with the average value of an image.
	 * 
	 * @param in The input BufferedImage
	 */
	public ThresholdBinarize(BufferedImage in) {
		setFields(in);
		Operation();
	}
	
	/**
	 * Constructs an instance of the ThresholdBinarize class. 
	 * Conducts a threshold with a specified value.
	 * 
	 * @param in The input BufferedImage
	 * @param val The value to conduct thresholding with
	 */
	public ThresholdBinarize(BufferedImage in, int val) {
		setFields(in);
		Operation(val);
	}
	
	/**
	 * Constructs an instance of the ThresholdBinarize class. 
	 * Conducts a threshold with a specified value without complete binarization.
	 * 
	 * @param in The input BufferedImage
	 * @param val The value to conduct thresholding with
	 * @param single Complete binarazation occurs if false, other partial binarization occurs if true
	 */
	public ThresholdBinarize(BufferedImage in, int val, boolean single) {
		setFields(in);
		OperationSingle(val);
	}
		
	/**
	 * The ThresholdBinarize operation.
	 * Conducts a threshold on the average value of the image.
	 * 
	 * @return The output BufferedImage
	 */
	public BufferedImage Operation() {
		BufferedImage imageout = new BufferedImage(w,h,BufferedImage.TYPE_INT_RGB);
		
		double avg = 0.0;
		for(int i = 0; i < w; i++) {
			for(int j = 0; j < h; j++) {
				avg += (0.2989*r[i][j] + 0.5870*g[i][j] + 0.1140*b[i][j]);
			}
		}
		avg = avg/(w*h);
		
		int threshold = (int) avg;
		
		for(int i = 0; i < w; i++) {
			for(int j = 0 ; j < h; j++) {
				if(r[i][j] > threshold)
					r[i][j] = 255;
				if(g[i][j] > threshold)
					g[i][j] = 255;
				if(b[i][j] > threshold)
					b[i][j] = 255;
				if(r[i][j] <= threshold)
					r[i][j] = 0;
				if(g[i][j] <= threshold)
					g[i][j] = 0;
				if(b[i][j] <= threshold)
					b[i][j] = 0;
				
				double grey_d = (0.2989*r[i][j]) + (0.5870*g[i][j]) + (0.1140*b[i][j]);
				int grey = (int) grey_d;
				
				Color c = new Color(grey, grey, grey);
				imageout.setRGB(i, j, c.getRGB());
			}
		}
				
		output = imageout;
		return output;
	}
	
	/**
	 * The ThresholdBinarize operation.
	 * Conducts a threshold on a specified value.
	 * 
	 * @param val The threshold value
	 * @return The output BufferedImage
	 */
	public BufferedImage Operation(int val) {
		BufferedImage imageout = new BufferedImage(w,h,BufferedImage.TYPE_INT_RGB);
		int threshold = val;
		
		for(int i = 0; i < w; i++) {
			for(int j = 0 ; j < h; j++) {
				if(r[i][j] > threshold)
					r[i][j] = 255;
				if(g[i][j] > threshold)
					g[i][j] = 255;
				if(b[i][j] > threshold)
					b[i][j] = 255;
				if(r[i][j] <= threshold)
					r[i][j] = 0;
				if(g[i][j] <= threshold)
					g[i][j] = 0;
				if(b[i][j] <= threshold)
					b[i][j] = 0;
				
				double grey_d = (0.2989*r[i][j]) + (0.5870*g[i][j]) + (0.1140*b[i][j]);
				int grey = (int) grey_d;
				
				Color c = new Color(grey, grey, grey);
				imageout.setRGB(i, j, c.getRGB());
			}
		}
				
		output = imageout;
		return output;
	}
	
	/**
	 * The ThresholdBinarize operation.
	 * Conducts a threshold on a specified value with partial binarization.
	 * 
	 * @param val The threshold value
	 * @return The output BufferedImage
	 */
	public BufferedImage OperationSingle(int val) {
		BufferedImage imageout = new BufferedImage(w,h,BufferedImage.TYPE_INT_RGB);
				
		int threshold = val;
		
		for(int i = 0; i < w; i++) {
			for(int j = 0 ; j < h; j++) {
				if(r[i][j] <= threshold)
					r[i][j] = 0;
				if(g[i][j] <= threshold)
					g[i][j] = 0;
				if(b[i][j] <= threshold)
					b[i][j] = 0;
				
				double grey_d = (0.2989*r[i][j]) + (0.5870*g[i][j]) + (0.1140*b[i][j]);
				int grey = (int) grey_d;
				
				Color c = new Color(grey, grey, grey);
				imageout.setRGB(i, j, c.getRGB());
			}
		}
				
		output = imageout;
		return output;
	}	

}
