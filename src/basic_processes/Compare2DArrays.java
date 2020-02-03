package basic_processes;


/**
* The CompareThree2DArrays Class checks if three arrays have equal values.
* This is for Compare2DArrays if an RGB image is a BW image.
*
* @author  Anuj Gajjar
* @version 1.0
* @since   Summer 2019
*/
public class Compare2DArrays {
	
	/**
	* Constructor that initiates an instance of the Compare2DArrays class.
	*
	* @return void
	*/
	public Compare2DArrays() {}
	
	/**
	* Checks if two 2D arrays are equal.
	*
	* @param arr1 the first 2-D array
	* @param arr2 the second 2-D array
	* @return true if all arrays equal, false otherwise 
	*/
	public boolean twoArrayComparision(int[][] arr1, int[][] arr2) {
		// Get the sizes of the arrays
		int size1x = arr1.length;
		int size1y = arr1[0].length;
		int size2x = arr2.length;
		int size2y = arr2[0].length;
		
		// If the sizes are equal proceed to do comparision
		if(size1x == size2x && size1y == size2y) {
			// Mantain variable to keep on checking if the array is equal
			boolean isEqual = true;
			
			// Loop through all array elements in btoh arrays
			for(int i = 0; i < size1x; i++) {
				for(int j = 0; j < size1y; j++) {
					// Check if the array values are equal
					if(arr1[i][j] == arr2[i][j]) {
						isEqual = true;
					}
					else {
						// If the array values are not equal set isEqual to false and break loop
						isEqual = false;
						break;
					}
				}
			}
			return isEqual;
		}
		else {
			return false;
		}
	}
	
	/**
	* Checks if three 2D arrays are equal.
	*
	* @param arr1 the first 2-D array
	* @param arr2 the second 2-D array
	* @param arr3 the third 2-D array
	* @return true if all arrays equal, false otherwise 
	*/
	public boolean threeArrayComparision(int[][] arr1, int[][] arr2, int arr3[][]) {
		// Compare arr 1 and arr2, arr1 and arr3, and arr2 and arr3 to do 3D comparision
		boolean comp1 = twoArrayComparision(arr1, arr2);
		boolean comp2 = twoArrayComparision(arr1, arr3);
		boolean comp3 = twoArrayComparision(arr2, arr3);
		
		// If all comparisons are true, then return true, else false
		if(comp1 == comp2 == comp3)
			return true;
		else 
			return false;
	}
	
}
