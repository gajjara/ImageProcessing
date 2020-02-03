package unsharpMasking;

import java.awt.image.BufferedImage;
import edgeDetection.EdgeDetection;
import basic_processes.*;


public class Main {
	/**
	 * Main class to test the unsharpMasking class
	 */
	public static void main(String[] args) {
		String input = "/Users/Anuj/Desktop/sample.png";
		String output = "/Users/Anuj/Desktop/sample_sharp.png";
		BufferedImage in = new ImageRead(input).getBufferedImage();
		BufferedImage out = new UnsharpMasking(in, 1.0).getOutput();
		new ImageWrite(out, output);
		
		BufferedImage inedge = new EdgeDetection(in).getFinalImage();
		BufferedImage outedge = new EdgeDetection(out).getFinalImage();
		
		new LoadImageApp(in, "Input");
		new LoadImageApp(out, "Output");
		new LoadImageApp(inedge, "Input Edges");
		new LoadImageApp(outedge, "Output Edges");
	}

}
