package equalize;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import basic_processes.ImageRead;
import basic_processes.LoadImageApp;
import src.ImageProcess;

/**
* The Normalize class histogram normalizes an image based on a specified value or adaptively
* normalizes an image based on the best constrast image.
*
* @author  Anuj Gajjar
* @version 1.0
* @since   Summer 2019
*/
public class Normalize extends ImageProcess {
	// Number of integer values (256 for uint8 images)
	int nintns = 256;
	
	// For storing histogram distribution
	private int[] idealdist;

	/**
	 * Construct an instance of the Normalize class
	 * 
	 * @return void
	 */
	public Normalize() {}
	
	/**
	 * Construct an instance of the Normalize with an input BufferedImage. 
	 * This constructor conducts adaptive normalization on the input BufferedImage.
	 * 
	 * @param img the input BufferedImage
	 * @return void
	 */
	public Normalize(BufferedImage img) {
		setFields(img);
		idealdist = getIdealDist();
		Operation();
	}
	
	/**
	 * Construct an instance of the Normalize with an input BufferedImage and input value
	 * This constructor conducts normalization based on the value on the input BufferedImage.
	 * 
	 * @param img the input BufferedImage
	 * @param val the integer value to normalize the image to
	 * @return void
	 */
	public Normalize(BufferedImage img, int val) {
		setFields(img);
		idealdist = getIdealDist();
		Operation(val);
	}

	/**
	 * Conduct adaptive normalization on the inputted image.
	 * 
	 * @return the adaptively normalized BufferedImage
	 */
	public BufferedImage Operation() {
		//Get histogram
		int[] red_counts = histogram(r);
		int[] green_counts = histogram(g);
		int[] blue_counts = histogram(b);
		
		//Get CDF
		int[] redcdf = cdf(red_counts);
		int[] greencdf = cdf(green_counts);
		int[] bluecdf = cdf(blue_counts);

		//Get Norm Val
		int red_val = calcNormVal(r, redcdf);
		int green_val = calcNormVal(g, greencdf);
		int blue_val = calcNormVal(b, bluecdf);
		
		//Apply Ideal Norm and get Ideal Norm histogram
		int[] red_hv = norm(redcdf, red_val);
		int[] green_hv = norm(greencdf, green_val);
		int[] blue_hv = norm(bluecdf, blue_val);

		//Set Values to Norm histogram
		int[][] newred = newVal(r, red_hv);
		int[][] newgreen = newVal(g, green_hv);
		int[][] newblue = newVal(b, blue_hv);

		// Write the BufferedImage based on the normalized values
		BufferedImage norm = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);

		for(int i = 0; i < w; i++) {
			for(int j = 0; j < h; j++) {		
				Color c = new Color(newred[i][j], newgreen[i][j], newblue[i][j]);
				int rgb = c.getRGB();
				norm.setRGB(i, j, rgb);
			}
		}

		output = norm;
		return output;
	}
	
	/**
	 * Conduct normalization based on an inputted value on the inputted image.
	 * 
	 * @param val the value to normalize the inputted image to
	 * @return the value-normalized BufferedImage
	 */
	public BufferedImage Operation(int val) {
		//Get histogram
		int[] red_counts = histogram(r);
		int[] green_counts = histogram(g);
		int[] blue_counts = histogram(b);
		
		//Get CDF
		int[] redcdf = cdf(red_counts);
		int[] greencdf = cdf(green_counts);
		int[] bluecdf = cdf(blue_counts);
		
		//Get Ideal Norm
		int[] red_hv = norm(redcdf, val);
		int[] green_hv = norm(greencdf, val);
		int[] blue_hv = norm(bluecdf, val);

		//Set Values to Norm
		int[][] newred = newVal(r, red_hv);
		int[][] newgreen = newVal(g, green_hv);
		int[][] newblue = newVal(b, blue_hv);

		BufferedImage norm = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);

		for(int i = 0; i < w; i++) {
			for(int j = 0; j < h; j++) {		
				Color c = new Color(newred[i][j], newgreen[i][j], newblue[i][j]);
				int rgb = c.getRGB();
				norm.setRGB(i, j, rgb);
			}
		}

		output = norm;
		return output;
	}
	
	/**
	 * Compute the histogram (or PDF) of the inputted image.
	 * 
	 * @param data the inputted image data array
	 * @return the histogram of the inputted image data array
	 */
	private int[] histogram(int[][] data) {
		int[] counts = new int[nintns];
		for(int i = 0; i < w; i++) {
			for(int j = 0; j < h; j++) {
				int val = data[i][j];
				int ihistval = counts[val];
				int fhistval = ihistval + 1;
				counts[val] = fhistval;
			}
		}
		
		/*TESTING
		for(int i = 0; i < nintns; i++) 
			System.out.print(counts[i] + "\t");
		System.out.println();
		/*TESTING*/
		
		return counts;
	}
	
	/**
	 * Compute the CDF of an histogram
	 * 
	 * @param histogram integer array histogram
	 * @return the CDF of the inputted histogram
	 */
	private int[] cdf(int[] histogram) {
		int[] cdf = new int[nintns];
		int sum = 0;
		for(int i = 0; i < nintns; i++) {
			sum += histogram[i];
			cdf[i] = sum;
		}
		
		/*TESTING
		for(int i = 0; i < nintns; i++) 
			System.out.print(cdf[i] + "\t");
		System.out.println();
		/*TESTING*/
		
		
		return cdf;
	}
	
	/**
	 * Computes the normalizing histogram.
	 * 
	 * @param cdf the CDF that is used for normalization
	 * @param level the integer value to calculate the normalization histogram for
	 * @return the normalizing histogram
	 */
	private int[] norm(int[] cdf, int level) {
		// Calculate CDF min
		int cdf_min = Integer.MAX_VALUE;
		for(int i = 0; i < nintns; i++) {
			if(cdf[i] < cdf_min)
				cdf_min = cdf[i];
		}
		
		// Number of integer levels
		double levels = (double) level;
		
		// The CDF min stored as a double
		double cdf_mind = (double) cdf_min;
		
		// Number of pixels
		double npixels = (h*w);
		
		// Normalization histogram calculation
		int[] hv = new int[nintns];
		for(int i = 0; i < nintns; i++) {
			double cdfd = (double) cdf[i];

			//_hv[i] = (int) ((levels - 1)*((cdfd - cdf_mind)/(npixels - cdf_mind)));
			hv[i] = 1 + (int) ((levels - 2)*((cdfd - cdf_mind)/(npixels - cdf_mind)));
		}
		
		/*TESTING
		/for(int i = 0; i < nintns; i++) 
			System.out.print(hv[i] + "\t");
		System.out.println();
		/*TESTING*/
		
		return hv;
	}
		
	/**
	 * Adaptively calculates the best normalization value by calculating the best contrast normalization value.
	 * 
	 * @param original the original image data array
	 * @param cdf the CDF to calculate the normalization histogram for
	 * @return the best normalization value
	 */
	private int calcNormVal(int[][] original, int[] cdf) {
		
		/**
		 * To find normalization value: 
		 * 		Loop through all possible intensities for normalization
		 * 		Normalize data for each possible intensities
		 * 		Get histogram of that normalized data
		 * 		Compare that histogram with an ideal histogram of normal distrubution between 0 and 255
		 * 		Use a KL divergence for histogram and ideal distribution and store value for intensity
		 *		Find best KL divergence score and use its intensity
		 */
		int normval = 128;
		int[] histogram_i = idealdist;
		ArrayList<Double> KLDs = new ArrayList<Double>();
		
		//Loop through all possible values
		for(int val = 1; val <= 255; val++) {
			//System.out.println("\ti = " + val);
			
			// Get normalization histogram and apply the normalization histogram
			int[] norm = norm(cdf, val);
			int[] histogram_f = histogram(newVal(original, norm));
			
			// Store KLD value
			double KLD = 0.0;
			
			// Compute KLD value
			for(int i = 0; i < nintns; i++) {
				double num = ((double) histogram_i[i]);
				double dem = ((double) histogram_f[i]);
				if(dem == 0.0)
					dem = 1;
				double div = (num/dem);
				double log = Math.log(div);
				
				if(Double.isInfinite(log))
					log = 0.0;
				
				KLD += num*log;
			}
			
			// Store KLD values
			KLDs.add(KLD);			
		}
		
		//Identify best KLD
		double min = Double.MAX_VALUE;
		for(int i = 0; i < KLDs.size(); i++) {
			if(KLDs.get(i) < min) {
				min = KLDs.get(i);
				normval = i;
			}
		}		
		
		return normval;
	}
	
	/**
	 * Applys and returns the normalized image based on an inputted normalization histogram
	 * 
	 * @param original the inputted image data array
	 * @param norm the normalization histogram
	 * @return the normalized output image data array
	 */
	private int[][] newVal(int[][] original, int[] norm) {
		int[][] newval = new int[w][h];
		
		for(int i = 0; i < w; i++) {
			for(int j = 0; j < h; j++) {
				int oldval = original[i][j];

				// Apply new value from normalization histogram
				for(int k = 0; k < nintns; k++) {
					if(oldval == k) {
						newval[i][j] = norm[k];
						break;
					}
				}
				
				// Prevent any negative values
				if(newval[i][j] < 0)
					newval[i][j] = 0;
			}
		}
		
		return newval;
		
	}
	
	/**
	 * Computes and stores teh ideal histogram PDF for a best constrast image.
	 *
	 * @return the ideal histogram for a best constrast uint8 image
	 */
	public int[] getIdealDist() {
		int[] ideal = new int[nintns];
		
		// Number of data points
		double ndata = (w*h);
		
		// Desired standard deviation
		double std = 36.5714285714;
		
		// Desired average (or mean)
		double mean = 128.0;
		
		// Calculate the first constant for a PDF equation
		double constant1_1 = Math.sqrt((2*(Math.PI)*std*std));
		double constant1 = 1/constant1_1;
		
		// Caluclate the second constant for a PDF equation
		double constant2 = (-1)/(2*std*std);
				
		// Calculate the ideal histogram
		for(int i = 0; i < ideal.length; i++) {
			double x = (double) i;
			double exp = Math.exp((constant2*(x - mean)*(x - mean)));
			double func = constant1*exp;
			
			ideal[i] = (int) Math.round((ndata*func));
		}
		
		// Sum up the already calculated histogram
		int sum = 0;
		for(int i = 0; i < ideal.length; i++) {
			sum += ideal[i];
		}
		
		// Set the value at the mean 
		ideal[128] = ideal[128] + (((int) ndata) - sum);
		
		return ideal;
	}

	@SuppressWarnings("unused")
	private void output1DIntArr(int[] data, String title) {
		System.out.println(title);
		for(int i = 0; i < data.length; i++) {
			System.out.print(data[i] + "\t");
		}
		System.out.println();
	}
	
	@SuppressWarnings("unused")
	private void output1DDblArr(double[] data, String title) {
		System.out.println(title);
		for(int i = 0; i < data.length; i++) {
			System.out.print(data[i] + "\t");
		}
		System.out.println();
	}
	
	@SuppressWarnings("unused")
	private double avg2DIntArr(int[][] data) {
		double xysize = (double) (data.length)*(data[0].length);
		
		double avg = 0.0;
		for(int i = 0; i < data.length; i++) {
			for(int j = 0; j < data[0].length; j++) {
				avg += ((double) data[i][j]);
			}
		}
		avg = avg/xysize;
		
		return avg;
	}
	
	// Main for testing
	public static void main(String[] args) {
		BufferedImage img = new ImageRead("/Users/Anuj/Desktop/Current_Work/Image_Processing/Sample_Photos/sample_9.jpg").getBufferedImage();
		BufferedImage out = new Normalize(img, 255).getOutput();
		new LoadImageApp(out, "");
	}
	
}
