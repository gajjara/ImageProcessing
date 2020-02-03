package noiseRemoval;

import java.awt.image.BufferedImage;
import basic_processes.ImageRead;
import basic_processes.ImageWrite;
import basic_processes.LoadImageApp;

public class Main {
	public static void main(String[] args) {
		String input = "/Users/Anuj/Desktop/sample.png";
		String output = "/Users/Anuj/Desktop/sample_nr.png";
		BufferedImage in = new ImageRead(input).getBufferedImage();
		BufferedImage out = new NoiseRemoval(in).getOutput();
		new ImageWrite(out, output);
	
		new LoadImageApp(in, "Input");
		new LoadImageApp(out, "Output");
		
	
	}
}

