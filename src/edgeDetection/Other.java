package edgeDetection;


import java.awt.image.BufferedImage;
import basic_processes.*;
import equalize.Average;
import equalize.Normalize;

public class Other {

	/**
	 * Other main class for testing of the edgeDetection library
	 */
	public static void main(String[] args) {
		String in = "/Users/Anuj/Pictures/DSC02652 - Version 2.JPG";
		BufferedImage input = new ImageRead(in).getBufferedImage();
		BufferedImage edge = new EdgeDetection(input).getOutput();
		BufferedImage over = new Average(input, edge).getOutput();
		BufferedImage norm = new Normalize(over).getOutput();
		BufferedImage renorm = new Normalize(norm, 216).getOutput();
		new ImageWrite(renorm, "/Users/Anuj/Desktop/valpo.png");
		new LoadImageApp(renorm,"");
	}

}
