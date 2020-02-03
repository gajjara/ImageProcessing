package basic_processes;

//import java.awt.Color;
import java.awt.image.*;
import java.io.*;
import javax.imageio.ImageIO;

/**
* The ImageWrite class writes the data of an inputted BufferedImage to a filepath.
*
* @author  Anuj Gajjar
* @version 1.0
* @since   Summer 2019
*/
public class ImageWrite {
	private BufferedImage bufferedimg; // Holds the BufferedImage of the input image
	private int image_height; // Height of input image
	private int image_width; // Width of input image
	private int[][] image; // The image data
	//private int[][] red;
	//private int[][] green;
	//private int[][] blue;

	/**
	* Constructor that initiates an instance of the ImageWrite class.
	*
	* @return void
	*/
	public ImageWrite() {
		bufferedimg = null;
	}
	
	/**
	* Constructor that initiates an instance of the ImageWrite class with an inputted BufferedIMage
	*
	* @param img BufferedImage that is to be written
	* @return void
	*/
	public ImageWrite(BufferedImage img) {
		setImage(img);
		setBufferedImg();
	}
	
	/**
	* Constructor that initiates an instance of the ImageWrite class with an inputted BufferedImage and filepath. 
	* And writes the BufferedImage with the specified filepath.
	*
	* @param img BufferedImage that is to be written
	* @param filepath the file path to write the image to
	* @return void
	*/
	public ImageWrite(BufferedImage img, String filepath) {
		setImage(img);
		setBufferedImg();
		toFile(filepath);
	}
	
	/**
	* Constructor that initiates an instance of the ImageWrite class with inputted image data that is to be written.
	*
	* @param img 2-D array (of image data) that is to be written
	* @return void
	*/
	public ImageWrite(int[][] img) {
		setImage(img);
		setBufferedImg();
	}

	/**
	* Constructor that initiates an instance of the ImageWrite class with inputted image date and filepath. 
	* And writes the image data with the specified filepath.
	* 
	* @param img 2-D array (of image data) that is to be written
	* @param filepath the file path to write the image to
	* @return void
	*/
	public ImageWrite(int[][] img, String filepath) {
		setImage(img);
		setBufferedImg();
		toFile(filepath);
	}
	
	/**
	* Sets the stored image data, image width and height from an inputted BufferedImage.
	*
	* @param img BufferedImage that is to be written
	* @return void
	*/
	public void setImage(BufferedImage img) {
		image_width = img.getWidth();
		image_height = img.getHeight();
		image = new int[image_width][image_height];
		for(int i = 0; i < image_width; i++) {
			for(int j = 0; j < image_height; j++) {
				 image[i][j] = img.getRGB(i, j);
			}
		}
	}
	
	/**
	* Sets the stored image data, image width and height from inputted image data.
	*
	* @param img the image data that is to be written
	* @return void
	*/
	public void setImage(int[][] img) {
		image = img;
		image_width = image.length;
		image_height = image[0].length;
	}

	/**
	* Sets the buffered image from the inputted image data.
	*
	* @return void
	*/
	private void setBufferedImg() {
		bufferedimg = new BufferedImage(image_width, image_height, BufferedImage.TYPE_INT_RGB);
		for(int i = 0; i < image_width; i++) {
			for(int j = 0; j < image_height; j++) {
				bufferedimg.setRGB(i, j, toRGB(image[i][j]));
			}
		}

	}
	
	/**
	* Sets the buffered image from an inputted BufferedImage
	* 
	* @param image the BufferedImage that is to be written
	* @return void
	*/
	public void setBufferedImg(BufferedImage image) {
		bufferedimg = image;
	}
	
	/**
	* Converts an image data value to an RGB value mapped bitwise.
	* 
	* @param val the value to turn into an RGB value
	* @return void
	*/
	private int toRGB(int val) {
		return 0xff000000 | (val << 16) | (val << 8) | val;
	}
	
	/**
	* Writes the inputted BufferedImage to the specified filepath.
	* 
	* @param filepath the file path to write the image to
	* @return void
	*/
	public void toFile(String filepath) {
		// Set a new file
		File outputfile = new File(filepath);
		try {
			// Write the file using ImageIO
			if(!filepath.contains("png")) {
				ImageIO.write(bufferedimg, "png", outputfile);
			}
			else {
				throw new IOException("Invalid filepath, must use .png format");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	* Returns the written BufferedImage.
	* 
	* @return the stored BufferedImage
	*/
	public BufferedImage getBufferedImg() {
		return bufferedimg;
	}

	/*
	public ImageWrite(int[][] r, int[][] g, int[][] b) {
		setRGBImage(r, g, b);
		setRGBBufferedImg();
	}
	*/
	
	/*
	public void setRGBImage(int[][] r, int[][] g, int[][] b) {
		image_width = r.length;
		image_height = r[0].length;
		
		image = new int[image_width][image_height];
		
		red = r;
		green = g;
		blue = b;
		
		for(int i = 0; i < image_width; i++) {
			for(int j = 0; j < image_width; j++) {
				image[i][j] = 
						RGBtoVal(red[i][j], green[i][j], blue[i][j]);
			}
		}
		
	}
	*/
	
	/*
	private void setRGBBufferedImg() {
		bufferedimg = new BufferedImage(image_width, image_height, BufferedImage.TYPE_INT_RGB);
		for(int i = 0; i < image_width; i++) {
			for(int j = 0; j < image_height; j++) {
				bufferedimg.setRGB(i, j, image[i][j]);
			}
		}
	}
	*/
	
	/*
	private int RGBtoVal(int r, int g, int b) {
		Color c = new Color(r, g, b);
		return c.getRGB();
	}
	*/

}
