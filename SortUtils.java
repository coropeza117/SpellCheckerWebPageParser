package edu.uwm.cs351.util;


import java.util.Comparator;


/**
 * @author * CHRISTIAN OROPEZA CS-351 ...RECIEVED HELP FROM BOOK, CS LIBRARY TUTORS, ONLINE CS TUTOR, AND ADVICE FROM FRIENDS ON HOW TO APPROACH FIXING PERSISTENT BUGS.
 * COLLABORATORS: JOSHUA KNIGHT, JULLIAN MURENO, BIJAY PANTA, JIAHUI YANG , MIGUEL GARCIA, MARAWAN SALAMA, ESTELLE BRADY (WHILE IN TUTORING LIBRARY SECTION) BUT NO CODE WAS SHARED.
 * Online Sources: merge & merge sort - https://www.geeksforgeeks.org/iterative-merge-sort/
 * 										https://www.digitalocean.com/community/tutorials/merge-sort-algorithm-java-c-python
 * 					  merge sort keep - https://www.geeksforgeeks.org/merge-sort/
 * 
 * 				   
 */


/**
 * An class with utility code for sorting and using sorted arrays.
 * @param K type of keys
 */
public class SortUtils<T>
{
	private Comparator<T> comparator;
	
	/**
	 * Create an instance using the given comparator.  If it is null, use
	 * natural ordering assuming the type is comparable (`compareTo`).
	 * @param c comparator, or if null, use natural ordering
	 */
	@SuppressWarnings("unchecked")
	public SortUtils(Comparator<T> c) 
	{
		if (c == null)	c = (o1,o2) -> ((Comparable<T>)o1).compareTo(o2);
		
		comparator = c;
	}
	
	/** merge sort the elements of the array
	 * @param array must not be null
	 * 1. Create a temporary array for use in the merge step
	 * 2. Call the recursive mergeSortKeep helper method
	 */
	public void mergeSort(T[] array) 
	{
		if(array == null)	throw new NullPointerException();

		@SuppressWarnings("unchecked")
		T[] tempArr = (T[]) new Object[array.length];

		mergeSortKeep(0, tempArr.length, array, tempArr);	
	}
	
	/** Merge sort "in" array between lo and hi and put results in "out".
	 * The "in" array will be used for scratch in the same range.
	 * @param lo >= 0
	 * @param hi >= lo
	 * @param in must not be null
	 * @param out must not be null or same as in
	 * 1. return nothing if parameters are not the correct size or array's are null
	 * 2. declare and initialize variable that add lo & hi together and divide by 2 to get midpoint of the range 
	 * 3. recursive calls - Sort the two subarrays using the mergeSortKeep method for [lo, mid) & [mid, hi) bounds while passing array parameters
	 * 4. call recursive merge method using all 3 bound (lo, mid, hi) & both array parameters
	 */
	public void mergeSortMove(int lo, int hi, T[] in, T[] out) 
	{
		if(lo < 0 || lo >= hi)	return;

		if(in == null || out == null)	return;

		if(hi - lo <= 1)	out[lo] = in[lo];

		int mid = (lo + hi) / 2;

		mergeSortKeep(lo, mid, in, out);
		mergeSortKeep(mid, hi, in, out);

		merge(lo, mid, hi, in, out);
	}

	/** merge sort "in" array between lo and hi in place using temp array for scratch
	 * @param lo >= 0
	 * @param hi >= lo
	 * @param data must not be null, length >= hi
	 * @param temp must not be null or same as data, length >= hi
	 * 1. If the range [lo, hi) contains more than one element, then it can be divided into two subarrays, if equal or less than 1 simply return.
	 * 2. int mid - Compute the midpoint of the range
	 * 3. recursive calls - Sort the two subarrays using the mergeSortKeep method for [lo, mid) & [mid, hi)
	 * 4. call recursive merge method using all 3 bound (lo, mid, hi) & both array parameters
	 * 5. use standard while loop with condition "if hi is greater than lo" tcopy the sorted elements 
	 *    from the temp array back into the data array while incrementing lo bound
	 */
	public void mergeSortKeep(int lo, int hi, T[] data, T[] temp) 
	{
		if(lo < 0 || hi < lo)	return;
		if(data == null || data.length < hi)	return;
		if(temp == null || temp == data || data.length < hi)	return;
		if(hi - lo <= 1) return;

		int mid = (lo + hi) / 2;

		mergeSortKeep(lo, mid, data, temp);
		mergeSortKeep(mid, hi, data, temp);

		merge(lo, mid, hi, data, temp);

		while(hi > lo)	data[lo] = temp[lo++];
	}

	/** merge sorted lists in [lo,mid) and [mid,hi) in "in" into [lo,hi) in "out".
	 * @param lo >= 0
	 * @param mid >= lo
	 * @param hi >= mid
	 * @param in must not be null, length >= hi
	 * @param out must not be null or same as in, length >= hi
	 * Find sizes of two subarrays to be merged
	 * 1. Initialize two pointers to the start of the two subarrays
	 * 2. for loop conditions - Iterate over the range [lo, hi) in the output array
	 * 3. if inside for loop - If the pointer to the first subarray is past the end of the subarray,
	 *    or if the second pointer is not past the end of the subarray and
	 *    the element at the second pointer is smaller than the element at the
	 *    first pointer, then the next smallest element comes from the second subarray
	 * 4. else inside for loop - Otherwise, the next smallest element comes from the first subarray
	 */
	public void merge(int lo, int mid, int hi, T[] in, T[] out) 
	{
		int tempLo = lo, tempMid = mid;

		for(int temp = lo; temp < hi; ++temp) 
		{	        
			if(tempLo == mid || (tempMid < hi && comparator.compare(in[tempLo], in[tempMid]) > 0))	out[temp] = in[tempMid++];	

			else if(tempLo != mid || (tempMid < hi && comparator.compare(in[tempLo], in[tempMid]) <= 0))	out[temp] = in[tempLo++];	
		}
	}
	
	/** Write elements from sorted array in range [lo1,hi1)
	 * into out [lo1,...] as long as they
	 * don't occur in sorted array rem in range [lo2,hi2).  
	 * The result (out) will also be sorted.  
	 * @param lo1 >= 0
	 * @param hi1 >= lo1
	 * @param lo2 >= 0
	 * @param hi2 >= lo2
	 * @param in must not be null, length >= hi1
	 * @param rem must not be null, length >= hi2
	 * @param out must not be null, length >= hi1.
	 * The array out may be the same as the in, but not the same as rem.
	 * @return the index after the last element added into out.
	 * 1. declare and initialize int variables for lo1, & lo2 to be stored an used/updated later
	 * 2. while i (lo1) is < hi1 && j (lo2) < hi2 we loop and enter conditions
	 * 3. if array in[i] is less than array rem[j] then in[i] is not in rem, so we add it to out
	 * 4. else if array in[i] is less than array rem[j] then in[i] occurs in rem, so it is skipped
	 * 5. otherwise or else we know in[i] is greater than rem[j], so move to the next element in rem
	 * 6. lastly we have a while loop - while i is < hi1 only we add the remaining elements from array in to out.
	 * 7. return lo1 bound
	 */
	public int difference(int lo1, int hi1, int lo2, int hi2, T[] in, T[] rem, T[] out) 
	{
		int i = lo1, j = lo2, k = lo1;

		while (i < hi1 && j < hi2) 
		{
			if (comparator.compare(in[i], rem[j]) < 0)	out[k++] = in[i++];

			else if (comparator.compare(in[i], rem[j]) == 0)	++i;

			else	++j;
		}

		while (i < hi1)	out[k++] = in[i++];

		return k;
	}
	
	/**
	 * Remove duplicate elements (ones with 0 comparison)
	 * from a sorted array.  If the array is not sorted,
	 * it won't necessarily work correctly.
	 * @param array must not be null
	 * @return number of unique elements.
	 * 1. if array is null return 0 as a base case
	 * 2. Declare and initialize int's for number of unique elements an i to use in while loop
	 * 3. declare and initialize a generic temp variable set to null to compare in while loop
	 * 4. enter while loop while i is less than length of array being passed
	 * 5. if condition is met create another temp uniqElem variable and store elements found in array to variable 
	 * 6. if uniqElem is not equal temp then we increment number of elements & set temp variable to number of items in array being passed
	 * 7. for every loop in while increment number of elements 
	 * 8. finally return the number of elements
	 */
	public int uniq(T[] array) 
	{
		if (array == null)	return 0;

		int numElements = 0, i = 0;
		
		T temp = null;

		while (i < array.length) 
		{
			T uniqElem = array[i];

			if (!(uniqElem.equals(temp))) 
			{
				++numElements;
				temp = uniqElem;
			}

			++i;
		}

		return numElements;
	}
	
	/**
	 * Remove duplicate elements (one with 0 comparison)
	 * from a sorted array range [lo,hi), writing the unique elements to
	 * the second array [lo,...).
	 * @param lo >= 0
	 * @param hi >= lo
	 * @param in must not be null
	 * @param out must not be null.  This array may be the same as in without problems.
	 * @return index after unique elements
	 * 1. return 0 if parameters are not the correct size or array's are null
	 * 2. Declare and initialize int's to lo bound to be returned and used in while loop argument
	 * 3. Declare and initialize T generic current variable and set to null
	 * 4. while i is less than hi enter while loop
	 * 5. if i is equal to lo or array in using lo bound is not equal to current store the ints in in array to out array and increment index
	 *    next store the in array integers inside of the current variable
	 * 6. each time we loop in the while loop we increment i & then outside of the while loop we finally return the index
	 */
	public int uniq(int lo, int hi, T[] in, T[] out) 
	{
		if (in == null || out == null) return 0;

		if (lo < 0 || hi < lo || hi > in.length) return 0;

		int index = lo, i = lo;

		T current = null;

		while (i < hi) 
		{
			if (i == lo || comparator.compare(in[i], current) != 0) 
			{
				out[index++] = in[i];
				current = in[i];
			}

			++i;
		}

		return index;
	}
}
