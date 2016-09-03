package comp2402a5;

import java.util.AbstractQueue;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Random;

/**
 * This class implements a priority queue as a class binary heap
 * stored implicitly in an array
 * @author morin
 *
 * @param <T>
 */
public class DaryHeap<T> extends BinaryHeap<T> {
	
	/**
	 * The number value of d
	 */
	protected int d;

	/**
	 * Create a new empty binary heap
	 * @param clz
	 */
	public DaryHeap(Class<T> clz, int d) {
		super(clz, new DefaultComparator<T>());
		this.d = d;
	}

	public DaryHeap(Class<T> clz, Comparator<T> c0, int d) {
		super(clz, c0);
		this.d = d;
	}


	/**
	 * returns the value of d
	 */
	public int valueOfD(){
		return d;
	}


	/**
	 * Retrieves the index in the array of the parent of the value at index i
	 *
	 * @param i
	 * @return the index of the parent of the value at index i
	 */
	@Override 
	public int parent(int i) {
		return (int) Math.floor(i-1)/d;  //Find parent from child
	}
	
	
	/**
	 * Retrieves the index of the jth child, 0 <= j < d, of the value at index i
	 * 
	 * @param i
	 * @param j
	 * @return the index of the jth child value at index i
	 */
	public int child(int i, int j) {
		return i*d + j + 1; //Find child from parent indice i and jth child
	}
	
	
	/**
	 *
	 * You need to override some of the BinaryHeap methods here
	 * (in particular, add and remove)
	 */
	public boolean add(T x) {
		if (n + 1 > a.length) resize();
		a[n++] = x;
		bubbleUp(n-1);
		return true;
	}

	public T remove() {
		T x = a[0];
		a[0] = a[--n];
		trickleDown(0);
		if (3*n < a.length) resize();
		return x;
	}

	protected void bubbleUp(int i) {
		int p = parent(i);
		while (i > 0 && c.compare(a[i], a[p]) < 0) {
			swap(i,p);
			i = p;
			p = parent(i);
		}
	}

	protected void trickleDown(int i) {
		do {
			int j = -1;
			int smallest = child(i,0); //Set smallest = to the leftmost child of i
			for(int m = 0;m<d;m++){
				if (child(i, m) < n && c.compare(a[child(i,m)], a[smallest]) < 0) //ensure the child is in n elements and it is smaller then smallest
					smallest = child(i,m); //save new smallest child
			}
			if (smallest < n && c.compare(a[smallest], a[i]) < 0) { //if the smallest child is in n elements and it is smaller then the parent
				j = smallest; //replace j with smallest
			}
			if (j >= 0)	swap(i, j);	//if j was replaced swap with i
				i = j;				//set i to j so the while loop continues
		} while (i >= 0);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// create a 3-ary heap
		BinaryHeap<Integer> b = new BinaryHeap<Integer>(Integer.class);
		DaryHeap<Integer> h2 = new DaryHeap<Integer>(Integer.class, 2);
		DaryHeap<Integer> h3 = new DaryHeap<Integer>(Integer.class, 3);
		DaryHeap<Integer> h5 = new DaryHeap<Integer>(Integer.class, 5);
		DaryHeap<Integer> h10 = new DaryHeap<Integer>(Integer.class, 10);
		double time2, time3, time5, time10, btime;
		time2 = time3 = time5 = time10 = btime = 0;
		int trials = 20;
		Random r = new Random();
		double stop = 0;
		double start = 0;
		int n = 0;
		
		//Part 1 of testing
		for (int j = 0; j<trials; j++){
			//Timing adding 262144 elements 2 children
			n = 262144;
			start = System.nanoTime();
			for (int i = 0; i < n; i++) {
				h2.add(r.nextInt(2500));
			}
			stop = System.nanoTime();
			time2 += 1e-9*(stop-start);

			//Timing adding 262144 elements	3 children
			start = System.nanoTime();
			for (int i = 0; i < n; i++) {
				h3.add(r.nextInt(2500));
			}
			stop = System.nanoTime();
			time3 += 1e-9*(stop-start);

			//Timing adding 262144 elements 5 children
			start = System.nanoTime();
			for (int i = 0; i < n; i++) {
				h5.add(r.nextInt(2500));
			}
			stop = System.nanoTime();
			time5 += 1e-9*(stop-start);

			//Timing adding 262144 elements 10 children
			start = System.nanoTime();
			for (int i = 0; i < n; i++) {
				h10.add(r.nextInt(2500));
			}
			stop = System.nanoTime();
			time10 += 1e-9*(stop-start);

			//Timing adding 262144 elements binary heap
			start = System.nanoTime();
			for (int i = 0; i < n; i++) {
				b.add(r.nextInt(2500));
			}
			stop = System.nanoTime();
			btime += 1e-9*(stop-start);

			b = new BinaryHeap<Integer>(Integer.class);
			h2 = new DaryHeap<Integer>(Integer.class, 2);
			h3 = new DaryHeap<Integer>(Integer.class, 3);
			h5 = new DaryHeap<Integer>(Integer.class, 5);
			h10 = new DaryHeap<Integer>(Integer.class, 10);
		}
			System.out.println("performing " + n + " adds  to 2 children Dary Heap took:\n");
			System.out.println(time2/trials + "[per trial]\n");

			System.out.println("performing " + n + " adds  to 3 children Dary Heap took:\n");
			System.out.println(time3/trials + "[per trial]\n");

			System.out.println("performing " + n + " adds  to 5 children Dary Heap took:\n");
			System.out.println(time5/trials + "[per trial]\n");

			System.out.println("performing " + n + " adds  to 10 children Dary Heap took:\n");
			System.out.println(time10/trials + "[per trial]\n");

			System.out.println("performing " + n + " adds  to Binary Heap took:\n");
			System.out.println(btime/trials + "[per trial]\n");

		time2 = time3 = time5 = time10 = btime = 0;


		//Part 2 of testing 
		for(int j = 0; j<trials;j++){
			//Do adds and removes for 2 children
			n = 262144;
			
			for (int i = 0; i < n; i++) {
				h2.add(r.nextInt(2500));
			}

			start = System.nanoTime();
			for (int i = 0; i < n; i++) {
				if (r.nextBoolean()) {
					h2.add(r.nextInt());
				} else {
					h2.remove();
				}
			}
			stop = System.nanoTime();
			time2 += 1e-9*(stop-start);

			//Do adds and removes for 3 children
			for (int i = 0; i < n; i++) {
				h3.add(r.nextInt(2500));
			}

			start = System.nanoTime();
			for (int i = 0; i < n; i++) {
				if (r.nextBoolean()) {
					h3.add(r.nextInt());
				} else {
					h3.remove();
				}
			}
			stop = System.nanoTime();
			time3 += 1e-9*(stop-start);

			//Do adds and removes for 5 children
			for (int i = 0; i < n; i++) {
				h5.add(r.nextInt(2500));
			}

			start = System.nanoTime();
			for (int i = 0; i < n; i++) {
				if (r.nextBoolean()) {
					h5.add(r.nextInt());
				} else {
					h5.remove();
				}
			}
			stop = System.nanoTime();
			time5 += 1e-9*(stop-start);

			//Do adds and removes for 10 children
			for (int i = 0; i < n; i++) {
				h10.add(r.nextInt(2500));
			}

			start = System.nanoTime();
			for (int i = 0; i < n; i++) {
				if (r.nextBoolean()) {
					h10.add(r.nextInt());
				} else {
					h10.remove();
				}
			}
			stop = System.nanoTime();
			time10 += 1e-9*(stop-start);

			//Do adds and removes for binary heap
			
			for (int i = 0; i < n; i++) {
				b.add(r.nextInt(2500));
			}

			start = System.nanoTime();
			for (int i = 0; i < n; i++) {
				if (r.nextBoolean()) {
					b.add(r.nextInt());
				} else {
					b.remove();
				}
			}
			stop = System.nanoTime();
			btime += 1e-9*(stop-start);

			b = new BinaryHeap<Integer>(Integer.class);
			h2 = new DaryHeap<Integer>(Integer.class, 2);
			h3 = new DaryHeap<Integer>(Integer.class, 3);
			h5 = new DaryHeap<Integer>(Integer.class, 5);
			h10 = new DaryHeap<Integer>(Integer.class, 10);

		}
		System.out.println("performing " + n/2 + " adds and " + n/2 + " removes to 2 children Dary Heap took:\n");
		System.out.println(time2/trials + "[per trial]\n");

		System.out.println("performing " + n/2 + " adds and " + n/2 + " removes to 3 children Dary Heap took:\n");
		System.out.println(time3/trials + "[per trial]\n");
		
		System.out.println("performing " + n/2 + " adds and " + n/2 + " removes to 5 children Dary Heap took:\n");
		System.out.println(time5/trials + "[per trial]\n");
		
		System.out.println("performing " + n/2 + " adds and " + n/2 + " removes to 10 children Dary Heap took:\n");
		System.out.println(time10/trials + "[per trial]\n");

		System.out.println("performing " + n/2 + " adds and " + n/2 + " removes to Binary Heap took:\n");
		System.out.println(btime/trials + "[per trial]\n");



		//Part 3 of testing
		System.out.println("We will now test it is a priority queue:\n");
		n = 15;
		for (int i = 0; i < n; i++) {
			h5.add(r.nextInt(2500));
		}
		System.out.println(h5);
		for(int i = 0; i< n ; i++){
			h5.remove();
			System.out.println(h5);
		}

		System.out.println("We see it has properly prioritised the higheest priority item.");



	}

}
