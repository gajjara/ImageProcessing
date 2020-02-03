package edgeDetection;

public class Sobel {
	
	private int[][] image; //Input image
	private int[][] image_edge; //Edge detected image
	private double[][] gradient; // Gradient that is used for edge detection
	
	// The gradient for the x-direction sobel convolution
	private int[][] gx = {	{-1, 0, 1}, 
								{-2, 0, 2}, 
								{-1, 0, 1}	};
	
	// The gradient for the y-direction sobel convolution
	private int[][] gy = {	{-1, -2, -1},	
								{0, 0, 0},
								{1, 2, 1}	};	
	
	// Alternate x-direction Sobel fitler
	@SuppressWarnings("unused")
	private int[][] gx_alt = { 	{3, 0, -3},
								{10, 0, -10},
								{3, 0, -3}	};
	// Alternate y-direction Sobel filter
	@SuppressWarnings("unused")
	private int[][] gy_alt = { 	{3, 10, 3},
								{0, 0 ,0},
								{-3, -10, -3}	};
	
	/**
	 * Constructs an instance of the Sobel class.
	 * 
	 * @return void
	 */
	public Sobel() {
		image = null;
	}
	
	/**
	 * Constructs an instance of the Sobel class with an inputted image data array.
	 * Note that the image data array must be in grayscale.
	 * 
	 * @param img the grayscale image data array
	 * @return void
	 */
	public Sobel(int[][] img) {
		image = img;
		image_edge = new int[image.length][image[0].length];
		gradient = new double[image.length][image[0].length];
	}
	
	/**
	 * Conducts the sobel convolution on the inputted image.
	 * 
	 * @return the edge detected image as a 2-D int array
	 */
	public int[][] Convolution() {
		double maxGradient = -1;
		
		// Loop through image data
		for(int i = 0; i < (image.length - 2); i++) {
			for(int j = 0; j < (image[0].length - 2); j++) {
				// Keep running sum
				double SX = 0.0;
				double SY = 0.0;
				
				// Apply convolution with the running sum
				for(int a = 0; a < 3; a++) {
					for(int b = 0; b < 3; b++) {
						SX += gx[a][b]*image[i + a][j + b];
						SY += gy[a][b]*image[i + a][j + b];
					}
				}
				
				// Compute gradient
				gradient[i][j] = Math.sqrt(SX*SX + SY*SY);
				double g = gradient[i][j];
				
				// Store max of the gradient for scaling down of image data to max uint8 value
				if(maxGradient < g)
					maxGradient = g;
			}
		}
		
		// Scale for scaling down the values of the image data
		double scale = 255.0/maxGradient;
	
		// Scale down all values that are greater than the max uint8 value
		for(int i = 0; i < image.length; i++) {
			for(int j = 0; j < image[0].length; j++) {
				double val = scale*(gradient[i][j]);
				int value = (int) val;
								
				if(value > 255)
					value = 255;
				
				image_edge[i][j] = value;
			}
		}
		
		return image_edge;
	}

}
