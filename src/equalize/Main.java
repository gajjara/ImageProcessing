package equalize;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import equalize.OilPainting;
import basic_processes.*;

@SuppressWarnings("unused")
public class Main {
	/**
	 * Main class to use the developed equalize library
	 */
	public static void main(String[] args) {
		ImageRead read1 = new ImageRead("/Users/Anuj/Desktop/Current_Work/Image_Processing/Sample_Photos/sample_9.jpg");
		BufferedImage sample = read1.getBufferedImage();
		OilPainting paint = new OilPainting(sample);
		BufferedImage edge = paint.getEdge();
		BufferedImage avg = paint.getAvg();
		BufferedImage norm = paint.getNorm();
		
		LoadImageApp LoadImageApp1 = new LoadImageApp(sample, "Initial Image");
		LoadImageApp LoadImageApp2 = new LoadImageApp(edge, "Edge Detected Image");
		LoadImageApp LoadImageApp3 = new LoadImageApp(avg, "Average of Initial and Edge Detected Image");
		LoadImageApp LoadImageApp4 = new LoadImageApp(norm, "Normalized Image");
	}
}
