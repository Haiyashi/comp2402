package comp2402a3;

import java.util.AbstractSequentialList;
import java.util.ListIterator;

/**
 * An implementation of the Deque interface as a doubly-linked circular list 
 * of blocks of size b 
 * 
 * You must add code to the addFirst, addLast, removeFirst, and removeLast 
 * methods so that these operations all have constant amortized runtime
 * (independent of the block size b)
 *
 * @param <T>  the type of elements stored in the list
 */
public class SEListDeque<T> extends SEList<T> {

	/** 
	 *  Creates a SEListDeque object with block size b
	 *
	 * @param b the block size of the BDeques in the nodes
	 * @param c the class (type) of data being stored in the deque
	 */	
	public SEListDeque(int b, Class<T> c) {
		super(b, c);
	}

	
	/** 
	 * add to the front of the deque
	 *
	 * @param x the element to add to the front of the deque
	 */
	public void addFirst(T x) {
		//Assign first node name first
		Node first = dummy.next;
		//if the first node doesnt exist or is full a new first node is made
		if (first == dummy || first.d.size() == b+1)
			first = addBefore(first);
		//We add 'x' to the first position of the new first node
		first.d.add(0,x);
	}

	/** 
	 * add to the back of the deque
	 *
	 * @param x the element to add to the back of the deque
	 */
	public void addLast(T x) {
		//Assign name 'last' to the last node (dummy previous)
		Node last = dummy.prev;
		//If the last node doesnt exist or is full a new las node is made
		if (last.d.size() == b+1 || last == dummy)
			last = addBefore(dummy);
		//We add 'x' to the last position of the new last node
		last.d.add( last.d.size(), x);	
	}

	
	/** 
	 * remove an item from the front of the deque
	 */
	public T removeFirst() {
		//Assign name 'first' to furst node
		Node first = dummy.next;
		//if the fisrt node doesnt exist then throw an exception
		if (first == dummy) 
			throw new IndexOutOfBoundsException();
		//remove from te first element of 'first' and store the value
		T x = first.d.remove(0);
		//if that node is empty remove it 
		if (first.d.size() ==0)
			remove(first);
		//return the removed value 'x'
		return x;
	}

	/** 
	 * remove an item from the back of the deque
	 */
	public T removeLast() {
		//
		Node last = dummy.prev;
		//If the last node doesnt exist then throw exception
		if (last == dummy)
			throw new IndexOutOfBoundsException();
		//remove the last postion of the last node
		T x = last.d.remove(last.d.size()-1);
		//if the last node is empty remove it 
		if (last.d.size() ==0)
			remove(last);
		//return the removed 'x' value
		return x;
	}
	
	public static void main(String[] args) {
		
		int n = 12;
		int b = 4;
		
		SEListDeque<Integer> sedeque = new SEListDeque<Integer>(b, Integer.class);
		
		for (int i = 0; i < 10; i++) {
			sedeque.addFirst(i);
		}
		System.out.println(sedeque);
		for (int i = 0; i < 10; i++) {
			sedeque.addLast(i);
		}		
		System.out.println(sedeque);
		for (int i = 0; i < 5; i++) {
			sedeque.removeFirst();
		}
		System.out.println(sedeque);
		for (int i = 0; i < 5; i++) {
			sedeque.removeLast();
		}		
		System.out.println(sedeque);

	}
}
