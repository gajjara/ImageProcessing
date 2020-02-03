package equalize;

import java.awt.image.BufferedImage;
import edgeDetection.EdgeDetection;
import basic_processes.ImageRead;
import basic_processes.ImageWrite;

public class Experimentation {
	
	/**
	 * Class for experimenting with algorithm development
	 */
	public static void main(String[] args) {
		
		for(int i = 1; i <= 50; i++) {
			String in_filepath;
			String out_filepath;
			
			String sub1 = "/Users/Anuj/Desktop/Sample_Photos/sample_";
			String sub2 = Integer.toString(i);
			String sub3 = ".jpg";
			String sub4 = "/Users/Anuj/Desktop/deoFrames/normframes/sample_norm_";
			
			in_filepath = sub1 + sub2 + sub3;
			out_filepath = sub4 + sub2 + sub3;
			
			BufferedImage input = new ImageRead(in_filepath).getBufferedImage();
			BufferedImage edge = new EdgeDetection(input).getFinalImage();
			BufferedImage avg = new Average(input, edge).Operation();
			BufferedImage output = new Normalize(avg).Operation();
			
			new ImageWrite(output, out_filepath);
			
			System.out.println(i);
		}
		
		
		/*
		for(int i = 1; i <= 2229; i++) {
			String in_filepath;
			String out_filepath;
			
			String sub1 = "/Users/Anuj/Desktop/Sample_Photos/sample_";
			String sub2 = Integer.toString(i);
			String sub3 = ".jpg";
			String sub4 = "/Users/Anuj/Desktop/deoFrames/normframes/sample_norm_";
			
			in_filepath = sub1 + sub2 + sub3;
			out_filepath = sub4 + sub2 + sub3;
			
			BufferedImage input = new ImageRead(in_filepath).getBufferedImage();
			BufferedImage output = new Normalize(input).Operation();
			
			new ImageWrite(output, out_filepath);
			
			System.out.println(i);
		}
		*/
	}
}
