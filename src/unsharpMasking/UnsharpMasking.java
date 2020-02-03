package unsharpMasking;

import java.awt.Color;
import java.awt.image.BufferedImage;
import src.ImageProcess;

/**
* The UnsharpMasking is a class to conduct an UnsharpMasking operation for increasing the
* sharpness of an image.
* 
* @author  Anuj Gajjar
* @version 1.0
* @since   Summer 2019
*/
public class UnsharpMasking extends ImageProcess {
	// Sharpen constant (stronger sharpening if higher)
	double sharpenAmount = 1.0;
	
	// Create gradient for unsharp masking
	private double[][] grad = {	
			{ -1*sharpenAmount	, -1*sharpenAmount	, -1*sharpenAmount 	},
			{ -1*sharpenAmount	,  9*sharpenAmount	, -1*sharpenAmount	},
			{ -1*sharpenAmount	, -1*sharpenAmount	, -1*sharpenAmount	}
	};
	
	/**
	 * Construct an instance of the UnsharpMasking class with an input image.
	 * 
	 * @param in The input BufferedImage
	 * @return void
	 */
	public UnsharpMasking(BufferedImage in) {
		setFields(in);
		Operation();
	}
	
	/**
	 * Construct an instance of the UnsharpMasking class with an input image 
	 * and a specified sharpening constant.
	 * 
	 * @param in The input BufferedImage
	 * @param sharpeningAmount the sharpneing amount
	 * @return void
	 */
	public UnsharpMasking(BufferedImage in, double sharpeningAmount) {
		setFields(in, sharpeningAmount);
		Operation();
	}
	
	/**
	 * Sets the fields for an instance of the UnsharpMasking class with an input image 
	 * and a specified sharpening constant.
	 * 
	 * @param in The input BufferedImage
	 * @param sharpeningAmount the sharpneing amount
	 * @return void
	 */
	public void setFields(BufferedImage in, double sharpeningAmount) {
		setFields(in);
		sharpenAmount = sharpeningAmount;
		resetGrad();
	}

	/**
	 * Readjusts the convoltuional sum gradient for a new sharpening amount.
	 * 
	 * @return void
	 */
	private void resetGrad() {
		double[][] newGrad = {	
				{ -1*sharpenAmount	, -1*sharpenAmount	, -1*sharpenAmount 	},
				{ -1*sharpenAmount	,  9*sharpenAmount	, -1*sharpenAmount	},
				{ -1*sharpenAmount	, -1*sharpenAmount	, -1*sharpenAmount	}
		};
		grad = newGrad;
	}
	
	/**
	 * The UnsharpMasking operation.
	 * 
	 * @return The output BufferedImage
	 */
	public BufferedImage Operation() {
		BufferedImage imageout = new BufferedImage(w,h,BufferedImage.TYPE_INT_RGB);
		
		for(int i = 0; i < w - 2; i++) {
			for(int j = 0; j < h - 2; j++) {
				// Conduct convolution sum for each RGB channel
				double redsum = 0.0;
				double greensum = 0.0;
				double bluesum = 0.0;
				
				for(int h = 0; h < 3; h++) {
					for(int k = 0; k < 3; k++) {
						redsum += grad[h][k]*r[i + h][j + k];
						greensum += grad[h][k]*g[i + h][j + k];
						bluesum += grad[h][k]*b[i + h][j + k];		
					}
				}
								
				// Generate masking value
				int r_s = (int) (redsum);
				int g_s = (int) (greensum);
				int b_s = (int) (bluesum);
				
				// Apply new value
				int r_n = (r_s + r[i][j])/2;
				int g_n = (g_s + g[i][j])/2;
				int b_n = (b_s + b[i][j])/2;
				
				// Check if any values are about max uint8 value
				if(r_n > 255)
					r_n = 255;
				if(g_n > 255)
					g_n = 255;
				if(b_n > 255)
					b_n = 255;
				if(r_n < 0)
					r_n = 0;
				if(g_n < 0)
					g_n = 0;
				if(b_n < 0)
					b_n = 0;
				
				// Apply new RGB value
				Color c = new Color(r_n, g_n, b_n);
				imageout.setRGB(i, j, c.getRGB());
			}
		}
		
		output = imageout;
		return output;
	}
}
