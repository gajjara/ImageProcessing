package threshold;

import java.awt.image.BufferedImage;
import edgeDetection.EdgeDetection;
import basic_processes.ImageRead;
import basic_processes.ImageWrite;
import basic_processes.LoadImageApp;
import noiseRemoval.NoiseRemoval;
import unsharpMasking.UnsharpMasking;

@SuppressWarnings("unused")
public class Main {
	/**
	 * Main class to test the threshold class
	 */
	public static void main(String[] args) {
		String input = "/Users/Anuj/Desktop/ChestCT/norms/image_1.png";
		//String output = "/Users/Anuj/Desktop/sample_threshold.png";
		BufferedImage in = new ImageRead(input).getBufferedImage();
		//in = new NoiseRemoval(in).getOutput();
		//in = new EdgeDetection(in).getOutput();
		//in = new UnsharpMasking(in).getOutput();
		BufferedImage out = new ThresholdBinarize(in, 168, true).getOutput();
		//new ImageWrite(out, output);
		
		new LoadImageApp(in, "Input");
		new LoadImageApp(out, "Output");
	}
}
