package comp2402a3;

import java.util.AbstractQueue;
import java.util.Iterator;
import java.util.Queue;
import java.util.Stack;


/**
 * An implementation of a Stack that also can efficiently return  
 * the minimum element in the current Stack.  Every operation -
 * push, pop, peek, and min - must run in constant time
 *
 * @param <T> the class of objects stored in the queue
 */
public class MinStack<T extends Comparable<T>> extends SLListStack<T> {
	//initialize new stack to keep track of minimum element
	Stack<T> minStack = new Stack<T>();

	//Overide Push
	public T push(T x){
		//Sourced from SLListStack push function
		Node u = new Node();
		u.x = x;

		//Will track new min values in minStack
		if (minStack.empty() || (minStack.peek()).compareTo(x) >= 0)
			minStack.push(x);

		//Sourced from SLListStack
		u.next = head;
		head = u;
		if (n == 0) 
			tail = u;
		n++;
		return x;
	}

	//Overide Pop
	public T pop() {
		//If empty return
		if (n == 0) 
			return null;

		//Sourced from SLListStack
		T x = head.x;
		head = head.next;         
		if (--n == 0) 
			tail = null;

		//Will Pop from minStack if it is popping the min value
		 if (!minStack.empty() && x == minStack.peek())
			return minStack.pop();
		
		//Return pop val
		return x;
	}

	public T min(){
		//Base Case: if empty return null
		if (n == 0) return null;
		//Will return the top of the minStack if stack is not empty
		else 
			return minStack.peek();
	}

	public static void main(String[] args){
		
		MinStack<Character> stack = new MinStack<Character>();
		
		String datasequence = "JKLMNFGHICDEBAA";
		for (int i = 0; i < datasequence.length(); i++){
			stack.push(datasequence.charAt(i));
			System.out.println(stack + ", min = " + stack.min());
		}
		while(stack.size() > 0) {
			stack.pop();
			System.out.println(stack + ", min = " + stack.min());
		}
	}

}