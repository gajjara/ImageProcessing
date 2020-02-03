package edgeDetection;

import java.awt.image.BufferedImage;
import basic_processes.*;

/**
* The EdgeDetection conducts edge detection on an image based on a specific edge detection algorithm
* Note: the Sobel algorithm is only implemented
*
* @author  Anuj Gajjar
* @version 1.0
* @since   Summer 2019
*/
public class EdgeDetection {
	
	// Hold the initial and final Buffered Images
	private BufferedImage init;
	private BufferedImage finl;
	
	/**
	* Constructor that initiates an instance of the Edge Detection class that automatically runs the
	* Sobel edge detection algorithm on an inputted BufferedImage.
	* 
	* @param input the BufferedImage to run edge detection on
	* @return void
	*/
	public EdgeDetection(BufferedImage input) {
		Sobel(input);
	}
	
	/**
	* Constructor that initiates an instance of the Edge Detection class that automatically runs the
	* Sobel edge detection algorithm on an image with a specified filepath. The output image is also written
	* to a specified filepath.
	* 
	* @param in_filepath the filepath of the inputted image
	* @param out_filepath the filepath of the outputted image
	* @return void
	*/
	public EdgeDetection(String in_filepath, String out_filepath) {
		Sobel(in_filepath, out_filepath);
	}
	
	/**
	* Constructor that initiates an instance of the Edge Detection class that automatically runs the
	* Sobel edge detection algorithm on an image with a specified filepath. The output image is also written
	* to a specified filepath.
	* 
	* @param in_filepath the filepath of the inputted image
	* @param out_filepath the filepath of the outputted image
	* @return void
	*/
	public EdgeDetection(String in_filepath, String algorithm, String out_filepath, boolean printData, boolean dispImage) {
		if(algorithm.equals("Sobel")) {
			Sobel(in_filepath, out_filepath, printData, dispImage);
		}
		else if(!algorithm.equals("Sobel")) {
			System.out.println("Algorithm not avalible or algorithm string is mispelled.");
			System.out.println("Algorithms avalible are: /'Sobel/'");
		}
	}
	
	/**
	* Runs the Sobel edge detection algorithm on the inputted image with a specified input BufferedImage.
	* 
	* @param input the inputtted BufferedImage
	* @return void
	*/
	private void Sobel(BufferedImage input) {
		ImageRead imgread = new ImageRead(input);  /*Read Image*/
		Sobel sobel = new Sobel(imgread.getGrayScale()); /*Run Sobel Algorithm*/
		int[][] sobel_img = sobel.Convolution(); /*Run Sobel Algorithm*/
		ImageWrite imgwrite = new ImageWrite(sobel_img);/*Write Edge Detected Image*/
		finl = imgwrite.getBufferedImg(); 
	}
	
	/**
	* Runs the Sobel edge detection algorithm on an image with a specified filepath. The output image is also written
	* to a specified filepath.
	* 
	* @param in_filepath the filepath of the inputted image
	* @param out_filepath the filepath of the outputted image
	* @return void
	*/
	private void Sobel(String in_filepath, String out_filepath) {
		ImageRead imgread = new ImageRead(in_filepath); /*Read Image*/
		init = imgread.getBufferedImage(); /*Generate BufferedImage*/
		Sobel sobel = new Sobel(imgread.getGrayScale()); /*Run Sobel Algorithm*/
		int[][] sobel_img = sobel.Convolution(); /*Get Edge Detected Image*/
		ImageWrite imgwrite = new ImageWrite(sobel_img, out_filepath); /*Write Edge Detected Image*/
		finl = imgwrite.getBufferedImg(); 
	}
	
	/**
	* Runs the Sobel edge detection algorithm on an image with a specified filepath. The output image is also written
	* to a specified filepath. This function has boolean flags for printing the output image data and displaying the image.
	* 
	* @param in_filepath the filepath of the inputted image
	* @param out_filepath the filepath of the outputted image
	* @param printData when true a 100x100 array of the output image is printed
	* @param dispImage the inital and final image is displayed if true
	* @return void
	*/
	private void Sobel(String in_filepath, String out_filepath, boolean printData, boolean dispImage) {
		ImageRead imgread = new ImageRead(in_filepath);		/*Read Image*/
		BufferedImage bufimg = imgread.getBufferedImage();	/*Generate BufferedImage*/	
		init = bufimg;
		int[][] image = imgread.getGrayScale();				/*Generate Raw Image Data*/
		Sobel sobel = new Sobel(imgread.getGrayScale());		/*Run Sobel Algorithm*/
		int[][] sobel_img = sobel.Convolution();				/*Run Sobel Algoirhtm*/
		ImageWrite imgwrite = new ImageWrite(sobel_img, out_filepath);	/*Write Edge Detected Image*/ 
		finl = imgwrite.getBufferedImg(); 
		
		/*Print Data of Images*/
		if(printData == true) {
			Array2DPrint print = new Array2DPrint();
			int width_print = 101; int height_print = 101;
			
			System.out.println("Initial Image: ");print.print(image, true, width_print, height_print);
			System.out.println("Final Image: "); print.print(sobel_img, true, width_print, height_print);
		}
		/*Display Images*/
		if(dispImage == true) {
			new LoadImageApp(bufimg, "Initial Image");
			new LoadImageApp(imgwrite.getBufferedImg(), "Final Image");	
		}
	}
	
	/**
	* Returns the input BufferedImage.
	* 
	* @return the input BufferedImage
	*/
	public BufferedImage getInitialImage() {
		return init;
	}
	
	/**
	* Returns the output BufferedImage.
	* 
	* @return the output BufferedImage
	*/
	public BufferedImage getFinalImage() {
		return finl;
	}
	
	/**
	* Returns the output BufferedImage.
	* 
	* @return the output BufferedImage
	*/
	public BufferedImage getOutput() {
		return finl;
	}
}
