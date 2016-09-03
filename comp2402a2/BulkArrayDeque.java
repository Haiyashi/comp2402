package comp2402a2;

import java.util.Collection;
import java.util.Collections;
import java.util.Deque;
import java.util.List;
import java.util.Random;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;


public class BulkArrayDeque<T> extends ArrayDeque<T> {
	
	public BulkArrayDeque(Class<T> clazz) {
		super(clazz);
	}
	
	/**
	 * Remove all the elements with indices that belong to the range [i, j)
	 * @param i the starting index
	 * @param c the stopping index
	 */
	public void removeRange(int x, int y) {
		int rangeVal = y - x;

		if (x < n/2) {
    		for (int k = y - 1; k > 0; k--){
    			if (j+k-rangeVal >=0){
      				a[(j+k)%a.length] = a[(j+k-rangeVal)%a.length];
       			}
			}
     		j = (j + rangeVal) % a.length;

		}else{
     		for (int k = x; k < n-1; k++){
     			if (j+k+rangeVal <= n-1){
     				a[(j+k)%a.length] = a[(j+k+rangeVal)%a.length];
     			}
     		}
     	}
     	n -= rangeVal;
     	if (3*n < a.length) resize();
 		}
	
	/**
	 * testing method
	 */
	public static void doIt(BufferedReader r, PrintWriter w){
		//
		// testing code for your BulkArrayDeque 
		// 
		
		
	}
	
	
	/**
	 * The driver.  Open a BufferedReader and a PrintWriter, either from System.in
	 * and System.out or from filenames specified on the command line, then call doIt.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			BufferedReader r;
			PrintWriter w;
			if (args.length == 0) {
				r = new BufferedReader(new InputStreamReader(System.in));
				w = new PrintWriter(System.out);
			} else if (args.length == 1) {
				r = new BufferedReader(new FileReader(args[0]));
				w = new PrintWriter(System.out);				
			} else {
				r = new BufferedReader(new FileReader(args[0]));
				w = new PrintWriter(new FileWriter(args[1]));
			}
			long start = System.nanoTime();
			doIt(r, w);
			w.flush();
			long stop = System.nanoTime();
			System.err.println("Execution time: " + 10e-9 * (stop-start));
		} catch (IOException e) {
			System.err.println(e);
			System.exit(-1);
		}
	}
}
