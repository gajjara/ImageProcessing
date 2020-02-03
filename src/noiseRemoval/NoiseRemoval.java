package noiseRemoval;

import java.awt.Color;
import java.awt.image.BufferedImage;
import src.ImageProcess;

public class NoiseRemoval extends ImageProcess {
	public NoiseRemoval(BufferedImage in) {
		setFields(in);
		Operation();
	}

	public BufferedImage Operation() {
		BufferedImage imageout = new BufferedImage(w,h,BufferedImage.TYPE_INT_RGB);
		
		//Median Filter
		int[][] r_m = new int[w][h];
		int[][] g_m = new int[w][h];
		int[][] b_m = new int[w][h];

		for(int i = 0; i < (w - 2); i++) {
			for(int j = 0; j < (h - 2); j++) {
				int[][] windowred = new int[3][3];
				int[] windowred1D = new int[3*3];
				int[][] windowgreen = new int[3][3];
				int[] windowgreen1D = new int[3*3];
				int[][] windowblue = new int[3][3];
				int[] windowblue1D = new int[3*3];
				
				for(int h = 0; h < 3; h++) {
					for(int k = 0; k < 3; k++) {
						windowred[h][k] = r[i + h][j + k];
						windowred1D[h*3 + k] = windowred[h][k];
						
						windowgreen[h][k] = g[i + h][j + k];
						windowgreen1D[h*3 + k] = windowgreen[h][k];
						
						windowblue[h][k] = b[i + h][j + k];
						windowblue1D[h*3 + k] = windowblue[h][k];
					}
				}
				sort(windowred1D, 0, windowred1D.length - 1);
				sort(windowgreen1D, 0, windowgreen1D.length - 1);
				sort(windowblue1D, 0, windowblue1D.length - 1);

				int medianred;
				if (windowred1D.length % 2 == 0)
				    medianred = (windowred1D[windowred1D.length/2] + windowred1D[windowred1D.length/2 - 1])/2;
				else
				    medianred = windowred1D[windowred1D.length/2];
				for(int h = 0; h < 3; h++) {
					for(int k = 0; k < 3; k++) {
						r_m[i + h][j + k] = medianred;
					}
				}
				
				int mediangreen;
				if (windowgreen1D.length % 2 == 0)
				    mediangreen = (windowgreen1D[windowgreen1D.length/2] + windowgreen1D[windowgreen1D.length/2 - 1])/2;
				else
				    mediangreen = windowgreen1D[windowgreen1D.length/2];
				for(int h = 0; h < 3; h++) {
					for(int k = 0; k < 3; k++) {
						g_m[i + h][j + k] = mediangreen;
					}
				}
				
				int medianblue;
				if (windowblue1D.length % 2 == 0)
				    medianblue = (windowblue1D[windowblue1D.length/2] + windowblue1D[windowblue1D.length/2 - 1])/2;
				else
				    medianblue = windowblue1D[windowblue1D.length/2];
				for(int h = 0; h < 3; h++) {
					for(int k = 0; k < 3; k++) {
						b_m[i + h][j + k] = medianblue;
					}
				}
				
			}
		}
		
		for(int i = 0; i < w; i++) {
			for(int j = 0; j < h; j++) {
				Color c = new Color(r_m[i][j], g_m[i][j], b_m[i][j]);
				imageout.setRGB(i, j, c.getRGB());
			}
		}
		
			
		output = imageout;
		return output;
	}
	
    // Merges two subarrays of arr[]. 
    // First subarray is arr[l..m] 
    // Second subarray is arr[m+1..r] 
    private void merge(int arr[], int l, int m, int r) 
    { 
        // Find sizes of two subarrays to be merged 
        int n1 = m - l + 1; 
        int n2 = r - m; 
  
        /* Create temp arrays */
        int L[] = new int [n1]; 
        int R[] = new int [n2]; 
  
        /*Copy data to temp arrays*/
        for (int i=0; i<n1; ++i) 
            L[i] = arr[l + i]; 
        for (int j=0; j<n2; ++j) 
            R[j] = arr[m + 1+ j]; 
  
  
        /* Merge the temp arrays */
  
        // Initial indexes of first and second subarrays 
        int i = 0, j = 0; 
  
        // Initial index of merged subarry array 
        int k = l; 
        while (i < n1 && j < n2) 
        { 
            if (L[i] <= R[j]) 
            { 
                arr[k] = L[i]; 
                i++; 
            } 
            else
            { 
                arr[k] = R[j]; 
                j++; 
            } 
            k++; 
        } 
  
        /* Copy remaining elements of L[] if any */
        while (i < n1) 
        { 
            arr[k] = L[i]; 
            i++; 
            k++; 
        } 
  
        /* Copy remaining elements of R[] if any */
        while (j < n2) 
        { 
            arr[k] = R[j]; 
            j++; 
            k++; 
        } 
    } 
  
    // Main function that sorts arr[l..r] using 
    // merge() 
    private void sort(int arr[], int l, int r) 
    { 
        if (l < r) 
        { 
            // Find the middle point 
            int m = (l+r)/2; 
  
            // Sort first and second halves 
            sort(arr, l, m); 
            sort(arr , m+1, r); 
  
            // Merge the sorted halves 
            merge(arr, l, m, r); 
        } 
    } 


}
