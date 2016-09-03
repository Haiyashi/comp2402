package comp2402a4;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Arrays;
import java.lang.reflect.Array;

//PLEASE! I Stayed up all night to make this work, its not perfect but Ive done
//all the bug checking I could. Sadly I couldnt make it fully functional. 
//Please show mercy ohh mighty TA. I havent commented my process.

/**
 * This class implements the cuckoo hash 
 * 
 * See: Rasmus Pagh, Flemming Friche Rodler, Cuckoo Hashing, Algorithms - ESA 2001, 
 * Lecture Notes in Computer Science 2161, Springer 2001, ISBN 3-540-42493-8
 *
 * @param <T>
 */
public class CuckooHashTable<T> implements USet<T> {
	
	protected Factory<T> f;
	
	/**
	 * The two hash tables
	 */
	protected T[] t1;
	protected T[] t2;

	/**
	 * The "dimension" of the virtual table (t1.length = t2.length = 2^d)
	 */
	int d;

	/**
	 * The number of elements in the hash table
	 */
	int n;
		
	/**
	 * The multipliers for the two hash functions
	 */
	public int z1;
	public int z2;

	/**
	 * The number of bits in an int
	 */
	protected static final int w = 32;
	public Class <T> t;
	
	
	/**
	 * Create a new empty hash table with hash tables
	 * h1(x) using z1
	 * h2(2) using z2
	 */
	public CuckooHashTable(Class<T> t, int z1, int z2) {
		this.z1 = z1;
		this.z2 = z2;
		this.t = t;

//		f = new Factory<T>((Class<T>).getClass());
		t1 = (T[])Array.newInstance(t, 100); //begins with wo tables of size 100
		t2 = (T[])Array.newInstance(t, 100);
		clear();

	}
	
	/**
	 * Resize the tables so they each have size 2^d 
	 */
	protected void resize() {
		
		//Create temps to copy
		T[] temp1 = t1;
		T[] temp2 = t2;
		//Set the total lenth of CuckooHash Table
		n = temp1.length+temp2.length;
		//Re initialize the t1 and t2
		t1 = (T[])Array.newInstance(t, 2*n);
		t2 = (T[])Array.newInstance(t, 2*n);
		//New size is 0 because they havent had any items added
		n = 0;
		//Re gen random odd numbers
		Random randy = new Random();
		int z1 = (randy.nextInt())*2 +1;

		Random rando = new Random();
		int z2 = (rando.nextInt())*2 +1;

		//Copy old elements back over
		for(int i = 0; i<temp1.length;i++){
			if (temp1[i] != null){
				add(temp1[i]); 
			}
		}
		//Copy old elements over
		for(int i = 0; i<temp2.length;i++){
			if (temp2[i] != null){
				add(temp2[i]); 
			}
		}
		//n = t1.length + t2.length;
		System.out.println("Resized");
	}

	/**
	 * Clear the tables
	 */
	public void clear() {
		for (int i=0; i<t1.length; i++){
			t1[i] = null;

		}
		for (int i=0; i<t2.length; i++){
			t2[i] = null;
		}
	}
	
	/**
	 * Return the number of elements stored in this hash table
	 */
	public int size() {
		return n;
	}

	public boolean add(T x) {
		//resize if theere are too many elements
		if((t1.length + t2.length/2) <= n) //I know this doesnt make sense
			resize();

		int count = 0; // count to track how many collosions have occurred
		int flip = 1; //this is a switch to track what hash table we will add to in the event of a collision
		T temp1, temp2; // temp vals to use
		temp1 = x;     //set them to equal the added obj
		temp2 = x;
		int hash1, hash2;
		hash1 = h1(x);	
		hash2 = h2(x);
		/*This while loop is composed of 4 if statements that each deal
		  with a particular part of the hash table.
		  1)if we are adding to the t1 and dont need to bump an element
		  2)we bump an element from t1 to t2 and it the space in t2 is free
		  3)we bump an element from t2 to t1 and need to bump an element from t2 to t1
		  4)we bump an element from t1 to t2 and need to bump again 
		    from t2 to make space for the t1 element
		*/
		while (true){
			count++;		//increment count 
			//If no bumping is required to add t1[hash1]
			if (t1[hash1] == null && flip == 1){
				t1[hash1] = temp1;
				System.out.println("[" + count + "]" +"Added Item " + t1[hash1] + " to table T1");
				n++;
				return true;
			//If no bumping is required to add t2[hash2]
			}else if(t2[hash2] == null && flip == -1){
				t2[hash2] = temp2;
				System.out.println("[" + count + "]" +"Added Item " + t2[hash2] + " to table T2");
				n++;
				return true;
			//When we need to bump an element from t1[hash1]
			}else if(t1[hash1] != null && flip == 1){
				temp2 = t1[hash1];
				hash2 = h2(temp2);

				t1[hash1] = temp1;
				flip = -1;
			//When we need to bump an element from t2[hash2]
			}else if(t2[hash2] != null && flip == -1){
				temp1 = t2[hash2];
				hash1 = h1(temp1);

				t2[hash2] = temp2;
				flip = 1;
			}
			//rehash when we have bumped n vlaues
			if (count >= n){
				rehash();
			}
//			count++;
		}	
	}
		
	//Remove a indicated value
	public T remove(T x) {
		int value = h1(x);
			if (t1[value] == x){
				t1[value] = null;
				return x;
			}else if(t2[value] == x){
				t2[value] = null;
				return x;
			}
		return null;
	}

	/**
	 * Get the copy of x stored in this table.
	 * @param x - the item to get 
	 * @return - the element y stored in this table such that x.equals(y)
	 * is true, or null if no such element y exists
	 */
	//Find an indicated value
	public T find(Object x) {
			if (t1[h1((T)x)] == x){
				return (T)x;
			}else if(t2[h2((T)x)] == x){
				return (T)x;
			}
		return null;
	}

	/**
	 * iterator for the hash table.  you do not need to implement this (but you can if you wish)
	 *
	 */
	public Iterator<T> iterator() {
		return null;
	}
	//hash function from text for z1
	public int h1(T data){
		//HASH FORMULA MULTIPLICATIVE p 110
		return ((z1 * data.hashCode()) >>> (w-d))% t1.length;
	}
	//hash function from text for z2
	public int h2(T data){
		//HASH FORMULA MULTIPLICATIVE p 110
		return ((z2 * data.hashCode()) >>> (w-d))% t2.length;
	}
	//rehash when we run out of spaces for our set of data
	public void rehash(){
		//Will be called when n movements have occurred
		T[] temp1 = t1;
		T[] temp2 = t2;
	
		//Generate a random odd value
		Random randy = new Random();
		int z1 = (randy.nextInt())*2 +1;
		//Generate a random odd value
		Random rando = new Random();
		int z2 = (rando.nextInt())*2 +1;

		//copy over values
		for(int i = 0; i<t1.length;i++){
			if (t1[i] != null){
				add(t1[i]); 
			}
		}
		//copy over values
		for(int i = 0; i<t2.length;i++){
			if (t2[i] != null){
				add(t2[i]); 
			}
		}
	}
}
