package edgeDetection;

import java.awt.Color;
import basic_processes.*;
import java.awt.image.BufferedImage;

public class Main {
	/**
	 * Main class to use the developed edgeDetection library
	 */
	public static void main(String[] args) {
		
		for(int a = 1; a <= 2229; a++) {
			String jpg = ".jpg";
			String path_i = "/Users/Anuj/Desktop/VideoFrames/frame_";
			String path_e = "/Users/Anuj/Desktop/VideoFrames/edgeframes/frame_";
			String path_e_end = ""/*"_edge"*/;
			String path_a = "/Users/Anuj/Desktop/VideoFrames/avgframes/frame_";
			String path_a_end = ""/*"_avg"*/;
			String path_n = "/Users/Anuj/Desktop/VideoFrames/normframes/frame_";
			String path_n_end = ""/*"_avg_norm"*/;
			String number = Integer.toString(a);
			
			String input = path_i + number + jpg;
			String edg = path_e + number + path_e_end + jpg;
			String avg = path_a + number + path_a_end + jpg;
			String nor = path_n + number + path_n_end + jpg;
			
			EdgeDetection edgedetection = new EdgeDetection(input, 
					"Sobel",edg, false, false);
			
			/**OVERLAY ON ORIGINAL IMAGE **/
			BufferedImage init = edgedetection.getInitialImage();
			BufferedImage edge = edgedetection.getFinalImage();
			BufferedImage over = new BufferedImage(init.getWidth(), init.getHeight(), BufferedImage.TYPE_INT_RGB);
			
			for(int i = 0; i < init.getWidth(); i++) {
				for(int j = 0; j < init.getHeight(); j++) {
					Color initcolor = new Color(init.getRGB(i, j));
					Color edgecolor = new Color(edge.getRGB(i, j));
				
					int r = (initcolor.getRed() + edgecolor.getRed())/2;
					int g = (initcolor.getGreen() + edgecolor.getGreen())/2;
					int b = (initcolor.getBlue() + edgecolor.getBlue())/2;
					
					Color overcolor = new Color(r, g, b);
					
					over.setRGB(i, j, overcolor.getRGB());
				}
			}
			
			/**ADJUST BRIGHTNESS OF OVERLAYED IMAGE**/
			
			ImageRead avgi = new ImageRead(over);
			
			int w = avgi.getWidth();
			int h = avgi.getHeight();
			int nintns = 256;
			
			int[][] red = avgi.getRed();
			int[][] green = avgi.getGreen();
			int[][] blue = avgi.getBlue();
			
			int[] red_counts = new int[nintns];
			int[] green_counts = new int[nintns];
			int[] blue_counts = new int[nintns];

			//Get histograms
			for(int i = 0; i < w; i++) {
				for(int j = 0; j < h; j++) {
					int rval = red[i][j];
					int gval = green[i][j];
					int bval = blue[i][j];
					
					int ihistrval = red_counts[rval];
					int ihistgval = green_counts[gval];
					int ihistbval = blue_counts[bval];
					
					int fhistrval = ihistrval + 1;
					int fhistgval = ihistgval + 1;
					int fhistbval = ihistbval + 1;
					
					red_counts[rval] = fhistrval;
					green_counts[gval] = fhistgval;
					blue_counts[bval] = fhistbval;		
				}
			}
			
			int[] redcdf = new int[nintns];
			int[] greencdf = new int[nintns];
			int[] bluecdf = new int[nintns];
			
			int redsum = 0, greensum = 0, bluesum = 0;
			
			for(int i = 0; i < nintns; i++) {
				redsum += red_counts[i];
				redcdf[i] = redsum;
				greensum += green_counts[i];
				greencdf[i] = greensum;
				bluesum += blue_counts[i];
				bluecdf[i] = bluesum;
			}
			
			int redcdf_max = 0;
			int redcdf_min = Integer.MAX_VALUE;
			int greencdf_max = 0;
			int greencdf_min = Integer.MAX_VALUE;
			int bluecdf_max = 0;
			int bluecdf_min = Integer.MAX_VALUE;
			
			for(int i = 0; i < nintns; i++) {
				if(redcdf[i] > redcdf_max) 
					redcdf_max = redcdf[i];
				if(redcdf[i] < redcdf_min)
					redcdf_min = redcdf[i];
				if(greencdf[i] > greencdf_max) 
					greencdf_max = greencdf[i];
				if(greencdf[i] < greencdf_min)
					greencdf_min = greencdf[i];
				if(bluecdf[i] > bluecdf_max) 
					bluecdf_max = bluecdf[i];
				if(bluecdf[i] < bluecdf_min)
					bluecdf_min = bluecdf[i];
			}
			
			//Calculate max level
			int redcountmax = 0, greencountmax = 0, bluecountmax = 0, levelmin = 0;
			int redcounter = 0, greencounter = 0, bluecounter = 0;
			boolean redcountfound = false, greencountfound = false, bluecountfound = false;
			
			while(redcounter < nintns && greencounter < nintns && bluecounter < nintns) {
				if(red_counts[redcounter] == 0) {
					if(redcountfound == false) {
						redcountmax = redcounter;
						redcountfound = true;
					}
				}
				if(green_counts[greencounter] == 0) {
					if(greencountfound == false) {
						greencountmax = greencounter;
						greencountfound = true;
					}
				}
				if(blue_counts[bluecounter] == 0) {
					if(bluecountfound == false) {
						bluecountmax = bluecounter;
						bluecountfound = true;
					}
				}
				
				redcounter++;
				greencounter++;
				bluecounter++;
			}
			
			int[] rgbcountmaxvalues = {redcountmax, greencountmax, bluecountmax};
				
			boolean redcountzero = false, greencountzero = false, bluecountzero = false;				
				
			int counter = 0;
			while(counter < rgbcountmaxvalues.length) {
				if(rgbcountmaxvalues[counter] == 0) {
					if(counter == 0) {
						redcountzero = true;
					}
					if(counter == 1) {
						greencountzero = true;
					}
					if(counter == 2) {
						bluecountzero = true;
					}
				}
				counter++;
			}
				
			if(redcountzero == true && greencountzero == true && bluecountzero == true)
				levelmin = 255;
			else if(redcountzero == true && greencountzero == true) 
				levelmin = bluecountmax;
			else if(redcountzero == true && bluecountzero == true) 
				levelmin = greencountmax;
			else if(greencountzero == true && bluecountzero == true) 
				levelmin = redcountmax;
			else if(redcountzero == true) 
				levelmin = Math.min(greencountmax, bluecountmax);
			else if(greencountzero == true) 
				levelmin = Math.min(redcountmax, bluecountmax);
			else if(bluecountzero == true) 
				levelmin = Math.min(redcountmax, greencountmax);
			else 
				levelmin = Math.min(redcountmax, Math.min(greencountmax, bluecountmax));
			
			if(levelmin == 0)
				levelmin = 255;
			
			double npixels = h*w;
			double levels = levelmin;
			int[] red_hv = new int[nintns];
			int[] green_hv = new int[nintns];
			int[] blue_hv = new int[nintns];
			
			for(int i = 0; i < nintns; i++) {
				double redcdf_mind = (double) redcdf_min;
				double greencdf_mind = (double) greencdf_min;
				double bluecdf_mind = (double) bluecdf_min;
				
				double redcdfd = (double) redcdf[i];
				double greencdfd = (double) greencdf[i];
				double bluecdfd = (double) bluecdf[i];
				
				//red_hv[i] = (int) ((levels - 1)*((redcdfd - redcdf_mind)/(npixels - redcdf_mind)));
				//green_hv[i] = (int) ((levels - 1)*((greencdfd - greencdf_mind)/(npixels - greencdf_mind)));
				//blue_hv[i] = (int) ((levels - 1)*((bluecdfd - bluecdf_mind)/(npixels - bluecdf_mind)));
				
				red_hv[i] = 1 + (int) ((levels - 2)*((redcdfd - redcdf_mind)/(npixels - redcdf_mind)));
				green_hv[i] = 1+(int) ((levels - 2)*((greencdfd - greencdf_mind)/(npixels - greencdf_mind)));
				blue_hv[i] = 1 +(int) ((levels - 2)*((bluecdfd - bluecdf_mind)/(npixels - bluecdf_mind)));
			}
			
			int[][] newred = new int[w][h];
			int[][] newgreen = new int[w][h];
			int[][] newblue = new int[w][h];
			
			for(int i = 0; i < w; i++) {
				for(int j = 0; j < h; j++) {
					int oldred = red[i][j];
					int oldgreen = green[i][j];
					int oldblue = blue[i][j];
					
					for(int k = 0; k < nintns; k++) {
						if(oldred == k) {
							newred[i][j] = red_hv[k];
							break;
						}
					}
					for(int k = 0; k < nintns; k++) {
						if(oldgreen == k) {
							newgreen[i][j] = green_hv[k];
							break;
						}
					}
					for(int k = 0; k < nintns; k++) {
						if(oldblue == k) {
							newblue[i][j] = blue_hv[k];
							break;
						}
					}
				}
			}
			
			BufferedImage norm = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
			
			for(int i = 0; i < w; i++) {
				for(int j = 0; j < h; j++) {
					Color c = new Color(newred[i][j], newgreen[i][j], newblue[i][j]);
					int rgb = c.getRGB();
					norm.setRGB(i, j, rgb);
				}
			}
			
			ImageWrite write = new ImageWrite();
			write.setBufferedImg(over);
			write.toFile(avg);
			
			ImageWrite write1 = new ImageWrite();
			write1.setBufferedImg(norm);
			write1.toFile(nor);
			
			System.out.println("Image " + a + " Algorithmed");
			System.out.println(input);
			System.out.println(edg);
			System.out.println(avg);
			System.out.println(nor);
			System.out.println();
		}
		
		
	}
}
