package src;

import java.awt.image.BufferedImage;
import basic_processes.ImageRead;
import basic_processes.ImageWrite;

/**
 * Class for testing the timing of the algorithms developed for processing medical images
 * 
 * @author Anuj Gajjar
 * @version 1.0
 * @since Summer 2019
 *
 */
public class testing {
	public static void main(String[] args) {
		long sumTime = 0;
		int n_images = 150;
		int i_images = 0;
																		
		for(int i = 1 + i_images; i <= n_images + i_images; i++) {
			// Start timing
			long startTime = System.currentTimeMillis();
			
			// Store filepaths
			String folder = "Lung_Cancer_CT";
			String n = Integer.toString(i);
			String tag = ".png";
			String input =  "/Users/Anuj/Desktop/" + folder + "/image_"		 + n + tag;
			String edge = "/Users/Anuj/Desktop/" + folder + "/edges/image_"	 + n + tag;
			String output = "/Users/Anuj/Desktop/" + folder + "/norms/image_"	 + n + tag;
			
			// Conduct process
			BufferedImage in = new ImageRead(input).getBufferedImage(); 	
			DicomProcess f = new DicomProcess(in, true, i);
			BufferedImage edg = f.getEdge();
			BufferedImage out = f.getOutput();
			new ImageWrite(edg, edge);
			new ImageWrite(out, output);
			
			// End time for process
			long endTime = System.currentTimeMillis();
			// Calculate time elapsed
			long timeElapsed = endTime - startTime;
			// Add to total time
			sumTime += timeElapsed;
			System.out.println("Normalized and Edge Detected Images of Image " + Integer.toString(i) + " Is Written");
			System.out.println("Execution time in milliseconds: " + timeElapsed);
			System.out.println();
		}
		
		System.out.println("All " + n_images + " Images are Normalized and Edge Detected");
		System.out.println("Time elapsed in milliseconds: " + sumTime);
	}
}
