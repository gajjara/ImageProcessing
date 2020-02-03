package edgeDetection;


import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.concurrent.TimeUnit;
import basic_processes.*;

@SuppressWarnings("unused")
public class Experimentation {

	/**
	 * Class for experimenting with algorithm development
	 */
	public static void main(String[] args) {
		ImageRead init = new ImageRead("/Users/Anuj/Desktop/Sample_Photos/sample_9.jpg");
		
		ImageRead avg = new ImageRead("/Users/Anuj/Desktop/Sample_Photos/Edge_Avg/sample_9_avg.jpg");
		
		int w = avg.getWidth();
		int h = avg.getHeight();
		int nintns = 256;
		
		int[][] red = avg.getRed();
		int[][] green = avg.getGreen();
		int[][] blue = avg.getBlue();
		
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
		
		int redcdf_min = Integer.MAX_VALUE;
		int greencdf_min = Integer.MAX_VALUE;
		int bluecdf_min = Integer.MAX_VALUE;
		
		for(int i = 0; i < nintns; i++) {
			if(redcdf[i] < redcdf_min)
				redcdf_min = redcdf[i];
			if(greencdf[i] < greencdf_min)
				greencdf_min = greencdf[i];
			if(bluecdf[i] < bluecdf_min)
				bluecdf_min = bluecdf[i];
		}
		
		//Calculate max level
		int redcountmax = 0, greencountmax = 0, bluecountmax = 0, levelmin;
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
		levelmin = Math.min(redcountmax, Math.min(greencountmax, bluecountmax));
		System.out.println(redcountmax + " " + greencountmax + " " + bluecountmax);
		
		double npixels = h*w;
		double levels = levelmin;
		System.out.println("Levels: " + levels);
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
		
		BufferedImage newimg = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
		
		for(int i = 0; i < w; i++) {
			for(int j = 0; j < h; j++) {
				Color c = new Color(newred[i][j], newgreen[i][j], newblue[i][j]);
				int rgb = c.getRGB();
				newimg.setRGB(i, j, rgb);
			}
		}
		
		new LoadImageApp(init.getBufferedImage(), "Original");
		new LoadImageApp(newimg, "Final");
		
		System.out.println("Intensities");
		for(int i = 0; i < nintns; i++) {
			System.out.print(i + "\t");
		}
		System.out.println();

		System.out.println("Red counts");
		for(int i = 0; i < nintns; i++) {
			System.out.print(red_counts[i] + "\t");
		}
		System.out.println("");
		
		System.out.println("Green counts");
		for(int i = 0; i < nintns; i++) {
			System.out.print(green_counts[i] + "\t");
		}
		System.out.println("");
		
		System.out.println("Blue counts");
		for(int i = 0; i < nintns; i++) {
			System.out.print(blue_counts[i] + "\t");
		}
		System.out.println("");
		
		System.out.println("Red CDF");
		for(int i = 0; i < nintns; i++) {
			System.out.print(redcdf[i] + "\t");
		}
		System.out.println();
		
		System.out.println("Green CDF");
		for(int i = 0; i < nintns; i++) {
			System.out.print(greencdf[i] + "\t");
		}
		System.out.println();
		
		System.out.println("Blue CDF");
		for(int i = 0; i < nintns; i++) {
			System.out.print(bluecdf[i] + "\t");
		}
		System.out.println();
		
		System.out.println("Red h(v)");
		for(int i = 0; i < nintns; i++) {
			System.out.print(red_hv[i] + "\t");
		}
		System.out.println();
		
		System.out.println("Green h(v)");
		for(int i = 0; i < nintns; i++) {
			System.out.print(green_hv[i] + "\t");
		}
		System.out.println();
		
		System.out.println("Blue h(v)");
		for(int i = 0; i < nintns; i++) {
			System.out.print(blue_hv[i] + "\t");
		}
		System.out.println();
		
	}

}
