package basic_processes;

/**
* The Array2DPrint Class, prints x and y amount of elements in a 2D array.
*
* @author  Anuj Gajjar
* @version 1.0
* @since   Summer 2019
*/
public class Array2DPrint {
	
	/**
	* Constructor that initiates an instance of the Array2DPrint class.
	*
	* @return void
	*/
	public Array2DPrint() {}
	
	/**
	* Prints out a 2D array, with an x and y amount of elements printed.
	*
	* @param i the 2-D array to print
	* @param endln if true and endln is printed after printing the array
	* @param elementsX the number of rows to print from row 0 to elementsX
	* @param elementsY the number of columns to print from column 0 to elementsY
	* @return void
	*/
	public void print(int[][] i, boolean endln, int elementsX, int elementsY) {
		for(int c = 0; c < elementsX; c++) {
			for(int d = 0; d < elementsY; d++) {
				// Print out element
				System.out.print(i[c][d]);
				System.out.print("\t");
			}
			System.out.println();
		}
		// Print an endln after the array has printed
		if(endln == true) {
			System.out.println();
		}
	}
}
