package basic_processes;

import java.awt.Color;
import java.awt.image.*;
import java.io.*;
import javax.imageio.ImageIO;

/**
* The ImageRead class reads and stores the data of an inputted image from a filepath or BufferedImage.
*
* @author  Anuj Gajjar
* @version 1.0
* @since   Summer 2019
*/
public class ImageRead {
	private BufferedImage bufferedimg; // Holds the BufferedImage of the input image
	private int buffer_height; // Height of input image
	private int buffer_width;  // Width of input image
	private int[][] image; // Image data (if BW image)
	private int[][] red; // Red channel data
	private int[][] green; // Green channel data
	private int[][] blue; // Blue channel data
	private int[][] grayscale; // Gray scale data conversion of RGB image (if RGB image)
	
	/**
	* Constructor that initiates an instance of the ImageRead class.
	*
	* @return void
	*/
	public ImageRead() {
		bufferedimg = null;
	}
	
	/**
	* Constructor that initiates an instance of the ImageRead class with a specified filepath.
	*
	* @param filepath the file path of the image
	* @return void
	*/
	public ImageRead(String filepath) {
		setBufferedImg(filepath);
		setHeightAndWidth();
		setImage();
		setRedGreenBlue();
		setGrayScale();
	}
	
	/**
	* Constructor that initiates an instance of the ImageRead class with a specified BufferedImage.
	*
	* @param image the BufferedImage of the image
	* @return void
	*/
	public ImageRead(BufferedImage image) {
		bufferedimg = image;
		setHeightAndWidth();
		setImage();
		setRedGreenBlue();
		setGrayScale();
	}

	/**
	* Sets the stored BufferedImage of this class from a filepath.
	* 
	* @param filepath the file path of the image
	* @return void
	*/
	public void setBufferedImg(String filepath) {
		try {
			bufferedimg = ImageIO.read(new File(filepath));
		} catch(IOException e) {}	
	}
	
	/**
	* Sets the stored height and width of this class from a filepath.
	* 
	* @param filepath the file path of the image
	* @return void
	*/
	public void setHeightAndWidth() {
		buffer_height = bufferedimg.getHeight();
		buffer_width = bufferedimg.getWidth();
	}
	
	/**
	* Sets the stored image data of the inputed image.
	* 
	* @return void
	*/
	public void setImage() {
		image = new int[buffer_width][buffer_height];
		for(int i = 0; i < buffer_width; i++) {
			for(int j = 0; j < buffer_height; j++) {
				image[i][j] = bufferedimg.getRGB(i, j);
			}
		}
	}
	
	/**
	* Returns the stored BufferedImage.
	* 
	* @return the stored BufferedImage 
	*/
	public BufferedImage getBufferedImage() {
		return bufferedimg;
	}
	
	/**
	* Returns the stored image height.
	* 
	* @return the stored image height 
	*/
	public int getHeight() {
		return buffer_height;
	}
	
	/**
	* Returns the stored image width.
	* 
	* @return the stored image width 
	*/
	public int getWidth() {
		return buffer_width;
	}
	
	/**
	* Returns the stored image data.
	* 
	* @return the stored image data 
	*/
	public int[][] getImage() {
		return image;
	}
	
	/**
	* Sets the RGB data arrays.
	* 
	* @return void
	*/
	public void setRedGreenBlue() {
		red = new int[buffer_width][buffer_height];
		green = new int[buffer_width][buffer_height];
		blue = new int[buffer_width][buffer_height];
		
		for(int i = 0; i < buffer_width; i++) {
			for(int j = 0; j < buffer_height; j++) {
				Color c = new Color(bufferedimg.getRGB(i, j));				
				red[i][j] = c.getRed();
				green[i][j] = c.getGreen();
				blue[i][j] = c.getBlue();	
			}
		}
	}
	
	/**
	* Sets an RGB image to its grayscale component.
	* 
	* @return void
	*/
	public void setGrayScale() {
		grayscale = new int[buffer_width][buffer_height];
		for(int i = 0; i < buffer_width; i++) {
			for(int j = 0; j < buffer_height; j++) {
				grayscale[i][j] = calcGrayScale(image[i][j]);
			}
		}
	}
	
	/**
	* Calculates the grayscale value of an inputted RGB value.
	* 
	* @param rgb the integer storing RGB data
	* @return void
	*/
    public int calcGrayScale(int rgb) {
    	// Obtain RGB values
        int r = (rgb >> 16) & 0xff;
        int g = (rgb >> 8) & 0xff;
        int b = (rgb) & 0xff;

        // Convert to grayscale
        // from https://en.wikipedia.org/wiki/Grayscale, calculating luminance
        int gray = (int)(0.2126 * r + 0.7152 * g + 0.0722 * b);
        // could also be int gray = (r + g + b) / 3;
        
        // Return
        return gray;
    }
	
    /**
	* Returns the red-channel data.
	* 
	* @return the stored red-channel data 
	*/
	public int[][] getRed() {
		return red;
	}
	
	/**
	* Returns the green-channel data.
	* 
	* @return the stored green-channel data 
	*/
	public int[][] getGreen() {
		return green;
	}
	
	/**
	* Returns the blue-channel data.
	* 
	* @return the stored blue-channel data 
	*/
	public int[][] getBlue() {
		return blue;
	}
	
	/**
	* Returns the calculate grayscale data.
	* 
	* @return the stored grayscale data
	*/
	public int[][] getGrayScale() {
		return grayscale;
	}
}
