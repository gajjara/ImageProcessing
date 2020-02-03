package equalize;

import java.awt.image.BufferedImage;
import basic_processes.ImageRead;
import basic_processes.ImageWrite;
import edgeDetection.Sobel;
import src.ImageProcess;

/**
* The OilPainting class turns an RGB image and makes it look like an oil painting.
* This class is purely for experimentation.
*
* @author  Anuj Gajjar
* @version 1.0
* @since   Summer 2019
*/
public class OilPainting extends ImageProcess {
	
	// Store Buffered Images
	private BufferedImage input;
	private BufferedImage edge;
	private BufferedImage avg;
	private BufferedImage norm;
	private BufferedImage output;
	
	/**
	 * Construct an instance of the OilPainting class
	 * 
	 * @return void
	 */
	public OilPainting() {}
	
	/**
	 * Construct an instance of the OilPainting class with an inputted BufferedImage and 
	 * 
	 * @return void
	 */
	public OilPainting(BufferedImage img) {
		input = img;
		Operation(img);
	}
	
	/**
	 * Conducts the OilPainting operation based on an inputted BufferedImage
	 * 
	 * @parma img the inputted BufferedImage
	 * @return the outputted BufferedImage
	 */
	public BufferedImage Operation(BufferedImage img) {
		if(input == null) 
			input = img;
		
		ImageRead read1 = new ImageRead(input);
		
		/*Sobel w/Greyscale Input*/
		Sobel sobel = new Sobel(read1.getGrayScale());
		edge = new ImageWrite(sobel.Convolution()).getBufferedImg();
		
		/*Average Images*/
		avg = new Average(input, edge).Operation();
		
		/*Normalize avg Image*/
		norm = new Normalize(avg).getOutput();
		
		output = norm;
		
		return output;
	}
	
	/**
	 * Returns the inputted BufferedImage
	 * 
	 * @return the inputted BufferedImage
	 */
	public BufferedImage getInput() {
		return input;
	}
	
	/**
	 * Returns the edge detected BufferedImage
	 * 
	 * @return the edge detected BufferedImage
	 */
	public BufferedImage getEdge() {
		return edge;
	}
	
	/**
	 * Returns the averaged BufferedImage
	 * 
	 * @return the averaged BufferedImage
	 */
	public BufferedImage getAvg() {
		return avg;
	}
	
	/**
	 * Returns the normalized BufferedImage
	 * 
	 * @return the normalized BufferedImage
	 */
	public BufferedImage getNorm() {
		return norm;
	}

	/**
	 * Returns the outputted BufferedImage
	 * 
	 * @return the outputted BufferedImage
	 */
	public BufferedImage getOutput() {
		return output;
	}

	@Override
	public BufferedImage Operation() {
		return null;
	}
	
}
