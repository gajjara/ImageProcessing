package basic_processes;

/*
 * Copyright (c) 1995, 2008, Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Oracle or the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */ 

/*
 * NOTE: MODIFIED FROM ORIGINAL CODE FOUND FROM: 
 * https://docs.oracle.com/javase/tutorial/2d/images/examples/LoadImageApp.java
 */


import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
import javax.swing.*;

/**
 * This class loads an Image from an external file or BufferedImage
 * 
 * @author  Anuj Gajjar
 * @version 1.0
 * @since   Summer 2019
 */
@SuppressWarnings("serial")
public class LoadImageApp extends Component {

	BufferedImage img; // The stored Bufferedimage
	String title = ""; // The title to show the image with

	/**
	 * Draws an image on a Graphics object.
	 *
	 * @param g the Graphics object
	 * @return void
	 */
	public void paint(Graphics g) {
		g.drawImage(img, 0, 0, getWidth(), getHeight(), null);
	}

	/**
	 * Creates an instance of the LoadImageApp class.
	 * 
	 * @return void
	 */
	public LoadImageApp() {
		/*
       try {
           img = ImageIO.read(new File("/Users/Anuj/Desktop/sample.jpg"));
       } catch (IOException e) {
       }
		 */
	}

	/**
	 * Creates an instance of the LoadImageApp class with a specified filepath to an image.
	 * 
	 * @param filepath the file path of an image to view
	 * @return void
	 */
	public LoadImageApp(String filepath) {
		try {
			img = ImageIO.read(new File(filepath));
		} catch (IOException e) {
		}
	}

	/**
	 * Creates an instance (and runs the instance) of the LoadImageApp class with a specified BufferedImage.
	 * 
	 * @param bufferedimg The BufferedImage to view
	 * @return void
	 */
	public LoadImageApp(BufferedImage bufferedimg) {
		img = bufferedimg;
		Applet();
	}

	/**
	 * Creates an instance (and runs the instance) of the LoadImageApp class with a specified BufferedImage and title.
	 * 
	 * @param bufferedimg the BufferedImage to view
	 * @param title the title of the figure that shows the image
	 * @return void
	 */
	public LoadImageApp(BufferedImage bufferedimg, String title) {
		img = bufferedimg;
		this.title = title;
		Applet();
	}

	/**
	 * Returns the dimensions of the applet to view the image
	 * 
	 * @return the dimension object that stores the dimension of the image to view
	 */
	public Dimension getPreferredSize() {
		if (img == null) {
			return new Dimension(100,100);
		} else {
			return new Dimension(img.getWidth(), img.getHeight());
		}
	}

	/**
	 * Sets the title of the applet.
	 * 
	 * @param t the title of hte applet
	 * @return void
	 */
	public void setTitle(String t) {
		title = t;
	}

	/**
	 * The applet that shows the image.
	 * 
	 * @return void
	 */
	public void Applet() {
		// Create new frame
		JFrame f = new JFrame(title);

		// Create component listener object for applet resizing
		f.addComponentListener(new ComponentListener() {

			@Override
			public void componentHidden(ComponentEvent arg0) {}

			@Override
			public void componentMoved(ComponentEvent arg0) {}

			// Allow the resizing of the window without creating any zoom on an image
			@Override
			public void componentResized(ComponentEvent arg0) {

				int W = img.getWidth();  
				int H = img.getHeight();  
				Rectangle b = arg0.getComponent().getBounds();
				arg0.getComponent().setBounds(b.x, b.y, b.width, b.width*H/W);
			}

			@Override
			public void componentShown(ComponentEvent arg0) {}

		});

		// Create window listener to show the applet
		f.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});

		// Run applet
		f.add(this);
		f.pack();
		f.setVisible(true);
	}

	// Main class for testing
	public static void main(String[] args) {
		/*

        JFrame f = new JFrame("Load Image Sample");

        f.addWindowListener(new WindowAdapter(){
                public void windowClosing(WindowEvent e) {
                    System.exit(0);
                }
            });

        f.add(new LoadImageApp());
        f.pack();
        f.setVisible(true);

		 */
	}
}
