package src;

import java.awt.image.BufferedImage;
import edgeDetection.EdgeDetection;
import basic_processes.ImageRead;
import basic_processes.ImageWrite;
import basic_processes.LoadImageApp;
import equalize.Average;
import equalize.Normalize;
import noiseRemoval.NoiseRemoval;
import threshold.ThresholdBinarize;
import unsharpMasking.UnsharpMasking;

/**
 * DicomProcess class is a generalized processing algorithm for medical images.
 * 
 * @author Anuj Gajjar
 * @version 1.0
 * @since Summer 2019
 */
@SuppressWarnings("unused")
public class DicomProcess extends ImageProcess {

	// The edge image
	BufferedImage edge;
	
	// The renormalization value
	int renormval = 216;
	
	// The threshold value
	int thresval  = 162;
	
	/**
	 * Construct an instance of the DicomProcess class
	 * 
	 * @return void
	 */
	public DicomProcess() {
		super();
	}
	
	/**
	 * Construct an instance of the DicomProcess class with an input BufferedImage
	 * 
	 * @return void
	 */
	public DicomProcess(BufferedImage in) {
		super(in);
	}
	
	/**
	 * Construct an instance of the DicomProcess class with an input BufferedImage, a boolean vlaue
	 * to show the status of the algorithm, and a counter showing which image is processed.
	 *  
	 * @param in The input BufferedImage value
	 * @param showStatus Shows the status of the algoirthm if true, does nothing if false
	 * @param nImage The image number processed
	 */
	public DicomProcess(BufferedImage in, boolean showStatus, int nImage) {
		setFields(in);
		Operation(showStatus, nImage);
	}
	
	/**
	public void DicomProcessAll(String in, String edg, String out, boolean writeEdge, boolean showStatus, boolean showImage, int nImage) {
		input = new ImageRead(in).getBufferedImage();
		setFields(input);		
		Operation(showStatus, nImage);

		if(showImage == true) {
			new LoadImageApp(input, "Input Image");
			new LoadImageApp(this.getEdge(), "Edge Detected Image");
			new LoadImageApp(this.getOutput(), "Output Image");
		}
		
		if(writeEdge == true) {
			new ImageWrite(edge, edg);
		}
		new ImageWrite(output, out); 
	}
	*/
	
	/**
	 * The DicomProcess image process operation.
	 * 
	 * @return The output BufferedImage
	 */
	@Override
	public BufferedImage Operation() {
		BufferedImage NR = new NoiseRemoval(input).getOutput();
		BufferedImage Edge = new EdgeDetection(NR).getOutput();
		BufferedImage shrp = new UnsharpMasking(Edge).getOutput();
			edge = shrp;
		BufferedImage avg = new Average(input, shrp).getOutput();
		BufferedImage norm = new Normalize(avg).getOutput();
		BufferedImage renorm = new Normalize(norm, renormval).getOutput();
		//BufferedImage thres = new Threshold(renorm, thresval, true).getOutput();
		
		output = renorm;
		return output;
	}
	
	/**
	 * The DicomProcess image process operation.
	 * 
	 * @param showStatus Shows the status of the algoirthm if true, does nothing if false
	 * @param nImage The image number processed
	 * @return The output BufferedImage
	 */
	public BufferedImage Operation(boolean showStatus, int nImage) {
		if(showStatus == false) {
			return Operation();
		}
		else {
			String status1;
			if(nImage < 0) {
				status1 = "Image Inputted";
			}
			else {
				status1 = "Image " + Integer.toString(nImage) + " Inputted";
			}
			System.out.println(status1);
			BufferedImage NR = new NoiseRemoval(input).getOutput();
			BufferedImage Edge = new EdgeDetection(NR).getOutput();
			BufferedImage shrp = new UnsharpMasking(Edge).getOutput();
				edge = shrp;
			System.out.println("Edge Detection Complete");
			BufferedImage avg = new Average(input, shrp).getOutput();
			System.out.println("Overlay Complete");
			BufferedImage norm = new Normalize(avg).getOutput();
			System.out.println("Constrast Enhancing Normalization Complete");
			BufferedImage renorm = new Normalize(norm, renormval).getOutput();
			System.out.println("Brightness Enhancing Normalization Complete");
			//BufferedImage thres = new Threshold(renorm, thresval, true).getOutput();
			//System.out.println("Thresholding Complete");
			
			output = renorm;
			
			return output;
		}
		
	}
	
	/**
	 * Returns the edge detected image
	 * @return The BufferedImage of the edge detected image
	 */
	public BufferedImage getEdge() {
		return edge;
	}

}
